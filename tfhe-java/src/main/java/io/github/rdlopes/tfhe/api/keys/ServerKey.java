package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.FheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

/// A decompressed, CPU-only server key used to perform homomorphic operations.
///
/// ## When to use this class
///
/// Use `ServerKey` directly only in these two situations:
///
/// 1. **Conformant key for ciphertext deserialization** — pass it as the key parameter
///    when deserializing ciphertexts produced under this key:
///
///    {@snippet lang=java :
///    ServerKey serverKey = keySet.getServerKey();
///    FheUint32 ct = FheUint32.deserialize(buffer, serverKey);
///    }
///
/// 2. **CPU-only path after direct deserialization** — when you hold serialized
///    `ServerKey` bytes (not `CompressedServerKey` bytes):
///
///    {@snippet lang=java :
///    ServerKey serverKey = ServerKey.deserialize(buffer);
///    serverKey.use(); // CPU only — GPU is not activated
///    }
///
/// ## Prefer [CompressedServerKey] for key activation
///
/// For activating FHE operations (especially with GPU support), always prefer
/// [CompressedServerKey#use()]:
///
/// {@snippet lang=java :
/// // Activates CPU + GPU transparently based on -Dtfhe.gpu
/// keySet.getCompressedServerKey().use();
/// }
///
/// ## GPU note
///
/// `ServerKey` is **always CPU-only**, even when `tfhe.gpu=true`.
/// For GPU support, use [CompressedServerKey#use()] or [CompressedServerKey#decompressToGpu()].
///
/// @see CompressedServerKey the canonical server key with transparent GPU support
public class ServerKey extends NativePointer implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ServerKey.class);

  ServerKey() {
    logger.trace("init");
    super(TfheHeader::server_key_destroy);
  }

  /// Decompresses a [CompressedServerKey] into a CPU [ServerKey].
  ServerKey(CompressedServerKey compressedServerKey) {
    this();
    logger.trace("init - from CompressedServerKey");
    execute(() -> compressed_server_key_decompress(compressedServerKey.getValue(), getAddress()));
  }

  /// Deserializes a CPU [ServerKey] from the given buffer.
  ///
  /// > **Note:** This method always produces a CPU-only key.
  /// > For GPU support, serialize a [CompressedServerKey] on the client side and use
  /// > `CompressedServerKey.deserialize(buffer).use()` on the server side.
  public static ServerKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);
    ServerKey deserialized = new ServerKey();
    execute(() -> server_key_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));
    return dynamicBuffer;
  }

  private static final ThreadLocal<ServerKey> CURRENT_KEY = new ThreadLocal<>();

  /// Returns the [ServerKey] currently registered on this thread, or `null` if none.
  public static ServerKey current() {
    return CURRENT_KEY.get();
  }

  /// Runs the given task with this key registered as the current server key,
  /// then restores the previous key (if any) when the task completes.
  public void runWith(Runnable runnable) {
    ServerKey oldKey = CURRENT_KEY.get();
    CURRENT_KEY.set(this);
    this.use();
    try {
      runnable.run();
    } finally {
      if (oldKey != null) {
        CURRENT_KEY.set(oldKey);
        oldKey.use();
      } else {
        CURRENT_KEY.remove();
        this.unset();
      }
    }
  }

  /// Calls the given task with this key registered as the current server key,
  /// then restores the previous key (if any) when the task completes.
  ///
  /// @param <V> the return type of the callable
  /// @return the value returned by the callable
  public <V> V callWith(java.util.concurrent.Callable<V> callable) throws Exception {
    ServerKey oldKey = CURRENT_KEY.get();
    CURRENT_KEY.set(this);
    this.use();
    try {
      return callable.call();
    } finally {
      if (oldKey != null) {
        CURRENT_KEY.set(oldKey);
        oldKey.use();
      } else {
        CURRENT_KEY.remove();
        this.unset();
      }
    }
  }

  /// Registers this CPU key with the TFHE runtime on the current thread.
  ///
  /// For transparent CPU+GPU activation, use [CompressedServerKey#use()] instead.
  public void use() {
    logger.trace("use");
    execute(() -> set_server_key(getValue()));
  }

  /// Unregisters the current server key from the TFHE runtime on this thread.
  public void unset() {
    logger.trace("unset");
    execute(TfheHeader::unset_server_key);
  }
}
