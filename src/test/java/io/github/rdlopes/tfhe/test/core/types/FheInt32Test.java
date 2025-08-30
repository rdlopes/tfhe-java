package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt32;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt32;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt32Test {

  private ClientKey clientKey;
  private PublicKey publicKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    KeySet keySet = new ConfigBuilder().build()
                                       .generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();

    publicKey = PublicKey.newWith(clientKey);
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    int originalValue = 123456;
    FheInt32 encrypted = FheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithClientKeyNegative() {
    int originalValue = -123456;
    FheInt32 encrypted = FheInt32.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    int originalValue = 500000;
    FheInt32 encrypted = FheInt32.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKeyNegative() {
    int originalValue = -500000;
    FheInt32 encrypted = FheInt32.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = 2147483647; // Max int32
    FheInt32 encrypted = FheInt32.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivialNegative() {
    int originalValue = -2147483648; // Min int32
    FheInt32 encrypted = FheInt32.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(10000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(20000, clientKey);

    FheInt32 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsAddOperationWithNegative() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(30000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(-10000, clientKey);

    FheInt32 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(20000);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(10000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(20000, clientKey);

    encrypted1.addAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsSubOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(50000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(20000, clientKey);

    FheInt32 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsSubOperationResultingNegative() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(20000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(50000, clientKey);

    FheInt32 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-30000);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(50000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(20000, clientKey);

    encrypted1.subAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(30000);
  }

  @Test
  void performsMulOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(500, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(600, clientKey);

    FheInt32 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300000);
  }

  @Test
  void performsMulOperationWithNegative() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(-500, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(600, clientKey);

    FheInt32 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-300000);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(500, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(600, clientKey);

    encrypted1.mulAssign(encrypted2);
    int decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(300000);
  }

  @Test
  void performsScalarAddOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(100000, clientKey);

    FheInt32 result = encrypted.scalarAdd(50000);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsScalarAddOperationWithNegativeScalar() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(100000, clientKey);

    FheInt32 result = encrypted.scalarAdd(-50000);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(50000);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(100000, clientKey);

    encrypted.scalarAddAssign(50000);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(150000, clientKey);

    FheInt32 result = encrypted.scalarSub(50000);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(150000, clientKey);

    encrypted.scalarSubAssign(50000);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(1000, clientKey);

    FheInt32 result = encrypted.scalarMul(500);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000);
  }

  @Test
  void performsScalarMulOperationWithNegativeScalar() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(1000, clientKey);

    FheInt32 result = encrypted.scalarMul(-500);
    assertThat(result).isNotNull();

    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(-500000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(1000, clientKey);

    encrypted.scalarMulAssign(500);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000);
  }

  @Test
  void performsEqualityOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(1234567, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(1234567, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsEqualityOperationWithNegative() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(-1234567, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(-1234567, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(1234567, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(-1234567, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(2000000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperationWithNegative() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(-1000000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(-2000000, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(2000000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(-2000000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(-1000000, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt32 encrypted1 = FheInt32.encryptWithClientKey(1000000, clientKey);
    FheInt32 encrypted2 = FheInt32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(-1234567, clientKey);

    FheBool result = encrypted.scalarEq(-1234567);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(1234567, clientKey);

    FheBool result = encrypted.scalarNe(-1234567);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted.scalarGe(1000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(2000000, clientKey);

    FheBool result = encrypted.scalarGt(1000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(-2000000, clientKey);

    FheBool result = encrypted.scalarLe(-1000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt32 encrypted = FheInt32.encryptWithClientKey(1000000, clientKey);

    FheBool result = encrypted.scalarLt(2000000);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    int originalValue = -3000000;
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
    int originalValue = -4000000;
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
  void clonesSuccessfully() {
    int originalValue = -2500000;
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
