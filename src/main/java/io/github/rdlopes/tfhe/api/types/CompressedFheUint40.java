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

public class CompressedFheUint40 extends NativePointer
  implements CompressedFheType<Long, FheUint40, CompressedFheUint40> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint40.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint40_destroy(struct CompressedFheUint40 *ptr);
     }
   */
  CompressedFheUint40() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint40_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint40 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint40 deserialized = new CompressedFheUint40();
    execute(() -> compressed_fhe_uint40_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint40 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheUint40 encrypted = new CompressedFheUint40();
    execute(() -> compressed_fhe_uint40_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint40 decompress() {
    FheUint40 decompressed = new FheUint40();
    execute(() -> compressed_fhe_uint40_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint40 clone() {
    CompressedFheUint40 cloned = new CompressedFheUint40();
    execute(() -> compressed_fhe_uint40_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
