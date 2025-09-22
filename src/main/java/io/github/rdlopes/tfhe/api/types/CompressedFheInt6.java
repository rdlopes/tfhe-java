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

public class CompressedFheInt6 extends NativePointer
  implements CompressedFheType<Byte, FheInt6, CompressedFheInt6> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt6.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int6_destroy(struct CompressedFheInt6 *ptr);
     }
   */
  CompressedFheInt6() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int6_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt6 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt6 deserialized = new CompressedFheInt6();
    execute(() -> compressed_fhe_int6_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt6 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheInt6 encrypted = new CompressedFheInt6();
    execute(() -> compressed_fhe_int6_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt6 decompress() {
    FheInt6 decompressed = new FheInt6();
    execute(() -> compressed_fhe_int6_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt6 clone() {
    CompressedFheInt6 cloned = new CompressedFheInt6();
    execute(() -> compressed_fhe_int6_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
