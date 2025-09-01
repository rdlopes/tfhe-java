package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint2;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint2Test {
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
    byte originalValue = (byte) 10;
    FheUint2 encrypted = FheUint2.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    byte originalValue = (byte) 10;
    FheUint2 encrypted = FheUint2.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 10;
    FheUint2 encrypted = FheUint2.encryptTrivial(originalValue);

    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheUint2 result = a.add(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    a.addAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsSubOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheUint2 result = a.sub(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    a.subAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void performsMulOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 4, clientKey);

    FheUint2 result = a.mul(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 4, clientKey);

    a.mulAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12);
  }


  @Test
  void performsAndOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 15, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheUint2 result = a.and(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 15, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    a.andAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void performsOrOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheUint2 result = a.or(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    a.orAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsXorOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheUint2 result = a.xor(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    a.xorAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }


  @Test
  void performsScalarAddOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheUint2 result = a.scalarAdd((byte) 3);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 13);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    a.scalarAddAssign((byte) 3);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 13);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheUint2 result = a.scalarSub((byte) 3);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    a.scalarSubAssign((byte) 3);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheUint2 result = a.scalarMul((byte) 2);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    a.scalarMulAssign((byte) 2);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }


  @Test
  void performsEqualityOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarEq((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarNe((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarGe((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarGt((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.scalarLe((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.scalarLt((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 15, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint2 deserialized = FheUint2.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 15, clientKey);

    CompressedFheUint2 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint2 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void clones() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 15, clientKey);

    FheUint2 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }
}
