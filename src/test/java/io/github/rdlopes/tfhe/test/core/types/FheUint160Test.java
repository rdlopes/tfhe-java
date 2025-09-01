package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint160;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint160;
import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint160Test {
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
    U256 originalValue = U256.valueOf("1000");
    FheUint160 encrypted = FheUint160.encryptWithClientKey(originalValue, clientKey);
    U256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    U256 originalValue = U256.valueOf("1000");
    FheUint160 encrypted = FheUint160.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    U256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U256 originalValue = U256.valueOf("1000");
    FheUint160 encrypted = FheUint160.encryptTrivial(originalValue);

    U256 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(originalValue);
  }


  @Test
  void performsAddOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheUint160 result = a.add(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void performsAddAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    a.addAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void performsSubOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheUint160 result = a.sub(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("500"));
  }

  @Test
  void performsSubAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    a.subAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("500"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("3"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("4"), clientKey);

    FheUint160 result = a.mul(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("12"));
  }

  @Test
  @Tag("largeBitSize")
  void performsMulAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("3"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("4"), clientKey);

    a.mulAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("12"));
  }


  @Test
  void performsAndOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1500"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheUint160 result = a.and(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1000"));
  }

  @Test
  void performsAndAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1500"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    a.andAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1000"));
  }

  @Test
  void performsOrOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheUint160 result = a.or(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void performsOrAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    a.orAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void performsXorOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheUint160 result = a.xor(b);
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void performsXorAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    a.xorAssign(b);
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }


  @Test
  void performsScalarAddOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheUint160 result = a.scalarAdd(U256.valueOf("100"));
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1100"));
  }

  @Test
  void performsScalarAddAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    a.scalarAddAssign(U256.valueOf("100"));
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1100"));
  }

  @Test
  void performsScalarSubOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheUint160 result = a.scalarSub(U256.valueOf("100"));
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("900"));
  }

  @Test
  void performsScalarSubAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    a.scalarSubAssign(U256.valueOf("100"));
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("900"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheUint160 result = a.scalarMul(U256.valueOf("2"));
    U256 decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1000"));
  }

  @Test
  @Tag("largeBitSize")
  void performsScalarMulAssignOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    a.scalarMulAssign(U256.valueOf("2"));
    U256 decrypted = a.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1000"));
  }


  @Test
  void performsEqualityOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.eq(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheBool result = a.ne(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsGreaterEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheBool result = a.ge(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsGreaterThanOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheBool result = a.gt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.le(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsLessThanOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);
    FheUint160 b = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.lt(b);
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.scalarEq(U256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.scalarNe(U256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGe(U256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarGreaterThanOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("1000"), clientKey);

    FheBool result = a.scalarGt(U256.valueOf("500"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessEqualOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheBool result = a.scalarLe(U256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarLessThanOperation() {
    FheUint160 a = FheUint160.encryptWithClientKey(U256.valueOf("500"), clientKey);

    FheBool result = a.scalarLt(U256.valueOf("1000"));
    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void serializesAndDeserializes() {
    FheUint160 original = FheUint160.encryptWithClientKey(U256.valueOf("1500"), clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheUint160 deserialized = FheUint160.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    U256 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint160 original = FheUint160.encryptWithClientKey(U256.valueOf("1500"), clientKey);

    CompressedFheUint160 compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheUint160 decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();
    assertThat(decompressed.getValue()).isNotNull();

    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }

  @Test
  void clones() {
    FheUint160 original = FheUint160.encryptWithClientKey(U256.valueOf("1500"), clientKey);

    FheUint160 cloned = original.clone();
    assertThat(cloned).isNotNull();
    assertThat(cloned.getValue()).isNotNull();
    assertThat(cloned).isNotSameAs(original);

    U256 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("1500"));
  }
}
