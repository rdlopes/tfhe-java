package io.github.rdlopes.tfhe.ffm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class NativeLibrary {
  public static final String NAME = "tfhe";
  private static final Logger logger = LoggerFactory.getLogger(NativeLibrary.class);
  private static volatile boolean loaded = false;

  public static synchronized void load() {
    logger.trace("load");

    if (loaded) {
      logger.debug("TFHE native library already loaded");
      return;
    }

    logger.debug("Loading TFHE native library from name");
    try {
      loadFromName();
      loaded = true;
      return;
    } catch (Throwable throwable) {
      logger.debug("Failed loading TFHE native library from name, message: {}", throwable.getMessage());
    }

    logger.debug("Loading TFHE native library from path");
    try {
      loadFromPath();
      loaded = true;
    } catch (Throwable throwable) {
      logger.debug("Failed loading TFHE native library from path, message: {}", throwable.getMessage());
    }
  }

  private static void loadFromName() {
    logger.trace("loadFromName");
    System.loadLibrary(NAME);
    logger.info("TFHE native library loaded from name");
  }

  private static void loadFromPath() throws IOException {
    logger.trace("loadFromPath");

    File temporaryLibraryDirectory;
    temporaryLibraryDirectory = new File(FileUtils.getTempDirectory(), "tfhe-native-" + System.currentTimeMillis());
    if (!temporaryLibraryDirectory.mkdirs()) {
      throw new IOException("Failed to create temporary directory: " + temporaryLibraryDirectory);
    }
    FileUtils.forceDeleteOnExit(temporaryLibraryDirectory);
    logger.debug("Temporary directory for TFHE native library: {}", temporaryLibraryDirectory);

    String libraryJarPath = "/native/%s/%s/%s".formatted(osName(), osArch(), System.mapLibraryName(NAME));
    try (InputStream is = NativeLibrary.class.getResourceAsStream(libraryJarPath)) {
      if (is == null) {
        throw new RuntimeException("Native library not found in " + libraryJarPath);
      }
      File libraryFile = new File(temporaryLibraryDirectory, new File(libraryJarPath).getName());
      FileUtils.copyInputStreamToFile(is, libraryFile);
      FileUtils.forceDeleteOnExit(libraryFile);
      logger.debug("Loading TFHE native library from path {}", libraryFile);
      System.load(libraryFile.getAbsolutePath());
    }
  }

  private static String osName() {
    logger.trace("osName");

    if (SystemUtils.IS_OS_MAC) {
      return "osx";
    } else if (SystemUtils.IS_OS_WINDOWS) {
      return "windows";
    } else if (SystemUtils.IS_OS_LINUX) {
      return "linux";
    }

    throw new RuntimeException("Unsupported operating system: " + SystemUtils.OS_NAME);
  }

  private static String osArch() {
    logger.trace("osArch");

    String osArch = SystemUtils.OS_ARCH.toLowerCase();
    if (osArch.contains("aarch64") || osArch.contains("arm64")) {
      return "aarch_64";
    } else if (osArch.contains("amd64") || osArch.contains("x86_64")) {
      return "x86_64";
    }

    throw new RuntimeException("Unsupported architecture: " + osArch);
  }
}
