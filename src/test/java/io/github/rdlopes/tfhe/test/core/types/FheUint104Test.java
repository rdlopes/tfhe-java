package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint104;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint104;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint104Test {
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    U128 originalValue = U128.valueOf("100");
    FheUint104 encrypted = FheUint104.encryptWithClientKey(originalValue, clientKey);
    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    U128 originalValue = U128.valueOf("100");
    FheUint104 encrypted = FheUint104.encryptWithPublicKey(originalValue, publicKey);
    U128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U128 originalValue = U128.valueOf("100");
    FheUint104 encrypted = FheUint104.encryptTrivial(originalValue);
    U128 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheUint104 original = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint104 deserialized = FheUint104.deserialize(buffer, serverKey);
    U128 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint104 original = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint104 compressed = original.compress();
    FheUint104 decompressed = compressed.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheUint104 original = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    FheUint104 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    U128 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheUint104 a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    FheUint104 b = FheUint104.encryptWithClientKey(U128.valueOf("50"), clientKey);

    FheUint104 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("150"));

    FheUint104 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("50"));

    FheUint104 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("5000"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("150"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("50"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheUint104 a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    FheUint104 b = FheUint104.encryptWithClientKey(U128.valueOf("50"), clientKey);

    FheUint104 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("32"));

    FheUint104 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("118"));

    FheUint104 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("86"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("32"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("118"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheUint104 a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    FheUint104 b = FheUint104.encryptWithClientKey(U128.valueOf("50"), clientKey);

    FheBool eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(clientKey)).isEqualTo(false);

    FheBool ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(clientKey)).isEqualTo(true);

    assertThat(a.ge(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.gt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.le(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
    assertThat(a.lt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
  }

  @Test
  void performsScalarAddOperations() {
    FheUint104 a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);

    FheUint104 r = a.scalarAdd(U128.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("107"));

    a = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    a.scalarAddAssign(U128.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U128.valueOf("107"));
  }


}
