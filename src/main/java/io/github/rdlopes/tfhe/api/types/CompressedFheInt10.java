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

public class CompressedFheInt10 extends NativePointer
  implements CompressedFheType<Short, FheInt10, CompressedFheInt10> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt10.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int10_destroy(struct CompressedFheInt10 *ptr);
     }
   */
  CompressedFheInt10() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int10_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt10 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt10 deserialized = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt10 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt10 encrypted = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt10 decompress() {
    FheInt10 decompressed = new FheInt10();
    execute(() -> compressed_fhe_int10_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt10 clone() {
    CompressedFheInt10 cloned = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
