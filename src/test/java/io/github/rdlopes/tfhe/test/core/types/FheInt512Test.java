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
import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class FheInt512Test {
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
    I1024 originalValue = I1024.valueOf("100");
    FheInt512 encrypted = FheInt512.encryptWithClientKey(originalValue, clientKey);
    I1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    I1024 originalValue = I1024.valueOf("100");
    FheInt512 encrypted = FheInt512.encryptWithPublicKey(originalValue, publicKey);
    I1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    I1024 originalValue = I1024.valueOf("100");
    FheInt512 encrypted = FheInt512.encryptTrivial(originalValue);
    I1024 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheInt512 original = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt512 deserialized = FheInt512.deserialize(buffer, serverKey);
    I1024 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheInt512 original = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    CompressedFheInt512 compressed = original.compress();
    FheInt512 decompressed = compressed.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheInt512 original = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    FheInt512 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    I1024 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheInt512 a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I1024.valueOf("50"), clientKey);

    FheInt512 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("150"));

    FheInt512 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("50"));

    FheInt512 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("5000"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("150"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("50"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheInt512 a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I1024.valueOf("50"), clientKey);

    FheInt512 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("32"));

    FheInt512 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("118"));

    FheInt512 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("86"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("32"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("118"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheInt512 a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    FheInt512 b = FheInt512.encryptWithClientKey(I1024.valueOf("50"), clientKey);

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
    FheInt512 a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);

    FheInt512 r = a.scalarAdd(I1024.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("107"));

    a = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    a.scalarAddAssign(I1024.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(I1024.valueOf("107"));
  }


}
