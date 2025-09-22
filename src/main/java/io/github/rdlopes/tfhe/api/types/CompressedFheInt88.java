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

public class CompressedFheInt88 extends NativePointer
  implements CompressedFheType<I128, FheInt88, CompressedFheInt88> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt88.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int88_destroy(struct CompressedFheInt88 *ptr);
     }
   */
  CompressedFheInt88() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int88_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt88 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt88 deserialized = new CompressedFheInt88();
    execute(() -> compressed_fhe_int88_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt88 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt88 encrypted = new CompressedFheInt88();
    execute(() -> compressed_fhe_int88_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt88 decompress() {
    FheInt88 decompressed = new FheInt88();
    execute(() -> compressed_fhe_int88_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt88 clone() {
    CompressedFheInt88 cloned = new CompressedFheInt88();
    execute(() -> compressed_fhe_int88_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
