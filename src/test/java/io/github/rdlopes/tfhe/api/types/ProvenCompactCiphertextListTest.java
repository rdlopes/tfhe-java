package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;
import static org.assertj.core.api.Assertions.assertThat;

class ProvenCompactCiphertextListTest {
  private KeySet keySet;
  private CompactPkeCrs crs;

  @BeforeEach
  void setUp() {
    // 1. Build CRS using a separate config builder
    ConfigBuilder crsBuilder = new ConfigBuilder();
    execute(() -> use_dedicated_compact_public_key_parameters(
        crsBuilder.getAddress(),
        CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
    ));
    Config crsConfig = crsBuilder.build();
    crs = new CompactPkeCrs(crsConfig, 8);

    // 2. Build KeySet using the public KeySet builder
    keySet = KeySet.builder()
                   .useCompactKeyEncryptionParameters(CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .build();
    keySet.getServerKey().use();
  }

  @Test
  void testCrsSerialization() {
    assertThat(crs).isNotNull();
    try (DynamicBuffer serialized = crs.serialize()) {
      byte[] bytes = serialized.toByteArray();
      assertThat(bytes).isNotEmpty();

      try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes);
           CompactPkeCrs deserialized = CompactPkeCrs.deserialize(buffer)) {
        assertThat(deserialized).isNotNull();
      }
    }
  }

  @Test
  void testProvenCompactListBuildVerifyAndExpand() {
    CompactPublicKey compactPublicKey = new CompactPublicKey(keySet.getClientKey());
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);

    builder.push(true);
    builder.push((byte) 42);
    builder.push(12);

    byte[] metadata = new byte[]{1, 2, 3, 4};

    // Build proven compact ciphertext list
    try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF)) {
      assertThat(provenList).isNotNull();

      // Test serialization & deserialization
      try (DynamicBuffer serialized = provenList.serialize()) {
        byte[] bytes = serialized.toByteArray();
        assertThat(bytes).isNotEmpty();

        try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes);
             ProvenCompactCiphertextList deserialized = ProvenCompactCiphertextList.deserialize(buffer)) {
          assertThat(deserialized).isNotNull();

          // Verify and expand on the server/decryptor side
          try (CompactCiphertextListExpander expander = deserialized.verifyAndExpand(crs, compactPublicKey, metadata)) {
            assertThat(expander).isNotNull();
            assertThat(expander.size()).isEqualTo(3);

            assertThat(expander.getKindOf(0)).isEqualTo(FheTypes.FheBool);
            assertThat(expander.getKindOf(1)).isEqualTo(FheTypes.FheInt8);
            assertThat(expander.getKindOf(2)).isEqualTo(FheTypes.FheInt32);

            FheBool decryptedBool = expander.getFheBool(0);
            FheInt8 decryptedInt8 = expander.getFheInt8(1);
            FheInt32 decryptedInt32 = expander.getFheInt32(2);

            assertThat(decryptedBool.decrypt(keySet.getClientKey())).isTrue();
            assertThat(decryptedInt8.decrypt(keySet.getClientKey())).isEqualTo((byte) 42);
            assertThat(decryptedInt32.decrypt(keySet.getClientKey())).isEqualTo(12);
          }
        }
      }
    }
  }

  @Test
  void testProvenCompactListConformantDeserialization() {
    CompactPublicKey compactPublicKey = new CompactPublicKey(keySet.getClientKey());
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);

    builder.push(false);
    builder.push((byte) 7);

    byte[] metadata = new byte[]{9, 8, 7};

    try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF)) {
      assertThat(provenList).isNotNull();

      try (DynamicBuffer serialized = provenList.serialize()) {
        byte[] bytes = serialized.toByteArray();
        assertThat(bytes).isNotEmpty();

        try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes);
             ProvenCompactCiphertextList deserialized = ProvenCompactCiphertextList.deserializeConformant(buffer, compactPublicKey, crs)) {
          assertThat(deserialized).isNotNull();

          try (CompactCiphertextListExpander expander = deserialized.verifyAndExpand(crs, compactPublicKey, metadata)) {
            assertThat(expander).isNotNull();
            assertThat(expander.size()).isEqualTo(2);

            FheBool decryptedBool = expander.getFheBool(0);
            FheInt8 decryptedInt8 = expander.getFheInt8(1);

            assertThat(decryptedBool.decrypt(keySet.getClientKey())).isFalse();
            assertThat(decryptedInt8.decrypt(keySet.getClientKey())).isEqualTo((byte) 7);
          }
        }
      }
    }
  }
}
