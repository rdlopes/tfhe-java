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

public class CompressedFheInt14 extends NativePointer
  implements CompressedFheType<Short, FheInt14, CompressedFheInt14> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt14.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int14_destroy(struct CompressedFheInt14 *ptr);
     }
   */
  CompressedFheInt14() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int14_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt14 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt14 deserialized = new CompressedFheInt14();
    execute(() -> compressed_fhe_int14_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt14 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt14 encrypted = new CompressedFheInt14();
    execute(() -> compressed_fhe_int14_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt14 decompress() {
    FheInt14 decompressed = new FheInt14();
    execute(() -> compressed_fhe_int14_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt14 clone() {
    CompressedFheInt14 cloned = new CompressedFheInt14();
    execute(() -> compressed_fhe_int14_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
