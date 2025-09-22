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

public class CompressedFheInt136 extends NativePointer
  implements CompressedFheType<I256, FheInt136, CompressedFheInt136> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt136.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int136_destroy(struct CompressedFheInt136 *ptr);
     }
   */
  CompressedFheInt136() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int136_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt136 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt136 deserialized = new CompressedFheInt136();
    execute(() -> compressed_fhe_int136_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt136 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt136 encrypted = new CompressedFheInt136();
    execute(() -> compressed_fhe_int136_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt136 decompress() {
    FheInt136 decompressed = new FheInt136();
    execute(() -> compressed_fhe_int136_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt136 clone() {
    CompressedFheInt136 cloned = new CompressedFheInt136();
    execute(() -> compressed_fhe_int136_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
