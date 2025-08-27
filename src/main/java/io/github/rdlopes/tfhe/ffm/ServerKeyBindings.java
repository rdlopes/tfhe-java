package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_1.*;

public final class ServerKeyBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ServerKeyBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void serialize(MemorySegment serverKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("serialize - serverKeyAddress: {}, dynamicBufferAddress: {}", serverKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> server_key_serialize(addressValue(serverKeyAddress), dynamicBufferAddress));
  }

  public static void safeSerialize(MemorySegment serverKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("safeSerialize - serverKeyAddress: {}, dynamicBufferAddress: {}", serverKeyAddress, dynamicBufferAddress);
    executeWithErrorHandling(() -> server_key_safe_serialize(addressValue(serverKeyAddress), dynamicBufferAddress, SERDE_MAX_SIZE));
  }

  public static void deserialize(MemorySegment dynamicBufferViewAddress, MemorySegment serverKeyAddress) {
    logger.trace("deserialize - dynamicBufferViewAddress: {}, serverKeyAddress: {}", dynamicBufferViewAddress, serverKeyAddress);
    executeWithErrorHandling(() -> server_key_deserialize(dynamicBufferViewAddress, serverKeyAddress));
  }
}
