import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/// Bootstraps jextract and generates Panama FFM Java bindings from the tfhe-rs C header.
///
/// Run directly via: `java scripts/GenerateBindings.java [options]`
///
/// Options:
/// - `--header <path>` — path to tfhe.h (auto-detected if omitted)
/// - `--output <path>` — bindings output directory (default: `native-bundle/bindings`)
public class GenerateBindings {

    private static final String JEXTRACT_BASE_URL = "https://download.java.net/java/early_access/jextract/25/2/openjdk-25-jextract+2-4_";

    private static final System.Logger LOG = System.getLogger(GenerateBindings.class.getName());

    public static void main(String[] args) {
        try {
            Path customHeader = null;
            Path outputDir = Path.of("native-bundle", "bindings");

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--header" when (i + 1 < args.length) -> { customHeader = Path.of(args[++i]); }
                    case "--output" when (i + 1 < args.length) -> { outputDir = Path.of(args[++i]); }
                    case "--help", "-h" -> { printHelp(); return; }
                }
            }

            LOG.log(System.Logger.Level.INFO, "Generating FFM Java bindings...");

            Path jextractExec = bootstrapJextract();
            Path headerFile = locateHeader(customHeader);
            generateBindings(jextractExec, headerFile, outputDir);

            LOG.log(System.Logger.Level.INFO, "Bindings generated successfully into {0}", outputDir.toAbsolutePath());
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "GENERATION FAILED: " + e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void printHelp() {
        LOG.log(System.Logger.Level.INFO, """
                Usage: java scripts/GenerateBindings.java [options]
                Options:
                  --header <path>   Path to tfhe.h header file (auto-detected if omitted)
                  --output <path>   Bindings output directory (default: native-bundle/bindings)
                  --help, -h        Print this help message
                """);
    }

    // --- jextract bootstrap ---

    private static Path bootstrapJextract() throws Exception {
        Path cacheDir = Path.of("native-bundle", "jextract");
        Path jextractBinDir = cacheDir.resolve("jextract-25").resolve("bin");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        Path jextractExec = jextractBinDir.resolve(isWindows ? "jextract.bat" : "jextract");

        if (Files.exists(jextractExec)) {
            LOG.log(System.Logger.Level.INFO, "Found cached jextract at: {0}", jextractExec.toAbsolutePath());
            return jextractExec;
        }

        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        String platform;
        if (osName.contains("win")) platform = "windows-x64";
        else if (osName.contains("mac")) platform = (osArch.contains("aarch64") || osArch.contains("arm64")) ? "macos-aarch64" : "macos-x64";
        else if (osName.contains("nux")) platform = "linux-x64";
        else throw new UnsupportedOperationException("Unsupported OS: " + osName);

        String downloadUrl = JEXTRACT_BASE_URL + platform + "_bin.tar.gz";
        Path downloadTarget = cacheDir.resolve("download").resolve("jextract.tar.gz");

        LOG.log(System.Logger.Level.INFO, "Bootstrapping jextract ({0})...", platform);
        downloadFile(downloadUrl, downloadTarget);
        extractTarGz(downloadTarget, cacheDir);

        // macOS: clear quarantine flag
        if (osName.contains("mac")) {
            try {
                run(cacheDir, "xattr", "-r", "-d", "com.apple.quarantine", "jextract-25");
            } catch (Exception e) {
                LOG.log(System.Logger.Level.WARNING, "Failed to clear macOS quarantine: {0}", e.getMessage());
            }
        }

        // Unix: set executable permission
        if (!isWindows) {
            try {
                run(jextractBinDir, "chmod", "+x", "jextract");
            } catch (Exception e) {
                LOG.log(System.Logger.Level.WARNING, "Failed to set executable permission: {0}", e.getMessage());
            }
        }

        if (Files.exists(jextractExec)) {
            LOG.log(System.Logger.Level.INFO, "Successfully bootstrapped jextract!");
            return jextractExec;
        }
        throw new FileNotFoundException("jextract binary not found at " + jextractExec.toAbsolutePath());
    }

    // --- Header location ---

