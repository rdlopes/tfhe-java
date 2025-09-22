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

public class CompressedFheUint10 extends NativePointer
  implements CompressedFheType<Short, FheUint10, CompressedFheUint10> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint10.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint10_destroy(struct CompressedFheUint10 *ptr);
     }
   */
  CompressedFheUint10() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint10_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint10 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint10 deserialized = new CompressedFheUint10();
    execute(() -> compressed_fhe_uint10_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint10 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheUint10 encrypted = new CompressedFheUint10();
    execute(() -> compressed_fhe_uint10_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint10 decompress() {
    FheUint10 decompressed = new FheUint10();
    execute(() -> compressed_fhe_uint10_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint10 clone() {
    CompressedFheUint10 cloned = new CompressedFheUint10();
    execute(() -> compressed_fhe_uint10_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
