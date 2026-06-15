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

public class ServerKey extends NativePointer implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ServerKey.class);
  private final boolean isGpu;

  ServerKey() {
    this(false);
  }

  ServerKey(boolean isGpu) {
    logger.trace("init - isGpu: {}", isGpu);

    super(isGpu ? TfheHeader::cuda_server_key_destroy : TfheHeader::server_key_destroy);
    this.isGpu = isGpu;
  }

  public ServerKey(CompressedServerKey compressedServerKey) {
    this(Boolean.getBoolean("tfhe.gpu"));
    execute(() -> {
      if (Boolean.getBoolean("tfhe.gpu")) {
        return compressed_server_key_decompress_to_gpu(compressedServerKey.getValue(), getAddress());
      } else {
        return compressed_server_key_decompress(compressedServerKey.getValue(), getAddress());
      }
    });
  }

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

  public static ServerKey current() {
    return CURRENT_KEY.get();
  }

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

  public void use() {
    logger.trace("use");

    if (isGpu) {
      execute(() -> set_cuda_server_key(getValue()));
    } else {
      execute(() -> set_server_key(getValue()));
    }
  }

  public void unset() {
    logger.trace("unset");

    execute(TfheHeader::unset_server_key);
  }
}
