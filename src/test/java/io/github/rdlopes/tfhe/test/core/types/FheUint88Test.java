package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint88;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint88;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint88Test {
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
    FheUint88 encrypted = FheUint88.encryptWithClientKey(originalValue, clientKey);
    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    U128 originalValue = U128.valueOf("1000");
    FheUint88 encrypted = FheUint88.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U128 originalValue = U128.valueOf("1000");
    FheUint88 encrypted = FheUint88.encryptTrivial(originalValue);

    U128 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint88 result = a.add(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.addAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint88 result = a.sub(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.subAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("3"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("4"), clientKey);

    FheUint88 result = a.mul(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("3"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("4"), clientKey);

    a.mulAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint88 result = a.and(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.andAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint88 result = a.or(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.orAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint88 result = a.xor(b);
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.xorAssign(b);
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint88 result = a.scalarAdd(U128.valueOf("100"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.scalarAddAssign(U128.valueOf("100"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheUint88 result = a.scalarSub(U128.valueOf("100"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    a.scalarSubAssign(U128.valueOf("100"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheUint88 result = a.scalarMul(U128.valueOf("2"));
    U128 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    a.scalarMulAssign(U128.valueOf("2"));
    U128 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);
    FheUint88 b = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(U128.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint88 a = FheUint88.encryptWithClientKey(U128.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(U128.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint88 original = FheUint88.encryptWithClientKey(U128.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint88 deserialized = FheUint88.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    U128 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint88 original = FheUint88.encryptWithClientKey(U128.valueOf("1500"), clientKey);

    CompressedFheUint88 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint88 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }

  @Test
  void clones() {
    FheUint88 original = FheUint88.encryptWithClientKey(U128.valueOf("1500"), clientKey);

    FheUint88 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    U128 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("1500"));
  }
}
