package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint8;
import io.github.rdlopes.tfhe.core.types.FheUint8;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint8Test {
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
    byte originalValue = 42;
    CompressedFheUint8 compressed = CompressedFheUint8.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    byte originalValue = 42;
    CompressedFheUint8 compressed = CompressedFheUint8.encryptWithClientKey(originalValue, clientKey);

    FheUint8 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint8 original = CompressedFheUint8.encryptWithClientKey((byte) 123, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint8 deserialized = CompressedFheUint8.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint8 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 123);
  }

  @Test
  void clones() {
    CompressedFheUint8 original = CompressedFheUint8.encryptWithClientKey((byte) 77, clientKey);

    CompressedFheUint8 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint8 decompressed = cloned.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 77);
  }

  @Test
  void roundTripFromFheUint8() {
    byte originalValue = (byte) 200;
    FheUint8 fheUint8 = FheUint8.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint8 compressed = fheUint8.compress();
    FheUint8 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
