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

  @AfterEach
  void tearDown() {
    configBuilder.cleanNativeResources();
    config.cleanNativeResources();
    clientKey.cleanNativeResources();
    serverKey.cleanNativeResources();
  }

  @Test
  void encryptsWithClientKey() {
    byte originalValue = 42;
    CompressedFheUint8 compressed = CompressedFheUint8.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    compressed.cleanNativeResources();
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

    compressed.cleanNativeResources();
    decompressed.cleanNativeResources();
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

    original.cleanNativeResources();
    buffer.cleanNativeResources();
    deserialized.cleanNativeResources();
    decompressed.cleanNativeResources();
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

    original.cleanNativeResources();
    cloned.cleanNativeResources();
    decompressed.cleanNativeResources();
  }

  @Test
  void roundTripFromFheUint8() {
    byte originalValue = (byte) 200;
    FheUint8 fheUint8 = FheUint8.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint8 compressed = fheUint8.compress();
    FheUint8 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

    fheUint8.cleanNativeResources();
    compressed.cleanNativeResources();
    decompressed.cleanNativeResources();
  }
}
