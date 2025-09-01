package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt14;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt14;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt14Test {
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
    FheInt14 encrypted = FheInt14.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    short originalValue = (short) 1000;
    FheInt14 encrypted = FheInt14.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 1000;
    FheInt14 encrypted = FheInt14.encryptTrivial(originalValue);

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheInt14 result = a.add(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsAddAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    a.addAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsSubOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheInt14 result = a.sub(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsSubAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    a.subAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsMulOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 3, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 4, clientKey);

    FheInt14 result = a.mul(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 3, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 4, clientKey);

    a.mulAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }


  @Test
  void performsAndOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheInt14 result = a.and(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsAndAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    a.andAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsOrOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheInt14 result = a.or(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsOrAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    a.orAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsXorOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheInt14 result = a.xor(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }

  @Test
  void performsXorAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    a.xorAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }


  @Test
  void performsScalarAddOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheInt14 result = a.scalarAdd((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    a.scalarAddAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarSubOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheInt14 result = a.scalarSub((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    a.scalarSubAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarMulOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheInt14 result = a.scalarMul((short) 2);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);

    a.scalarMulAssign((short) 2);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }


  @Test
  void performsEqualityOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarEq((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarNe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGt((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLe((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLt((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 1500, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt14 deserialized = FheInt14.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 1500, clientKey);

    CompressedFheInt14 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt14 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void clones() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 1500, clientKey);

    FheInt14 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }
}
