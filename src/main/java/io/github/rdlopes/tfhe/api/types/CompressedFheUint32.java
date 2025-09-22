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

public class CompressedFheUint32 extends NativePointer
  implements CompressedFheType<Integer, FheUint32, CompressedFheUint32> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint32.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint32_destroy(struct CompressedFheUint32 *ptr);
     }
   */
  CompressedFheUint32() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint32_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint32 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint32 deserialized = new CompressedFheUint32();
    execute(() -> compressed_fhe_uint32_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint32 encrypt(Integer clearValue, ClientKey clientKey) {
    CompressedFheUint32 encrypted = new CompressedFheUint32();
    execute(() -> compressed_fhe_uint32_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint32 decompress() {
    FheUint32 decompressed = new FheUint32();
    execute(() -> compressed_fhe_uint32_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint32 clone() {
    CompressedFheUint32 cloned = new CompressedFheUint32();
    execute(() -> compressed_fhe_uint32_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
