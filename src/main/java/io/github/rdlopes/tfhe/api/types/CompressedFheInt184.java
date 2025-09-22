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

public class CompressedFheInt184 extends NativePointer
  implements CompressedFheType<I256, FheInt184, CompressedFheInt184> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt184.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int184_destroy(struct CompressedFheInt184 *ptr);
     }
   */
  CompressedFheInt184() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int184_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt184 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt184 deserialized = new CompressedFheInt184();
    execute(() -> compressed_fhe_int184_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt184 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt184 encrypted = new CompressedFheInt184();
    execute(() -> compressed_fhe_int184_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt184 decompress() {
    FheInt184 decompressed = new FheInt184();
    execute(() -> compressed_fhe_int184_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt184 clone() {
    CompressedFheInt184 cloned = new CompressedFheInt184();
    execute(() -> compressed_fhe_int184_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