    private static Path locateHeader(Path customHeader) throws FileNotFoundException {
        if (customHeader != null) {
            if (Files.exists(customHeader)) return customHeader;
            throw new FileNotFoundException("Custom header not found: " + customHeader.toAbsolutePath());
        }

        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        String targetOs = osName.contains("win") ? "windows" : (osName.contains("mac") ? "osx" : "linux");
        String targetArch = (osArch.contains("aarch64") || osArch.contains("arm64")) ? "aarch_64" : "x86_64";

        List<Path> candidates = List.of(
                Path.of("native-bundle", "libs", targetOs, targetArch, "tfhe.h"),
                Path.of("native-bundle", "tfhe-rs", "target", "release", "tfhe.h")
        );

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                LOG.log(System.Logger.Level.INFO, "Found header: {0}", candidate.toAbsolutePath());
                return candidate;
            }
        }

        throw new FileNotFoundException(
                "Could not locate tfhe.h. Searched:\n" +
                candidates.stream().map(p -> "  - " + p.toAbsolutePath()).collect(Collectors.joining("\n")));
    }

    private static String getClangTargetTriple() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        boolean isArm = osArch.contains("aarch64") || osArch.contains("arm64");

        if (osName.contains("win")) {
            return "x86_64-pc-windows-msvc";
        } else if (osName.contains("mac")) {
            return isArm ? "arm64-apple-macosx" : "x86_64-apple-darwin";
        } else if (osName.contains("nux")) {
            return isArm ? "aarch64-unknown-linux-gnu" : "x86_64-unknown-linux-gnu";
        }
        return null;
    }

    // --- Binding generation ---

    private static void generateBindings(Path jextractExec, Path headerFile, Path outputDir) throws Exception {
        Files.createDirectories(outputDir);

        Path workingDir = headerFile.getParent();
        Path rawIncludes = workingDir.resolve("jextract-includes.txt");
        Path filteredIncludes = workingDir.resolve("jextract-includes-filtered.txt");
        Path compileFlagsFile = workingDir.resolve("compile_flags.txt");

        try {
            // Write compile_flags.txt with clang target triple
            String targetTriple = getClangTargetTriple();
            if (targetTriple != null) {
                Files.write(compileFlagsFile, List.of("-target", targetTriple));
            }

            // 1. Dump includes
            LOG.log(System.Logger.Level.INFO, "Dumping includes list...");
            run(workingDir,
                    jextractExec.toAbsolutePath().toString(),
                    headerFile.getFileName().toString(),
                    "--dump-includes", rawIncludes.getFileName().toString());

            // 2. Filter to tfhe-related includes only
            LOG.log(System.Logger.Level.INFO, "Filtering includes...");
            List<String> filteredLines = Files.readAllLines(rawIncludes).stream()
                    .filter(line -> line.contains("tfhe"))
                    .toList();
            Files.write(filteredIncludes, filteredLines);
            LOG.log(System.Logger.Level.INFO, "Filtered {0} includes from {1} total",
                    filteredLines.size(), Files.readAllLines(rawIncludes).size());

            // 3. Generate FFM Java bindings
            LOG.log(System.Logger.Level.INFO, "Running jextract...");
            run(workingDir,
                    jextractExec.toAbsolutePath().toString(),
                    "@" + filteredIncludes.getFileName().toString(),
                    headerFile.getFileName().toString(),
                    "--header-class-name", "TfheHeader",
                    "--target-package", "io.github.rdlopes.tfhe.core.ffm",
                    "--output", outputDir.toAbsolutePath().toString());

            LOG.log(System.Logger.Level.INFO, "Java bindings generated in: {0}", outputDir.toAbsolutePath());
        } finally {
            // Clean up temporary files
            Files.deleteIfExists(rawIncludes);
            Files.deleteIfExists(filteredIncludes);
            Files.deleteIfExists(compileFlagsFile);
        }
    }

    // --- Utilities ---

    private static void downloadFile(String urlStr, Path targetPath) throws Exception {
        LOG.log(System.Logger.Level.INFO, "Downloading {0}", urlStr);
        Files.createDirectories(targetPath.getParent());
        HttpResponse<Path> response = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build()
                .send(HttpRequest.newBuilder().uri(URI.create(urlStr)).build(),
                        HttpResponse.BodyHandlers.ofFile(targetPath));
        if (response.statusCode() != 200) {
            throw new IOException("Download failed (status %d): %s".formatted(response.statusCode(), urlStr));
        }
    }

    private static void extractTarGz(Path tarGzFile, Path destDir) throws Exception {
        LOG.log(System.Logger.Level.INFO, "Extracting {0}", tarGzFile);
        Files.createDirectories(destDir);
        run(destDir, "tar", "-xzf", tarGzFile.toAbsolutePath().toString());
    }

    private static void run(Path workingDir, String... command) throws Exception {
        int exitCode = new ProcessBuilder(command)
                .directory(workingDir.toFile())
                .inheritIO()
                .start()
                .waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed (exit %d): %s".formatted(exitCode, String.join(" ", command)));
        }
    }
}
