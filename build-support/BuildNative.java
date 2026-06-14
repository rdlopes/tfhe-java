import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Cross-platform script to build tfhe-rs C API libraries and generate Panama FFM bindings.
 * Run directly via: java build-support/BuildNative.java [args]
 */
public class BuildNative {

    private static final String JEXTRACT_VERSION = "25+2-4";
    private static final String JEXTRACT_BASE_URL = "https://download.java.net/java/early_access/jextract/25/2/openjdk-25-jextract+2-4_";
    private static final String TFHE_RS_REPO = "https://github.com/zama-ai/tfhe-rs.git";
    private static final String TFHE_RS_COMMIT = "407fa762bce85df9f5e4485c8573672a005d3c6a";

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BuildNative.class.getName());

    static {
        // Reset standard logging to clear default console handler
        java.util.logging.LogManager.getLogManager().reset();
        java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
        java.util.logging.ConsoleHandler handler = new java.util.logging.ConsoleHandler();
        handler.setFormatter(new java.util.logging.Formatter() {
            @Override
            public String format(java.util.logging.LogRecord record) {
                return record.getMessage() + System.lineSeparator();
            }
        });
        rootLogger.addHandler(handler);
    }

    private static void log(String msg) {
        LOGGER.info(msg);
    }

    private static void logError(String msg) {
        LOGGER.severe(msg);
    }

    public static void main(String[] args) {
        try {
            boolean onlyBindings = false;
            Path customHeader = null;
            Path outputDir = Path.of(".native", "bindings");
            Path libsDir = Path.of(".native", "libs");

            for (int i = 0; i < args.length; i++) {
                if ("--only-bindings".equals(args[i])) {
                    onlyBindings = true;
                } else if ("--header".equals(args[i]) && i + 1 < args.length) {
                    customHeader = Path.of(args[++i]);
                } else if ("--output".equals(args[i]) && i + 1 < args.length) {
                    outputDir = Path.of(args[++i]);
                } else if ("--libs-dir".equals(args[i]) && i + 1 < args.length) {
                    libsDir = Path.of(args[++i]);
                } else if ("--help".equals(args[i]) || "-h".equals(args[i])) {
                    printHelp();
                    return;
                }
            }

            log("Starting TFHE-Java native build/generation phase...");
            log("Mode: " + (onlyBindings ? "Only Bindings" : "Full Build"));

            // 1. Detect platform and bootstrap jextract
            Path jextractExec = bootstrapJextract();

            // 2. Clone and build tfhe-rs from source (if not onlyBindings)
            Path tfheRsDir = Path.of(".native", "tfhe-rs");
            if (!onlyBindings) {
                verifyToolsInstalled();
                setupTfheRsRepo(tfheRsDir);
                buildNativeLibrary(tfheRsDir);
            }

            // 3. Locate the C header file
            Path headerFile = locateHeader(customHeader, tfheRsDir);
            log("Using C header file: " + headerFile.toAbsolutePath());

            // 4. Generate the bindings
            generateBindings(jextractExec, headerFile, outputDir);

            // 5. Copy libraries to native libs folder (if not onlyBindings)
            if (!onlyBindings) {
                copyNativeLibraries(tfheRsDir, libsDir);
            }

            log("Initialization and generation completed successfully!");

        } catch (Exception e) {
            logError("BUILD FAILED: " + e.getMessage());
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            logError(sw.toString());
            System.exit(1);
        }
    }

    private static void printHelp() {
        log("Usage: java build-support/BuildNative.java [options]");
        log("Options:");
        log("  --only-bindings     Generate Panama FFM Java bindings from existing headers. Skip rust compilation.");
        log("  --header <path>     Specify custom path to tfhe.h header file.");
        log("  --output <path>     Specify bindings output directory (default: .native/bindings).");
        log("  --libs-dir <path>   Specify native libraries copy destination (default: .native/libs).");
        log("  --help, -h          Print this help message.");
    }

    private static void verifyToolsInstalled() throws Exception {
        try {
            runCommandQuietly("git", "--version");
        } catch (Exception e) {
            throw new RuntimeException("Git is not installed or not on PATH. Git is required to clone tfhe-rs.");
        }
        try {
            runCommandQuietly("cargo", "--version");
        } catch (Exception e) {
            throw new RuntimeException("Rust/Cargo is not installed or not on PATH. Cargo is required to build tfhe-rs.");
        }
    }

    private static Path bootstrapJextract() throws Exception {
        Path cacheDir = Path.of(".jextract");
        Path jextractBinDir = cacheDir.resolve("jextract-25").resolve("bin");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        Path jextractExec = jextractBinDir.resolve(isWindows ? "jextract.bat" : "jextract");

        if (Files.exists(jextractExec)) {
            log("Found cached jextract at: " + jextractExec.toAbsolutePath());
            return jextractExec;
        }

        // Detect platform
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        String platform = "";
        String extension = "";

        if (osName.contains("win")) {
            platform = "windows-x64";
            extension = ".tar.gz";
        } else if (osName.contains("mac")) {
            if (osArch.contains("aarch64") || osArch.contains("arm64")) {
                platform = "macos-aarch64";
            } else {
                platform = "macos-x64";
            }
            extension = ".tar.gz";
        } else if (osName.contains("nux")) {
            platform = "linux-x64";
            extension = ".tar.gz";
        } else {
            throw new UnsupportedOperationException("Unsupported OS for jextract bootstrapping: " + osName);
        }

        String downloadUrl = JEXTRACT_BASE_URL + platform + "_bin" + extension;
        Path downloadTarget = cacheDir.resolve("download").resolve("jextract" + extension);

        log("jextract not found locally. Bootstrapping " + platform + " build...");
        downloadFile(downloadUrl, downloadTarget);

        if (extension.equals(".zip")) {
            extractZip(downloadTarget, cacheDir);
        } else {
            extractTarGz(downloadTarget, cacheDir);
            // On macOS, we need to clear the quarantine flag
            if (osName.contains("mac")) {
                try {
                    log("Removing quarantine flag from jextract on macOS...");
                    runCommand(cacheDir.toFile(), "xattr", "-r", "-d", "com.apple.quarantine", "jextract-25");
                } catch (Exception e) {
                    logError("Warning: failed to clear macOS quarantine attribute: " + e.getMessage());
                }
            }
        }

        // Set execute permissions for unix
        if (!isWindows) {
            try {
                runCommand(jextractBinDir.toFile(), "chmod", "+x", "jextract");
            } catch (Exception e) {
                logError("Warning: failed to set executable permission on jextract: " + e.getMessage());
            }
        }

        if (Files.exists(jextractExec)) {
            log("Successfully bootstrapped jextract!");
            return jextractExec;
        } else {
            throw new FileNotFoundException("Expected jextract binary at " + jextractExec.toAbsolutePath() + " but it was not found after extraction.");
        }
    }

    private static void downloadFile(String urlStr, Path targetPath) throws Exception {
        log("Downloading " + urlStr + " -> " + targetPath.toAbsolutePath());
        Files.createDirectories(targetPath.getParent());
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(targetPath));
        if (response.statusCode() != 200) {
            throw new IOException("Failed to download jextract from " + urlStr + ", status: " + response.statusCode());
        }
    }

    private static void extractZip(Path zipFile, Path destDir) throws Exception {
        log("Extracting ZIP " + zipFile + " -> " + destDir.toAbsolutePath());
        Files.createDirectories(destDir);
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(Files.newInputStream(zipFile)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path entryPath = destDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(entryPath))) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            os.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    private static void extractTarGz(Path tarGzFile, Path destDir) throws Exception {
        log("Extracting TAR.GZ " + tarGzFile + " -> " + destDir.toAbsolutePath());
        Files.createDirectories(destDir);
        runCommand(destDir.toFile(), "tar", "-xzf", tarGzFile.toAbsolutePath().toString());
    }

    private static void setupTfheRsRepo(Path tfheRsDir) throws Exception {
        if (!Files.exists(tfheRsDir)) {
            log("Cloning tfhe-rs repository...");
            Files.createDirectories(tfheRsDir.getParent());
            runCommand(Path.of(".native").toFile(), "git", "clone", TFHE_RS_REPO, "tfhe-rs");
        } else {
            log("tfhe-rs repository already cloned.");
        }

        log("Checking out pinned commit: " + TFHE_RS_COMMIT);
        runCommand(tfheRsDir.toFile(), "git", "checkout", TFHE_RS_COMMIT);
    }

    private static void buildNativeLibrary(Path tfheRsDir) throws Exception {
        log("Building tfhe-rs C API library from source (profile: release)...");
        // We use cargo directly instead of 'make build_c_api' to ensure compatibility with Windows without make
        runCommand(tfheRsDir.toFile(),
                "cargo",
                "+nightly-2026-04-22",
                "build",
                "--profile", "release",
                "--features=boolean-c-api,shortint-c-api,high-level-c-api,zk-pok,experimental-force_fft_algo_dif4",
                "-p", "tfhe");
    }

    private static Path locateHeader(Path customHeader, Path tfheRsDir) throws FileNotFoundException {
        if (customHeader != null) {
            if (Files.exists(customHeader)) {
                return customHeader;
            }
            throw new FileNotFoundException("Custom header not found: " + customHeader.toAbsolutePath());
        }

        // Try standard locations
        Path[] candidates = {
                tfheRsDir.resolve("target").resolve("release").resolve("tfhe.h"),
                Path.of("native-libs", "tfhe.h"),
                Path.of(".native", "libs", "tfhe.h")
        };

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }

        throw new FileNotFoundException("Could not locate tfhe.h. Please compile the Rust library or specify it via --header.");
    }

    private static void generateBindings(Path jextractExec, Path headerFile, Path outputDir) throws Exception {
        log("Generating FFM Java bindings from C header...");
        Files.createDirectories(outputDir);

        Path workingDir = headerFile.getParent();
        Path rawIncludes = workingDir.resolve("jextract-includes.txt");
        Path filteredIncludes = workingDir.resolve("jextract-includes-filtered.txt");

        // 1. Dump includes
        log("Dumping includes list...");
        runCommand(workingDir.toFile(),
                jextractExec.toAbsolutePath().toString(),
                headerFile.getFileName().toString(),
                "--dump-includes",
                rawIncludes.getFileName().toString()
        );

        // 2. Filter includes in Java (replaces shell grep)
        log("Filtering includes list (replaces grep)...");
        List<String> filteredLines = Files.readAllLines(rawIncludes).stream()
                .filter(line -> line.contains("tfhe"))
                .collect(Collectors.toList());
        Files.write(filteredIncludes, filteredLines);
        log("Filtered " + filteredLines.size() + " lines out of " + Files.readAllLines(rawIncludes).size());

        // 3. Generate bindings
        log("Running jextract to generate FFM Java code...");
        runCommand(workingDir.toFile(),
                jextractExec.toAbsolutePath().toString(),
                "@" + filteredIncludes.getFileName().toString(),
                headerFile.getFileName().toString(),
                "--header-class-name", "TfheHeader",
                "--target-package", "io.github.rdlopes.tfhe.ffm",
                "--output", outputDir.toAbsolutePath().toString()
        );
        log("Java bindings generated in: " + outputDir.toAbsolutePath());
    }

    private static void copyNativeLibraries(Path tfheRsDir, Path libsDir) throws Exception {
        log("Copying native libraries to bundle folder...");

        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        String targetOsDir;
        String targetArchDir;

        // Map OS
        if (osName.contains("win")) {
            targetOsDir = "windows";
        } else if (osName.contains("mac")) {
            targetOsDir = "osx";
        } else if (osName.contains("nux")) {
            targetOsDir = "linux";
        } else {
            throw new UnsupportedOperationException("Unsupported OS for library copying: " + osName);
        }

        // Map Arch
        if (osArch.contains("aarch64") || osArch.contains("arm64")) {
            targetArchDir = "aarch_64";
        } else if (osArch.contains("amd64") || osArch.contains("x86_64")) {
            targetArchDir = "x86_64";
        } else {
            throw new UnsupportedOperationException("Unsupported architecture for library copying: " + osArch);
        }

        Path targetDir = libsDir.resolve(targetOsDir).resolve(targetArchDir);
        Files.createDirectories(targetDir);

        Path releaseDir = tfheRsDir.resolve("target").resolve("release");

        // Copy license
        Path licenseSrc = tfheRsDir.resolve("tfhe").resolve("LICENSE");
        Path licenseDest = libsDir.resolve("LICENSE");
        if (Files.exists(licenseSrc)) {
            Files.copy(licenseSrc, licenseDest, StandardCopyOption.REPLACE_EXISTING);
            log("Copied LICENSE to " + licenseDest.toAbsolutePath());
        }

        // Copy header
        Path headerSrc = releaseDir.resolve("tfhe.h");
        Path headerDest = targetDir.resolve("tfhe.h");
        if (Files.exists(headerSrc)) {
            Files.copy(headerSrc, headerDest, StandardCopyOption.REPLACE_EXISTING);
            log("Copied tfhe.h to " + headerDest.toAbsolutePath());
        }

        // Copy binary libraries
        String[] extensions = {".dll", ".so", ".dylib"};
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(releaseDir)) {
            for (Path entry : stream) {
                String filename = entry.getFileName().toString();
                for (String ext : extensions) {
                    if (filename.endsWith(ext) && !filename.contains("dangling")) {
                        Path dest = targetDir.resolve(filename);
                        Files.copy(entry, dest, StandardCopyOption.REPLACE_EXISTING);
                        log("Copied " + filename + " to " + dest.toAbsolutePath());
                    }
                }
            }
        }
    }

    private static void runCommand(File workingDir, String... command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workingDir);
        pb.inheritIO();
        Process p = pb.start();
        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code " + exitCode + ": " + String.join(" ", command));
        }
    }

    private static void runCommandQuietly(String... command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process p = pb.start();
        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code " + exitCode);
        }
    }
}
