package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint2048;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint2048;
import io.github.rdlopes.tfhe.core.types.U2048;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint2048Test {
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
    U2048 originalValue = U2048.valueOf("1000");
    FheUint2048 encrypted = FheUint2048.encryptWithClientKey(originalValue, clientKey);
    U2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    U2048 originalValue = U2048.valueOf("1000");
    FheUint2048 encrypted = FheUint2048.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    U2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U2048 originalValue = U2048.valueOf("1000");
    FheUint2048 encrypted = FheUint2048.encryptTrivial(originalValue);

    U2048 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheUint2048 result = a.add(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    a.addAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheUint2048 result = a.sub(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    a.subAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("3"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("4"), clientKey);

    FheUint2048 result = a.mul(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("3"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("4"), clientKey);

    a.mulAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1500"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheUint2048 result = a.and(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1500"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    a.andAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheUint2048 result = a.or(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    a.orAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheUint2048 result = a.xor(b);
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    a.xorAssign(b);
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheUint2048 result = a.scalarAdd(U2048.valueOf("100"));
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    a.scalarAddAssign(U2048.valueOf("100"));
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheUint2048 result = a.scalarSub(U2048.valueOf("100"));
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    a.scalarSubAssign(U2048.valueOf("100"));
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheUint2048 result = a.scalarMul(U2048.valueOf("2"));
    U2048 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    a.scalarMulAssign(U2048.valueOf("2"));
    U2048 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(U2048.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(U2048.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(U2048.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(U2048.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(U2048.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(U2048.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint2048 deserialized = FheUint2048.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    U2048 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("1500"), clientKey);

    CompressedFheUint2048 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint2048 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }

  @Test
  void clones() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("1500"), clientKey);

    FheUint2048 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    U2048 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("1500"));
  }
}
