
package io.github.rdlopes.tfhe.api.types.booleans;

import io.github.rdlopes.tfhe.api.keys.Config;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;

class CompressedFheBoolTest {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheBoolTest.class);
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    keySet = config.generateKeys();
    keySet.serverKey()
          .setAsKey();
  }

  @AfterEach
  void tearDown() {
    keySet.destroy();
  }

  @Test
  void serializesAndDeserializesUnsafely() {
    logger.trace("serializesAndDeserializesUnsafely");

    Boolean originalValue = false;
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(originalValue, keySet.clientKey());
    byte[] serialized = original.unsafeSerialize();
    CompressedFheBool deserialized = CompressedFheBool.unsafeDeserialize(serialized);
    FheBool deserializedDecompressed = deserialized.decompress();
    Boolean decrypted = deserializedDecompressed.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializesSafely() {
    logger.trace("serializesAndDeserializesSafely");

    Boolean originalValue = true;
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(originalValue, keySet.clientKey());

    byte[] serialized = original.serialize();
    CompressedFheBool deserialized = CompressedFheBool.deserialize(serialized, keySet.serverKey());
    FheBool deserializedDecompressed = deserialized.decompress();
    Boolean decrypted = deserializedDecompressed.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    logger.trace("compressesAndDecompresses");

    Boolean value = true;
    FheBool original = FheBool.encryptWithClientKey(value, keySet.clientKey());
    CompressedFheBool compressed = original.compress();
    FheBool decompressed = compressed.decompress();
    Boolean decrypted = decompressed.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(true);
  }

}
