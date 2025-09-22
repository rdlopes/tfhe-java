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

public class CompressedFheUint112 extends NativePointer
  implements CompressedFheType<U128, FheUint112, CompressedFheUint112> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint112.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint112_destroy(struct CompressedFheUint112 *ptr);
     }
   */
  CompressedFheUint112() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint112_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint112 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint112 deserialized = new CompressedFheUint112();
    execute(() -> compressed_fhe_uint112_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint112 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint112 encrypted = new CompressedFheUint112();
    execute(() -> compressed_fhe_uint112_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint112 decompress() {
    FheUint112 decompressed = new FheUint112();
    execute(() -> compressed_fhe_uint112_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint112 clone() {
    CompressedFheUint112 cloned = new CompressedFheUint112();
    execute(() -> compressed_fhe_uint112_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
