package io.github.rdlopes.tfhe.api.keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public record KeySet(ClientKey clientKey, ServerKey serverKey) implements AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(KeySet.class);

  public static FheKeySetBuilder builder() {
    return new FheKeySetBuilder();
  }

  public ClientKey getClientKey() {
    logger.trace("getClientKey");
    return clientKey;
  }

  public ServerKey getServerKey() {
    logger.trace("getServerKey");
    return serverKey;
  }
  
  /// Destroys both the client key and server key native resources.
  /// Usable in try-with-resources for deterministic cleanup.
  @Override
  public void close() {
    logger.trace("close");
    clientKey.destroy();
    serverKey.destroy();
  }
  
  public static class FheKeySetBuilder {
    private static final Logger logger = LoggerFactory.getLogger(FheKeySetBuilder.class);
    private final ConfigBuilder builder = new ConfigBuilder();

    public FheKeySetBuilder useCustomParameters(CustomParameters customParameters) {
      execute(() -> config_builder_use_custom_parameters(builder.getAddress(), customParameters.address()));
      return this;
    }

    public FheKeySetBuilder useCompactKeyEncryptionParameters(CompactPublicKeyEncryptionParameters encryptionParameters) {
      execute(() -> use_dedicated_compact_public_key_parameters(builder.getAddress(), encryptionParameters.address()));
      return this;
    }

    public FheKeySetBuilder enableCompression(CompressionParameters compressionParameters) {
      execute(() -> config_builder_enable_compression(builder.getAddress(), compressionParameters.address()));
      return this;
    }

    public KeySet build() {
      logger.trace("build");
      Config config = builder.build();
      ClientKey clientKey = new ClientKey();
      ServerKey serverKey = new ServerKey();
      config.initialize(clientKey, serverKey);
      return new KeySet(clientKey, serverKey);
    }
  }
}
