package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt160;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt160;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt160Test {
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
    FheInt160 encrypted = FheInt160.encryptWithClientKey(originalValue, clientKey);
    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    I256 originalValue = I256.valueOf("1000");
    FheInt160 encrypted = FheInt160.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I256 originalValue = I256.valueOf("1000");
    FheInt160 encrypted = FheInt160.encryptTrivial(originalValue);

    I256 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt160 result = a.add(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.addAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt160 result = a.sub(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.subAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("3"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("4"), clientKey);

    FheInt160 result = a.mul(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("3"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("4"), clientKey);

    a.mulAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt160 result = a.and(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.andAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt160 result = a.or(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.orAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt160 result = a.xor(b);
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.xorAssign(b);
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt160 result = a.scalarAdd(I256.valueOf("100"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.scalarAddAssign(I256.valueOf("100"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheInt160 result = a.scalarSub(I256.valueOf("100"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    a.scalarSubAssign(I256.valueOf("100"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheInt160 result = a.scalarMul(I256.valueOf("2"));
    I256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    a.scalarMulAssign(I256.valueOf("2"));
    I256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);
    FheInt160 b = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(I256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt160 a = FheInt160.encryptWithClientKey(I256.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(I256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt160 original = FheInt160.encryptWithClientKey(I256.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt160 deserialized = FheInt160.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    I256 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt160 original = FheInt160.encryptWithClientKey(I256.valueOf("1500"), clientKey);

    CompressedFheInt160 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt160 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }

  @Test
  void clones() {
    FheInt160 original = FheInt160.encryptWithClientKey(I256.valueOf("1500"), clientKey);

    FheInt160 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    I256 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("1500"));
  }
}
