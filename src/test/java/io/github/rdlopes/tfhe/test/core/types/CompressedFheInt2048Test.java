package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt2048;
import io.github.rdlopes.tfhe.core.types.FheInt2048;
import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

@Tag("largeBitSize")
class CompressedFheInt2048Test {
  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();

    serverKey.setAsKey();
  }


  @Test
  void encryptsWithClientKey() {
    long originalValue = 123456789L;
    CompressedFheInt2048 compressed = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

  }

  @Test
  void decompressesAndDecrypts() {
    long originalValue = -987654321L;
    CompressedFheInt2048 compressed = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);

    FheInt2048 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void serializesAndDeserializes() {
    String originalValue = "12345678901234567890";
    CompressedFheInt2048 original = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheInt2048 deserialized = CompressedFheInt2048.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheInt2048 decompressed = deserialized.decompress();
    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void clones() {
    String originalValue = "-555666777888999000111";
    CompressedFheInt2048 original = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);

    CompressedFheInt2048 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheInt2048 decompressed = cloned.decompress();
    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void roundTripFromFheInt2048() {
    String originalValue = "98765432109876543210987654321098765432109876543210";
    FheInt2048 fheInt2048 = FheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);
    CompressedFheInt2048 compressed = fheInt2048.compress();
    FheInt2048 decompressed = compressed.decompress();
    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }
}
