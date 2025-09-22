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

public class CompressedFheUint96 extends NativePointer
  implements CompressedFheType<U128, FheUint96, CompressedFheUint96> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint96.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint96_destroy(struct CompressedFheUint96 *ptr);
     }
   */
  CompressedFheUint96() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint96_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint96 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint96 deserialized = new CompressedFheUint96();
    execute(() -> compressed_fhe_uint96_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint96 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint96 encrypted = new CompressedFheUint96();
    execute(() -> compressed_fhe_uint96_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint96 decompress() {
    FheUint96 decompressed = new FheUint96();
    execute(() -> compressed_fhe_uint96_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint96 clone() {
    CompressedFheUint96 cloned = new CompressedFheUint96();
    execute(() -> compressed_fhe_uint96_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
