package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.shortint.*;
import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.ffm.NativeCallException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CoverageExtensionsTest {

  static {
    io.github.rdlopes.tfhe.ffm.NativeLibrary.load();
  }

  @Test
  void testTrivialDecryption() {
    KeySet keySet = KeySet.builder().build();
    keySet.getServerKey().use();

    // FheBool trivial decryption
    FheBool trivialBool = FheBool.encrypt(true);
    assertThat(trivialBool.tryDecryptTrivial()).contains(true);

    FheBool trivialBoolFalse = FheBool.encrypt(false);
    assertThat(trivialBoolFalse.tryDecryptTrivial()).contains(false);

    // FheBool non-trivial decryption should be empty
    FheBool normalBool = FheBool.encrypt(true, keySet.getClientKey());
    assertThat(normalBool.tryDecryptTrivial()).isEmpty();

    // FheInt8 trivial decryption
    FheInt8 trivialInt8 = FheInt8.encrypt((byte) 42);
    assertThat(trivialInt8.tryDecryptTrivial()).contains((byte) 42);

    FheInt8 normalInt8 = FheInt8.encrypt((byte) 42, keySet.getClientKey());
    assertThat(normalInt8.tryDecryptTrivial()).isEmpty();

    // FheUint32 trivial decryption
    FheUint32 trivialUint32 = FheUint32.encrypt(1337);
    assertThat(trivialUint32.tryDecryptTrivial()).contains(1337);
  }

  @Test
  void testCompactPkeCrs() {
    KeySet keySet = KeySet.builder().build();
    keySet.getServerKey().use();

    ConfigBuilder crsBuilder = new ConfigBuilder();
    execute(() -> use_dedicated_compact_public_key_parameters(
        crsBuilder.getAddress(),
        CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
    ));
    Config crsConfig = crsBuilder.build();

    try (CompactPkeCrs crs = new CompactPkeCrs(crsConfig, 8)) {
      assertThat(crs).isNotNull();

      // Test unsafe serialization/deserialization
      try (DynamicBuffer serialized = crs.serializeUnsafe(true)) {
        assertThat(serialized.toByteArray()).isNotEmpty();
        try (CompactPkeCrs deserialized = CompactPkeCrs.deserializeUnsafe(serialized)) {
          assertThat(deserialized).isNotNull();
        }
      }

      // Test parameter-based deserialization
      try (DynamicBuffer serializedSafe = crs.serialize(true)) {
        assertThatExceptionOfType(NativeCallException.class)
            .isThrownBy(() -> CompactPkeCrs.deserializeFromParams(serializedSafe));

        assertThatExceptionOfType(NativeCallException.class)
            .isThrownBy(() -> CompactPkeCrs.safeDeserializeFromParams(serializedSafe));
      }
    }
  }

  @Test
  void testShortintExtensions() {
    ShortintClientKey.KeyPair pair = ShortintClientKey.generateKeysWithParameters(
        CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128
    );
    assertThat(pair.clientKey).isNotNull();
    assertThat(pair.serverKey).isNotNull();

    // Test getDegree and setDegree
    ShortintCiphertext ct = pair.clientKey.encrypt(2);
    long degree = ct.getDegree();
    ct.setDegree(degree + 1);
    assertThat(ct.getDegree()).isEqualTo(degree + 1);

    // Test createTrivial
    ShortintCiphertext ctTrivial = pair.serverKey.createTrivial(3);
    assertThat(pair.clientKey.decrypt(ctTrivial)).isEqualTo(3);

    // Test unchecked division
    ShortintCiphertext ct2 = pair.clientKey.encrypt(2);
    ShortintCiphertext ct1 = pair.clientKey.encrypt(1);
    ShortintCiphertext ctDiv = pair.serverKey.uncheckedDiv(ct2, ct1);
    assertThat(pair.clientKey.decrypt(ctDiv)).isEqualTo(2);

    pair.serverKey.uncheckedDivAssign(ct2, ct1);
    assertThat(pair.clientKey.decrypt(ct2)).isEqualTo(2);

    // Test unchecked comparisons
    ShortintCiphertext ctA = pair.clientKey.encrypt(3);
    ShortintCiphertext ctB = pair.clientKey.encrypt(2);

    ShortintCiphertext eq = pair.serverKey.uncheckedEqual(ctA, ctB);
    assertThat(pair.clientKey.decrypt(eq)).isEqualTo(0);

    ShortintCiphertext ne = pair.serverKey.uncheckedNotEqual(ctA, ctB);
    assertThat(pair.clientKey.decrypt(ne)).isEqualTo(1);

    ShortintCiphertext gt = pair.serverKey.uncheckedGreater(ctA, ctB);
    assertThat(pair.clientKey.decrypt(gt)).isEqualTo(1);

    ShortintCiphertext ge = pair.serverKey.uncheckedGreaterOrEqual(ctA, ctB);
    assertThat(pair.clientKey.decrypt(ge)).isEqualTo(1);

    ShortintCiphertext lt = pair.serverKey.uncheckedLess(ctA, ctB);
    assertThat(pair.clientKey.decrypt(lt)).isEqualTo(0);

    ShortintCiphertext le = pair.serverKey.uncheckedLessOrEqual(ctA, ctB);
    assertThat(pair.clientKey.decrypt(le)).isEqualTo(0);
  }

  @Test
  void testShortintCompressedKeys() {
    ShortintClientKey clientKey = ShortintClientKey.generate(
        CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128
    );

    // Compressed public key lifecycle
    ShortintCompressedPublicKey compPubKey = clientKey.genCompressedPublicKey();
    try (DynamicBuffer buffer = compPubKey.serialize()) {
      assertThat(buffer.toByteArray()).isNotEmpty();
      ShortintCompressedPublicKey deserializedPubKey = ShortintCompressedPublicKey.deserialize(buffer);
      ShortintPublicKey pubKey = deserializedPubKey.decompress();
      assertThat(pubKey).isNotNull();
      ShortintCiphertext ctPub = pubKey.encrypt(2);
      assertThat(clientKey.decrypt(ctPub)).isEqualTo(2);
    }

    // Direct encryption using compressed public key
    ShortintCiphertext ctCompPub = compPubKey.encrypt(3);
    assertThat(clientKey.decrypt(ctCompPub)).isEqualTo(3);

    // Compressed server key lifecycle
    ShortintCompressedServerKey compServKey = clientKey.genCompressedServerKey();
    try (DynamicBuffer buffer = compServKey.serialize()) {
      assertThat(buffer.toByteArray()).isNotEmpty();
      ShortintCompressedServerKey deserializedServKey = ShortintCompressedServerKey.deserialize(buffer);
      ShortintServerKey servKey = deserializedServKey.decompress();
      assertThat(servKey).isNotNull();
      ShortintCiphertext ctA = clientKey.encrypt(2);
      ShortintCiphertext ctB = clientKey.encrypt(1);
      ShortintCiphertext ctAdd = servKey.smartAdd(ctA, ctB);
      assertThat(clientKey.decrypt(ctAdd)).isEqualTo(3);
    }
  }
}
