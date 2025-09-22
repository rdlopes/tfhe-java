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

public class CompressedFheInt4 extends NativePointer
  implements CompressedFheType<Byte, FheInt4, CompressedFheInt4> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt4.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int4_destroy(struct CompressedFheInt4 *ptr);
     }
   */
  CompressedFheInt4() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int4_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt4 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt4 deserialized = new CompressedFheInt4();
    execute(() -> compressed_fhe_int4_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt4 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheInt4 encrypted = new CompressedFheInt4();
    execute(() -> compressed_fhe_int4_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt4 decompress() {
    FheInt4 decompressed = new FheInt4();
    execute(() -> compressed_fhe_int4_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt4 clone() {
    CompressedFheInt4 cloned = new CompressedFheInt4();
    execute(() -> compressed_fhe_int4_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
