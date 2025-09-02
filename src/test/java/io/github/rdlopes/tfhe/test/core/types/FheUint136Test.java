package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint136;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint136;
import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class FheUint136Test {
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
    U256 originalValue = U256.valueOf("100");
    FheUint136 encrypted = FheUint136.encryptWithClientKey(originalValue, clientKey);
    U256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    U256 originalValue = U256.valueOf("100");
    FheUint136 encrypted = FheUint136.encryptWithPublicKey(originalValue, publicKey);
    U256 decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    U256 originalValue = U256.valueOf("100");
    FheUint136 encrypted = FheUint136.encryptTrivial(originalValue);
    U256 decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void serializesAndDeserializes() {
    FheUint136 original = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint136 deserialized = FheUint136.deserialize(buffer, serverKey);
    U256 decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void compressesAndDecompresses() {
    FheUint136 original = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint136 compressed = original.compress();
    FheUint136 decompressed = compressed.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    FheUint136 original = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    FheUint136 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    U256 decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void performsArithmeticOperations() {
    FheUint136 a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    FheUint136 b = FheUint136.encryptWithClientKey(U256.valueOf("50"), clientKey);

    FheUint136 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("150"));

    FheUint136 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("50"));

    FheUint136 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("5000"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("150"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("50"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("5000"));
  }

  @Test
  void performsBitwiseOperations() {
    FheUint136 a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    FheUint136 b = FheUint136.encryptWithClientKey(U256.valueOf("50"), clientKey);

    FheUint136 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("32"));

    FheUint136 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("118"));

    FheUint136 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("86"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("32"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("118"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("86"));
  }

  @Test
  void performsComparisonOperations() {
    FheUint136 a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    FheUint136 b = FheUint136.encryptWithClientKey(U256.valueOf("50"), clientKey);

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
    FheUint136 a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);

    FheUint136 r = a.scalarAdd(U256.valueOf("7"));
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("107"));

    a = FheUint136.encryptWithClientKey(U256.valueOf("100"), clientKey);
    a.scalarAddAssign(U256.valueOf("7"));
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(U256.valueOf("107"));
  }


}
