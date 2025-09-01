package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt1024;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt1024;
import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt1024Test {
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
    I1024 originalValue = I1024.valueOf("1000");
    FheInt1024 encrypted = FheInt1024.encryptWithClientKey(originalValue, clientKey);
    I1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    I1024 originalValue = I1024.valueOf("1000");
    FheInt1024 encrypted = FheInt1024.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I1024 originalValue = I1024.valueOf("1000");
    FheInt1024 encrypted = FheInt1024.encryptTrivial(originalValue);

    I1024 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheInt1024 result = a.add(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    a.addAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheInt1024 result = a.sub(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    a.subAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("3"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("4"), clientKey);

    FheInt1024 result = a.mul(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("3"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("4"), clientKey);

    a.mulAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheInt1024 result = a.and(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    a.andAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheInt1024 result = a.or(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    a.orAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheInt1024 result = a.xor(b);
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    a.xorAssign(b);
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheInt1024 result = a.scalarAdd(I1024.valueOf("100"));
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    a.scalarAddAssign(I1024.valueOf("100"));
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheInt1024 result = a.scalarSub(I1024.valueOf("100"));
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    a.scalarSubAssign(I1024.valueOf("100"));
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheInt1024 result = a.scalarMul(I1024.valueOf("2"));
    I1024 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    a.scalarMulAssign(I1024.valueOf("2"));
    I1024 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);
    FheInt1024 b = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(I1024.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(I1024.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(I1024.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(I1024.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(I1024.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt1024 a = FheInt1024.encryptWithClientKey(I1024.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(I1024.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt1024 original = FheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt1024 deserialized = FheInt1024.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    I1024 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt1024 original = FheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);

    CompressedFheInt1024 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt1024 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }

  @Test
  void clones() {
    FheInt1024 original = FheInt1024.encryptWithClientKey(I1024.valueOf("1500"), clientKey);

    FheInt1024 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    I1024 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("1500"));
  }
}
