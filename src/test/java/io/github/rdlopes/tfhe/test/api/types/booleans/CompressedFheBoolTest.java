package io.github.rdlopes.tfhe.test.api.types.booleans;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.booleans.CompressedFheBool;
import io.github.rdlopes.tfhe.api.types.booleans.FheBool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class CompressedFheBoolTest {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheBoolTest.class);

  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @Test
  void roundTrip() {
    Boolean value = true;
    FheBool original = FheBool.encryptWithClientKey(value, clientKey);
    CompressedFheBool compressed = original.compress();
    byte[] buffer = compressed.unsafeSerialize();
    CompressedFheBool deserialized = CompressedFheBool.unsafeDeserialize(buffer);
    FheBool decompressed = deserialized.decompress();
    Boolean decrypted = decompressed.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(true, clientKey);
    CompressedFheBool cloned = original.clone();
    FheBool clonedDecompressed = cloned.decompress();
    Boolean clonedDecrypted = clonedDecompressed.decryptWithClientKey(clientKey);

    assertThat(clonedDecrypted).isEqualTo(true);
  }

  @Test
  void serializesAndDeserializesSafely() {
    Boolean originalValue = true;
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.serialize();
    CompressedFheBool deserialized = CompressedFheBool.deserialize(serialized, serverKey);
    FheBool deserializedDecompressed = deserialized.decompress();
    Boolean decrypted = deserializedDecompressed.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializesUnsafely() {
    Boolean originalValue = false;
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.unsafeSerialize();
    CompressedFheBool deserialized = CompressedFheBool.unsafeDeserialize(serialized);
    FheBool deserializedDecompressed = deserialized.decompress();
    Boolean decrypted = deserializedDecompressed.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(originalValue);
  }

}
