package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheBool;
import io.github.rdlopes.tfhe.core.types.FheBool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheBoolTest {
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
  void encryptsWithClientKey() {
    boolean originalValue = true;
    CompressedFheBool compressed = CompressedFheBool.encryptWithClientKey(originalValue, clientKey);

    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    boolean originalValue = true;
    CompressedFheBool compressed = CompressedFheBool.encryptWithClientKey(originalValue, clientKey);

    FheBool decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsFalse() {
    boolean originalValue = false;
    CompressedFheBool compressed = CompressedFheBool.encryptWithClientKey(originalValue, clientKey);

    FheBool decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(true, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheBool deserialized = CompressedFheBool.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheBool decompressed = deserialized.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(true, clientKey);

    CompressedFheBool cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheBool decompressed = cloned.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void roundTripFromFheBool() {
    boolean originalValue = true;
    FheBool fheBool = FheBool.encryptWithClientKey(originalValue, clientKey);
    CompressedFheBool compressed = fheBool.compress();
    FheBool decompressed = compressed.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
