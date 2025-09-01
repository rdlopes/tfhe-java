package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt32;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt32;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt32Test {
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
    int originalValue = 42;
    FheInt32 encrypted = FheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    int originalValue = 100;
    FheInt32 encrypted = FheInt32.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = -123456;
    FheInt32 encrypted = FheInt32.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(10, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(15, clientKey);

    FheInt32 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(25);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(10, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(15, clientKey);

    encrypted1.addAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(25);
  }

  @Test
  void performsSubOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(30, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(15, clientKey);

    FheInt32 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(30, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(15, clientKey);

    encrypted1.subAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15);
  }

  @Test
  void performsMulOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(5, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(6, clientKey);

    FheInt32 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(5, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(6, clientKey);

    encrypted1.mulAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30);
  }

  @Test
  void performsAndOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(15, clientKey); // 0x0F
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(7, clientKey);  // 0x07

    FheInt32 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(7); // 0x07
  }

  @Test
  void performsAndAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(15, clientKey); // 0x0F
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(7, clientKey);  // 0x07

    encrypted1.andAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(7); // 0x07
  }

  @Test
  void performsOrOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(8, clientKey);  // 0x08
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(4, clientKey);  // 0x04

    FheInt32 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12); // 0x0C
  }

  @Test
  void performsOrAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(8, clientKey);  // 0x08
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(4, clientKey);  // 0x04

    encrypted1.orAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12); // 0x0C
  }

  @Test
  void performsXorOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(15, clientKey); // 0x0F
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(7, clientKey);  // 0x07

    FheInt32 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(8); // 0x08
  }

  @Test
  void performsXorAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(15, clientKey); // 0x0F
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(7, clientKey);  // 0x07

    encrypted1.xorAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(8); // 0x08
  }

  @Test
  void performsScalarAddOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(10, clientKey);

    FheInt32 result = encrypted.scalarAdd(15);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(25);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(10, clientKey);

    encrypted.scalarAddAssign(15);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(25);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(30, clientKey);

    FheInt32 result = encrypted.scalarSub(15);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(30, clientKey);

    encrypted.scalarSubAssign(15);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(5, clientKey);

    FheInt32 result = encrypted.scalarMul(6);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(5, clientKey);

    encrypted.scalarMulAssign(6);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30);
  }

  @Test
  void performsEqualityOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(42, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(42, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(24, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(50, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(50, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(30, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(30, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted.scalarEq(42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(42, clientKey);

    FheBool result = encrypted.scalarNe(24);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(50, clientKey);

    FheBool result = encrypted.scalarGe(42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(50, clientKey);

    FheBool result = encrypted.scalarGt(42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(30, clientKey);

    FheBool result = encrypted.scalarLe(42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(30, clientKey);

    FheBool result = encrypted.scalarLt(42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    int originalValue = 123456;
    FheInt32 original = FheInt32.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheInt32 deserialized = FheInt32.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    int decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    int originalValue = -789012;
    FheInt32 original = FheInt32.encryptWithClientKey(originalValue, clientKey);

    CompressedFheInt32 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clones() {
    int originalValue = 77777;
    FheInt32 original = FheInt32.encryptWithClientKey(originalValue, clientKey);
    FheInt32 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    int decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
