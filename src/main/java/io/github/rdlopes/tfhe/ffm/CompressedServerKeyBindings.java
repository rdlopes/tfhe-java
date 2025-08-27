package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_15.*;

public final class CompressedServerKeyBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(CompressedServerKeyBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void createNew(MemorySegment clientKeyAddress, MemorySegment compressedServerKeyAddress) {
    logger.trace("createNew - clientKeyAddress: {}, compressedServerKeyAddress: {}", clientKeyAddress, compressedServerKeyAddress);
    executeWithErrorHandling(() -> compressed_server_key_new(addressValue(clientKeyAddress), compressedServerKeyAddress));
  }

  public static void serialize(MemorySegment compressedServerKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("serialize - compressedServerKeyAddress: {}, dynamicBufferAddress: {}", compressedServerKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> compressed_server_key_serialize(addressValue(compressedServerKeyAddress), dynamicBufferAddress));
  }

  public static void safeSerialize(MemorySegment compressedServerKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("safeSerialize - compressedServerKeyAddress: {}, dynamicBufferAddress: {}", compressedServerKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> compressed_server_key_safe_serialize(addressValue(compressedServerKeyAddress), dynamicBufferAddress, SERDE_MAX_SIZE));
  }

  public static void deserialize(MemorySegment dynamicBufferViewAddress, MemorySegment compressedServerKeyAddress) {
    logger.trace("deserialize - dynamicBufferViewAddress: {}, compressedServerKeyAddress: {}", dynamicBufferViewAddress, compressedServerKeyAddress);
    executeWithErrorHandling(() -> compressed_server_key_deserialize(dynamicBufferViewAddress, compressedServerKeyAddress));
  }

  public static void safeDeserialize(MemorySegment dynamicBufferViewAddress, MemorySegment compressedServerKeyAddress) {
    logger.trace("safeDeserialize - dynamicBufferViewAddress: {}, compressedServerKeyAddress: {}", dynamicBufferViewAddress, compressedServerKeyAddress);
    executeWithErrorHandling(() -> compressed_server_key_safe_deserialize(dynamicBufferViewAddress, SERDE_MAX_SIZE, compressedServerKeyAddress));
  }

  public static void decompress(MemorySegment compressedServerKeyAddress, MemorySegment serverKeyAddress) {
    logger.trace("decompress - compressedServerKeyAddress: {}, serverKeyAddress: {}", compressedServerKeyAddress, serverKeyAddress);
    executeWithErrorHandling(() -> compressed_server_key_decompress(addressValue(compressedServerKeyAddress), serverKeyAddress));
  }

  public static void destroy(MemorySegment compressedServerKeyAddress) {
    logger.trace("destroy - compressedServerKeyAddress: {}", compressedServerKeyAddress);
    executeWithErrorHandling(() -> compressed_server_key_destroy(addressValue(compressedServerKeyAddress)));
  }
}
