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

public class CompressedFheInt2 extends NativePointer
  implements CompressedFheType<Byte, FheInt2, CompressedFheInt2> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt2.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int2_destroy(struct CompressedFheInt2 *ptr);
     }
   */
  CompressedFheInt2() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int2_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt2 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt2 deserialized = new CompressedFheInt2();
    execute(() -> compressed_fhe_int2_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt2 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheInt2 encrypted = new CompressedFheInt2();
    execute(() -> compressed_fhe_int2_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt2 decompress() {
    FheInt2 decompressed = new FheInt2();
    execute(() -> compressed_fhe_int2_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int2_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt2 clone() {
    CompressedFheInt2 cloned = new CompressedFheInt2();
    execute(() -> compressed_fhe_int2_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
