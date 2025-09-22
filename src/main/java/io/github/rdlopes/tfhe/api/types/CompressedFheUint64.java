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

public class CompressedFheUint64 extends NativePointer
  implements CompressedFheType<Long, FheUint64, CompressedFheUint64> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint64.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint64_destroy(struct CompressedFheUint64 *ptr);
     }
   */
  CompressedFheUint64() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint64_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint64 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint64 deserialized = new CompressedFheUint64();
    execute(() -> compressed_fhe_uint64_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint64 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheUint64 encrypted = new CompressedFheUint64();
    execute(() -> compressed_fhe_uint64_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint64 decompress() {
    FheUint64 decompressed = new FheUint64();
    execute(() -> compressed_fhe_uint64_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint64 clone() {
    CompressedFheUint64 cloned = new CompressedFheUint64();
    execute(() -> compressed_fhe_uint64_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
