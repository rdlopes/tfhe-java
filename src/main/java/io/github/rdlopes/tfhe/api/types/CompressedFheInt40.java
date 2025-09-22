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

public class CompressedFheInt40 extends NativePointer
  implements CompressedFheType<Long, FheInt40, CompressedFheInt40> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt40.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int40_destroy(struct CompressedFheInt40 *ptr);
     }
   */
  CompressedFheInt40() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int40_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt40 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt40 deserialized = new CompressedFheInt40();
    execute(() -> compressed_fhe_int40_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt40 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheInt40 encrypted = new CompressedFheInt40();
    execute(() -> compressed_fhe_int40_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt40 decompress() {
    FheInt40 decompressed = new FheInt40();
    execute(() -> compressed_fhe_int40_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt40 clone() {
    CompressedFheInt40 cloned = new CompressedFheInt40();
    execute(() -> compressed_fhe_int40_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
