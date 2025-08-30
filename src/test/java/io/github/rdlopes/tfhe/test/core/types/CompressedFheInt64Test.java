package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt64;
import io.github.rdlopes.tfhe.core.types.FheInt64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt64Test {

  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    KeySet keySet = new ConfigBuilder().build()
                                       .generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @Test
  void encryptsWithClientKey() {
    long originalValue = 12345678901234L;
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyNegative() {
    long originalValue = -12345678901234L;
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyMaxValue() {
    long originalValue = 9223372036854775807L; // Max long
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyMinValue() {
    long originalValue = -9223372036854775808L; // Min long
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    long originalValue = 12345678901234L;
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);

    FheInt64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsNegative() {
    long originalValue = -12345678901234L;
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);

    FheInt64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsMaxValue() {
    long originalValue = 9223372036854775807L; // Max long
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);

    FheInt64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsMinValue() {
    long originalValue = -9223372036854775808L; // Min long
    CompressedFheInt64 compressed = CompressedFheInt64.encryptWithClientKey(originalValue, clientKey);

    FheInt64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheInt64 original = CompressedFheInt64.encryptWithClientKey(-30000000000000L, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheInt64 deserialized = CompressedFheInt64.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheInt64 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-30000000000000L);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt64 original = CompressedFheInt64.encryptWithClientKey(-25000000000000L, clientKey);

    CompressedFheInt64 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheInt64 decompressed = cloned.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-25000000000000L);
  }

  @Test
  void roundTripFromFheInt64() {
    long originalValue = -40000000000000L;
    FheInt64 fheInt64 = FheInt64.encryptWithClientKey(originalValue, clientKey);
    CompressedFheInt64 compressed = fheInt64.compress();
    FheInt64 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
