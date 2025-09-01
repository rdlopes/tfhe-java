package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt24;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt24;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt24Test {
  private ClientKey clientKey;
  private ServerKey serverKey;
  private PublicKey publicKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();

    publicKey = PublicKey.newWith(clientKey);
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    int originalValue = 100000;
    FheInt24 encrypted = FheInt24.encryptWithClientKey(originalValue, clientKey);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    int originalValue = 100000;
    FheInt24 encrypted = FheInt24.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = 100000;
    FheInt24 encrypted = FheInt24.encryptTrivial(originalValue);

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheInt24 result = a.add(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    a.addAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsSubOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheInt24 result = a.sub(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(50000);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    a.subAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(50000);
  }

  @Test
  void performsMulOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(3, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(4, clientKey);

    FheInt24 result = a.mul(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(3, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(4, clientKey);

    a.mulAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12);
  }


  @Test
  void performsAndOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(100000, clientKey);

    FheInt24 result = a.and(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(100000, clientKey);

    a.andAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsOrOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheInt24 result = a.or(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(116720);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    a.orAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(116720);
  }

  @Test
  void performsXorOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheInt24 result = a.xor(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(83440);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    a.xorAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(83440);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheInt24 result = a.scalarAdd(10000);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(110000);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    a.scalarAddAssign(10000);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(110000);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheInt24 result = a.scalarSub(10000);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(90000);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    a.scalarSubAssign(10000);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(90000);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);

    FheInt24 result = a.scalarMul(2);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);

    a.scalarMulAssign(2);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }


  @Test
  void performsEqualityOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);
    FheInt24 b = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarEq(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarNe(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarGe(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarGt(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.scalarLe(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt24 a = FheInt24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.scalarLt(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt24 original = FheInt24.encryptWithClientKey(150000, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt24 deserialized = FheInt24.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    int decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt24 original = FheInt24.encryptWithClientKey(150000, clientKey);

    CompressedFheInt24 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt24 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void clones() {
    FheInt24 original = FheInt24.encryptWithClientKey(150000, clientKey);

    FheInt24 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    int decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }
}
