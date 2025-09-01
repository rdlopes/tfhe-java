package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint64;
import io.github.rdlopes.tfhe.core.types.FheUint64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint64Test {
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

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
  }

  @Test
  void encryptsWithClientKey() {
    long originalValue = 12345678901234L;
    CompressedFheUint64 compressed = CompressedFheUint64.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    long originalValue = 12345678901234L;
    CompressedFheUint64 compressed = CompressedFheUint64.encryptWithClientKey(originalValue, clientKey);

    FheUint64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint64 original = CompressedFheUint64.encryptWithClientKey(30000000000000L, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint64 deserialized = CompressedFheUint64.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint64 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000000000000L);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint64 original = CompressedFheUint64.encryptWithClientKey(25000000000000L, clientKey);

    CompressedFheUint64 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint64 decompressed = cloned.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(25000000000000L);
  }

  @Test
  void roundTripFromFheUint64() {
    long originalValue = 40000000000000L;
    FheUint64 fheUint64 = FheUint64.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint64 compressed = fheUint64.compress();
    FheUint64 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
