package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt64;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt64Test {
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
    long originalValue = 12345678901234L;
    FheInt64 encrypted = FheInt64.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    long originalValue = 50000000000000L;
    FheInt64 encrypted = FheInt64.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void encryptsAndDecryptsTrivial() {
    long originalValue = 9223372036854775807L; // Max long
    FheInt64 encrypted = FheInt64.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Long decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void performsAddOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(1000000000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(2000000000L, clientKey);

    FheInt64 result = encrypted1.add(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsAddAssignOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(1000000000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(2000000000L, clientKey);

    encrypted1.addAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsSubOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(5000000000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(2000000000L, clientKey);

    FheInt64 result = encrypted1.sub(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsSubAssignOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(5000000000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(2000000000L, clientKey);

    encrypted1.subAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsMulOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(50000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(60000L, clientKey);

    FheInt64 result = encrypted1.mul(encrypted2);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsMulAssignOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(50000L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(60000L, clientKey);

    encrypted1.mulAssign(encrypted2);
    long decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(3000000000L);

  }

  @Test
  void performsScalarAddOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(10000000000L, clientKey);

    FheInt64 result = encrypted.scalarAdd(5000000000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15000000000L);

  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(10000000000L, clientKey);

    encrypted.scalarAddAssign(5000000000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(15000000000L);

  }

  @Test
  void performsScalarSubOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(15000000000L, clientKey);

    FheInt64 result = encrypted.scalarSub(5000000000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(10000000000L);

  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(15000000000L, clientKey);

    encrypted.scalarSubAssign(5000000000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(10000000000L);

  }

  @Test
  void performsScalarMulOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(1000000L, clientKey);

    FheInt64 result = encrypted.scalarMul(5000L);
    assertThat(result).isNotNull();

    long decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(5000000000L);

  }

  @Test
  void performsScalarMulAssignOperation() {
    FheInt64 encrypted = FheInt64.encryptWithClientKey(1000000L, clientKey);

    encrypted.scalarMulAssign(5000L);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(5000000000L);

  }

  @Test
  void performsEqualityOperation() {
    FheInt64 encrypted1 = FheInt64.encryptWithClientKey(12345678901234L, clientKey);
    FheInt64 encrypted2 = FheInt64.encryptWithClientKey(12345678901234L, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();

  }

  @Test
  void serializesAndDeserializes() {
    long originalValue = -30000000000000L;
    FheInt64 original = FheInt64.encryptWithClientKey(originalValue, clientKey);

    DynamicBufferView serialized = original.serialize();
    assertThat(serialized).isNotNull();
    assertThat(serialized.getLength()).isGreaterThan(0);

    FheInt64 deserialized = FheInt64.deserialize(serialized, serverKey);
    assertThat(deserialized).isNotNull();

    long decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void compressesAndDecompresses() {
    long originalValue = -40000000000000L;
    FheInt64 original = FheInt64.encryptWithClientKey(originalValue, clientKey);

    CompressedFheInt64 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt64 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }

  @Test
  void clones() {
    long originalValue = -25000000000000L;
    FheInt64 original = FheInt64.encryptWithClientKey(originalValue, clientKey);
    FheInt64 cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    long decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);

  }
}
