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

public class CompressedFheUint152 extends NativePointer
  implements CompressedFheType<U256, FheUint152, CompressedFheUint152> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint152.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint152_destroy(struct CompressedFheUint152 *ptr);
     }
   */
  CompressedFheUint152() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint152_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint152 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint152 deserialized = new CompressedFheUint152();
    execute(() -> compressed_fhe_uint152_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint152 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint152 encrypted = new CompressedFheUint152();
    execute(() -> compressed_fhe_uint152_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint152 decompress() {
    FheUint152 decompressed = new FheUint152();
    execute(() -> compressed_fhe_uint152_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint152 clone() {
    CompressedFheUint152 cloned = new CompressedFheUint152();
    execute(() -> compressed_fhe_uint152_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
