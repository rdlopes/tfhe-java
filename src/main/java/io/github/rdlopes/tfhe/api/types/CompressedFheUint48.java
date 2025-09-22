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

public class CompressedFheUint48 extends NativePointer
  implements CompressedFheType<Long, FheUint48, CompressedFheUint48> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint48.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint48_destroy(struct CompressedFheUint48 *ptr);
     }
   */
  CompressedFheUint48() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint48_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint48 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint48 deserialized = new CompressedFheUint48();
    execute(() -> compressed_fhe_uint48_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint48 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheUint48 encrypted = new CompressedFheUint48();
    execute(() -> compressed_fhe_uint48_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint48 decompress() {
    FheUint48 decompressed = new FheUint48();
    execute(() -> compressed_fhe_uint48_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint48 clone() {
    CompressedFheUint48 cloned = new CompressedFheUint48();
    execute(() -> compressed_fhe_uint48_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
