package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt128;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt128;
import io.github.rdlopes.tfhe.core.types.I128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt128Test {
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
    I128 originalValue = I128.valueOf("100");
    FheInt128 encrypted = FheInt128.encryptWithClientKey(originalValue, clientKey);
    I128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    I128 originalValue = I128.valueOf("100");
    FheInt128 encrypted = FheInt128.encryptWithPublicKey(originalValue, publicKey);
    I128 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I128 originalValue = I128.valueOf("100");
    FheInt128 encrypted = FheInt128.encryptTrivial(originalValue);
    I128 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheInt128 original = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt128 deserialized = FheInt128.deserialize(buffer, serverKey);
    I128 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt128 original = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    CompressedFheInt128 compressed = original.compress();
    FheInt128 decompressed = compressed.decompress();
    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheInt128 original = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    FheInt128 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    I128 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheInt128 a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    FheInt128 b = FheInt128.encryptWithClientKey(I128.valueOf("50"), clientKey);

    FheInt128 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("150"));

    FheInt128 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("50"));

    FheInt128 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("5000"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("150"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("50"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheInt128 a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    FheInt128 b = FheInt128.encryptWithClientKey(I128.valueOf("50"), clientKey);

    FheInt128 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("32"));

    FheInt128 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("118"));

    FheInt128 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("86"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("32"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("118"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheInt128 a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    FheInt128 b = FheInt128.encryptWithClientKey(I128.valueOf("50"), clientKey);

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
    FheInt128 a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);

    FheInt128 r = a.scalarAdd(I128.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("107"));

    a = FheInt128.encryptWithClientKey(I128.valueOf("100"), clientKey);
    a.scalarAddAssign(I128.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I128.valueOf("107"));
  }


}
