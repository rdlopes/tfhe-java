package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint8;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint8;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint8Test {
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


  @Test
  void encryptsAndDecryptsWithClientKey() {
    byte originalValue = 42;
    FheUint8 encrypted = FheUint8.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = 100;
    FheUint8 encrypted = FheUint8.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 255;
    FheUint8 encrypted = FheUint8.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAddOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 10, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 15, clientKey);

    FheUint8 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 25);
  }

  @Test
  void performsAddAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 10, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 15, clientKey);

    encrypted1.addAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 25);
  }

  @Test
  void performsSubOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 30, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 15, clientKey);

    FheUint8 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsSubAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 30, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 15, clientKey);

    encrypted1.subAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsMulOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 5, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 6, clientKey);

    FheUint8 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 30);
  }

  @Test
  void performsMulAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 5, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 6, clientKey);

    encrypted1.mulAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 30);
  }

  @Test
  void performsAndOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 15, clientKey); // 0x0F
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 7, clientKey);  // 0x07

    FheUint8 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7); // 0x07
  }

  @Test
  void performsAndAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 15, clientKey); // 0x0F
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 7, clientKey);  // 0x07

    encrypted1.andAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7); // 0x07
  }

  @Test
  void performsOrOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 8, clientKey);  // 0x08
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 4, clientKey);  // 0x04

    FheUint8 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12); // 0x0C
  }

  @Test
  void performsOrAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 8, clientKey);  // 0x08
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 4, clientKey);  // 0x04

    encrypted1.orAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 12); // 0x0C
  }

  @Test
  void performsXorOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 15, clientKey); // 0x0F
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 7, clientKey);  // 0x07

    FheUint8 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8); // 0x08
  }

  @Test
  void performsXorAssignOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 15, clientKey); // 0x0F
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 7, clientKey);  // 0x07

    encrypted1.xorAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8); // 0x08
  }

  @Test
  void performsScalarAddOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 10, clientKey);

    FheUint8 result = encrypted.scalarAdd((byte) 15);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 25);
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 10, clientKey);

    encrypted.scalarAddAssign((byte) 15);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 25);
  }

  @Test
  void performsScalarSubOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 30, clientKey);

    FheUint8 result = encrypted.scalarSub((byte) 15);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 30, clientKey);

    encrypted.scalarSubAssign((byte) 15);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 15);
  }

  @Test
  void performsScalarMulOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 5, clientKey);

    FheUint8 result = encrypted.scalarMul((byte) 6);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 30);
  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 5, clientKey);

    encrypted.scalarMulAssign((byte) 6);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 30);
  }

  @Test
  void performsEqualityOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 42, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 42, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 24, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterEqualOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 50, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 50, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 30, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint8 encrypted1 = FheUint8.encryptWithClientKey((byte) 30, clientKey);
    FheUint8 encrypted2 = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted.scalarEq((byte) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 42, clientKey);

    FheBool result = encrypted.scalarNe((byte) 24);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 50, clientKey);

    FheBool result = encrypted.scalarGe((byte) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 50, clientKey);

    FheBool result = encrypted.scalarGt((byte) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 30, clientKey);

    FheBool result = encrypted.scalarLe((byte) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint8 encrypted = FheUint8.encryptWithClientKey((byte) 30, clientKey);

    FheBool result = encrypted.scalarLt((byte) 42);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    byte originalValue = 123;
    FheUint8 original = FheUint8.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheUint8 deserialized = FheUint8.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void compressesAndDecompresses() {
    byte originalValue = (byte) 200;
    FheUint8 original = FheUint8.encryptWithClientKey(originalValue, clientKey);

    CompressedFheUint8 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint8 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void clones() {
    byte originalValue = 77;
    FheUint8 original = FheUint8.encryptWithClientKey(originalValue, clientKey);
    FheUint8 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }
}
