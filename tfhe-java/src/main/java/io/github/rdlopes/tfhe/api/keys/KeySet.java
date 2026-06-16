package io.github.rdlopes.tfhe.api.keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

/// Container for a complete FHE key set generated on the **client side**.
/// Holds a [ClientKey] (always secret) and a [CompressedServerKey] (shareable with the server).
///
/// ## Typical usage — local computation
///
/// {@snippet lang=java :
/// KeySet keySet = KeySet.builder()
///     .useCustomParameters(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
///     .build();
///
/// // Activate the server key (transparent CPU or CPU+GPU if -Dtfhe.gpu=true)
/// keySet.getCompressedServerKey().use();
///
/// FheUint8 ct = FheUint8.encrypt((byte) 42, keySet.getClientKey());
/// }
///
/// ## Sending the server key to a remote server
///
/// Serialize [CompressedServerKey] — **not** [ServerKey] — so that the server
/// can activate GPU acceleration transparently:
///
/// {@snippet lang=java :
/// DynamicBuffer bytes = keySet.getCompressedServerKey().serialize();
/// sendToServer(bytes.toByteArray());
/// }
///
/// ## Conformant ciphertext deserialization
///
/// When deserializing a ciphertext produced under this key set, pass the CPU
/// [ServerKey] as the conformant key:
///
/// {@snippet lang=java :
/// FheUint32 result = FheUint32.deserialize(buffer, keySet.getServerKey());
/// }
///
/// ## GPU activation
///
/// No code change required — just add {@code -Dtfhe.gpu=true} to the JVM.
/// [CompressedServerKey#use()] activates both the CPU and GPU keys automatically.
///
/// ## Lifecycle
///
/// Implements [AutoCloseable] — use in try-with-resources or call [#close()] explicitly.
///
/// @see CompressedServerKey the canonical server key and main entry point for key activation
/// @see io.github.rdlopes.tfhe.api.keys the package-level guide with client/server scenarios
public final class KeySet implements AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(KeySet.class);

  private final ClientKey clientKey;
  private final CompressedServerKey compressedServerKey;

  KeySet(ClientKey clientKey, CompressedServerKey compressedServerKey) {
    this.clientKey = clientKey;
    this.compressedServerKey = compressedServerKey;
  }

  public static FheKeySetBuilder builder() {
    return new FheKeySetBuilder();
  }

  public ClientKey getClientKey() {
    logger.trace("getClientKey");
    return clientKey;
  }

  /// Returns the canonical [CompressedServerKey].
  /// Call [CompressedServerKey#use()] on it to activate CPU and/or GPU keys for FHE operations.
  ///
  /// Use this method to:
  /// - activate the server key: {@code keySet.getCompressedServerKey().use()}
  /// - serialize and send to a remote server: {@code keySet.getCompressedServerKey().serialize()}
  public CompressedServerKey getCompressedServerKey() {
    logger.trace("getCompressedServerKey");
    return compressedServerKey;
  }

  /// Returns the lazily-cached CPU [ServerKey] for use as a **conformant key** in
  /// ciphertext deserialization.
  ///
  /// {@snippet lang=java :
  /// FheUint32 result = FheUint32.deserialize(buffer, keySet.getServerKey());
  /// }
  ///
  /// To **activate** the server key for FHE operations, prefer
  /// [#getCompressedServerKey()].use() which also enables GPU if configured.
  public ServerKey getServerKey() {
    logger.trace("getServerKey");
    return compressedServerKey.getCpuKey();
  }

  /// Destroys all native resources: the client key and the compressed server key
  /// (which also destroys any cached CPU/GPU derived keys).
  @Override
  public void close() {
    logger.trace("close");
    clientKey.destroy();
    compressedServerKey.destroy();
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

      // generate_keys initialises the ClientKey (required by the native API).
      // The ServerKey it also creates is discarded; CompressedServerKey becomes the canonical form.
      ServerKey tempServerKey = new ServerKey();
      config.initialize(clientKey, tempServerKey);
      tempServerKey.destroy();

      CompressedServerKey compressedServerKey = new CompressedServerKey(clientKey);
      return new KeySet(clientKey, compressedServerKey);
    }
  }
}
