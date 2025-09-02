package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt2;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt2Test {
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
    byte originalValue = (byte) 1;
    FheInt2 encrypted = FheInt2.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 1;
    FheInt2 encrypted = FheInt2.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 1;
    FheInt2 encrypted = FheInt2.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt2 original = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt2 deserialized = FheInt2.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt2 original = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    CompressedFheInt2 compressed = original.compress();
    FheInt2 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void clonesSuccessfully() {
    FheInt2 original = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    FheInt2 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt2 a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    FheInt2 b = FheInt2.encryptWithClientKey((byte) 1, clientKey);

    FheInt2 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);

    FheInt2 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    FheInt2 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt2 a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    FheInt2 b = FheInt2.encryptWithClientKey((byte) 1, clientKey);

    FheInt2 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    FheInt2 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    FheInt2 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);
  }

  @Test
  void performsComparisonOperations() {
    FheInt2 a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    FheInt2 b = FheInt2.encryptWithClientKey((byte) 1, clientKey);

    FheBool eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(clientKey)).isEqualTo(true);

    FheBool ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(clientKey)).isEqualTo(false);

    assertThat(a.ge(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.gt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
    assertThat(a.le(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.lt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
  }

  @Test
  void performsScalarAddOperations() {
    FheInt2 a = FheInt2.encryptWithClientKey((byte) 1, clientKey);

    FheInt2 r = a.scalarAdd((byte) 1);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);

    a = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    a.scalarAddAssign((byte) 1);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);
  }


}
