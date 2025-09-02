package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt8;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheInt8;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheInt8Test {
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
    byte originalValue = (byte) 100;
    FheInt8 encrypted = FheInt8.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 100;
    FheInt8 encrypted = FheInt8.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 100;
    FheInt8 encrypted = FheInt8.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void serializesAndDeserializes() {
    FheInt8 original = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheInt8 deserialized = FheInt8.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void compressesAndDecompresses() {
    FheInt8 original = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    CompressedFheInt8 compressed = original.compress();
    FheInt8 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void clonesSuccessfully() {
    FheInt8 original = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    FheInt8 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void performsArithmeticOperations() {
    FheInt8 a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    FheInt8 b = FheInt8.encryptWithClientKey((byte) 50, clientKey);

    FheInt8 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 150);

    FheInt8 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 50);

    FheInt8 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 136);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 150);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 50);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 136);
  }

  @Test
  void performsBitwiseOperations() {
    FheInt8 a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    FheInt8 b = FheInt8.encryptWithClientKey((byte) 50, clientKey);

    FheInt8 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 32);

    FheInt8 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 118);

    FheInt8 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 86);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 32);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 118);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 86);
  }

  @Test
  void performsComparisonOperations() {
    FheInt8 a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    FheInt8 b = FheInt8.encryptWithClientKey((byte) 50, clientKey);

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
    FheInt8 a = FheInt8.encryptWithClientKey((byte) 100, clientKey);

    FheInt8 r = a.scalarAdd((byte) 7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 107);

    a = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    a.scalarAddAssign((byte) 7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 107);
  }


}
