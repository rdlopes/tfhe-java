package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheBool;
import io.github.rdlopes.tfhe.core.types.FheBool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheBoolTest {
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
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithClientKey(originalValue, clientKey);
    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithPublicKey(originalValue, publicKey);
    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
    publicKey.destroy();
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptTrivial(originalValue);
    Boolean decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializes() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    DynamicBufferView buffer = original.serialize();
    FheBool deserialized = FheBool.deserialize(buffer, serverKey);
    boolean decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void compressesAndDecompresses() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    CompressedFheBool compressed = original.compress();
    FheBool decompressed = compressed.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void clonesSuccessfully() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    FheBool cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
    boolean decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }


  @Test
  void performsBooleanOperations() {
    FheBool a = FheBool.encryptWithClientKey(true, clientKey);
    FheBool b = FheBool.encryptWithClientKey(false, clientKey);

    FheBool andResult = a.and(b);
    assertThat(andResult.decryptWithClientKey(clientKey)).isFalse();

    FheBool orResult = a.or(b);
    assertThat(orResult.decryptWithClientKey(clientKey)).isTrue();

    FheBool c = FheBool.encryptWithClientKey(true, clientKey);
    FheBool d = FheBool.encryptWithClientKey(true, clientKey);
    FheBool xorResult = c.xor(d);
    assertThat(xorResult.decryptWithClientKey(clientKey)).isFalse();

    a = FheBool.encryptWithClientKey(true, clientKey);
    b = FheBool.encryptWithClientKey(false, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isFalse();

    a = FheBool.encryptWithClientKey(false, clientKey);
    b = FheBool.encryptWithClientKey(true, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isTrue();

    a = FheBool.encryptWithClientKey(true, clientKey);
    b = FheBool.encryptWithClientKey(false, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isTrue();

    a = FheBool.encryptWithClientKey(true, clientKey);
    FheBool notResult = a.not();
    assertThat(notResult.decryptWithClientKey(clientKey)).isFalse();
  }

  @Test
  void performsScalarBooleanOperations() {
    FheBool a = FheBool.encryptWithClientKey(true, clientKey);
    FheBool andResult = a.scalarAnd(false);
    assertThat(andResult.decryptWithClientKey(clientKey)).isFalse();

    FheBool b = FheBool.encryptWithClientKey(false, clientKey);
    FheBool orResult = b.scalarOr(true);
    assertThat(orResult.decryptWithClientKey(clientKey)).isTrue();

    FheBool c = FheBool.encryptWithClientKey(true, clientKey);
    FheBool xorResult = c.scalarXor(false);
    assertThat(xorResult.decryptWithClientKey(clientKey)).isTrue();

    a = FheBool.encryptWithClientKey(true, clientKey);
    a.scalarAndAssign(false);
    assertThat(a.decryptWithClientKey(clientKey)).isFalse();

    a = FheBool.encryptWithClientKey(false, clientKey);
    a.scalarOrAssign(true);
    assertThat(a.decryptWithClientKey(clientKey)).isTrue();

    a = FheBool.encryptWithClientKey(true, clientKey);
    a.scalarXorAssign(true);
    assertThat(a.decryptWithClientKey(clientKey)).isFalse();
  }

  @Test
  void performsEqualityOperations() {
    FheBool a = FheBool.encryptWithClientKey(true, clientKey);
    FheBool b = FheBool.encryptWithClientKey(true, clientKey);
    FheBool eqResult = a.eq(b);
    assertThat(eqResult.decryptWithClientKey(clientKey)).isTrue();

    FheBool c = FheBool.encryptWithClientKey(true, clientKey);
    FheBool d = FheBool.encryptWithClientKey(false, clientKey);
    FheBool neResult = c.ne(d);
    assertThat(neResult.decryptWithClientKey(clientKey)).isTrue();

    FheBool e = FheBool.encryptWithClientKey(true, clientKey);
    FheBool scalarEqResult = e.scalarEq(true);
    assertThat(scalarEqResult.decryptWithClientKey(clientKey)).isTrue();

    FheBool f = FheBool.encryptWithClientKey(true, clientKey);
    FheBool scalarNeResult = f.scalarNe(false);
    assertThat(scalarNeResult.decryptWithClientKey(clientKey)).isTrue();
  }

}
