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

public class CompressedFheInt128 extends NativePointer
  implements CompressedFheType<I128, FheInt128, CompressedFheInt128> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt128.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int128_destroy(struct CompressedFheInt128 *ptr);
     }
   */
  CompressedFheInt128() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int128_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt128 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt128 deserialized = new CompressedFheInt128();
    execute(() -> compressed_fhe_int128_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt128 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt128 encrypted = new CompressedFheInt128();
    execute(() -> compressed_fhe_int128_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt128 decompress() {
    FheInt128 decompressed = new FheInt128();
    execute(() -> compressed_fhe_int128_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt128 clone() {
    CompressedFheInt128 cloned = new CompressedFheInt128();
    execute(() -> compressed_fhe_int128_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
