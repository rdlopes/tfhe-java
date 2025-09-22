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

public class CompressedFheInt96 extends NativePointer
  implements CompressedFheType<I128, FheInt96, CompressedFheInt96> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt96.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int96_destroy(struct CompressedFheInt96 *ptr);
     }
   */
  CompressedFheInt96() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int96_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt96 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt96 deserialized = new CompressedFheInt96();
    execute(() -> compressed_fhe_int96_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt96 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt96 encrypted = new CompressedFheInt96();
    execute(() -> compressed_fhe_int96_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt96 decompress() {
    FheInt96 decompressed = new FheInt96();
    execute(() -> compressed_fhe_int96_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt96 clone() {
    CompressedFheInt96 cloned = new CompressedFheInt96();
    execute(() -> compressed_fhe_int96_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
