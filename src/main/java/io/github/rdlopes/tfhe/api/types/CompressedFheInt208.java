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

public class CompressedFheInt208 extends NativePointer
  implements CompressedFheType<I256, FheInt208, CompressedFheInt208> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt208.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int208_destroy(struct CompressedFheInt208 *ptr);
     }
   */
  CompressedFheInt208() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int208_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt208 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt208 deserialized = new CompressedFheInt208();
    execute(() -> compressed_fhe_int208_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt208 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt208 encrypted = new CompressedFheInt208();
    execute(() -> compressed_fhe_int208_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt208 decompress() {
    FheInt208 decompressed = new FheInt208();
    execute(() -> compressed_fhe_int208_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt208 clone() {
    CompressedFheInt208 cloned = new CompressedFheInt208();
    execute(() -> compressed_fhe_int208_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
