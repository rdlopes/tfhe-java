package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.CompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedFheInt64 extends NativePointer
  implements CompressedFheType<Long, FheInt64, CompressedFheInt64> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt64.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int64_destroy(struct CompressedFheInt64 *ptr);
     }
   */
  CompressedFheInt64() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int64_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt64 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt64 deserialized = new CompressedFheInt64();
    execute(() -> compressed_fhe_int64_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt64 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheInt64 encrypted = new CompressedFheInt64();
    execute(() -> compressed_fhe_int64_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt64 decompress() {
    FheInt64 decompressed = new FheInt64();
    execute(() -> compressed_fhe_int64_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt64 clone() {
    CompressedFheInt64 cloned = new CompressedFheInt64();
    execute(() -> compressed_fhe_int64_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
