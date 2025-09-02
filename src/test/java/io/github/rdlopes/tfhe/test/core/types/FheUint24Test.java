package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint24;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.core.types.FheUint24;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheUint24Test {
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
    int originalValue = 100;
    FheUint24 encrypted = FheUint24.encryptWithClientKey(originalValue, clientKey);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    int originalValue = 100;
    FheUint24 encrypted = FheUint24.encryptWithPublicKey(originalValue, publicKey);
    int decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    int originalValue = 100;
    FheUint24 encrypted = FheUint24.encryptTrivial(originalValue);
    Integer decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void serializesAndDeserializes() {
    FheUint24 original = FheUint24.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheUint24 deserialized = FheUint24.deserialize(buffer, serverKey);
    int decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void compressesAndDecompresses() {
    FheUint24 original = FheUint24.encryptWithClientKey(100, clientKey);
    CompressedFheUint24 compressed = original.compress();
    FheUint24 decompressed = compressed.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    FheUint24 original = FheUint24.encryptWithClientKey(100, clientKey);
    FheUint24 cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    int decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void performsArithmeticOperations() {
    FheUint24 a = FheUint24.encryptWithClientKey(100, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50, clientKey);

    FheUint24 addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(150);

    FheUint24 subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(50);

    FheUint24 mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(5000);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(150);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(50);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(5000);
  }

  @Test
  void performsBitwiseOperations() {
    FheUint24 a = FheUint24.encryptWithClientKey(100, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50, clientKey);

    FheUint24 rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(32);

    FheUint24 rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(118);

    FheUint24 rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(86);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(32);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(118);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(86);
  }

  @Test
  void performsComparisonOperations() {
    FheUint24 a = FheUint24.encryptWithClientKey(100, clientKey);
    FheUint24 b = FheUint24.encryptWithClientKey(50, clientKey);

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
    FheUint24 a = FheUint24.encryptWithClientKey(100, clientKey);

    FheUint24 r = a.scalarAdd(7);
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(107);

    a = FheUint24.encryptWithClientKey(100, clientKey);
    a.scalarAddAssign(7);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(107);
  }


}
