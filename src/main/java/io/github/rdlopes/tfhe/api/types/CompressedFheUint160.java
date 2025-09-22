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

public class CompressedFheUint160 extends NativePointer
  implements CompressedFheType<U256, FheUint160, CompressedFheUint160> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint160.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint160_destroy(struct CompressedFheUint160 *ptr);
     }
   */
  CompressedFheUint160() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint160_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint160 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint160 deserialized = new CompressedFheUint160();
    execute(() -> compressed_fhe_uint160_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint160 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint160 encrypted = new CompressedFheUint160();
    execute(() -> compressed_fhe_uint160_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint160 decompress() {
    FheUint160 decompressed = new FheUint160();
    execute(() -> compressed_fhe_uint160_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint160_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint160 clone() {
    CompressedFheUint160 cloned = new CompressedFheUint160();
    execute(() -> compressed_fhe_uint160_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
