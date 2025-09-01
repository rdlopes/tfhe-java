package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt48;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt48;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt48Test {
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
    long originalValue = 1000000L;
    FheInt48 encrypted = FheInt48.encryptWithClientKey(originalValue, clientKey);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    long originalValue = 1000000L;
    FheInt48 encrypted = FheInt48.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    long originalValue = 1000000L;
    FheInt48 encrypted = FheInt48.encryptTrivial(originalValue);

    Long decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheInt48 result = a.add(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1500000L);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    a.addAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1500000L);
  }

  @Test
  void performsSubOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheInt48 result = a.sub(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000L);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    a.subAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(500000L);
  }

  @Test
  void performsMulOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(3L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(4L, clientKey);

    FheInt48 result = a.mul(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12L);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(3L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(4L, clientKey);

    a.mulAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(12L);
  }


  @Test
  void performsAndOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheInt48 result = a.and(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1000000L);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(1000000L, clientKey);

    a.andAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1000000L);
  }

  @Test
  void performsOrOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheInt48 result = a.or(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1041248L);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    a.orAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1041248L);
  }

  @Test
  void performsXorOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheInt48 result = a.xor(b);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(582496L);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    a.xorAssign(b);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(582496L);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheInt48 result = a.scalarAdd(100000L);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1100000L);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    a.scalarAddAssign(100000L);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1100000L);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheInt48 result = a.scalarSub(100000L);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(900000L);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    a.scalarSubAssign(100000L);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(900000L);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheInt48 result = a.scalarMul(2L);
    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1000000L);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);

    a.scalarMulAssign(2L);
    long decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1000000L);
  }


  @Test
  void performsEqualityOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);
    FheInt48 b = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.scalarEq(1000000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.scalarNe(500000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.scalarGe(500000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(1000000L, clientKey);

    FheBool result = a.scalarGt(500000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheBool result = a.scalarLe(1000000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt48 a = FheInt48.encryptWithClientKey(500000L, clientKey);

    FheBool result = a.scalarLt(1000000L);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt48 original = FheInt48.encryptWithClientKey(1500000L, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt48 deserialized = FheInt48.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    long decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1500000L);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt48 original = FheInt48.encryptWithClientKey(1500000L, clientKey);

    CompressedFheInt48 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt48 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1500000L);
  }

  @Test
  void clones() {
    FheInt48 original = FheInt48.encryptWithClientKey(1500000L, clientKey);

    FheInt48 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    long decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(1500000L);
  }
}
