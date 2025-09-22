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

public class CompressedFheUint240 extends NativePointer
  implements CompressedFheType<U256, FheUint240, CompressedFheUint240> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint240.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint240_destroy(struct CompressedFheUint240 *ptr);
     }
   */
  CompressedFheUint240() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint240_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint240 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint240 deserialized = new CompressedFheUint240();
    execute(() -> compressed_fhe_uint240_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint240 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint240 encrypted = new CompressedFheUint240();
    execute(() -> compressed_fhe_uint240_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint240 decompress() {
    FheUint240 decompressed = new FheUint240();
    execute(() -> compressed_fhe_uint240_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint240 clone() {
    CompressedFheUint240 cloned = new CompressedFheUint240();
    execute(() -> compressed_fhe_uint240_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
