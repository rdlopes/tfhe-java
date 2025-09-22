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

public class CompressedFheInt176 extends NativePointer
  implements CompressedFheType<I256, FheInt176, CompressedFheInt176> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt176.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int176_destroy(struct CompressedFheInt176 *ptr);
     }
   */
  CompressedFheInt176() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int176_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt176 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt176 deserialized = new CompressedFheInt176();
    execute(() -> compressed_fhe_int176_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt176 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt176 encrypted = new CompressedFheInt176();
    execute(() -> compressed_fhe_int176_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt176 decompress() {
    FheInt176 decompressed = new FheInt176();
    execute(() -> compressed_fhe_int176_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int176_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt176 clone() {
    CompressedFheInt176 cloned = new CompressedFheInt176();
    execute(() -> compressed_fhe_int176_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
