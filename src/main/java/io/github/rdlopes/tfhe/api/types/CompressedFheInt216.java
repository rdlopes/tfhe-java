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

public class CompressedFheInt216 extends NativePointer
  implements CompressedFheType<I256, FheInt216, CompressedFheInt216> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt216.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int216_destroy(struct CompressedFheInt216 *ptr);
     }
   */
  CompressedFheInt216() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int216_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt216 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt216 deserialized = new CompressedFheInt216();
    execute(() -> compressed_fhe_int216_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt216 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt216 encrypted = new CompressedFheInt216();
    execute(() -> compressed_fhe_int216_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt216 decompress() {
    FheInt216 decompressed = new FheInt216();
    execute(() -> compressed_fhe_int216_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int216_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt216 clone() {
    CompressedFheInt216 cloned = new CompressedFheInt216();
    execute(() -> compressed_fhe_int216_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
