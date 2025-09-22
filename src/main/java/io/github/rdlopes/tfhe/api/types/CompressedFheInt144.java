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

public class CompressedFheInt144 extends NativePointer
  implements CompressedFheType<I256, FheInt144, CompressedFheInt144> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt144.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int144_destroy(struct CompressedFheInt144 *ptr);
     }
   */
  CompressedFheInt144() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int144_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt144 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt144 deserialized = new CompressedFheInt144();
    execute(() -> compressed_fhe_int144_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt144 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt144 encrypted = new CompressedFheInt144();
    execute(() -> compressed_fhe_int144_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt144 decompress() {
    FheInt144 decompressed = new FheInt144();
    execute(() -> compressed_fhe_int144_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int144_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt144 clone() {
    CompressedFheInt144 cloned = new CompressedFheInt144();
    execute(() -> compressed_fhe_int144_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
