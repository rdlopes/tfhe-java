package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheBool;
import io.github.rdlopes.tfhe.core.types.FheBool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheBoolTest {

  private ClientKey clientKey;
  private PublicKey publicKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    KeySet keySet = new ConfigBuilder().build()
                                       .generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();

    publicKey = PublicKey.newWith(clientKey);
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithClientKey(originalValue, clientKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithPublicKey(originalValue, publicKey);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void encryptsAndDecryptsTrivial() {
    boolean originalValue = true;
    FheBool encrypted = FheBool.encryptTrivial(originalValue);
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.getValue()).isNotNull();

    Boolean decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void performsAndOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(false, clientKey);

    FheBool result = encrypted1.and(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsAndAssignOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(false, clientKey);

    encrypted1.andAssign(encrypted2);
    boolean decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsOrOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(false, clientKey);

    FheBool result = encrypted1.or(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsOrAssignOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(false, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(true, clientKey);

    encrypted1.orAssign(encrypted2);
    boolean decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsXorOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted1.xor(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsXorAssignOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(false, clientKey);

    encrypted1.xorAssign(encrypted2);
    boolean decrypted = encrypted1.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted.not();
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsScalarAndOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted.scalarAnd(false);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsScalarAndAssignOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    encrypted.scalarAndAssign(false);
    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsScalarOrOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(false, clientKey);

    FheBool result = encrypted.scalarOr(true);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarOrAssignOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(false, clientKey);

    encrypted.scalarOrAssign(true);
    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarXorOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted.scalarXor(false);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarXorAssignOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    encrypted.scalarXorAssign(true);
    boolean decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isFalse();
  }

  @Test
  void performsEqualityOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted1.eq(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsNotEqualOperation() {
    FheBool encrypted1 = FheBool.encryptWithClientKey(true, clientKey);
    FheBool encrypted2 = FheBool.encryptWithClientKey(false, clientKey);

    FheBool result = encrypted1.ne(encrypted2);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarEqualityOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted.scalarEq(true);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void performsScalarNotEqualOperation() {
    FheBool encrypted = FheBool.encryptWithClientKey(true, clientKey);

    FheBool result = encrypted.scalarNe(false);
    assertThat(result).isNotNull();

    boolean decrypted = result.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void serializesAndDeserializes() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    DynamicBufferView buffer = original.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    FheBool deserialized = FheBool.deserialize(buffer, serverKey);
    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getValue()).isNotNull();

    boolean decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void compressesAndDecompresses() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);

    CompressedFheBool compressed = original.compress();
    assertThat(compressed).isNotNull();
    assertThat(compressed.getValue()).isNotNull();

    FheBool decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }

  @Test
  void clonesSuccessfully() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    FheBool cloned = original.clone();
    assertThat(cloned).isNotSameAs(original);

    FheBool encryptedEquality = cloned.eq(original);
    boolean decryptedEquality = encryptedEquality.decryptWithClientKey(clientKey);
    assertThat(decryptedEquality).isTrue();

    boolean decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isTrue();
  }
}
