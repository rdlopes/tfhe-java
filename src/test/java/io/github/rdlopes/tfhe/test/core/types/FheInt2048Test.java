package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt2048;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt2048;
import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class FheInt2048Test {
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
  void encryptsAndDecryptsWithClientKey() {
    long originalValue = 123456789L;
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    long originalValue = -987654321L;
    FheInt2048 encrypted = FheInt2048.encryptWithPublicKey(I2048.valueOf(originalValue), publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    String originalValue = "12345678901234567890";
    FheInt2048 encrypted = FheInt2048.encryptTrivial(I2048.valueOf(originalValue));
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I2048 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(200), clientKey);

    FheInt2048 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(200), clientKey);

    encrypted1.addAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  void performsSubOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(500), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(200), clientKey);

    FheInt2048 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(500), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(200), clientKey);

    encrypted1.subAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(15), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(20), clientKey);

    FheInt2048 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(15), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(20), clientKey);

    encrypted1.mulAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300);
  }

  @Test
  void performsAndOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    FheInt2048 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b1000);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    encrypted1.andAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b1000);
  }

  @Test
  void performsOrOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    FheInt2048 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b1110);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    encrypted1.orAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b1110);
  }

  @Test
  void performsXorOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    FheInt2048 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b0110);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(0b1010), clientKey);

    encrypted1.xorAssign(encrypted2);
    I2048 decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0b0110);
  }

  @Test
  void performsScalarAddOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheInt2048 result = encrypted.scalarAdd(I2048.valueOf(50));
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    encrypted.scalarAddAssign(I2048.valueOf(50));
    I2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheInt2048 result = encrypted.scalarSub(I2048.valueOf(30));
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(70);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    encrypted.scalarSubAssign(I2048.valueOf(30));
    I2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(70);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(15), clientKey);

    FheInt2048 result = encrypted.scalarMul(I2048.valueOf(4));
    assertThat(result).isNotNull();

    I2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(60);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(15), clientKey);

    encrypted.scalarMulAssign(I2048.valueOf(4));
    I2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(60);
  }

  @Test
  void performsEqualityOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(42), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(42), clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(42), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(24), clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt2048 encrypted1 = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);
    FheInt2048 encrypted2 = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(42), clientKey);

    FheBool result = encrypted.scalarEq(I2048.valueOf(42));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(42), clientKey);

    FheBool result = encrypted.scalarNe(I2048.valueOf(24));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheBool result = encrypted.scalarGe(I2048.valueOf(50));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(100), clientKey);

    FheBool result = encrypted.scalarGt(I2048.valueOf(50));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);

    FheBool result = encrypted.scalarLe(I2048.valueOf(100));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt2048 encrypted = FheInt2048.encryptWithClientKey(I2048.valueOf(50), clientKey);

    FheBool result = encrypted.scalarLt(I2048.valueOf(100));
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    String originalValue = "98765432109876543210";
    FheInt2048 original = FheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getAddress()).isNotNull();

    FheInt2048 deserialized = FheInt2048.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    I2048 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    String originalValue = "-12345678901234567890";
    FheInt2048 original = FheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);

    CompressedFheInt2048 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt2048 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clones() {
    String originalValue = "555666777888999000111";
    FheInt2048 original = FheInt2048.encryptWithClientKey(I2048.valueOf(originalValue), clientKey);

    FheInt2048 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();

    I2048 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
