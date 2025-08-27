package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_1.*;

public final class PublicKeyBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(PublicKeyBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void serialize(MemorySegment publicKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("serialize - publicKeyAddress: {}, dynamicBufferAddress: {}", publicKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> public_key_serialize(addressValue(publicKeyAddress), dynamicBufferAddress));
  }

  public static void safeSerialize(MemorySegment publicKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("safeSerialize - publicKeyAddress: {}, dynamicBufferAddress: {}", publicKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> public_key_safe_serialize(addressValue(publicKeyAddress), dynamicBufferAddress, SERDE_MAX_SIZE));
  }

  public static void deserialize(MemorySegment dynamicBufferViewAddress, MemorySegment publicKeyAddress) {
    logger.trace("deserialize - dynamicBufferViewAddress: {}, publicKeyAddress: {}", dynamicBufferViewAddress, publicKeyAddress);
    executeWithErrorHandling(() -> public_key_deserialize(dynamicBufferViewAddress, publicKeyAddress));
  }

  public static void safeDeserialize(MemorySegment dynamicBufferViewAddress, MemorySegment publicKeyAddress) {
    logger.trace("safeDeserialize - dynamicBufferViewAddress: {}, publicKeyAddress: {}", dynamicBufferViewAddress, publicKeyAddress);
    executeWithErrorHandling(() -> public_key_safe_deserialize(dynamicBufferViewAddress, SERDE_MAX_SIZE, publicKeyAddress));
  }
}
