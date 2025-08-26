package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

public final class ClientKeyBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ClientKeyBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void generate(MemorySegment configAddress, MemorySegment clientKeyAddress) {
    logger.trace("generate");
    executeWithErrorHandling(() -> client_key_generate(addressValue(configAddress), clientKeyAddress));
  }

  public static void serialize(MemorySegment clientKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("serialize");
    executeWithErrorHandling(() -> client_key_serialize(addressValue(clientKeyAddress), dynamicBufferAddress));
  }

  public static void safeSerialize(MemorySegment clientKeyAddress, MemorySegment dynamicBufferAddress) {
    logger.trace("safeSerialize");
    executeWithErrorHandling(() -> client_key_safe_serialize(addressValue(clientKeyAddress), dynamicBufferAddress, SERDE_MAX_SIZE));
  }

  public static void deserialize(MemorySegment dynamicBufferViewAddress, MemorySegment clientKeyAddress) {
    logger.trace("deserialize");
    executeWithErrorHandling(() -> client_key_deserialize(dynamicBufferViewAddress, clientKeyAddress));
  }

  public static void safeDeserialize(MemorySegment dynamicBufferViewAddress, MemorySegment clientKeyAddress) {
    logger.trace("safeDeserialize");
    executeWithErrorHandling(() -> client_key_safe_deserialize(dynamicBufferViewAddress, SERDE_MAX_SIZE, clientKeyAddress));
  }
}
