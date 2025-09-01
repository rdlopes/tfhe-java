package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint120;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint120;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint120Test {
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
    U128 originalValue = U128.valueOf("1000");
    FheUint120 encrypted = FheUint120.encryptWithClientKey(originalValue, clientKey);
    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    U128 originalValue = U128.valueOf("1000");
    FheUint120 encrypted = FheUint120.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U128 originalValue = U128.valueOf("1000");
    FheUint120 encrypted = FheUint120.encryptTrivial(originalValue);

    U128 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint120 result = a.add(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.addAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint120 result = a.sub(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.subAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("3"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("4"), clientKey);

    FheUint120 result = a.mul(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("3"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("4"), clientKey);

    a.mulAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint120 result = a.and(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.andAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint120 result = a.or(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.orAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint120 result = a.xor(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.xorAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint120 result = a.scalarAdd(U128.valueOf("100"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.scalarAddAssign(U128.valueOf("100"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint120 result = a.scalarSub(U128.valueOf("100"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.scalarSubAssign(U128.valueOf("100"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint120 result = a.scalarMul(U128.valueOf("2"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.scalarMulAssign(U128.valueOf("2"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);
    FheUint120 b = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint120 a = FheUint120.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint120 original = FheUint120.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint120 deserialized = FheUint120.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    U128 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint120 original = FheUint120.encryptWithClientKey(U128.valueOf("1500"), clientKey);

    CompressedFheUint120 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint120 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void clones() {
    FheUint120 original = FheUint120.encryptWithClientKey(U128.valueOf("1500"), clientKey);

    FheUint120 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    U128 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }
}
