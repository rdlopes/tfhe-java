package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt112;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt112;
import io.github.rdlopes.tfhe.core.types.I128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt112Test {
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
    I128 originalValue = I128.valueOf("1000");
    FheInt112 encrypted = FheInt112.encryptWithClientKey(originalValue, clientKey);
    I128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    I128 originalValue = I128.valueOf("1000");
    FheInt112 encrypted = FheInt112.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I128 originalValue = I128.valueOf("1000");
    FheInt112 encrypted = FheInt112.encryptTrivial(originalValue);

    I128 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheInt112 result = a.add(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    a.addAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheInt112 result = a.sub(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    a.subAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("3"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("4"), clientKey);

    FheInt112 result = a.mul(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("3"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("4"), clientKey);

    a.mulAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1500"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheInt112 result = a.and(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1500"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    a.andAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheInt112 result = a.or(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    a.orAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheInt112 result = a.xor(b);
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    a.xorAssign(b);
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheInt112 result = a.scalarAdd(I128.valueOf("100"));
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    a.scalarAddAssign(I128.valueOf("100"));
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheInt112 result = a.scalarSub(I128.valueOf("100"));
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    a.scalarSubAssign(I128.valueOf("100"));
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheInt112 result = a.scalarMul(I128.valueOf("2"));
    I128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    a.scalarMulAssign(I128.valueOf("2"));
    I128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);
    FheInt112 b = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(I128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(I128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(I128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(I128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(I128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt112 a = FheInt112.encryptWithClientKey(I128.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(I128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt112 original = FheInt112.encryptWithClientKey(I128.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt112 deserialized = FheInt112.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    I128 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt112 original = FheInt112.encryptWithClientKey(I128.valueOf("1500"), clientKey);

    CompressedFheInt112 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt112 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }

  @Test
  void clones() {
    FheInt112 original = FheInt112.encryptWithClientKey(I128.valueOf("1500"), clientKey);

    FheInt112 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    I128 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("1500"));
  }
}
