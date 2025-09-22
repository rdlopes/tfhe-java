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

public class CompressedFheUint24 extends NativePointer
  implements CompressedFheType<Integer, FheUint24, CompressedFheUint24> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint24.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint24_destroy(struct CompressedFheUint24 *ptr);
     }
   */
  CompressedFheUint24() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint24_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint24 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint24 deserialized = new CompressedFheUint24();
    execute(() -> compressed_fhe_uint24_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint24 encrypt(Integer clearValue, ClientKey clientKey) {
    CompressedFheUint24 encrypted = new CompressedFheUint24();
    execute(() -> compressed_fhe_uint24_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint24 decompress() {
    FheUint24 decompressed = new FheUint24();
    execute(() -> compressed_fhe_uint24_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint24 clone() {
    CompressedFheUint24 cloned = new CompressedFheUint24();
    execute(() -> compressed_fhe_uint24_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
