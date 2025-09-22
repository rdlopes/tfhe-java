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

public class CompressedFheInt192 extends NativePointer
  implements CompressedFheType<I256, FheInt192, CompressedFheInt192> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt192.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int192_destroy(struct CompressedFheInt192 *ptr);
     }
   */
  CompressedFheInt192() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int192_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt192 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt192 deserialized = new CompressedFheInt192();
    execute(() -> compressed_fhe_int192_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt192 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt192 encrypted = new CompressedFheInt192();
    execute(() -> compressed_fhe_int192_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt192 decompress() {
    FheInt192 decompressed = new FheInt192();
    execute(() -> compressed_fhe_int192_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt192 clone() {
    CompressedFheInt192 cloned = new CompressedFheInt192();
    execute(() -> compressed_fhe_int192_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
