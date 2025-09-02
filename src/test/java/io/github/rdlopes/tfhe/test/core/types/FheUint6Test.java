package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint6;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint6;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint6Test {
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
    byte originalValue = (byte) 63;
    FheUint6 encrypted = FheUint6.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 63;
    FheUint6 encrypted = FheUint6.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 63;
    FheUint6 encrypted = FheUint6.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void serializesAndDeserializes() {
    FheUint6 original = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint6 deserialized = FheUint6.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint6 original = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    CompressedFheUint6 compressed = original.compress();
    FheUint6 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void clonesSuccessfully() {
    FheUint6 original = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    FheUint6 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void performsArithmeticOperations() {
    FheUint6 a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    FheUint6 b = FheUint6.encryptWithClientKey((byte) 50, clientKey);

    FheUint6 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 49);

    FheUint6 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);

    FheUint6 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 14);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 49);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 14);
  }

  @Test
  void performsBitwiseOperations() {
    FheUint6 a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    FheUint6 b = FheUint6.encryptWithClientKey((byte) 50, clientKey);

    FheUint6 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 50);

    FheUint6 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 63);

    FheUint6 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 50);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 63);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);
  }

  @Test
  void performsComparisonOperations() {
    FheUint6 a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    FheUint6 b = FheUint6.encryptWithClientKey((byte) 50, clientKey);

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
    FheUint6 a = FheUint6.encryptWithClientKey((byte) 63, clientKey);

    FheUint6 r = a.scalarAdd((byte) 7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 6);

    a = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    a.scalarAddAssign((byte) 7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 6);
  }


}
