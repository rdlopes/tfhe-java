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

public class CompressedFheUint136 extends NativePointer
  implements CompressedFheType<U256, FheUint136, CompressedFheUint136> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint136.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint136_destroy(struct CompressedFheUint136 *ptr);
     }
   */
  CompressedFheUint136() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint136_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint136 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint136 deserialized = new CompressedFheUint136();
    execute(() -> compressed_fhe_uint136_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint136 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint136 encrypted = new CompressedFheUint136();
    execute(() -> compressed_fhe_uint136_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint136 decompress() {
    FheUint136 decompressed = new FheUint136();
    execute(() -> compressed_fhe_uint136_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint136 clone() {
    CompressedFheUint136 cloned = new CompressedFheUint136();
    execute(() -> compressed_fhe_uint136_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
