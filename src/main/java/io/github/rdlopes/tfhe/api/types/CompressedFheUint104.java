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

public class CompressedFheUint104 extends NativePointer
  implements CompressedFheType<U128, FheUint104, CompressedFheUint104> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint104.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint104_destroy(struct CompressedFheUint104 *ptr);
     }
   */
  CompressedFheUint104() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint104_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint104 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint104 deserialized = new CompressedFheUint104();
    execute(() -> compressed_fhe_uint104_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint104 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint104 encrypted = new CompressedFheUint104();
    execute(() -> compressed_fhe_uint104_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint104 decompress() {
    FheUint104 decompressed = new FheUint104();
    execute(() -> compressed_fhe_uint104_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint104 clone() {
    CompressedFheUint104 cloned = new CompressedFheUint104();
    execute(() -> compressed_fhe_uint104_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
