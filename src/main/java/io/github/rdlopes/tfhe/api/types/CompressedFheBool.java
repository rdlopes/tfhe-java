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

public class CompressedFheBool extends NativePointer
  implements CompressedFheType<Boolean, FheBool, CompressedFheBool> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheBool.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_bool_destroy(struct CompressedFheBool *ptr);
     }
   */
  CompressedFheBool() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_bool_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheBool deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheBool deserialized = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheBool encrypt(Boolean clearValue, ClientKey clientKey) {
    CompressedFheBool encrypted = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool decompress() {
    FheBool decompressed = new FheBool();
    execute(() -> compressed_fhe_bool_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheBool clone() {
    CompressedFheBool cloned = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
