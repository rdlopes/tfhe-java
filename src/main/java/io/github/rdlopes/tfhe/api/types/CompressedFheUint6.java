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

public class CompressedFheUint6 extends NativePointer
  implements CompressedFheType<Byte, FheUint6, CompressedFheUint6> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint6.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint6_destroy(struct CompressedFheUint6 *ptr);
     }
   */
  CompressedFheUint6() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint6_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint6 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint6 deserialized = new CompressedFheUint6();
    execute(() -> compressed_fhe_uint6_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint6 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheUint6 encrypted = new CompressedFheUint6();
    execute(() -> compressed_fhe_uint6_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint6 decompress() {
    FheUint6 decompressed = new FheUint6();
    execute(() -> compressed_fhe_uint6_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint6 clone() {
    CompressedFheUint6 cloned = new CompressedFheUint6();
    execute(() -> compressed_fhe_uint6_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
