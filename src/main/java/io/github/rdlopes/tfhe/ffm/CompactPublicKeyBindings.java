package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

public final class CompactPublicKeyBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(CompactPublicKeyBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void serialize(MemorySegment compressedPublicKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("serialize - compressedPublicKeyAddress: {}, dynamicBufferAddress: {}", compressedPublicKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> compressed_compact_public_key_serialize(addressValue(compressedPublicKeyAddress), dynamicBufferAddress));
  }

  public static void safeSerialize(MemorySegment compressedPublicKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("safeSerialize - compressedPublicKeyAddress: {}, dynamicBufferAddress: {}", compressedPublicKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> compressed_compact_public_key_safe_serialize(addressValue(compressedPublicKeyAddress), dynamicBufferAddress, SERDE_MAX_SIZE));
  }

  public static void deserialize(MemorySegment dynamicBufferViewAddress, MemorySegment compressedPublicKeyAddress) {
    logger.trace("deserialize - dynamicBufferViewAddress: {}, compressedPublicKeyAddress: {}", dynamicBufferViewAddress, compressedPublicKeyAddress);
    executeWithErrorHandling(() -> compressed_compact_public_key_deserialize(dynamicBufferViewAddress, compressedPublicKeyAddress));
  }

  public static void safeDeserialize(MemorySegment dynamicBufferViewAddress, MemorySegment compressedPublicKeyAddress) {
    logger.trace("safeDeserialize - dynamicBufferViewAddress: {}, compressedPublicKeyAddress: {}", dynamicBufferViewAddress, compressedPublicKeyAddress);
    executeWithErrorHandling(() -> compressed_compact_public_key_safe_deserialize(dynamicBufferViewAddress, SERDE_MAX_SIZE, compressedPublicKeyAddress));
  }

  public static void destroy(MemorySegment compressedPublicKeyAddress) {
    logger.trace("destroy - compressedPublicKeyAddress: {}", compressedPublicKeyAddress);
    executeWithErrorHandling(() -> compressed_compact_public_key_destroy(addressValue(compressedPublicKeyAddress)));
  }
}
