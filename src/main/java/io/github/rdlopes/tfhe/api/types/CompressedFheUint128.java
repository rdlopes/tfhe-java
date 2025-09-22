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

public class CompressedFheUint128 extends NativePointer
  implements CompressedFheType<U128, FheUint128, CompressedFheUint128> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint128.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint128_destroy(struct CompressedFheUint128 *ptr);
     }
   */
  CompressedFheUint128() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint128_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint128 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint128 deserialized = new CompressedFheUint128();
    execute(() -> compressed_fhe_uint128_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint128 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint128 encrypted = new CompressedFheUint128();
    execute(() -> compressed_fhe_uint128_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint128 decompress() {
    FheUint128 decompressed = new FheUint128();
    execute(() -> compressed_fhe_uint128_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint128 clone() {
    CompressedFheUint128 cloned = new CompressedFheUint128();
    execute(() -> compressed_fhe_uint128_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
