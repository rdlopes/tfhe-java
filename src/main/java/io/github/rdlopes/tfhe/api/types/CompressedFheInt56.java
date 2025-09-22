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

public class CompressedFheInt56 extends NativePointer
  implements CompressedFheType<Long, FheInt56, CompressedFheInt56> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt56.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int56_destroy(struct CompressedFheInt56 *ptr);
     }
   */
  CompressedFheInt56() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int56_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt56 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt56 deserialized = new CompressedFheInt56();
    execute(() -> compressed_fhe_int56_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt56 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheInt56 encrypted = new CompressedFheInt56();
    execute(() -> compressed_fhe_int56_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt56 decompress() {
    FheInt56 decompressed = new FheInt56();
    execute(() -> compressed_fhe_int56_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt56 clone() {
    CompressedFheInt56 cloned = new CompressedFheInt56();
    execute(() -> compressed_fhe_int56_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
