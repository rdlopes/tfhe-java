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

public class CompressedFheUint12 extends NativePointer
  implements CompressedFheType<Short, FheUint12, CompressedFheUint12> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint12.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint12_destroy(struct CompressedFheUint12 *ptr);
     }
   */
  CompressedFheUint12() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint12_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint12 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint12 deserialized = new CompressedFheUint12();
    execute(() -> compressed_fhe_uint12_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint12 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheUint12 encrypted = new CompressedFheUint12();
    execute(() -> compressed_fhe_uint12_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint12 decompress() {
    FheUint12 decompressed = new FheUint12();
    execute(() -> compressed_fhe_uint12_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint12 clone() {
    CompressedFheUint12 cloned = new CompressedFheUint12();
    execute(() -> compressed_fhe_uint12_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
