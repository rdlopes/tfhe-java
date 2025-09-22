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

public class CompressedFheUint2048 extends NativePointer
  implements CompressedFheType<U2048, FheUint2048, CompressedFheUint2048> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint2048.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint2048_destroy(struct CompressedFheUint2048 *ptr);
     }
   */
  CompressedFheUint2048() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint2048_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint2048 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint2048 deserialized = new CompressedFheUint2048();
    execute(() -> compressed_fhe_uint2048_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint2048 encrypt(U2048 clearValue, ClientKey clientKey) {
    CompressedFheUint2048 encrypted = new CompressedFheUint2048();
    execute(() -> compressed_fhe_uint2048_try_encrypt_with_client_key_u2048(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint2048 decompress() {
    FheUint2048 decompressed = new FheUint2048();
    execute(() -> compressed_fhe_uint2048_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint2048 clone() {
    CompressedFheUint2048 cloned = new CompressedFheUint2048();
    execute(() -> compressed_fhe_uint2048_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
