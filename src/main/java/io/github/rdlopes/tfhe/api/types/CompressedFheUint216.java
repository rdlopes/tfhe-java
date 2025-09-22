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

public class CompressedFheUint216 extends NativePointer
  implements CompressedFheType<U256, FheUint216, CompressedFheUint216> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint216.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint216_destroy(struct CompressedFheUint216 *ptr);
     }
   */
  CompressedFheUint216() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint216_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint216 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint216 deserialized = new CompressedFheUint216();
    execute(() -> compressed_fhe_uint216_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint216 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint216 encrypted = new CompressedFheUint216();
    execute(() -> compressed_fhe_uint216_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint216 decompress() {
    FheUint216 decompressed = new FheUint216();
    execute(() -> compressed_fhe_uint216_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint216_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint216 clone() {
    CompressedFheUint216 cloned = new CompressedFheUint216();
    execute(() -> compressed_fhe_uint216_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
