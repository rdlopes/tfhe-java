package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint32;
import io.github.rdlopes.tfhe.core.types.FheUint32;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint32Test {
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
    int originalValue = 123456;
    CompressedFheUint32 compressed = CompressedFheUint32.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

  }

  @Test
  void decompressesAndDecrypts() {
    int originalValue = 123456;
    CompressedFheUint32 compressed = CompressedFheUint32.encryptWithClientKey(originalValue, clientKey);

    FheUint32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint32 original = CompressedFheUint32.encryptWithClientKey(3000000, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint32 deserialized = CompressedFheUint32.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint32 decompressed = deserialized.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000);

  }

  @Test
  void clones() {
    CompressedFheUint32 original = CompressedFheUint32.encryptWithClientKey(2500000, clientKey);

    CompressedFheUint32 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint32 decompressed = cloned.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(2500000);

  }

  @Test
  void roundTripFromFheUint32() {
    int originalValue = 4000000;
    FheUint32 fheUint32 = FheUint32.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint32 compressed = fheUint32.compress();
    FheUint32 decompressed = compressed.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }
}
