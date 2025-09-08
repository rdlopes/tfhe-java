package io.github.rdlopes.tfhe.test.api.types.booleans;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.booleans.CompressedFheBool;
import io.github.rdlopes.tfhe.api.types.booleans.FheBool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class FheBoolTest {
  private static final Logger logger = LoggerFactory.getLogger(FheBoolTest.class);

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

  @Test
  void encryptsAndDecryptsWithClientKey() {
    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithClientKey(originalValue, clientKey);
    Boolean decrypted = encrypted.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(true);
  }
  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithPublicKey(originalValue, publicKey);
    Boolean decrypted = encrypted.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(true);
  }
  @Test
  void encryptsAndDecryptsTrivial() {
    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptTrivial(originalValue);
    Boolean decrypted = encrypted.decryptTrivial();

    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void compressesAndDecompresses() {
    Boolean value = true;
    FheBool original = FheBool.encryptWithClientKey(value, clientKey);
    CompressedFheBool compressed = original.compress();
    FheBool decompressed = compressed.decompress();
    Boolean decrypted = decompressed.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void performsLogicalOperations() {
    Boolean first = true;
    Boolean second = false;
    FheBool a = FheBool.encryptWithClientKey(first, clientKey);
    FheBool b = FheBool.encryptWithClientKey(second, clientKey);

    FheBool andResult = a.and(b);
    assertThat(andResult.decryptWithClientKey(clientKey)).isEqualTo(first && second);

    FheBool orResult = a.or(b);
    assertThat(orResult.decryptWithClientKey(clientKey)).isEqualTo(first || second);

    FheBool xorResult = a.xor(b);
    assertThat(xorResult.decryptWithClientKey(clientKey)).isEqualTo(first ^ second);

    FheBool notResult = a.not();
    assertThat(notResult.decryptWithClientKey(clientKey)).isEqualTo(!first);
  }
  @Test
  void performsScalarLogicalOperations() {
    Boolean value = true;
    FheBool a = FheBool.encryptWithClientKey(value, clientKey);
    Boolean scalar = false;

    FheBool scalarAndResult = a.scalarAnd(scalar);
    assertThat(scalarAndResult.decryptWithClientKey(clientKey)).isEqualTo(value && scalar);

    FheBool scalarOrResult = a.scalarOr(scalar);
    assertThat(scalarOrResult.decryptWithClientKey(clientKey)).isEqualTo(value || scalar);

    FheBool scalarXorResult = a.scalarXor(scalar);
    assertThat(scalarXorResult.decryptWithClientKey(clientKey)).isEqualTo(value ^ scalar);
  }

  @Test
  void performsComparisonOperations() {
    Boolean first = true;
    Boolean second = false;
    FheBool a = FheBool.encryptWithClientKey(first, clientKey);
    FheBool b = FheBool.encryptWithClientKey(second, clientKey);

    FheBool eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(clientKey)).isEqualTo(first == second);

    FheBool ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(clientKey)).isEqualTo(first != second);
  }
  @Test
  void performsScalarComparisonOperations() {
    Boolean value = true;
    FheBool a = FheBool.encryptWithClientKey(value, clientKey);
    Boolean scalar = false;

    FheBool scalarEq = a.scalarEq(scalar);
    assertThat(scalarEq.decryptWithClientKey(clientKey)).isEqualTo(value == scalar);

    FheBool scalarNe = a.scalarNe(scalar);
    assertThat(scalarNe.decryptWithClientKey(clientKey)).isEqualTo(value != scalar);
  }

  @Test
  void clonesSuccessfully() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    FheBool cloned = original.clone();
    Boolean clonedDecrypted = cloned.decryptWithClientKey(clientKey);

    assertThat(clonedDecrypted).isEqualTo(true);
  }

  @Test
  void serializesAndDeserializesSafely() {
    Boolean originalValue = true;
    FheBool original = FheBool.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.serialize();
    FheBool deserialized = FheBool.deserialize(serialized, serverKey);
    Boolean decrypted = deserialized.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(originalValue);
  }

  @Test
  void serializesAndDeserializesUnsafely() {
    Boolean originalValue = false;
    FheBool original = FheBool.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.unsafeSerialize();
    FheBool deserialized = FheBool.unsafeDeserialize(serialized);
    Boolean decrypted = deserialized.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(originalValue);
  }

}
