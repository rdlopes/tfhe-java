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

public class CompressedFheInt512 extends NativePointer
  implements CompressedFheType<I512, FheInt512, CompressedFheInt512> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt512.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int512_destroy(struct CompressedFheInt512 *ptr);
     }
   */
  CompressedFheInt512() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int512_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt512 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt512 deserialized = new CompressedFheInt512();
    execute(() -> compressed_fhe_int512_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt512 encrypt(I512 clearValue, ClientKey clientKey) {
    CompressedFheInt512 encrypted = new CompressedFheInt512();
    execute(() -> compressed_fhe_int512_try_encrypt_with_client_key_i512(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt512 decompress() {
    FheInt512 decompressed = new FheInt512();
    execute(() -> compressed_fhe_int512_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt512 clone() {
    CompressedFheInt512 cloned = new CompressedFheInt512();
    execute(() -> compressed_fhe_int512_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
