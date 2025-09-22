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

public class CompressedFheUint192 extends NativePointer
  implements CompressedFheType<U256, FheUint192, CompressedFheUint192> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint192.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint192_destroy(struct CompressedFheUint192 *ptr);
     }
   */
  CompressedFheUint192() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint192_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint192 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint192 deserialized = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint192 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint192 encrypted = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint192 decompress() {
    FheUint192 decompressed = new FheUint192();
    execute(() -> compressed_fhe_uint192_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint192 clone() {
    CompressedFheUint192 cloned = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
