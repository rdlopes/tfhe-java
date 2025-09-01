package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt16;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt16;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt16Test {
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
    short originalValue = (short) 1000;
    FheInt16 encrypted = FheInt16.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    short originalValue = (short) 1000;
    FheInt16 encrypted = FheInt16.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 1000;
    FheInt16 encrypted = FheInt16.encryptTrivial(originalValue);

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheInt16 result = a.add(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    a.addAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsSubOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheInt16 result = a.sub(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    a.subAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsMulOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 3, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 4, clientKey);

    FheInt16 result = a.mul(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 3, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 4, clientKey);

    a.mulAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }


  @Test
  void performsAndOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheInt16 result = a.and(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    a.andAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsOrOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheInt16 result = a.or(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    a.orAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsXorOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheInt16 result = a.xor(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    a.xorAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheInt16 result = a.scalarAdd((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    a.scalarAddAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheInt16 result = a.scalarSub((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    a.scalarSubAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheInt16 result = a.scalarMul((short) 2);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);

    a.scalarMulAssign((short) 2);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }


  @Test
  void performsEqualityOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);
    FheInt16 b = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarEq((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarNe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGt((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLe((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt16 a = FheInt16.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLt((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt16 original = FheInt16.encryptWithClientKey((short) 1500, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt16 deserialized = FheInt16.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt16 original = FheInt16.encryptWithClientKey((short) 1500, clientKey);

    CompressedFheInt16 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt16 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void clones() {
    FheInt16 original = FheInt16.encryptWithClientKey((short) 1500, clientKey);

    FheInt16 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }
}
