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

public class CompressedFheUint80 extends NativePointer
  implements CompressedFheType<U128, FheUint80, CompressedFheUint80> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint80.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint80_destroy(struct CompressedFheUint80 *ptr);
     }
   */
  CompressedFheUint80() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint80_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint80 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint80 deserialized = new CompressedFheUint80();
    execute(() -> compressed_fhe_uint80_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint80 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint80 encrypted = new CompressedFheUint80();
    execute(() -> compressed_fhe_uint80_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint80 decompress() {
    FheUint80 decompressed = new FheUint80();
    execute(() -> compressed_fhe_uint80_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint80 clone() {
    CompressedFheUint80 cloned = new CompressedFheUint80();
    execute(() -> compressed_fhe_uint80_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
