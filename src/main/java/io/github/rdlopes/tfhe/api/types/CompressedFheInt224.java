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

public class CompressedFheInt224 extends NativePointer
  implements CompressedFheType<I256, FheInt224, CompressedFheInt224> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt224.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int224_destroy(struct CompressedFheInt224 *ptr);
     }
   */
  CompressedFheInt224() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int224_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt224 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt224 deserialized = new CompressedFheInt224();
    execute(() -> compressed_fhe_int224_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt224 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt224 encrypted = new CompressedFheInt224();
    execute(() -> compressed_fhe_int224_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt224 decompress() {
    FheInt224 decompressed = new FheInt224();
    execute(() -> compressed_fhe_int224_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int224_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt224 clone() {
    CompressedFheInt224 cloned = new CompressedFheInt224();
    execute(() -> compressed_fhe_int224_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
