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

public class CompressedFheInt112 extends NativePointer
  implements CompressedFheType<I128, FheInt112, CompressedFheInt112> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt112.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int112_destroy(struct CompressedFheInt112 *ptr);
     }
   */
  CompressedFheInt112() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int112_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt112 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt112 deserialized = new CompressedFheInt112();
    execute(() -> compressed_fhe_int112_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt112 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt112 encrypted = new CompressedFheInt112();
    execute(() -> compressed_fhe_int112_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt112 decompress() {
    FheInt112 decompressed = new FheInt112();
    execute(() -> compressed_fhe_int112_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt112 clone() {
    CompressedFheInt112 cloned = new CompressedFheInt112();
    execute(() -> compressed_fhe_int112_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
