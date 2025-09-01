package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint1024;
import io.github.rdlopes.tfhe.core.types.FheUint1024;
import io.github.rdlopes.tfhe.core.types.U1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint1024Test {
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
    U1024 originalValue = U1024.valueOf("1000");
    CompressedFheUint1024 compressed = CompressedFheUint1024.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    U1024 originalValue = U1024.valueOf("1000");
    CompressedFheUint1024 compressed = CompressedFheUint1024.encryptWithClientKey(originalValue, clientKey);

    FheUint1024 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint1024 original = CompressedFheUint1024.encryptWithClientKey(U1024.valueOf("700"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint1024 deserialized = CompressedFheUint1024.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint1024 decompressed = deserialized.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("700"));
  }

  @Test
  void clones() {
    CompressedFheUint1024 original = CompressedFheUint1024.encryptWithClientKey(U1024.valueOf("1500"), clientKey);

    CompressedFheUint1024 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint1024 decompressed = cloned.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("1500"));
  }

  @Test
  void roundTripFromFheUint1024() {
    U1024 originalValue = U1024.valueOf("1500");
    FheUint1024 fheuint1024 = FheUint1024.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint1024 compressed = fheuint1024.compress();
    FheUint1024 decompressed = compressed.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
