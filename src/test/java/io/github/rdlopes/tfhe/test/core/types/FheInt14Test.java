package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt14;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt14;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt14Test {
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
    short originalValue = (short) 100;
    FheInt14 encrypted = FheInt14.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    short originalValue = (short) 100;
    FheInt14 encrypted = FheInt14.encryptWithPublicKey(originalValue, publicKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 100;
    FheInt14 encrypted = FheInt14.encryptTrivial(originalValue);
    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt14 deserialized = FheInt14.deserialize(buffer, serverKey);
    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 100, clientKey);
    CompressedFheInt14 compressed = original.compress();
    FheInt14 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    FheInt14 original = FheInt14.encryptWithClientKey((short) 100, clientKey);
    FheInt14 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 50, clientKey);

    FheInt14 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((short) 150);

    FheInt14 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((short) 50);

    FheInt14 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((short) 5000);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 150);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 50);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 5000);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 50, clientKey);

    FheInt14 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((short) 32);

    FheInt14 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((short) 118);

    FheInt14 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((short) 86);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 32);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 118);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 86);
  }

  @Test
  void performsComparisonOperations() {
    FheInt14 a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    FheInt14 b = FheInt14.encryptWithClientKey((short) 50, clientKey);

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
    FheInt14 a = FheInt14.encryptWithClientKey((short) 100, clientKey);

    FheInt14 r = a.scalarAdd((short) 7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((short) 107);

    a = FheInt14.encryptWithClientKey((short) 100, clientKey);
    a.scalarAddAssign((short) 7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 107);
  }


}
