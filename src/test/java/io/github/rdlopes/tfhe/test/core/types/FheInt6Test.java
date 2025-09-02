package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt6;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt6;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt6Test {
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
    byte originalValue = (byte) 31;
    FheInt6 encrypted = FheInt6.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 31;
    FheInt6 encrypted = FheInt6.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 31;
    FheInt6 encrypted = FheInt6.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt6 original = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt6 deserialized = FheInt6.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt6 original = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    CompressedFheInt6 compressed = original.compress();
    FheInt6 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void clonesSuccessfully() {
    FheInt6 original = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    FheInt6 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt6 a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    FheInt6 b = FheInt6.encryptWithClientKey((byte) 31, clientKey);

    FheInt6 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);

    FheInt6 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    FheInt6 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 254);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt6 a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    FheInt6 b = FheInt6.encryptWithClientKey((byte) 31, clientKey);

    FheInt6 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 31);

    FheInt6 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 31);

    FheInt6 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 31);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 31);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);
  }

  @Test
  void performsComparisonOperations() {
    FheInt6 a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    FheInt6 b = FheInt6.encryptWithClientKey((byte) 31, clientKey);

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
    FheInt6 a = FheInt6.encryptWithClientKey((byte) 31, clientKey);

    FheInt6 r = a.scalarAdd((byte) 7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 230);

    a = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    a.scalarAddAssign((byte) 7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 230);
  }


}
