package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelCompactListTest {

  @Test
  void compactPublicKeyUseCase() {
    // Create config with default parameters
    MemorySegment configBuilderPtr = createPointer(C_POINTER);
    MemorySegment configPtr = createPointer(C_POINTER);

    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    testCompactPublicKeyUseCaseWithConfig(configPtr.get(C_POINTER, 0));
  }

  @Test
  void compactPublicKeyUseCaseWithCustomParameters() {
    // Create config with custom parameters
    MemorySegment customConfigBuilderPtr = createPointer(C_POINTER);
    MemorySegment customConfigPtr = createPointer(C_POINTER);

    int rcBuilder = config_builder_default(customConfigBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcCustomParams = config_builder_use_custom_parameters(
      customConfigBuilderPtr,
      SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()
    );
    assertThat(rcCustomParams).isZero();

    int rcPkeParams = use_dedicated_compact_public_key_parameters(
      customConfigBuilderPtr,
      SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()
    );
    assertThat(rcPkeParams).isZero();

    int rcConfig = config_builder_build(customConfigBuilderPtr.get(C_POINTER, 0), customConfigPtr);
    assertThat(rcConfig).isZero();

    testCompactPublicKeyUseCaseWithConfig(customConfigPtr.get(C_POINTER, 0));
  }

  private void testCompactPublicKeyUseCaseWithConfig(MemorySegment config) {
    // Create dedicated keys for this test execution
    MemorySegment testClientKeyPtr = createPointer(C_POINTER);
    MemorySegment testServerKeyPtr = createPointer(C_POINTER);
    MemorySegment testPublicKeyPtr = createPointer(C_POINTER);

    int rcKeys = generate_keys(config, testClientKeyPtr, testServerKeyPtr);
    assertThat(rcKeys).isZero();

    int rcSetServerKey = set_server_key(testServerKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();

    int rcPublicKey = compact_public_key_new(testClientKeyPtr.get(C_POINTER, 0), testPublicKeyPtr);
    assertThat(rcPublicKey).isZero();

    // Create the compact list
    MemorySegment compactListPtr = createPointer(C_POINTER);
    MemorySegment builderPtr = createPointer(C_POINTER);

    int rcBuilderNew = compact_ciphertext_list_builder_new(testPublicKeyPtr.get(C_POINTER, 0), builderPtr);
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

    int rcBuild = compact_ciphertext_list_builder_build(builderPtr.get(C_POINTER, 0), compactListPtr);
    assertThat(rcBuild).isZero();

    // Destroy the builder
    int rcDestroyBuilder = compact_ciphertext_list_builder_destroy(builderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyBuilder).isZero();

    // Now expand values
    MemorySegment expanderPtr = createPointer(C_POINTER);
    int rcExpand = compact_ciphertext_list_expand(compactListPtr.get(C_POINTER, 0), expanderPtr);
    assertThat(rcExpand).isZero();

    MemorySegment lenPtr = createPointer(C_LONG);
    int rcLen = compact_ciphertext_list_expander_len(expanderPtr.get(C_POINTER, 0), lenPtr);
    assertThat(rcLen).isZero();
    assertThat(lenPtr.get(C_LONG, 0)).isEqualTo(4L);

    // Check types of slots
    MemorySegment typePtr = createPointer(C_INT);

    int rcType0 = compact_ciphertext_list_expander_get_kind_of(expanderPtr.get(C_POINTER, 0), 0, typePtr);
    assertThat(rcType0).isZero();
    assertThat(typePtr.get(C_INT, 0)).isEqualTo(Type_FheUint32());

    int rcType1 = compact_ciphertext_list_expander_get_kind_of(expanderPtr.get(C_POINTER, 0), 1, typePtr);
    assertThat(rcType1).isZero();
    assertThat(typePtr.get(C_INT, 0)).isEqualTo(Type_FheInt64());

    int rcType2 = compact_ciphertext_list_expander_get_kind_of(expanderPtr.get(C_POINTER, 0), 2, typePtr);
    assertThat(rcType2).isZero();
    assertThat(typePtr.get(C_INT, 0)).isEqualTo(Type_FheBool());

    int rcType3 = compact_ciphertext_list_expander_get_kind_of(expanderPtr.get(C_POINTER, 0), 3, typePtr);
    assertThat(rcType3).isZero();
    assertThat(typePtr.get(C_INT, 0)).isEqualTo(Type_FheUint2());

    // Get the values
    MemorySegment aPtr = createPointer(C_POINTER);
    MemorySegment bPtr = createPointer(C_POINTER);
    MemorySegment cPtr = createPointer(C_POINTER);
    MemorySegment dPtr = createPointer(C_POINTER);

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
    int rcDecryptA = fhe_uint32_decrypt(aPtr.get(C_POINTER, 0), testClientKeyPtr.get(C_POINTER, 0), clearA);
    assertThat(rcDecryptA).isZero();
    assertThat(clearA.get(C_INT, 0)).isEqualTo(38382);

    MemorySegment clearB = createPointer(C_LONG);
    int rcDecryptB = fhe_int64_decrypt(bPtr.get(C_POINTER, 0), testClientKeyPtr.get(C_POINTER, 0), clearB);
    assertThat(rcDecryptB).isZero();
    assertThat(clearB.get(C_LONG, 0)).isEqualTo(-1L);

    MemorySegment clearC = createPointer(C_BOOL);
    int rcDecryptC = fhe_bool_decrypt(cPtr.get(C_POINTER, 0), testClientKeyPtr.get(C_POINTER, 0), clearC);
    assertThat(rcDecryptC).isZero();
    assertThat(clearC.get(C_BOOL, 0)).isTrue();

    MemorySegment clearD = createPointer(C_CHAR);
    int rcDecryptD = fhe_uint2_decrypt(dPtr.get(C_POINTER, 0), testClientKeyPtr.get(C_POINTER, 0), clearD);
    assertThat(rcDecryptD).isZero();
    assertThat(clearD.get(C_CHAR, 0)).isEqualTo((byte) 3);

    // Clean up
    int rcDestroyA = fhe_uint32_destroy(aPtr.get(C_POINTER, 0));
    assertThat(rcDestroyA).isZero();
    int rcDestroyB = fhe_int64_destroy(bPtr.get(C_POINTER, 0));
    assertThat(rcDestroyB).isZero();
    int rcDestroyC = fhe_bool_destroy(cPtr.get(C_POINTER, 0));
    assertThat(rcDestroyC).isZero();
    int rcDestroyD = fhe_uint2_destroy(dPtr.get(C_POINTER, 0));
    assertThat(rcDestroyD).isZero();
    int rcDestroyCompactList = compact_ciphertext_list_destroy(compactListPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompactList).isZero();

    // Clean up the dedicated keys created for this test
    int rcDestroyClientKey = client_key_destroy(testClientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyServerKey = server_key_destroy(testServerKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
    int rcDestroyPublicKey = compact_public_key_destroy(testPublicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyPublicKey).isZero();
  }
}
