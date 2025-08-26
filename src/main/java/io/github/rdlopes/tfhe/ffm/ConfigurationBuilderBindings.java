package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class ConfigurationBuilderBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ConfigurationBuilderBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void initialize(MemorySegment address) {
    logger.trace("initialize - address: {}", address);
    executeWithErrorHandling(() -> config_builder_default(address));
  }

  public static void build(MemorySegment address, MemorySegment configAddress) {
    logger.trace("build - address: {}, configAddress: {}", address, configAddress);
    executeWithErrorHandling(() -> config_builder_build(addressValue(address), configAddress));
  }

  public static void clone(MemorySegment address, MemorySegment cloneAddress) {
    logger.trace("clone - address: {}, cloneAddress: {}", address, cloneAddress);
    executeWithErrorHandling(() -> config_builder_clone(addressValue(address), cloneAddress));
  }

  public static void useCustomParameters(MemorySegment address, MemorySegment parametersAddress) {
    logger.trace("useCustomParameters - address: {}, parametersAddress: {}", address, parametersAddress);
    executeWithErrorHandling(() -> config_builder_use_custom_parameters(address, parametersAddress));
  }

  public static void useDedicatedCompactPublicKeyParameters(MemorySegment address, MemorySegment compactPublicKeyParametersAddress) {
    logger.trace("useDedicatedCompactPublicKeyParameters - address: {}, compactPublicKeyParametersAddress: {}", address, compactPublicKeyParametersAddress);
    executeWithErrorHandling(() -> use_dedicated_compact_public_key_parameters(address, compactPublicKeyParametersAddress));
  }

  public static void enableCompression(MemorySegment address, MemorySegment compressionParametersAddress) {
    logger.trace("enableCompression - address: {}, compressionParametersAddress: {}", address, compressionParametersAddress);
    executeWithErrorHandling(() -> config_builder_enable_compression(address, compressionParametersAddress));
  }
}
