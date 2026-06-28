import java.io.*;
import java.nio.file.*;
import java.util.*;

/// Initializes the native libraries bundle by cloning tfhe-rs, building with Cargo,
/// copying compiled libraries, and generating Java FFM bindings.
///
/// This script reproduces locally the same `native-bundle/` layout as the CI
/// `native.yaml` workflow, enabling a seamless local development experience.
///
/// Run from the project root: `java scripts/InitializeNativeLibraries.java`
public class InitializeNativeLibraries {

    private static final String TFHE_RS_REPO = "https://github.com/zama-ai/tfhe-rs.git";
    private static final String TFHE_RS_COMMIT = "407fa762bce85df9f5e4485c8573672a005d3c6a";

    private static final Path NATIVE_BUNDLE = Path.of("native-bundle");
    private static final Path TFHE_RS_DIR = NATIVE_BUNDLE.resolve("tfhe-rs");
    private static final Path LIBS_DIR = NATIVE_BUNDLE.resolve("libs");

    private static final System.Logger LOG = System.getLogger(InitializeNativeLibraries.class.getName());

    public static void main(String[] args) {
        try {
            for (String arg : args) {
                if ("--help".equals(arg) || "-h".equals(arg)) {
                    printHelp();
                    return;
                }
            }

            LOG.log(System.Logger.Level.INFO, "Initializing native libraries bundle...");

            verifyToolsInstalled();
            cloneTfheRs();
            buildWithCargo();
            copyLibraries();
            generateBindings();

            LOG.log(System.Logger.Level.INFO, "Native libraries bundle initialized successfully.");
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "INITIALIZATION FAILED: " + e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void printHelp() {
        LOG.log(System.Logger.Level.INFO, """
                Usage: java scripts/InitializeNativeLibraries.java [options]

                Initializes the native-bundle/ directory with compiled tfhe-rs libraries
                and generated Java FFM bindings, matching the CI workflow layout.

                Options:
                  --help, -h   Print this help message
                """);
    }

    // --- Prerequisites ---

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

    // --- Clone ---

    private static void cloneTfheRs() throws Exception {
        if (!Files.exists(TFHE_RS_DIR)) {
            LOG.log(System.Logger.Level.INFO, "Cloning tfhe-rs repository...");
            Files.createDirectories(TFHE_RS_DIR.getParent());
            run(TFHE_RS_DIR.getParent(), "git", "clone", TFHE_RS_REPO, "tfhe-rs");
        } else {
            LOG.log(System.Logger.Level.INFO, "tfhe-rs repository already cloned. Resetting working tree...");
            run(TFHE_RS_DIR, "git", "reset", "--hard");
        }

        LOG.log(System.Logger.Level.INFO, "Checking out pinned commit: {0}", TFHE_RS_COMMIT);
        run(TFHE_RS_DIR, "git", "checkout", TFHE_RS_COMMIT);
    }

    // --- Build ---

    private static void buildWithCargo() throws Exception {
        LOG.log(System.Logger.Level.INFO, "Building tfhe-rs C API (profile: release)...");
        run(TFHE_RS_DIR,
                "cargo", "+nightly-2026-04-22", "build",
                "--profile", "release",
                "--features=boolean-c-api,shortint-c-api,high-level-c-api,zk-pok,experimental-force_fft_algo_dif4",
                "-p", "tfhe");
    }

    // --- Platform detection ---

    private static String normalizedOs() {
        String name = System.getProperty("os.name").toLowerCase();
        if (name.contains("mac")) return "osx";
        if (name.contains("win")) return "windows";
        if (name.contains("nux")) return "linux";
        throw new UnsupportedOperationException("Unsupported OS: " + name);
    }

    private static String normalizedArch() {
        String arch = System.getProperty("os.arch").toLowerCase();
        if (arch.contains("aarch64") || arch.contains("arm64")) return "aarch_64";
        if (arch.contains("amd64") || arch.contains("x86_64")) return "x86_64";
        throw new UnsupportedOperationException("Unsupported architecture: " + arch);
    }

    // --- Copy libraries ---

    private static void copyLibraries() throws Exception {
        LOG.log(System.Logger.Level.INFO, "Copying native libraries...");

        Path destDir = LIBS_DIR.resolve(normalizedOs()).resolve(normalizedArch());
        Files.createDirectories(destDir);

        Path releaseDir = TFHE_RS_DIR.resolve("target").resolve("release");

        // Copy header
        copyIfExists(releaseDir.resolve("tfhe.h"), destDir.resolve("tfhe.h"));

        // Copy LICENSE
        Path licenseSrc = TFHE_RS_DIR.resolve("tfhe").resolve("LICENSE");
        if (Files.exists(licenseSrc)) {
            Files.copy(licenseSrc, LIBS_DIR.resolve("LICENSE"), StandardCopyOption.REPLACE_EXISTING);
        }

        // Copy shared libraries and debug symbols
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(releaseDir)) {
            for (Path entry : stream) {
                String name = entry.getFileName().toString();
                if (name.contains("dangling")) continue;

                if (name.endsWith(".dll") || name.endsWith(".so") || name.endsWith(".dylib") || name.endsWith(".pdb")) {
                    Files.copy(entry, destDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                    LOG.log(System.Logger.Level.INFO, "  Copied {0}", name);
                }
            }
        }

        LOG.log(System.Logger.Level.INFO, "Libraries copied to {0}", destDir);
    }

    // --- Generate bindings ---

    private static void generateBindings() throws Exception {
        LOG.log(System.Logger.Level.INFO, "Generating Java FFM bindings...");

        Path headerFile = LIBS_DIR.resolve(normalizedOs()).resolve(normalizedArch()).resolve("tfhe.h");
        Path bindingsDir = NATIVE_BUNDLE.resolve("bindings");

        run(Path.of("."),
                "java", "scripts/GenerateBindings.java",
                "--header", headerFile.toString(),
                "--output", bindingsDir.toString());
    }

    // --- Utilities ---

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
