package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint12;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint12;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint12Test {
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
    FheUint12 encrypted = FheUint12.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    short originalValue = (short) 1000;
    FheUint12 encrypted = FheUint12.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 1000;
    FheUint12 encrypted = FheUint12.encryptTrivial(originalValue);

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheUint12 result = a.add(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    a.addAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsSubOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheUint12 result = a.sub(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    a.subAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsMulOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 3, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 4, clientKey);

    FheUint12 result = a.mul(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 3, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 4, clientKey);

    a.mulAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }


  @Test
  void performsAndOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1500, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheUint12 result = a.and(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 456);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1500, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    a.andAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 456);
  }

  @Test
  void performsOrOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheUint12 result = a.or(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    a.orAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsXorOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheUint12 result = a.xor(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    a.xorAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }


  @Test
  void performsScalarAddOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheUint12 result = a.scalarAdd((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    a.scalarAddAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheUint12 result = a.scalarSub((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    a.scalarSubAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheUint12 result = a.scalarMul((short) 2);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);

    a.scalarMulAssign((short) 2);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }


  @Test
  void performsEqualityOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarEq((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarNe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGt((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLe((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLt((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 1500, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint12 deserialized = FheUint12.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 1500, clientKey);

    CompressedFheUint12 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint12 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void clones() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 1500, clientKey);

    FheUint12 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }
}
