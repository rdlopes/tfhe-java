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

@Tag("intensive")
class FheUint2048Test {
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
    U2048 originalValue = U2048.valueOf("100");
    FheUint2048 encrypted = FheUint2048.encryptWithClientKey(originalValue, clientKey);
    U2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    U2048 originalValue = U2048.valueOf("100");
    FheUint2048 encrypted = FheUint2048.encryptWithPublicKey(originalValue, publicKey);
    U2048 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U2048 originalValue = U2048.valueOf("100");
    FheUint2048 encrypted = FheUint2048.encryptTrivial(originalValue);
    U2048 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint2048 deserialized = FheUint2048.deserialize(buffer, serverKey);
    U2048 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    CompressedFheUint2048 compressed = original.compress();
    FheUint2048 decompressed = compressed.decompress();
    U2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    FheUint2048 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    U2048 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("50"), clientKey);

    FheUint2048 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("150"));

    FheUint2048 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("50"));

    FheUint2048 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("5000"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("150"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("50"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("50"), clientKey);

    FheUint2048 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("32"));

    FheUint2048 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("118"));

    FheUint2048 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("86"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("32"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("118"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    FheUint2048 b = FheUint2048.encryptWithClientKey(U2048.valueOf("50"), clientKey);

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
    FheUint2048 a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);

    FheUint2048 r = a.scalarAdd(U2048.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("107"));

    a = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    a.scalarAddAssign(U2048.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U2048.valueOf("107"));
  }


}
