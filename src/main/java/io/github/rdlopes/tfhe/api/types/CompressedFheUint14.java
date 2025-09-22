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

public class CompressedFheUint14 extends NativePointer
  implements CompressedFheType<Short, FheUint14, CompressedFheUint14> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint14.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint14_destroy(struct CompressedFheUint14 *ptr);
     }
   */
  CompressedFheUint14() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint14_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint14 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint14 deserialized = new CompressedFheUint14();
    execute(() -> compressed_fhe_uint14_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint14 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheUint14 encrypted = new CompressedFheUint14();
    execute(() -> compressed_fhe_uint14_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint14 decompress() {
    FheUint14 decompressed = new FheUint14();
    execute(() -> compressed_fhe_uint14_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint14 clone() {
    CompressedFheUint14 cloned = new CompressedFheUint14();
    execute(() -> compressed_fhe_uint14_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
