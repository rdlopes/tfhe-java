package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelZkTest {

  private final MemorySegment configBuilderPtr = createPointer(C_POINTER);
  private final MemorySegment configPtr = createPointer(C_POINTER);
  private final MemorySegment clientKeyPtr = createPointer(C_POINTER);
  private final MemorySegment serverKeyPtr = createPointer(C_POINTER);
  private final MemorySegment publicKeyPtr = createPointer(C_POINTER);
  private final MemorySegment crsPtr = createPointer(C_POINTER);

  @BeforeEach
  void setUp() {
    // We want to use zk-proof, which requires bounded random distributions
    MemorySegment params = SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
    MemorySegment pkeParams = SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();

    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcCustomParams = config_builder_use_custom_parameters(configBuilderPtr, params);
    assertThat(rcCustomParams).isZero();

    int rcPkeParams = use_dedicated_compact_public_key_parameters(configBuilderPtr, pkeParams);
    assertThat(rcPkeParams).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    // Compute the CRS before generating keys
    long maxNumBits = 32L;
    int rcCrs = compact_pke_crs_from_config(configPtr.get(C_POINTER, 0), maxNumBits, crsPtr);
    assertThat(rcCrs).isZero();

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();

    int rcSetServerKey = set_server_key(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();

    int rcPublicKey = compact_public_key_new(clientKeyPtr.get(C_POINTER, 0), publicKeyPtr);
    assertThat(rcPublicKey).isZero();
  }

  @AfterEach
  void tearDown() {
    int rcDestroyClientKey = client_key_destroy(clientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyServerKey = server_key_destroy(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
    int rcDestroyPublicKey = compact_public_key_destroy(publicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyPublicKey).isZero();
    int rcDestroyCrs = compact_pke_crs_destroy(crsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCrs).isZero();
  }

  @Test
  void zkProofTest() {
    // Create metadata
    byte[] metadata = {'t', 'e', 's', 't'};
    MemorySegment metadataSegment = createPointer(metadata.length);
    for (int i = 0; i < metadata.length; i++) {
      metadataSegment.set(C_CHAR, i, metadata[i]);
    }

    // Create the compact list with proof
    MemorySegment compactListPtr = createPointer(C_POINTER);
    MemorySegment builderPtr = createPointer(C_POINTER);

    int rcBuilderNew = compact_ciphertext_list_builder_new(publicKeyPtr.get(C_POINTER, 0), builderPtr);
    assertThat(rcBuilderNew).isZero();

    // Push some values
    int rcPushU32 = compact_ciphertext_list_builder_push_u32(builderPtr.get(C_POINTER, 0), 38382);
    assertThat(rcPushU32).isZero();

    int rcPushI64 = compact_ciphertext_list_builder_push_i64(builderPtr.get(C_POINTER, 0), -1L);
    assertThat(rcPushI64).isZero();

    int rcPushBool = compact_ciphertext_list_builder_push_bool(builderPtr.get(C_POINTER, 0), true);
    assertThat(rcPushBool).isZero();

    int rcPushU2 = compact_ciphertext_list_builder_push_u2(builderPtr.get(C_POINTER, 0), (byte) 3);
    assertThat(rcPushU2).isZero();

    int rcBuildWithProof = compact_ciphertext_list_builder_build_with_proof_packed(
      builderPtr.get(C_POINTER, 0),
      crsPtr.get(C_POINTER, 0),
      metadataSegment,
      metadata.length,
      ZkComputeLoadProof(),
      compactListPtr
    );
    assertThat(rcBuildWithProof).isZero();

    // Destroy the builder
    int rcDestroyBuilder = compact_ciphertext_list_builder_destroy(builderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyBuilder).isZero();

    // Now we can expand values with verification
    MemorySegment aPtr = createPointer(C_POINTER);
    MemorySegment bPtr = createPointer(C_POINTER);
    MemorySegment cPtr = createPointer(C_POINTER);
    MemorySegment dPtr = createPointer(C_POINTER);

    MemorySegment expanderPtr = createPointer(C_POINTER);
    int rcVerifyAndExpand = proven_compact_ciphertext_list_verify_and_expand(
      compactListPtr.get(C_POINTER, 0),
      crsPtr.get(C_POINTER, 0),
      publicKeyPtr.get(C_POINTER, 0),
      metadataSegment,
      metadata.length,
      expanderPtr
    );
    assertThat(rcVerifyAndExpand).isZero();

    int rcGetA = compact_ciphertext_list_expander_get_fhe_uint32(expanderPtr.get(C_POINTER, 0), 0, aPtr);
    assertThat(rcGetA).isZero();

    int rcGetB = compact_ciphertext_list_expander_get_fhe_int64(expanderPtr.get(C_POINTER, 0), 1, bPtr);
    assertThat(rcGetB).isZero();

    int rcGetC = compact_ciphertext_list_expander_get_fhe_bool(expanderPtr.get(C_POINTER, 0), 2, cPtr);
    assertThat(rcGetC).isZero();

    int rcGetD = compact_ciphertext_list_expander_get_fhe_uint2(expanderPtr.get(C_POINTER, 0), 3, dPtr);
    assertThat(rcGetD).isZero();

    // Destroy the expander
    int rcDestroyExpander = compact_ciphertext_list_expander_destroy(expanderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyExpander).isZero();

    // Decrypt and verify values
    MemorySegment clearA = createPointer(C_INT);
    int rcDecryptA = fhe_uint32_decrypt(aPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearA);
    assertThat(rcDecryptA).isZero();
    assertThat(clearA.get(C_INT, 0)).isEqualTo(38382);

    MemorySegment clearB = createPointer(C_LONG);
    int rcDecryptB = fhe_int64_decrypt(bPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearB);
    assertThat(rcDecryptB).isZero();
    assertThat(clearB.get(C_LONG, 0)).isEqualTo(-1L);

    MemorySegment clearC = createPointer(C_BOOL);
    int rcDecryptC = fhe_bool_decrypt(cPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearC);
    assertThat(rcDecryptC).isZero();
    assertThat(clearC.get(C_BOOL, 0)).isTrue();

    MemorySegment clearD = createPointer(C_CHAR);
    int rcDecryptD = fhe_uint2_decrypt(dPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearD);
    assertThat(rcDecryptD).isZero();
    assertThat(clearD.get(C_CHAR, 0)).isEqualTo((byte) 3);

    // Clean up
    int rcDestroyA = fhe_uint32_destroy(aPtr.get(C_POINTER, 0));
    assertThat(rcDestroyA).isZero();
    int rcDestroyB = fhe_int64_destroy(bPtr.get(C_POINTER, 0));
    assertThat(rcDestroyB).isZero();
    int rcDestroyCVal = fhe_bool_destroy(cPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCVal).isZero();
    int rcDestroyD = fhe_uint2_destroy(dPtr.get(C_POINTER, 0));
    assertThat(rcDestroyD).isZero();

    int rcDestroyCompactList = proven_compact_ciphertext_list_destroy(compactListPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompactList).isZero();
  }

  @Test
  void zkProofFailureTest() {
    // Test that verification fails with wrong metadata or corrupted proof

    // Create compact list with original metadata
    byte[] originalMetadata = {'c', '-', 'a', 'p', 'i'};
    MemorySegment originalMetadataSegment = createPointer(originalMetadata.length);
    for (int i = 0; i < originalMetadata.length; i++) {
      originalMetadataSegment.set(C_CHAR, i, originalMetadata[i]);
    }

    MemorySegment compactListPtr = createPointer(C_POINTER);
    MemorySegment builderPtr = createPointer(C_POINTER);

    int rcBuilderNew = compact_ciphertext_list_builder_new(publicKeyPtr.get(C_POINTER, 0), builderPtr);
    assertThat(rcBuilderNew).isZero();

    int rcPushU32 = compact_ciphertext_list_builder_push_u32(builderPtr.get(C_POINTER, 0), 12345);
    assertThat(rcPushU32).isZero();

    int rcBuildWithProof = compact_ciphertext_list_builder_build_with_proof_packed(
      builderPtr.get(C_POINTER, 0),
      crsPtr.get(C_POINTER, 0),
      originalMetadataSegment,
      originalMetadata.length,
      ZkComputeLoadProof(),
      compactListPtr
    );
    assertThat(rcBuildWithProof).isZero();

    int rcDestroyBuilder = compact_ciphertext_list_builder_destroy(builderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyBuilder).isZero();

    // Try to verify with different metadata - this should fail
    byte[] wrongMetadata = {'w', 'r', 'o', 'n', 'g'};
    MemorySegment wrongMetadataSegment = createPointer(wrongMetadata.length);
    for (int i = 0; i < wrongMetadata.length; i++) {
      wrongMetadataSegment.set(C_CHAR, i, wrongMetadata[i]);
    }

    MemorySegment expanderPtr = createPointer(C_POINTER);
    int rcVerifyAndExpand = proven_compact_ciphertext_list_verify_and_expand(
      compactListPtr.get(C_POINTER, 0),
      crsPtr.get(C_POINTER, 0),
      publicKeyPtr.get(C_POINTER, 0),
      wrongMetadataSegment,
      wrongMetadata.length,
      expanderPtr
    );
    // This should fail with non-zero error code
    assertThat(rcVerifyAndExpand).isNotZero();

    // Clean up
    int rcDestroyCompactList = proven_compact_ciphertext_list_destroy(compactListPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompactList).isZero();
  }
}
