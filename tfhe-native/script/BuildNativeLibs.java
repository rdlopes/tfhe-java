import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Builds tfhe-rs native libraries and copies them to the Maven output directory.
 * <p>
 * Run directly via: {@code java script/BuildNativeLibraries.java [options]}
 * <p>
 * Options:
 * <ul>
 *   <li>{@code --output <path>} — destination for native libs (default: target/classes/native)</li>
 * </ul>
 */
public class BuildNativeLibs {

    private static final String TFHE_RS_REPO = "https://github.com/zama-ai/tfhe-rs.git";
    private static final String TFHE_RS_COMMIT = "407fa762bce85df9f5e4485c8573672a005d3c6a";

    private static final System.Logger LOG = System.getLogger(BuildNativeLibs.class.getName());

    public static void main(String[] args) {
        try {
            Path outputDir = Path.of("target", "classes", "native");

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--output" -> { if (i + 1 < args.length) outputDir = Path.of(args[++i]); }
                    case "--help", "-h" -> { printHelp(); return; }
                }
            }

            LOG.log(System.Logger.Level.INFO, "Building tfhe-rs native libraries...");

            verifyToolsInstalled();

            Path tfheRsDir = Path.of("target", "native", "tfhe-rs");
            cloneTfheRs(tfheRsDir);
            buildWithCargo(tfheRsDir);
            copyLibraries(tfheRsDir, outputDir);

            LOG.log(System.Logger.Level.INFO, "Native libraries built successfully into {0}", outputDir.toAbsolutePath());
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "BUILD FAILED: " + e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void printHelp() {
        LOG.log(System.Logger.Level.INFO, """
                Usage: java script/BuildNativeLibs.java [options]
                Options:
                  --output <path>   Destination for native libs (default: target/classes/native)
                  --help, -h        Print this help message
                """);
    }

    private static void verifyToolsInstalled() throws Exception {
        try {
            runQuietly("git", "--version");
        } catch (Exception e) {
            throw new RuntimeException("Git is not installed or not on PATH.");
        }
        try {
            runQuietly("cargo", "--version");
        } catch (Exception e) {
            throw new RuntimeException("Rust/Cargo is not installed or not on PATH.");
        }
    }

    private static void cloneTfheRs(Path tfheRsDir) throws Exception {
        if (!Files.exists(tfheRsDir)) {
            LOG.log(System.Logger.Level.INFO, "Cloning tfhe-rs repository...");
            Files.createDirectories(tfheRsDir.getParent());
            run(tfheRsDir.getParent(), "git", "clone", TFHE_RS_REPO, "tfhe-rs");
        } else {
            LOG.log(System.Logger.Level.INFO, "tfhe-rs repository already cloned. Resetting working tree...");
            run(tfheRsDir, "git", "reset", "--hard");
        }

        LOG.log(System.Logger.Level.INFO, "Checking out pinned commit: {0}", TFHE_RS_COMMIT);
        run(tfheRsDir, "git", "checkout", TFHE_RS_COMMIT);
    }

    private static void buildWithCargo(Path tfheRsDir) throws Exception {
        LOG.log(System.Logger.Level.INFO, "Building tfhe-rs C API (profile: release)...");
        run(tfheRsDir,
                "cargo", "+nightly-2026-04-22", "build",
                "--profile", "release",
                "--features=boolean-c-api,shortint-c-api,high-level-c-api,zk-pok,experimental-force_fft_algo_dif4",
                "-p", "tfhe");
    }

    private static void copyLibraries(Path tfheRsDir, Path outputDir) throws Exception {
        LOG.log(System.Logger.Level.INFO, "Copying native libraries...");

        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        String targetOs;
        if (osName.contains("win")) targetOs = "windows";
        else if (osName.contains("mac")) targetOs = "osx";
        else if (osName.contains("nux")) targetOs = "linux";
        else throw new UnsupportedOperationException("Unsupported OS: " + osName);

        String targetArch;
        if (osArch.contains("aarch64") || osArch.contains("arm64")) targetArch = "aarch_64";
        else if (osArch.contains("amd64") || osArch.contains("x86_64")) targetArch = "x86_64";
        else throw new UnsupportedOperationException("Unsupported arch: " + osArch);

        Path destDir = outputDir.resolve(targetOs).resolve(targetArch);
        Files.createDirectories(destDir);

        Path releaseDir = tfheRsDir.resolve("target").resolve("release");

        // Copy header
        copyIfExists(releaseDir.resolve("tfhe.h"), destDir.resolve("tfhe.h"));

        // Copy license
        Path licenseSrc = tfheRsDir.resolve("tfhe").resolve("LICENSE");
        if (Files.exists(licenseSrc)) {
            Files.copy(licenseSrc, outputDir.resolve("LICENSE"), StandardCopyOption.REPLACE_EXISTING);
        }

        // Copy binary libraries (.dll, .so, .dylib)
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(releaseDir)) {
            for (Path entry : stream) {
                String name = entry.getFileName().toString();
                if (!name.contains("dangling") && (name.endsWith(".dll") || name.endsWith(".so") || name.endsWith(".dylib"))) {
                    Files.copy(entry, destDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                    LOG.log(System.Logger.Level.INFO, "  Copied {0}", name);
                }
            }
        }
    }

    private static void copyIfExists(Path src, Path dest) throws IOException {
        if (Files.exists(src)) {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        }
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

    private static void runQuietly(String... command) throws Exception {
        int exitCode = new ProcessBuilder(command).start().waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code " + exitCode);
        }
    }
}
