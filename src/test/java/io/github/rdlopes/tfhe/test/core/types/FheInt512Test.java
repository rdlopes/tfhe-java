package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt512;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt512;
import io.github.rdlopes.tfhe.core.types.I512;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt512Test {
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
    I512 originalValue = I512.valueOf("1000");
    FheInt512 encrypted = FheInt512.encryptWithClientKey(originalValue, clientKey);
    I512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    I512 originalValue = I512.valueOf("1000");
    FheInt512 encrypted = FheInt512.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    I512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I512 originalValue = I512.valueOf("1000");
    FheInt512 encrypted = FheInt512.encryptTrivial(originalValue);

    I512 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheInt512 result = a.add(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    a.addAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheInt512 result = a.sub(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    a.subAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("3"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("4"), clientKey);

    FheInt512 result = a.mul(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("3"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("4"), clientKey);

    a.mulAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1500"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheInt512 result = a.and(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1500"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    a.andAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheInt512 result = a.or(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    a.orAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheInt512 result = a.xor(b);
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    a.xorAssign(b);
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheInt512 result = a.scalarAdd(I512.valueOf("100"));
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    a.scalarAddAssign(I512.valueOf("100"));
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheInt512 result = a.scalarSub(I512.valueOf("100"));
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    a.scalarSubAssign(I512.valueOf("100"));
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheInt512 result = a.scalarMul(I512.valueOf("2"));
    I512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    a.scalarMulAssign(I512.valueOf("2"));
    I512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(I512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(I512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(I512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(I512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(I512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheInt512 a = FheInt512.encryptWithClientKey(I512.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(I512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheInt512 original = FheInt512.encryptWithClientKey(I512.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheInt512 deserialized = FheInt512.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    I512 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt512 original = FheInt512.encryptWithClientKey(I512.valueOf("1500"), clientKey);

    CompressedFheInt512 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheInt512 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    I512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }

  @Test
  void clones() {
    FheInt512 original = FheInt512.encryptWithClientKey(I512.valueOf("1500"), clientKey);

    FheInt512 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    I512 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I512.valueOf("1500"));
  }
}
