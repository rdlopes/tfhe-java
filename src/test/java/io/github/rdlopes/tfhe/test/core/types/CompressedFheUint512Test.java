package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint512;
import io.github.rdlopes.tfhe.core.types.FheUint512;
import io.github.rdlopes.tfhe.core.types.U512;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint512Test {
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
    U512 originalValue = U512.valueOf("1000");
    CompressedFheUint512 compressed = CompressedFheUint512.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    U512 originalValue = U512.valueOf("1000");
    CompressedFheUint512 compressed = CompressedFheUint512.encryptWithClientKey(originalValue, clientKey);

    FheUint512 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheUint512 original = CompressedFheUint512.encryptWithClientKey(U512.valueOf("700"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheUint512 deserialized = CompressedFheUint512.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheUint512 decompressed = deserialized.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("700"));
  }

  @Test
  void clones() {
    CompressedFheUint512 original = CompressedFheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);

    CompressedFheUint512 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheUint512 decompressed = cloned.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void roundTripFromFheUint512() {
    U512 originalValue = U512.valueOf("1500");
    FheUint512 fheuint512 = FheUint512.encryptWithClientKey(originalValue, clientKey);
    CompressedFheUint512 compressed = fheuint512.compress();
    FheUint512 decompressed = compressed.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
