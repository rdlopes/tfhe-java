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

public class CompressedFheUint56 extends NativePointer
  implements CompressedFheType<Long, FheUint56, CompressedFheUint56> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint56.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint56_destroy(struct CompressedFheUint56 *ptr);
     }
   */
  CompressedFheUint56() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint56_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint56 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint56 deserialized = new CompressedFheUint56();
    execute(() -> compressed_fhe_uint56_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint56 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheUint56 encrypted = new CompressedFheUint56();
    execute(() -> compressed_fhe_uint56_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint56 decompress() {
    FheUint56 decompressed = new FheUint56();
    execute(() -> compressed_fhe_uint56_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint56 clone() {
    CompressedFheUint56 cloned = new CompressedFheUint56();
    execute(() -> compressed_fhe_uint56_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
