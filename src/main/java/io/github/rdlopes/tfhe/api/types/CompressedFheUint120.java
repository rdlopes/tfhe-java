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

public class CompressedFheUint120 extends NativePointer
  implements CompressedFheType<U128, FheUint120, CompressedFheUint120> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint120.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint120_destroy(struct CompressedFheUint120 *ptr);
     }
   */
  CompressedFheUint120() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint120_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint120 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint120 deserialized = new CompressedFheUint120();
    execute(() -> compressed_fhe_uint120_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint120 encrypt(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint120 encrypted = new CompressedFheUint120();
    execute(() -> compressed_fhe_uint120_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint120 decompress() {
    FheUint120 decompressed = new FheUint120();
    execute(() -> compressed_fhe_uint120_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint120_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint120 clone() {
    CompressedFheUint120 cloned = new CompressedFheUint120();
    execute(() -> compressed_fhe_uint120_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
