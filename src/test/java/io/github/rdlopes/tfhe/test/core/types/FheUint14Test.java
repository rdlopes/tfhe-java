package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint14;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint14;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint14Test {
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
    FheUint14 encrypted = FheUint14.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    short originalValue = (short) 1000;
    FheUint14 encrypted = FheUint14.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 1000;
    FheUint14 encrypted = FheUint14.encryptTrivial(originalValue);

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheUint14 result = a.add(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    a.addAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void performsSubOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheUint14 result = a.sub(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    a.subAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 500);
  }

  @Test
  void performsMulOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 3, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 4, clientKey);

    FheUint14 result = a.mul(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 3, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 4, clientKey);

    a.mulAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12);
  }


  @Test
  void performsAndOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1500, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheUint14 result = a.and(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 456);
  }

  @Test
  void performsAndAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1500, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    a.andAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 456);
  }

  @Test
  void performsOrOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheUint14 result = a.or(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsOrAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    a.orAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1020);
  }

  @Test
  void performsXorOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheUint14 result = a.xor(b);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }

  @Test
  void performsXorAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    a.xorAssign(b);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 540);
  }


  @Test
  void performsScalarAddOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheUint14 result = a.scalarAdd((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    a.scalarAddAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1100);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheUint14 result = a.scalarSub((short) 100);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    a.scalarSubAssign((short) 100);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 900);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheUint14 result = a.scalarMul((short) 2);
    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);

    a.scalarMulAssign((short) 2);
    short decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1000);
  }


  @Test
  void performsEqualityOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);
    FheUint14 b = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarEq((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarNe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGe((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 1000, clientKey);

    FheBool result = a.scalarGt((short) 500);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLe((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint14 a = FheUint14.encryptWithClientKey((short) 500, clientKey);

    FheBool result = a.scalarLt((short) 1000);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint14 original = FheUint14.encryptWithClientKey((short) 1500, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint14 deserialized = FheUint14.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint14 original = FheUint14.encryptWithClientKey((short) 1500, clientKey);

    CompressedFheUint14 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint14 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }

  @Test
  void clones() {
    FheUint14 original = FheUint14.encryptWithClientKey((short) 1500, clientKey);

    FheUint14 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 1500);
  }
}
