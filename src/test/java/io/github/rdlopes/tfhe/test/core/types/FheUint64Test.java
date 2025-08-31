package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint64;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint64Test {

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
    long originalValue = 12345678901234L;
    FheUint64 encrypted = FheUint64.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    long originalValue = 50000000000000L;
    FheUint64 encrypted = FheUint64.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    long originalValue = 9223372036854775807L; // Max long (but treating as unsigned)
    FheUint64 encrypted = FheUint64.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Long decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(1000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(2000000000L, clientKey);

    FheUint64 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(1000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(2000000000L, clientKey);

    encrypted1.addAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsSubOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(5000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(2000000000L, clientKey);

    FheUint64 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(5000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(2000000000L, clientKey);

    encrypted1.subAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsMulOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(50000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(60000L, clientKey);

    FheUint64 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(50000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(60000L, clientKey);

    encrypted1.mulAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);
  }

  @Test
  void performsAndOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFF00000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x00000000FFFFFFFFL, clientKey);

    FheUint64 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0L);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFFFFFFFFFFL, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x00000000FFFFFFFFL, clientKey);

    encrypted1.andAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0x00000000FFFFFFFFL);
  }

  @Test
  void performsOrOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFF00000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x00000000FFFFFFFFL, clientKey);

    FheUint64 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFFFFFFFFFFFFFFL);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFF00000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x0000000000FFFFFFL, clientKey);

    encrypted1.orAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFFFFFF00FFFFFFL);
  }

  @Test
  void performsXorOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFFFFFFFFFFL, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x00000000FFFFFFFFL, clientKey);

    FheUint64 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFFFFFF00000000L);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(0xFFFFFFFFFFFFFFFFL, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(0x00000000FFFFFFFFL, clientKey);

    encrypted1.xorAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(0xFFFFFFFF00000000L);
  }

  @Test
  void performsScalarAddOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(10000000000L, clientKey);

    FheUint64 result = encrypted.scalarAdd(5000000000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15000000000L);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(10000000000L, clientKey);

    encrypted.scalarAddAssign(5000000000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15000000000L);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(15000000000L, clientKey);

    FheUint64 result = encrypted.scalarSub(5000000000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(10000000000L);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(15000000000L, clientKey);

    encrypted.scalarSubAssign(5000000000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(10000000000L);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(1000000L, clientKey);

    FheUint64 result = encrypted.scalarMul(5000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(5000000000L);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(1000000L, clientKey);

    encrypted.scalarMulAssign(5000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(5000000000L);
  }

  @Test
  void performsEqualityOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(12345678901234L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(12345678901234L, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(12345678901234L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(43210987654321L, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(20000000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(10000000000000L, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(20000000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(10000000000000L, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(10000000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(20000000000000L, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint64 encrypted1 = FheUint64.encryptWithClientKey(10000000000000L, clientKey);
    FheUint64 encrypted2 = FheUint64.encryptWithClientKey(20000000000000L, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(12345678901234L, clientKey);

    FheBool result = encrypted.scalarEq(12345678901234L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(12345678901234L, clientKey);

    FheBool result = encrypted.scalarNe(43210987654321L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(20000000000000L, clientKey);

    FheBool result = encrypted.scalarGe(10000000000000L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(20000000000000L, clientKey);

    FheBool result = encrypted.scalarGt(10000000000000L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(10000000000000L, clientKey);

    FheBool result = encrypted.scalarLe(20000000000000L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint64 encrypted = FheUint64.encryptWithClientKey(10000000000000L, clientKey);

    FheBool result = encrypted.scalarLt(20000000000000L);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    long originalValue = 30000000000000L;
    FheUint64 original = FheUint64.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheUint64 deserialized = FheUint64.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    long decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    long originalValue = 40000000000000L;
    FheUint64 original = FheUint64.encryptWithClientKey(originalValue, clientKey);

    CompressedFheUint64 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clonesSuccessfully() {
    long originalValue = 25000000000000L;
    FheUint64 original = FheUint64.encryptWithClientKey(originalValue, clientKey);
    FheUint64 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    long decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
