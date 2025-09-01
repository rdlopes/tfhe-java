package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt200;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt200;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt200Test {
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
    I256 originalValue = I256.valueOf("1000");
    FheInt200 encrypted = FheInt200.encryptWithClientKey(originalValue, clientKey);
    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    I256 originalValue = I256.valueOf("1000");
    FheInt200 encrypted = FheInt200.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I256 originalValue = I256.valueOf("1000");
    FheInt200 encrypted = FheInt200.encryptTrivial(originalValue);

    I256 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt200 result = a.add(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.addAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt200 result = a.sub(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.subAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("3"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("4"), clientKey);

    FheInt200 result = a.mul(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("3"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("4"), clientKey);

    a.mulAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt200 result = a.and(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.andAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt200 result = a.or(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.orAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt200 result = a.xor(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.xorAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt200 result = a.scalarAdd(I256.valueOf("100"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.scalarAddAssign(I256.valueOf("100"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt200 result = a.scalarSub(I256.valueOf("100"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.scalarSubAssign(I256.valueOf("100"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt200 result = a.scalarMul(I256.valueOf("2"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.scalarMulAssign(I256.valueOf("2"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);
    FheInt200 b = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt200 a = FheInt200.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt200 original = FheInt200.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt200 deserialized = FheInt200.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    I256 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt200 original = FheInt200.encryptWithClientKey(I256.valueOf("1500"), clientKey);

    CompressedFheInt200 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt200 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void clones() {
    FheInt200 original = FheInt200.encryptWithClientKey(I256.valueOf("1500"), clientKey);

    FheInt200 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    I256 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }
}
