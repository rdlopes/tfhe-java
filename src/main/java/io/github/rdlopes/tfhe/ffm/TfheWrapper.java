package io.github.rdlopes.tfhe.ffm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative_16.LIBRARY_ARENA;

public class TfheWrapper {
  private static final Logger logger = LoggerFactory.getLogger(TfheWrapper.class);
  private static volatile boolean loaded = false;

  public static MemorySegment createPointer(MemoryLayout layout) {
    logger.trace("createPointer - layout: {}", layout.name());

    return LIBRARY_ARENA.allocate(layout);
  }

  public static MemorySegment createPointer(long byteSize) {
    logger.trace("createPointer - byteSize: {}", byteSize);

    return LIBRARY_ARENA.allocate(byteSize);
  }

  public static MemorySegment createPointer(MemoryLayout layout, long count) {
    logger.trace("createPointer - layout: {}, count: {}", layout.name(), count);

    return LIBRARY_ARENA.allocate(layout, count);
  }

  public static synchronized void loadNativeLibrary() {
    logger.trace("load");

    if (loaded) {
      logger.debug("TFHE native library already loaded");
      return;
    }

    logger.debug("Loading TFHE native library from name");
    try {
      System.loadLibrary("tfhe");
      logger.info("TFHE native library loaded from name");
      loaded = true;
      return;
    } catch (Exception exception) {
      logger.debug("Failed loading TFHE native library from name", exception);
    }

    File temporaryLibraryDirectory;
    logger.debug("Creating temporary library directory");
    try {
      temporaryLibraryDirectory = new File(FileUtils.getTempDirectory(), "tfhe-native-" + System.currentTimeMillis());
      if (!temporaryLibraryDirectory.mkdirs()) {
        throw new IOException("Failed to create temporary directory: " + temporaryLibraryDirectory);
      }
      FileUtils.forceDeleteOnExit(temporaryLibraryDirectory);
      logger.debug("Temporary directory for TFHE native library: {}", temporaryLibraryDirectory);
    } catch (IOException ioException) {
      throw new RuntimeException("Failed creating temporary directory for TFHE native library", ioException);
    }

    String libraryJarPath = buildNativeLibraryJarPath();
    logger.debug("Loading TFHE native library from path {}", libraryJarPath);
    try (InputStream is = TfheWrapper.class.getResourceAsStream(libraryJarPath)) {
      if (is == null) {
        throw new RuntimeException("Native library not found in " + libraryJarPath);
      }
      File libraryFile = new File(temporaryLibraryDirectory, new File(libraryJarPath).getName());
      FileUtils.copyInputStreamToFile(is, libraryFile);
      FileUtils.forceDeleteOnExit(libraryFile);
      logger.debug("Loading TFHE native library from path {}", libraryFile);
      System.load(libraryFile.getAbsolutePath());
      logger.info("TFHE native library loaded from path");
      loaded = true;
    } catch (IOException e) {
      throw new RuntimeException("Failed loading TFHE native library from path " + libraryJarPath, e);
    }
  }

  private static String buildNativeLibraryJarPath() {
    String osPath;
    if (SystemUtils.IS_OS_MAC) {
      osPath = "osx";
    } else if (SystemUtils.IS_OS_WINDOWS) {
      osPath = "windows";
    } else if (SystemUtils.IS_OS_LINUX) {
      osPath = "linux";
    } else {
      throw new RuntimeException("Unsupported operating system: " + SystemUtils.OS_NAME);
    }

    String osArch = SystemUtils.OS_ARCH.toLowerCase();
    String architecturePath;
    if (osArch.contains("aarch64") || osArch.contains("arm64")) {
      architecturePath = "aarch_64";
    } else if (osArch.contains("amd64") || osArch.contains("x86_64")) {
      architecturePath = "x86_64";
    } else {
      throw new RuntimeException("Unsupported architecture: " + osArch);
    }

    return "/native/%s/%s/%s".formatted(osPath, architecturePath, System.mapLibraryName("tfhe"));
  }
}
