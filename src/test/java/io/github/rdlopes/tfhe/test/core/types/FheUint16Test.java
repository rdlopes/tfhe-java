package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint16;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint16;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint16Test {
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();

    serverKey.setAsKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    short originalValue = 42;
    FheUint16 encrypted = FheUint16.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    short originalValue = 100;
    FheUint16 encrypted = FheUint16.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 65535;
    FheUint16 encrypted = FheUint16.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 10, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 15, clientKey);

    FheUint16 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 25);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 10, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 15, clientKey);

    encrypted1.addAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 25);
  }

  @Test
  void performsSubOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 30, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 15, clientKey);

    FheUint16 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 15);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 30, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 15, clientKey);

    encrypted1.subAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 15);
  }

  @Test
  void performsMulOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 5, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 6, clientKey);

    FheUint16 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 30);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 5, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 6, clientKey);

    encrypted1.mulAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 30);
  }

  @Test
  void performsAndOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 15, clientKey); // 0x0F
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 7, clientKey);  // 0x07

    FheUint16 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 7); // 0x07
  }

  @Test
  void performsAndAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 15, clientKey); // 0x0F
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 7, clientKey);  // 0x07

    encrypted1.andAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 7); // 0x07
  }

  @Test
  void performsOrOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 8, clientKey);  // 0x08
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 4, clientKey);  // 0x04

    FheUint16 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12); // 0x0C
  }

  @Test
  void performsOrAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 8, clientKey);  // 0x08
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 4, clientKey);  // 0x04

    encrypted1.orAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 12); // 0x0C
  }

  @Test
  void performsXorOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 15, clientKey); // 0x0F
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 7, clientKey);  // 0x07

    FheUint16 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 8); // 0x08
  }

  @Test
  void performsXorAssignOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 15, clientKey); // 0x0F
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 7, clientKey);  // 0x07

    encrypted1.xorAssign(encrypted2);
    short decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 8); // 0x08
  }

  @Test
  void performsScalarAddOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 10, clientKey);

    FheUint16 result = encrypted.scalarAdd((short) 15);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 25);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 10, clientKey);

    encrypted.scalarAddAssign((short) 15);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 25);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 30, clientKey);

    FheUint16 result = encrypted.scalarSub((short) 15);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 15);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 30, clientKey);

    encrypted.scalarSubAssign((short) 15);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 15);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 5, clientKey);

    FheUint16 result = encrypted.scalarMul((short) 6);
    assertThat(result).isNotNull();

    short decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 30);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 5, clientKey);

    encrypted.scalarMulAssign((short) 6);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 30);
  }

  @Test
  void performsEqualityOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 42, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 42, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 24, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 50, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 50, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 30, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint16 encrypted1 = FheUint16.encryptWithClientKey((short) 30, clientKey);
    FheUint16 encrypted2 = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted.scalarEq((short) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 42, clientKey);

    FheBool result = encrypted.scalarNe((short) 24);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 50, clientKey);

    FheBool result = encrypted.scalarGe((short) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 50, clientKey);

    FheBool result = encrypted.scalarGt((short) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 30, clientKey);

    FheBool result = encrypted.scalarLe((short) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint16 encrypted = FheUint16.encryptWithClientKey((short) 30, clientKey);

    FheBool result = encrypted.scalarLt((short) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    short originalValue = 12345;
    FheUint16 original = FheUint16.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheUint16 deserialized = FheUint16.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    short originalValue = (short) 54321;
    FheUint16 original = FheUint16.encryptWithClientKey(originalValue, clientKey);

    CompressedFheUint16 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint16 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clones() {
    short originalValue = 7777;
    FheUint16 original = FheUint16.encryptWithClientKey(originalValue, clientKey);
    FheUint16 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
