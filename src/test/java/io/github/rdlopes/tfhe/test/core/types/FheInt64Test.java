package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt64;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt64Test {
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
    long originalValue = 100;
    FheInt64 encrypted = FheInt64.encryptWithClientKey(originalValue, clientKey);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    long originalValue = 100;
    FheInt64 encrypted = FheInt64.encryptWithPublicKey(originalValue, publicKey);
    long decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    long originalValue = 100;
    FheInt64 encrypted = FheInt64.encryptTrivial(originalValue);
    Long decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt64 original = FheInt64.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt64 deserialized = FheInt64.deserialize(buffer, serverKey);
    long decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt64 original = FheInt64.encryptWithClientKey(100, clientKey);
    CompressedFheInt64 compressed = original.compress();
    FheInt64 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    FheInt64 original = FheInt64.encryptWithClientKey(100, clientKey);
    FheInt64 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    long decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt64 a = FheInt64.encryptWithClientKey(100, clientKey);
    FheInt64 b = FheInt64.encryptWithClientKey(50, clientKey);

    FheInt64 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(150);

    FheInt64 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(50);

    FheInt64 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(5000);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(150);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(50);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(5000);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt64 a = FheInt64.encryptWithClientKey(100, clientKey);
    FheInt64 b = FheInt64.encryptWithClientKey(50, clientKey);

    FheInt64 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(32);

    FheInt64 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(118);

    FheInt64 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(86);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(32);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(118);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(86);
  }

  @Test
  void performsComparisonOperations() {
    FheInt64 a = FheInt64.encryptWithClientKey(100, clientKey);
    FheInt64 b = FheInt64.encryptWithClientKey(50, clientKey);

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
    FheInt64 a = FheInt64.encryptWithClientKey(100, clientKey);

    FheInt64 r = a.scalarAdd(7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(107);

    a = FheInt64.encryptWithClientKey(100, clientKey);
    a.scalarAddAssign(7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(107);
  }


}
