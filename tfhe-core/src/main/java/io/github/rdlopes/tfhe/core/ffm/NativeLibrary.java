package io.github.rdlopes.tfhe.core.ffm;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class NativeLibrary {
  private NativeLibrary() {
    // Utility class
  }
  
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
    } catch (LinkageError | SecurityException e) {
      logger.debug("Failed loading TFHE native library from name, message: {}", e.getMessage());
    }

    logger.debug("Loading TFHE native library from path");
    try {
      loadFromPath();
      loaded = true;
    } catch (IOException | LinkageError | SecurityException e) {
      String msg = "Failed to load TFHE native library from both name and classpath path";
      logger.error(msg, e);
      throw new UnsatisfiedLinkError(msg + ": " + e.getMessage());
    }
  }

  private static void loadFromName() {
    logger.trace("loadFromName");
    System.loadLibrary(NAME);
    logger.info("TFHE native library loaded from name");
  }
  
  @SuppressWarnings("java:S5443")
  private static void loadFromPath() throws IOException {
    logger.trace("loadFromPath");

    File temporaryLibraryDirectory = java.nio.file.Files.createTempDirectory("tfhe-native-").toFile();
    FileUtils.forceDeleteOnExit(temporaryLibraryDirectory);
    logger.debug("Temporary directory for TFHE native library: {}", temporaryLibraryDirectory);

    String libraryJarPath = "/native/%s/%s/%s".formatted(osName(), osArch(), System.mapLibraryName(NAME));
    try (InputStream is = NativeLibrary.class.getResourceAsStream(libraryJarPath)) {
      if (is == null) {
        throw new FileNotFoundException("Native library not found in " + libraryJarPath);
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

    String name = System.getProperty("os.name").toLowerCase();
    if (name.contains("mac")) {
      return "osx";
    } else if (name.contains("win")) {
      return "windows";
    } else if (name.contains("nux")) {
      return "linux";
    }
    
    throw new UnsupportedOperationException("Unsupported operating system: " + name);
  }

  private static String osArch() {
    logger.trace("osArch");

    String osArch = System.getProperty("os.arch").toLowerCase();
    if (osArch.contains("aarch64") || osArch.contains("arm64")) {
      return "aarch_64";
    } else if (osArch.contains("amd64") || osArch.contains("x86_64")) {
      return "x86_64";
    }
    
    throw new UnsupportedOperationException("Unsupported architecture: " + osArch);
  }
}
