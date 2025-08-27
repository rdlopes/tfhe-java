package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class ConfigBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ConfigBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static void generateKeys(MemorySegment config, MemorySegment clientKeyAddress, MemorySegment serverKeyAddress) {
    logger.trace("generateKeys - config: {}, clientKeyAddress: {}, serverKeyAddress: {}", config, clientKeyAddress, serverKeyAddress);
    executeWithErrorHandling(() -> generate_keys(addressValue(config), clientKeyAddress, serverKeyAddress));
  }

  public static void generateClientKey(MemorySegment configAddress, MemorySegment clientKeyAddress) {
    logger.trace("generate");
    executeWithErrorHandling(() -> client_key_generate(addressValue(configAddress), clientKeyAddress));
  }

}
