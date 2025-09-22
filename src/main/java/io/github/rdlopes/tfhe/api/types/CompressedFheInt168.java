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

public class CompressedFheInt168 extends NativePointer
  implements CompressedFheType<I256, FheInt168, CompressedFheInt168> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt168.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int168_destroy(struct CompressedFheInt168 *ptr);
     }
   */
  CompressedFheInt168() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int168_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt168 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt168 deserialized = new CompressedFheInt168();
    execute(() -> compressed_fhe_int168_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt168 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt168 encrypted = new CompressedFheInt168();
    execute(() -> compressed_fhe_int168_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 decompress() {
    FheInt168 decompressed = new FheInt168();
    execute(() -> compressed_fhe_int168_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt168 clone() {
    CompressedFheInt168 cloned = new CompressedFheInt168();
    execute(() -> compressed_fhe_int168_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
