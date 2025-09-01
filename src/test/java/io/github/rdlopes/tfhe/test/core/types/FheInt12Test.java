package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt12;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt12;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt12Test {
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
    FheInt12 encrypted = FheInt12.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    short originalValue = (short) 1000;
    FheInt12 encrypted = FheInt12.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 1000;
    FheInt12 encrypted = FheInt12.encryptTrivial(originalValue);

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheInt12 result = a.add(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    a.addAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsSubOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheInt12 result = a.sub(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    a.subAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsMulOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 3, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 4, clientKey);

    FheInt12 result = a.mul(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 3, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 4, clientKey);

    a.mulAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }


  @Test
  void performsAndOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheInt12 result = a.and(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    a.andAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsOrOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheInt12 result = a.or(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    a.orAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsXorOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheInt12 result = a.xor(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    a.xorAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheInt12 result = a.scalarAdd((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    a.scalarAddAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheInt12 result = a.scalarSub((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    a.scalarSubAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheInt12 result = a.scalarMul((short) 2);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);

    a.scalarMulAssign((short) 2);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }


  @Test
  void performsEqualityOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);
    FheInt12 b = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarEq((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarNe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGt((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLe((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt12 a = FheInt12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLt((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt12 original = FheInt12.encryptWithClientKey((short) 1500, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt12 deserialized = FheInt12.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt12 original = FheInt12.encryptWithClientKey((short) 1500, clientKey);

    CompressedFheInt12 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt12 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void clones() {
    FheInt12 original = FheInt12.encryptWithClientKey((short) 1500, clientKey);

    FheInt12 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }
}
