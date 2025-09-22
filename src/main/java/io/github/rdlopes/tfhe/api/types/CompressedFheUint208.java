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

public class CompressedFheUint208 extends NativePointer
  implements CompressedFheType<U256, FheUint208, CompressedFheUint208> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint208.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint208_destroy(struct CompressedFheUint208 *ptr);
     }
   */
  CompressedFheUint208() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint208_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint208 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint208 deserialized = new CompressedFheUint208();
    execute(() -> compressed_fhe_uint208_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint208 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint208 encrypted = new CompressedFheUint208();
    execute(() -> compressed_fhe_uint208_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint208 decompress() {
    FheUint208 decompressed = new FheUint208();
    execute(() -> compressed_fhe_uint208_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint208 clone() {
    CompressedFheUint208 cloned = new CompressedFheUint208();
    execute(() -> compressed_fhe_uint208_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
