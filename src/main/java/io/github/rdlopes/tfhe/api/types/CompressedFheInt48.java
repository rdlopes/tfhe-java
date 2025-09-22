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

public class CompressedFheInt48 extends NativePointer
  implements CompressedFheType<Long, FheInt48, CompressedFheInt48> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt48.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int48_destroy(struct CompressedFheInt48 *ptr);
     }
   */
  CompressedFheInt48() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int48_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt48 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt48 deserialized = new CompressedFheInt48();
    execute(() -> compressed_fhe_int48_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt48 encrypt(Long clearValue, ClientKey clientKey) {
    CompressedFheInt48 encrypted = new CompressedFheInt48();
    execute(() -> compressed_fhe_int48_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt48 decompress() {
    FheInt48 decompressed = new FheInt48();
    execute(() -> compressed_fhe_int48_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt48 clone() {
    CompressedFheInt48 cloned = new CompressedFheInt48();
    execute(() -> compressed_fhe_int48_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
