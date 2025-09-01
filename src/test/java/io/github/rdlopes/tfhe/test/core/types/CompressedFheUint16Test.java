package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint16;
import io.github.rdlopes.tfhe.core.types.FheUint16;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint16Test {
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
    short originalValue = 1234;
    CompressedFheUint16 compressed = CompressedFheUint16.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    short originalValue = 1234;
    CompressedFheUint16 compressed = CompressedFheUint16.encryptWithClientKey(originalValue, clientKey);

    FheUint16 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint16 original = CompressedFheUint16.encryptWithClientKey((short) 30000, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint16 deserialized = CompressedFheUint16.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint16 decompressed = deserialized.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 30000);
  }

  @Test
  void clones() {
    CompressedFheUint16 original = CompressedFheUint16.encryptWithClientKey((short) 25000, clientKey);

    CompressedFheUint16 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint16 decompressed = cloned.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 25000);
  }

  @Test
  void roundTripFromFheUint16() {
    short originalValue = (short) 40000;
    FheUint16 fheUint16 = FheUint16.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint16 compressed = fheUint16.compress();
    FheUint16 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
