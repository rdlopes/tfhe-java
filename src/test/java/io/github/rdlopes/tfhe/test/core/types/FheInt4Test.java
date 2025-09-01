package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt4;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt4Test {
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
    FheInt4 encrypted = FheInt4.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    byte originalValue = (byte) 10;
    FheInt4 encrypted = FheInt4.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 10;
    FheInt4 encrypted = FheInt4.encryptTrivial(originalValue);

    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 result = a.add(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    a.addAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsSubOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 result = a.sub(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    a.subAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void performsMulOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 3, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 4, clientKey);

    FheInt4 result = a.mul(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 3, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 4, clientKey);

    a.mulAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12);
  }


  @Test
  void performsAndOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 7, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheInt4 result = a.and(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 2);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 7, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    a.andAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 2);
  }

  @Test
  void performsOrOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 result = a.or(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    a.orAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsXorOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 result = a.xor(b);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    a.xorAssign(b);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheInt4 result = a.scalarAdd((byte) 3);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 13);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    a.scalarAddAssign((byte) 3);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 13);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheInt4 result = a.scalarSub((byte) 3);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    a.scalarSubAssign((byte) 3);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 result = a.scalarMul((byte) 2);
    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    a.scalarMulAssign((byte) 2);
    byte decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }


  @Test
  void performsEqualityOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarEq((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarNe((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarGe((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = a.scalarGt((byte) 5);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.scalarLe((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = a.scalarLt((byte) 10);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 15, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt4 deserialized = FheInt4.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 15, clientKey);

    CompressedFheInt4 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt4 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void clones() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 15, clientKey);

    FheInt4 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }
}
