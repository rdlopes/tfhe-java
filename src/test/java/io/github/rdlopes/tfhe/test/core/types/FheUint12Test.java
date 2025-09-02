package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint12;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint12;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint12Test {
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
    FheUint12 encrypted = FheUint12.encryptWithClientKey(originalValue, clientKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    short originalValue = (short) 100;
    FheUint12 encrypted = FheUint12.encryptWithPublicKey(originalValue, publicKey);
    short decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    short originalValue = (short) 100;
    FheUint12 encrypted = FheUint12.encryptTrivial(originalValue);
    Short decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void serializesAndDeserializes() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint12 deserialized = FheUint12.deserialize(buffer, serverKey);
    short decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 100, clientKey);
    CompressedFheUint12 compressed = original.compress();
    FheUint12 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 100, clientKey);
    FheUint12 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    short decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void performsArithmeticOperations() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 50, clientKey);

    FheUint12 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo((short) 150);

    FheUint12 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo((short) 50);

    FheUint12 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo((short) 904);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 150);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 50);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 904);
  }

  @Test
  void performsBitwiseOperations() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 50, clientKey);

    FheUint12 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo((short) 32);

    FheUint12 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo((short) 118);

    FheUint12 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo((short) 86);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 32);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 118);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 86);
  }

  @Test
  void performsComparisonOperations() {
    FheUint12 a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    FheUint12 b = FheUint12.encryptWithClientKey((short) 50, clientKey);

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
    FheUint12 a = FheUint12.encryptWithClientKey((short) 100, clientKey);

    FheUint12 r = a.scalarAdd((short) 7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo((short) 107);

    a = FheUint12.encryptWithClientKey((short) 100, clientKey);
    a.scalarAddAssign((short) 7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo((short) 107);
  }


}
