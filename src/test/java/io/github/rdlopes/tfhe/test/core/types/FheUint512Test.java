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

@Tag("intensive")
class FheUint512Test {
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
    U512 originalValue = U512.valueOf("100");
    FheUint512 encrypted = FheUint512.encryptWithClientKey(originalValue, clientKey);
    U512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    U512 originalValue = U512.valueOf("100");
    FheUint512 encrypted = FheUint512.encryptWithPublicKey(originalValue, publicKey);
    U512 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U512 originalValue = U512.valueOf("100");
    FheUint512 encrypted = FheUint512.encryptTrivial(originalValue);
    U512 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint512 deserialized = FheUint512.deserialize(buffer, serverKey);
    U512 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    CompressedFheUint512 compressed = original.compress();
    FheUint512 decompressed = compressed.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    FheUint512 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    U512 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("50"), clientKey);

    FheUint512 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("150"));

    FheUint512 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("50"));

    FheUint512 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("5000"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("150"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("50"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("50"), clientKey);

    FheUint512 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("32"));

    FheUint512 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("118"));

    FheUint512 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("86"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("32"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("118"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    FheUint512 b = FheUint512.encryptWithClientKey(U512.valueOf("50"), clientKey);

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
    FheUint512 a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);

    FheUint512 r = a.scalarAdd(U512.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("107"));

    a = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    a.scalarAddAssign(U512.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U512.valueOf("107"));
  }


}
