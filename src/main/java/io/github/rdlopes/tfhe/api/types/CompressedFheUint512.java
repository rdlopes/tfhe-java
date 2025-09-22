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

public class CompressedFheUint512 extends NativePointer
  implements CompressedFheType<U512, FheUint512, CompressedFheUint512> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint512.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint512_destroy(struct CompressedFheUint512 *ptr);
     }
   */
  CompressedFheUint512() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint512_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint512 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint512 deserialized = new CompressedFheUint512();
    execute(() -> compressed_fhe_uint512_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint512 encrypt(U512 clearValue, ClientKey clientKey) {
    CompressedFheUint512 encrypted = new CompressedFheUint512();
    execute(() -> compressed_fhe_uint512_try_encrypt_with_client_key_u512(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint512 decompress() {
    FheUint512 decompressed = new FheUint512();
    execute(() -> compressed_fhe_uint512_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint512 clone() {
    CompressedFheUint512 cloned = new CompressedFheUint512();
    execute(() -> compressed_fhe_uint512_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
