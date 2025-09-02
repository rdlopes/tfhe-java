package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint1024;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint1024;
import io.github.rdlopes.tfhe.core.types.U1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class FheUint1024Test {
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
    U1024 originalValue = U1024.valueOf("100");
    FheUint1024 encrypted = FheUint1024.encryptWithClientKey(originalValue, clientKey);
    U1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    U1024 originalValue = U1024.valueOf("100");
    FheUint1024 encrypted = FheUint1024.encryptWithPublicKey(originalValue, publicKey);
    U1024 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U1024 originalValue = U1024.valueOf("100");
    FheUint1024 encrypted = FheUint1024.encryptTrivial(originalValue);
    U1024 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheUint1024 original = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint1024 deserialized = FheUint1024.deserialize(buffer, serverKey);
    U1024 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint1024 original = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    CompressedFheUint1024 compressed = original.compress();
    FheUint1024 decompressed = compressed.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheUint1024 original = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    FheUint1024 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    U1024 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheUint1024 a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    FheUint1024 b = FheUint1024.encryptWithClientKey(U1024.valueOf("50"), clientKey);

    FheUint1024 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("150"));

    FheUint1024 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("50"));

    FheUint1024 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("5000"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("150"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("50"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheUint1024 a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    FheUint1024 b = FheUint1024.encryptWithClientKey(U1024.valueOf("50"), clientKey);

    FheUint1024 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("32"));

    FheUint1024 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("118"));

    FheUint1024 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("86"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("32"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("118"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheUint1024 a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    FheUint1024 b = FheUint1024.encryptWithClientKey(U1024.valueOf("50"), clientKey);

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
    FheUint1024 a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);

    FheUint1024 r = a.scalarAdd(U1024.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("107"));

    a = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    a.scalarAddAssign(U1024.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U1024.valueOf("107"));
  }


}
