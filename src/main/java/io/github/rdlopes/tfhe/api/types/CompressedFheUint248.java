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

public class CompressedFheUint248 extends NativePointer
  implements CompressedFheType<U256, FheUint248, CompressedFheUint248> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint248.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint248_destroy(struct CompressedFheUint248 *ptr);
     }
   */
  CompressedFheUint248() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint248_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint248 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint248 deserialized = new CompressedFheUint248();
    execute(() -> compressed_fhe_uint248_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint248 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint248 encrypted = new CompressedFheUint248();
    execute(() -> compressed_fhe_uint248_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint248 decompress() {
    FheUint248 decompressed = new FheUint248();
    execute(() -> compressed_fhe_uint248_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint248_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint248 clone() {
    CompressedFheUint248 cloned = new CompressedFheUint248();
    execute(() -> compressed_fhe_uint248_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
