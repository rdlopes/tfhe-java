package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint512;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint512;
import io.github.rdlopes.tfhe.core.types.U512;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint512Test {
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
    U512 originalValue = U512.valueOf("1000");
    FheUint512 encrypted = FheUint512.encryptWithClientKey(originalValue, clientKey);
    U512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    U512 originalValue = U512.valueOf("1000");
    FheUint512 encrypted = FheUint512.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    U512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U512 originalValue = U512.valueOf("1000");
    FheUint512 encrypted = FheUint512.encryptTrivial(originalValue);

    U512 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheUint512 result = a.add(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    a.addAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheUint512 result = a.sub(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    a.subAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("3"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("4"), clientKey);

    FheUint512 result = a.mul(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("3"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("4"), clientKey);

    a.mulAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheUint512 result = a.and(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    a.andAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheUint512 result = a.or(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    a.orAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheUint512 result = a.xor(b);
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    a.xorAssign(b);
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheUint512 result = a.scalarAdd(U512.valueOf("100"));
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    a.scalarAddAssign(U512.valueOf("100"));
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheUint512 result = a.scalarSub(U512.valueOf("100"));
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    a.scalarSubAssign(U512.valueOf("100"));
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheUint512 result = a.scalarMul(U512.valueOf("2"));
    U512 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    a.scalarMulAssign(U512.valueOf("2"));
    U512 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(U512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(U512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(U512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(U512.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(U512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(U512.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint512 deserialized = FheUint512.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    U512 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);

    CompressedFheUint512 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint512 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }

  @Test
  void clones() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("1500"), clientKey);

    FheUint512 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    U512 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("1500"));
  }
}
