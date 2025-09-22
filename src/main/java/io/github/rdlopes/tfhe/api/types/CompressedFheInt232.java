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

public class CompressedFheInt232 extends NativePointer
  implements CompressedFheType<I256, FheInt232, CompressedFheInt232> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt232.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int232_destroy(struct CompressedFheInt232 *ptr);
     }
   */
  CompressedFheInt232() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int232_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt232 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt232 deserialized = new CompressedFheInt232();
    execute(() -> compressed_fhe_int232_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt232 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt232 encrypted = new CompressedFheInt232();
    execute(() -> compressed_fhe_int232_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt232 decompress() {
    FheInt232 decompressed = new FheInt232();
    execute(() -> compressed_fhe_int232_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt232 clone() {
    CompressedFheInt232 cloned = new CompressedFheInt232();
    execute(() -> compressed_fhe_int232_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
