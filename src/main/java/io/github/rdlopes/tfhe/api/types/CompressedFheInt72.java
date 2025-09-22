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

public class CompressedFheInt72 extends NativePointer
  implements CompressedFheType<I128, FheInt72, CompressedFheInt72> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt72.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int72_destroy(struct CompressedFheInt72 *ptr);
     }
   */
  CompressedFheInt72() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int72_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt72 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt72 deserialized = new CompressedFheInt72();
    execute(() -> compressed_fhe_int72_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt72 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt72 encrypted = new CompressedFheInt72();
    execute(() -> compressed_fhe_int72_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt72 decompress() {
    FheInt72 decompressed = new FheInt72();
    execute(() -> compressed_fhe_int72_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt72 clone() {
    CompressedFheInt72 cloned = new CompressedFheInt72();
    execute(() -> compressed_fhe_int72_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
