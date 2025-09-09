package io.github.rdlopes.tfhe.api.types.booleans;

import io.github.rdlopes.tfhe.api.keys.Config;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;

class FheBoolTest {
  private static final Logger logger = LoggerFactory.getLogger(FheBoolTest.class);
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    keySet = config.generateKeys();
    keySet.serverKey()
          .setAsKey();
  }

  @AfterEach
  void tearDown() {
    keySet.destroy();
  }

  @Test
  void encryptsAndDecryptsWithClientKey() {
    logger.trace("encryptsAndDecryptsWithClientKey");

    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithClientKey(originalValue, keySet.clientKey());
    Boolean decrypted = encrypted.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(true);
  }
  @Test
  void encryptsAndDecryptsWithPublicKey() {
    logger.trace("encryptsAndDecryptsWithPublicKey");

    PublicKey publicKey = PublicKey.createFromClientKey(keySet.clientKey());
    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptWithPublicKey(originalValue, publicKey);
    Boolean decrypted = encrypted.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(true);
  }
  @Test
  void encryptsAndDecryptsTrivial() {
    logger.trace("encryptsAndDecryptsTrivial");

    Boolean originalValue = true;
    FheBool encrypted = FheBool.encryptTrivial(originalValue);
    Boolean decrypted = encrypted.decryptTrivial();

    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void compressesAndDecompresses() {
    logger.trace("compressesAndDecompresses");

    Boolean value = true;
    FheBool original = FheBool.encryptWithClientKey(value, keySet.clientKey());
    CompressedFheBool compressed = original.compress();
    FheBool decompressed = compressed.decompress();
    Boolean decrypted = decompressed.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void performsLogicalOperations() {
    logger.trace("performsLogicalOperations");

    Boolean first = true;
    Boolean second = false;
    FheBool a = FheBool.encryptWithClientKey(first, keySet.clientKey());
    FheBool b = FheBool.encryptWithClientKey(second, keySet.clientKey());

    FheBool andResult = a.and(b);
    assertThat(andResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first && second);

    FheBool orResult = a.or(b);
    assertThat(orResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first || second);

    FheBool xorResult = a.xor(b);
    assertThat(xorResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first ^ second);

    FheBool notResult = a.not();
    assertThat(notResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(!first);
  }
  @Test
  void performsScalarLogicalOperations() {
    logger.trace("performsScalarLogicalOperations");

    Boolean value = true;
    FheBool a = FheBool.encryptWithClientKey(value, keySet.clientKey());
    Boolean scalar = false;

    FheBool scalarAndResult = a.scalarAnd(scalar);
    assertThat(scalarAndResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value && scalar);

    FheBool scalarOrResult = a.scalarOr(scalar);
    assertThat(scalarOrResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value || scalar);

    FheBool scalarXorResult = a.scalarXor(scalar);
    assertThat(scalarXorResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value ^ scalar);
  }

  @Test
  void performsComparisonOperations() {
    logger.trace("performsComparisonOperations");

    Boolean first = true;
    Boolean second = false;
    FheBool a = FheBool.encryptWithClientKey(first, keySet.clientKey());
    FheBool b = FheBool.encryptWithClientKey(second, keySet.clientKey());

    FheBool eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(keySet.clientKey())).isEqualTo(first == second);

    FheBool ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(keySet.clientKey())).isEqualTo(first != second);
  }
  @Test
  void performsScalarComparisonOperations() {
    logger.trace("performsScalarComparisonOperations");

    Boolean value = true;
    FheBool a = FheBool.encryptWithClientKey(value, keySet.clientKey());
    Boolean scalar = false;

    FheBool scalarEq = a.scalarEq(scalar);
    assertThat(scalarEq.decryptWithClientKey(keySet.clientKey())).isEqualTo(value == scalar);

    FheBool scalarNe = a.scalarNe(scalar);
    assertThat(scalarNe.decryptWithClientKey(keySet.clientKey())).isEqualTo(value != scalar);
  }

  @Test
  void clonesSuccessfully() {
    logger.trace("clonesSuccessfully");

    FheBool original = FheBool.encryptWithClientKey(true, keySet.clientKey());
    FheBool cloned = original.clone();
    Boolean clonedDecrypted = cloned.decryptWithClientKey(keySet.clientKey());

    assertThat(clonedDecrypted).isEqualTo(true);
  }

  @Test
  void serializesAndDeserializesSafely() {
    logger.trace("serializesAndDeserializesSafely");

    Boolean originalValue = true;
    FheBool original = FheBool.encryptWithClientKey(originalValue, keySet.clientKey());

    byte[] serialized = original.serialize();
    FheBool deserialized = FheBool.deserialize(serialized, keySet.serverKey());
    Boolean decrypted = deserialized.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(originalValue);
  }
  @Test
  void serializesAndDeserializesUnsafely() {
    logger.trace("serializesAndDeserializesUnsafely");

    Boolean originalValue = false;
    FheBool original = FheBool.encryptWithClientKey(originalValue, keySet.clientKey());
    byte[] serialized = original.unsafeSerialize();
    FheBool deserialized = FheBool.unsafeDeserialize(serialized);
    Boolean decrypted = deserialized.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(originalValue);
  }

}
