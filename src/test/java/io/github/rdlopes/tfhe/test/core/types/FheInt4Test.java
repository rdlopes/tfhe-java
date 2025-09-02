package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt4;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt4Test {
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
    byte originalValue = (byte) 5;
    FheInt4 encrypted = FheInt4.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 5;
    FheInt4 encrypted = FheInt4.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 5;
    FheInt4 encrypted = FheInt4.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt4 deserialized = FheInt4.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    CompressedFheInt4 compressed = original.compress();
    FheInt4 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void clonesSuccessfully() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 3, clientKey);

    FheInt4 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 248);

    FheInt4 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    FheInt4 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 255);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 248);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 255);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 3, clientKey);

    FheInt4 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    FheInt4 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 7);

    FheInt4 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 6);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 7);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 6);
  }

  @Test
  void performsComparisonOperations() {
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    FheInt4 b = FheInt4.encryptWithClientKey((byte) 3, clientKey);

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
    FheInt4 a = FheInt4.encryptWithClientKey((byte) 5, clientKey);

    FheInt4 r = a.scalarAdd((byte) 2);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 7);

    a = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    a.scalarAddAssign((byte) 2);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 7);
  }


}
