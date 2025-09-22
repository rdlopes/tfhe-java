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

public class CompressedFheInt1024 extends NativePointer
  implements CompressedFheType<I1024, FheInt1024, CompressedFheInt1024> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt1024.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int1024_destroy(struct CompressedFheInt1024 *ptr);
     }
   */
  CompressedFheInt1024() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int1024_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt1024 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt1024 deserialized = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt1024 encrypt(I1024 clearValue, ClientKey clientKey) {
    CompressedFheInt1024 encrypted = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_try_encrypt_with_client_key_i1024(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt1024 decompress() {
    FheInt1024 decompressed = new FheInt1024();
    execute(() -> compressed_fhe_int1024_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt1024 clone() {
    CompressedFheInt1024 cloned = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
