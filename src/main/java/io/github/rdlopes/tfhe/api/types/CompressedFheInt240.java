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

public class CompressedFheInt240 extends NativePointer
  implements CompressedFheType<I256, FheInt240, CompressedFheInt240> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt240.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int240_destroy(struct CompressedFheInt240 *ptr);
     }
   */
  CompressedFheInt240() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int240_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt240 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt240 deserialized = new CompressedFheInt240();
    execute(() -> compressed_fhe_int240_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt240 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt240 encrypted = new CompressedFheInt240();
    execute(() -> compressed_fhe_int240_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt240 decompress() {
    FheInt240 decompressed = new FheInt240();
    execute(() -> compressed_fhe_int240_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt240 clone() {
    CompressedFheInt240 cloned = new CompressedFheInt240();
    execute(() -> compressed_fhe_int240_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
