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

public class CompressedFheInt200 extends NativePointer
  implements CompressedFheType<I256, FheInt200, CompressedFheInt200> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt200.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int200_destroy(struct CompressedFheInt200 *ptr);
     }
   */
  CompressedFheInt200() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int200_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt200 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt200 deserialized = new CompressedFheInt200();
    execute(() -> compressed_fhe_int200_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt200 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt200 encrypted = new CompressedFheInt200();
    execute(() -> compressed_fhe_int200_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt200 decompress() {
    FheInt200 decompressed = new FheInt200();
    execute(() -> compressed_fhe_int200_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int200_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt200 clone() {
    CompressedFheInt200 cloned = new CompressedFheInt200();
    execute(() -> compressed_fhe_int200_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
