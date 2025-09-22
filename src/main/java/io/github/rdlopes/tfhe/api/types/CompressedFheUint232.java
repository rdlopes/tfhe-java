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

public class CompressedFheUint232 extends NativePointer
  implements CompressedFheType<U256, FheUint232, CompressedFheUint232> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint232.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint232_destroy(struct CompressedFheUint232 *ptr);
     }
   */
  CompressedFheUint232() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint232_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint232 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint232 deserialized = new CompressedFheUint232();
    execute(() -> compressed_fhe_uint232_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint232 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint232 encrypted = new CompressedFheUint232();
    execute(() -> compressed_fhe_uint232_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint232 decompress() {
    FheUint232 decompressed = new FheUint232();
    execute(() -> compressed_fhe_uint232_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint232 clone() {
    CompressedFheUint232 cloned = new CompressedFheUint232();
    execute(() -> compressed_fhe_uint232_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
