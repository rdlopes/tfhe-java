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

public class CompressedFheInt248 extends NativePointer
  implements CompressedFheType<I256, FheInt248, CompressedFheInt248> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt248.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int248_destroy(struct CompressedFheInt248 *ptr);
     }
   */
  CompressedFheInt248() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int248_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt248 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt248 deserialized = new CompressedFheInt248();
    execute(() -> compressed_fhe_int248_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt248 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt248 encrypted = new CompressedFheInt248();
    execute(() -> compressed_fhe_int248_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt248 decompress() {
    FheInt248 decompressed = new FheInt248();
    execute(() -> compressed_fhe_int248_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int248_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt248 clone() {
    CompressedFheInt248 cloned = new CompressedFheInt248();
    execute(() -> compressed_fhe_int248_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
