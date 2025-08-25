package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class TfheConfigurationBuilder extends TfheNativeBase {
  private static final Logger logger = LoggerFactory.getLogger(TfheConfigurationBuilder.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void initialize(MemorySegment pointer) {
    logger.trace("initialize - pointer: {}", pointer);
    checkNativeCall(() -> config_builder_default(pointer));
  }

  public static void build(MemorySegment pointer, MemorySegment configPointer) {
    logger.trace("build - pointer: {}, configPointer: {}", pointer, configPointer);
    checkNativeCall(() -> config_builder_build(TfheMemoryAllocator.pointerValue(pointer), configPointer));
  }

  public static void clone(MemorySegment pointer, MemorySegment clonePointer) {
    logger.trace("clone - pointer: {}, clonePointer: {}", pointer, clonePointer);
    checkNativeCall(() -> config_builder_build(TfheMemoryAllocator.pointerValue(pointer), clonePointer));
  }

  public static void useCustomParameters(MemorySegment pointer, MemorySegment parametersPointer) {
    logger.trace("useCustomParameters - pointer: {}, parametersPointer: {}", pointer, parametersPointer);
    checkNativeCall(() -> config_builder_use_custom_parameters(pointer, parametersPointer));
  }

  public static void useDedicatedCompactPublicKeyParameters(MemorySegment pointer, MemorySegment compactPublicKeyParametersPointer) {
    logger.trace("useDedicatedCompactPublicKeyParameters - pointer: {}, compactPublicKeyParametersPointer: {}", pointer, compactPublicKeyParametersPointer);
    checkNativeCall(() -> use_dedicated_compact_public_key_parameters(pointer, compactPublicKeyParametersPointer));
  }
}
