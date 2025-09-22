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

public class CompressedFheUint72 extends NativePointer
  implements CompressedFheType<U128, FheUint72, CompressedFheUint72> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint72.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint72_destroy(struct CompressedFheUint72 *ptr);
     }
   */
  CompressedFheUint72() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint72_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint72 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint72 deserialized = new CompressedFheUint72();
    execute(() -> compressed_fhe_uint72_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint72 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint72 encrypted = new CompressedFheUint72();
    execute(() -> compressed_fhe_uint72_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint72 decompress() {
    FheUint72 decompressed = new FheUint72();
    execute(() -> compressed_fhe_uint72_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint72 clone() {
    CompressedFheUint72 cloned = new CompressedFheUint72();
    execute(() -> compressed_fhe_uint72_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
