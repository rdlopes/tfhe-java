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

public class CompressedFheInt12 extends NativePointer
  implements CompressedFheType<Short, FheInt12, CompressedFheInt12> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt12.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int12_destroy(struct CompressedFheInt12 *ptr);
     }
   */
  CompressedFheInt12() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int12_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt12 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt12 deserialized = new CompressedFheInt12();
    execute(() -> compressed_fhe_int12_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt12 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt12 encrypted = new CompressedFheInt12();
    execute(() -> compressed_fhe_int12_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt12 decompress() {
    FheInt12 decompressed = new FheInt12();
    execute(() -> compressed_fhe_int12_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt12 clone() {
    CompressedFheInt12 cloned = new CompressedFheInt12();
    execute(() -> compressed_fhe_int12_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
