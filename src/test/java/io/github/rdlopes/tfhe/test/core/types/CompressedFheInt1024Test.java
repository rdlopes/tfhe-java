package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt1024;
import io.github.rdlopes.tfhe.core.types.FheInt1024;
import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt1024Test {
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
    I1024 originalValue = I1024.valueOf("1000");
    CompressedFheInt1024 compressed = CompressedFheInt1024.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    I1024 originalValue = I1024.valueOf("1000");
    CompressedFheInt1024 compressed = CompressedFheInt1024.encryptWithClientKey(originalValue, clientKey);

    FheInt1024 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheInt1024 original = CompressedFheInt1024.encryptWithClientKey(I1024.valueOf("700"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheInt1024 deserialized = CompressedFheInt1024.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheInt1024 decompressed = deserialized.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("700"));
  }

  @Test
  void clones() {
    CompressedFheInt1024 original = CompressedFheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);

    CompressedFheInt1024 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheInt1024 decompressed = cloned.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void roundTripFromFheInt1024() {
    I1024 originalValue = I1024.valueOf("1500");
    FheInt1024 fheint1024 = FheInt1024.encryptWithClientKey(originalValue, clientKey);
    CompressedFheInt1024 compressed = fheint1024.compress();
    FheInt1024 decompressed = compressed.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
