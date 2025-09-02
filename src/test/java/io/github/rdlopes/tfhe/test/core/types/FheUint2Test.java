package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint2;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint2Test {
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
    byte originalValue = (byte) 3;
    FheUint2 encrypted = FheUint2.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 3;
    FheUint2 encrypted = FheUint2.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 3;
    FheUint2 encrypted = FheUint2.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void serializesAndDeserializes() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint2 deserialized = FheUint2.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    CompressedFheUint2 compressed = original.compress();
    FheUint2 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void clonesSuccessfully() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void performsArithmeticOperations() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 3, clientKey);

    FheUint2 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    FheUint2 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    FheUint2 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 1);
  }

  @Test
  void performsBitwiseOperations() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 3, clientKey);

    FheUint2 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 3);

    FheUint2 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 3);

    FheUint2 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 3);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 3);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);
  }

  @Test
  void performsComparisonOperations() {
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    FheUint2 b = FheUint2.encryptWithClientKey((byte) 3, clientKey);

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
    FheUint2 a = FheUint2.encryptWithClientKey((byte) 3, clientKey);

    FheUint2 r = a.scalarAdd((byte) 3);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    a = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    a.scalarAddAssign((byte) 3);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);
  }


}
