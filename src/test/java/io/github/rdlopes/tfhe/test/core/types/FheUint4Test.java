package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint4;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint4Test {
  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();

    serverKey.setAsKey();
  }


  @Test
  void encryptsAndDecryptsWithClientKey() {
    byte originalValue = 10;
    FheUint4 encrypted = FheUint4.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = 7;
    FheUint4 encrypted = FheUint4.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = 15;
    FheUint4 encrypted = FheUint4.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void performsAddOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 5, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    FheUint4 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsAddAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 5, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    encrypted1.addAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsSubOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    FheUint4 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);

  }

  @Test
  void performsSubAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    encrypted1.subAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);

  }

  @Test
  void performsMulOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 3, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 2, clientKey);

    FheUint4 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsMulAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 3, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 2, clientKey);

    encrypted1.mulAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsAndOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheUint4 result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsAndAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    encrypted1.andAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsOrOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheUint4 result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 14);

  }

  @Test
  void performsOrAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    encrypted1.orAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 14);

  }

  @Test
  void performsXorOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheUint4 result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsXorAssignOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 12, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    encrypted1.xorAssign(encrypted2);
    byte decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsScalarAddOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 5, clientKey);

    FheUint4 result = encrypted.scalarAdd((byte) 3);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 5, clientKey);

    encrypted.scalarAddAssign((byte) 3);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 8);

  }

  @Test
  void performsScalarSubOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheUint4 result = encrypted.scalarSub((byte) 3);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);

  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    encrypted.scalarSubAssign((byte) 3);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 7);

  }

  @Test
  void performsScalarMulOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    FheUint4 result = encrypted.scalarMul((byte) 2);
    assertThat(result).isNotNull();

    byte decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsScalarMulAssignOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 3, clientKey);

    encrypted.scalarMulAssign((byte) 2);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 6);

  }

  @Test
  void performsEqualityOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 7, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 7, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsNotEqualOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 7, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsGreaterEqualOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 7, clientKey);

    FheBool result = encrypted1.ge(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsGreaterThanOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 7, clientKey);

    FheBool result = encrypted1.gt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsLessEqualOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 5, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = encrypted1.le(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsLessThanOperation() {
    FheUint4 encrypted1 = FheUint4.encryptWithClientKey((byte) 5, clientKey);
    FheUint4 encrypted2 = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = encrypted1.lt(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 7, clientKey);

    FheBool result = encrypted.scalarEq((byte) 7);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 7, clientKey);

    FheBool result = encrypted.scalarNe((byte) 5);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = encrypted.scalarGe((byte) 7);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheBool result = encrypted.scalarGt((byte) 7);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = encrypted.scalarLe((byte) 10);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint4 encrypted = FheUint4.encryptWithClientKey((byte) 5, clientKey);

    FheBool result = encrypted.scalarLt((byte) 10);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void serializesAndDeserializes() {
    byte originalValue = 9;
    FheUint4 original = FheUint4.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getAddress()).isNotNull();

    FheUint4 deserialized = FheUint4.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void compressesAndDecompresses() {
    byte originalValue = 11;
    FheUint4 original = FheUint4.encryptWithClientKey(originalValue, clientKey);

    CompressedFheUint4 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint4 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void clones() {
    byte originalValue = 13;
    FheUint4 original = FheUint4.encryptWithClientKey(originalValue, clientKey);

    FheUint4 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();

    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }
}
