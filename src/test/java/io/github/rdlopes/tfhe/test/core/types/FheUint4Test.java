package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint4;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint4Test {
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
    byte originalValue = (byte) 10;
    FheUint4 encrypted = FheUint4.encryptWithClientKey(originalValue, clientKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    byte originalValue = (byte) 10;
    FheUint4 encrypted = FheUint4.encryptWithPublicKey(originalValue, publicKey);
    byte decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    byte originalValue = (byte) 10;
    FheUint4 encrypted = FheUint4.encryptTrivial(originalValue);
    Byte decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void serializesAndDeserializes() {
    FheUint4 original = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint4 deserialized = FheUint4.deserialize(buffer, serverKey);
    byte decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint4 original = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    CompressedFheUint4 compressed = original.compress();
    FheUint4 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void clonesSuccessfully() {
    FheUint4 original = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    byte decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void performsArithmeticOperations() {
    FheUint4 a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 b = FheUint4.encryptWithClientKey((byte) 6, clientKey);

    FheUint4 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    FheUint4 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 4);

    FheUint4 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((byte) 12);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 0);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 4);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 12);
  }

  @Test
  void performsBitwiseOperations() {
    FheUint4 a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 b = FheUint4.encryptWithClientKey((byte) 6, clientKey);

    FheUint4 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    FheUint4 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((byte) 14);

    FheUint4 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((byte) 12);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 2);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 14);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 12);
  }

  @Test
  void performsComparisonOperations() {
    FheUint4 a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    FheUint4 b = FheUint4.encryptWithClientKey((byte) 6, clientKey);

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
    FheUint4 a = FheUint4.encryptWithClientKey((byte) 10, clientKey);

    FheUint4 r = a.scalarAdd((byte) 3);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);

    a = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    a.scalarAddAssign((byte) 3);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((byte) 13);
  }


}
