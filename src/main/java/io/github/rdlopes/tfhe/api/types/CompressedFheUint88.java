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

public class CompressedFheUint88 extends NativePointer
  implements CompressedFheType<U128, FheUint88, CompressedFheUint88> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint88.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint88_destroy(struct CompressedFheUint88 *ptr);
     }
   */
  CompressedFheUint88() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint88_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint88 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint88 deserialized = new CompressedFheUint88();
    execute(() -> compressed_fhe_uint88_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint88 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint88 encrypted = new CompressedFheUint88();
    execute(() -> compressed_fhe_uint88_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint88 decompress() {
    FheUint88 decompressed = new FheUint88();
    execute(() -> compressed_fhe_uint88_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint88 clone() {
    CompressedFheUint88 cloned = new CompressedFheUint88();
    execute(() -> compressed_fhe_uint88_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
