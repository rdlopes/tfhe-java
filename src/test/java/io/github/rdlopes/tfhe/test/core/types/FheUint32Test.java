package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint32;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint32;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint32Test {

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
  void encryptsAndDecryptsWithClientKey() {
    int originalValue = 123456;
    FheUint32 encrypted = FheUint32.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    int originalValue = 500000;
    FheUint32 encrypted = FheUint32.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = (int) 4294967295L;
    FheUint32 encrypted = FheUint32.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(10000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(20000, clientKey);

    FheUint32 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(10000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(20000, clientKey);

    encrypted1.addAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsSubOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(50000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(20000, clientKey);

    FheUint32 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(50000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(20000, clientKey);

    encrypted1.subAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsMulOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(500, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(600, clientKey);

    FheUint32 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300000);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(500, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(600, clientKey);

    encrypted1.mulAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300000);
  }

  @Test
  void performsAndOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFFFF0000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x0000FFFF, clientKey);

    FheUint32 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFFFFFFFF, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x0000FFFF, clientKey);

    encrypted1.andAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0x0000FFFF);
  }

  @Test
  void performsOrOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFFFF0000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x0000FFFF, clientKey);

    FheUint32 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFFFFFF);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFF000000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x00FF0000, clientKey);

    encrypted1.orAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFF0000);
  }

  @Test
  void performsXorOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFFFFFFFF, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x0000FFFF, clientKey);

    FheUint32 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFF0000);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(0xFFFFFFFF, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(0x0000FFFF, clientKey);

    encrypted1.xorAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFF0000);
  }

  @Test
  void performsScalarAddOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(100000, clientKey);

    FheUint32 result = encrypted.scalarAdd(50000);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(100000, clientKey);

    encrypted.scalarAddAssign(50000);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(150000, clientKey);

    FheUint32 result = encrypted.scalarSub(50000);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(150000, clientKey);

    encrypted.scalarSubAssign(50000);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1000, clientKey);

    FheUint32 result = encrypted.scalarMul(500);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1000, clientKey);

    encrypted.scalarMulAssign(500);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000);
  }

  @Test
  void performsEqualityOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(1234567, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(1234567, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(1234567, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(7654321, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(2000000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(2000000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(1000000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint32 encrypted1 = FheUint32.encryptWithClientKey(1000000, clientKey);
    FheUint32 encrypted2 = FheUint32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1234567, clientKey);

    FheBool result = encrypted.scalarEq(1234567);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1234567, clientKey);

    FheBool result = encrypted.scalarNe(7654321);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted.scalarGe(1000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted.scalarGt(1000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted.scalarLe(2000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint32 encrypted = FheUint32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted.scalarLt(2000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    int originalValue = 3000000;
    FheUint32 original = FheUint32.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheUint32 deserialized = FheUint32.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    int decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    int originalValue = 4000000;
    FheUint32 original = FheUint32.encryptWithClientKey(originalValue, clientKey);

    CompressedFheUint32 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint32 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clonesSuccessfully() {
    int originalValue = 2500000;
    FheUint32 original = FheUint32.encryptWithClientKey(originalValue, clientKey);
    FheUint32 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    int decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
