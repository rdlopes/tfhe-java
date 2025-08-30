package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt32;
import io.github.rdlopes.tfhe.core.types.FheInt32;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt32Test {

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
    int originalValue = 123456;
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyNegative() {
    int originalValue = -123456;
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyMaxValue() {
    int originalValue = 2147483647; // Max int32
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void encryptsWithClientKeyMinValue() {
    int originalValue = -2147483648; // Min int32
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();
  }

  @Test
  void decompressesAndDecrypts() {
    int originalValue = 123456;
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);

    FheInt32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsNegative() {
    int originalValue = -123456;
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);

    FheInt32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsMaxValue() {
    int originalValue = 2147483647; // Max int32
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);

    FheInt32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void decompressesAndDecryptsMinValue() {
    int originalValue = -2147483648; // Min int32
    CompressedFheInt32 compressed = CompressedFheInt32.encryptWithClientKey(originalValue, clientKey);

    FheInt32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheInt32 original = CompressedFheInt32.encryptWithClientKey(-3000000, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    CompressedFheInt32 deserialized = CompressedFheInt32.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    FheInt32 decompressed = deserialized.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-3000000);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt32 original = CompressedFheInt32.encryptWithClientKey(-2500000, clientKey);

    CompressedFheInt32 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    FheInt32 decompressed = cloned.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-2500000);
  }

  @Test
  void roundTripFromFheInt32() {
    int originalValue = -4000000;
    FheInt32 fheInt32 = FheInt32.encryptWithClientKey(originalValue, clientKey);
    CompressedFheInt32 compressed = fheInt32.compress();
    FheInt32 decompressed = compressed.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
