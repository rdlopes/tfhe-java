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

public class CompressedFheInt80 extends NativePointer
  implements CompressedFheType<I128, FheInt80, CompressedFheInt80> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt80.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int80_destroy(struct CompressedFheInt80 *ptr);
     }
   */
  CompressedFheInt80() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int80_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt80 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt80 deserialized = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt80 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt80 encrypted = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt80 decompress() {
    FheInt80 decompressed = new FheInt80();
    execute(() -> compressed_fhe_int80_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt80 clone() {
    CompressedFheInt80 cloned = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
