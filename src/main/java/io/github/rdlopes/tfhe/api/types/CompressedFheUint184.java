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

public class CompressedFheUint184 extends NativePointer
  implements CompressedFheType<U256, FheUint184, CompressedFheUint184> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint184.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint184_destroy(struct CompressedFheUint184 *ptr);
     }
   */
  CompressedFheUint184() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint184_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint184 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint184 deserialized = new CompressedFheUint184();
    execute(() -> compressed_fhe_uint184_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint184 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint184 encrypted = new CompressedFheUint184();
    execute(() -> compressed_fhe_uint184_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint184 decompress() {
    FheUint184 decompressed = new FheUint184();
    execute(() -> compressed_fhe_uint184_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint184 clone() {
    CompressedFheUint184 cloned = new CompressedFheUint184();
    execute(() -> compressed_fhe_uint184_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
