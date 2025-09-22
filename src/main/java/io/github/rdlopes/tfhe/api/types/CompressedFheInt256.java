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

public class CompressedFheInt256 extends NativePointer
  implements CompressedFheType<I256, FheInt256, CompressedFheInt256> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt256.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int256_destroy(struct CompressedFheInt256 *ptr);
     }
   */
  CompressedFheInt256() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int256_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt256 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt256 deserialized = new CompressedFheInt256();
    execute(() -> compressed_fhe_int256_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt256 encrypt(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt256 encrypted = new CompressedFheInt256();
    execute(() -> compressed_fhe_int256_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt256 decompress() {
    FheInt256 decompressed = new FheInt256();
    execute(() -> compressed_fhe_int256_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int256_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt256 clone() {
    CompressedFheInt256 cloned = new CompressedFheInt256();
    execute(() -> compressed_fhe_int256_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
