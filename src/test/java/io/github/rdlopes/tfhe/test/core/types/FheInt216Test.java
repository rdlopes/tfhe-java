package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt216;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt216;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class FheInt216Test {
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
    I256 originalValue = I256.valueOf("100");
    FheInt216 encrypted = FheInt216.encryptWithClientKey(originalValue, clientKey);
    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    I256 originalValue = I256.valueOf("100");
    FheInt216 encrypted = FheInt216.encryptWithPublicKey(originalValue, publicKey);
    I256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I256 originalValue = I256.valueOf("100");
    FheInt216 encrypted = FheInt216.encryptTrivial(originalValue);
    I256 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheInt216 original = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt216 deserialized = FheInt216.deserialize(buffer, serverKey);
    I256 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt216 original = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt216 compressed = original.compress();
    FheInt216 decompressed = compressed.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheInt216 original = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    FheInt216 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    I256 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheInt216 a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    FheInt216 b = FheInt216.encryptWithClientKey(I256.valueOf("50"), clientKey);

    FheInt216 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("150"));

    FheInt216 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("50"));

    FheInt216 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("5000"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("150"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("50"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheInt216 a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    FheInt216 b = FheInt216.encryptWithClientKey(I256.valueOf("50"), clientKey);

    FheInt216 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("32"));

    FheInt216 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("118"));

    FheInt216 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("86"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("32"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("118"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheInt216 a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    FheInt216 b = FheInt216.encryptWithClientKey(I256.valueOf("50"), clientKey);

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
    FheInt216 a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);

    FheInt216 r = a.scalarAdd(I256.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("107"));

    a = FheInt216.encryptWithClientKey(I256.valueOf("100"), clientKey);
    a.scalarAddAssign(I256.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I256.valueOf("107"));
  }


}
