package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint24;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint24;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint24Test {
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
    FheUint24 encrypted = FheUint24.encryptWithClientKey(originalValue, clientKey);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    int originalValue = 100000;
    FheUint24 encrypted = FheUint24.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = 100000;
    FheUint24 encrypted = FheUint24.encryptTrivial(originalValue);

    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheUint24 result = a.add(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    a.addAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void performsSubOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheUint24 result = a.sub(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(50000);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    a.subAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(50000);
  }

  @Test
  void performsMulOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(3, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(4, clientKey);

    FheUint24 result = a.mul(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(3, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(4, clientKey);

    a.mulAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12);
  }


  @Test
  void performsAndOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(150000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(100000, clientKey);

    FheUint24 result = a.and(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(160);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(150000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(100000, clientKey);

    a.andAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(160);
  }

  @Test
  void performsOrOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheUint24 result = a.or(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(116720);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    a.orAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(116720);
  }

  @Test
  void performsXorOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheUint24 result = a.xor(b);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(83440);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    a.xorAssign(b);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(83440);
  }


  @Test
  void performsScalarAddOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheUint24 result = a.scalarAdd(10000);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(110000);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    a.scalarAddAssign(10000);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(110000);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheUint24 result = a.scalarSub(10000);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(90000);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    a.scalarSubAssign(10000);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(90000);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);

    FheUint24 result = a.scalarMul(2);
    int decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);

    a.scalarMulAssign(2);
    int decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100000);
  }


  @Test
  void performsEqualityOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarEq(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarNe(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarGe(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(100000, clientKey);

    FheBool result = a.scalarGt(50000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.scalarLe(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint24 a = FheUint24.encryptWithClientKey(50000, clientKey);

    FheBool result = a.scalarLt(100000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint24 original = FheUint24.encryptWithClientKey(150000, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint24 deserialized = FheUint24.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    int decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint24 original = FheUint24.encryptWithClientKey(150000, clientKey);

    CompressedFheUint24 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint24 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }

  @Test
  void clones() {
    FheUint24 original = FheUint24.encryptWithClientKey(150000, clientKey);

    FheUint24 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    int decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(150000);
  }
}
