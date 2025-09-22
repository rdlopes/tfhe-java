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

public class CompressedFheInt152 extends NativePointer
  implements CompressedFheType<I256, FheInt152, CompressedFheInt152> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt152.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int152_destroy(struct CompressedFheInt152 *ptr);
     }
   */
  CompressedFheInt152() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int152_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt152 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt152 deserialized = new CompressedFheInt152();
    execute(() -> compressed_fhe_int152_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt152 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt152 encrypted = new CompressedFheInt152();
    execute(() -> compressed_fhe_int152_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt152 decompress() {
    FheInt152 decompressed = new FheInt152();
    execute(() -> compressed_fhe_int152_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt152 clone() {
    CompressedFheInt152 cloned = new CompressedFheInt152();
    execute(() -> compressed_fhe_int152_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
