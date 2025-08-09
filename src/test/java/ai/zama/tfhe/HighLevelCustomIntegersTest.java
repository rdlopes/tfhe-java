package ai.zama.tfhe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static ai.zama.tfhe.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelCustomIntegersTest {
  private final MemorySegment configBuilderPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment configPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment clientKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment serverKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);

  @BeforeEach
  void setUp() {
    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();

    int rcSetServerKey = set_server_key(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();
  }

  @AfterEach
  void tearDown() {
    int rcDestroyClientKey = client_key_destroy(clientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyServerKey = server_key_destroy(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
  }

  @Test
  void uint256ClientKeyTest() {
    testUint256ClientKey(clientKeyPtr.get(C_POINTER, 0));
    testUint256EncryptTrivial(clientKeyPtr.get(C_POINTER, 0));
  }

  @Test
  void compactPublicKeyTest() {
    MemorySegment compressedPublicKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcCompressedPublicKey = compressed_compact_public_key_new(clientKeyPtr.get(C_POINTER, 0), compressedPublicKeyPtr);
    assertThat(rcCompressedPublicKey).isZero();

    testUint256CompactPublicKey(clientKeyPtr.get(C_POINTER, 0), compressedPublicKeyPtr.get(C_POINTER, 0));
    testInt32CompactPublicKey(clientKeyPtr.get(C_POINTER, 0), compressedPublicKeyPtr.get(C_POINTER, 0));

    // Clean up compressed public key
    int rcDestroyCompressedPublicKey = compressed_compact_public_key_destroy(compressedPublicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompressedPublicKey).isZero();
  }

  private void testUint256ClientKey(MemorySegment clientKey) {
    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment castResultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // Create U256 structures {1, 2, 3, 4} and {5, 6, 7, 8}
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32); // 4 * 8 bytes
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    int rcLhs = fhe_uint256_try_encrypt_with_client_key_u256(lhsClear, clientKey, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint256_try_encrypt_with_client_key_u256(rhsClear, clientKey, rhsPtr);
    assertThat(rcRhs).isZero();

    int rcAdd = fhe_uint256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKey, resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify result: {1+5, 2+6, 3+7, 4+8} = {6, 8, 10, 12}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(6L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(8L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(10L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(12L);

    // Test casting
    int rcCast = fhe_uint256_cast_into_fhe_uint64(resultPtr.get(C_POINTER, 0), castResultPtr);
    assertThat(rcCast).isZero();

    MemorySegment u64Clear = LIBRARY_ARENA.allocate(C_LONG_LONG);
    int rcDecryptCast = fhe_uint64_decrypt(castResultPtr.get(C_POINTER, 0), clientKey, u64Clear);
    assertThat(rcDecryptCast).isZero();
    assertThat(u64Clear.get(C_LONG_LONG, 0)).isEqualTo(6L);

    // Clean up
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyCast = fhe_uint64_destroy(castResultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCast).isZero();
  }

  private void testUint256EncryptTrivial(MemorySegment clientKey) {
    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // Create U256 structures {1, 2, 3, 4} and {5, 6, 7, 8}
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    int rcLhs = fhe_uint256_try_encrypt_trivial_u256(lhsClear, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint256_try_encrypt_trivial_u256(rhsClear, rhsPtr);
    assertThat(rcRhs).isZero();

    int rcAdd = fhe_uint256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKey, resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify result: {1+5, 2+6, 3+7, 4+8} = {6, 8, 10, 12}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(6L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(8L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(10L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(12L);

    // Clean up
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  private void testUint256CompactPublicKey(MemorySegment clientKey, MemorySegment compressedPublicKey) {
    MemorySegment publicKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment compactListPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcDecompress = compressed_compact_public_key_decompress(compressedPublicKey, publicKeyPtr);
    assertThat(rcDecompress).isZero();

    // Create compact list builder
    MemorySegment builderPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment expanderPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcBuilderNew = compact_ciphertext_list_builder_new(publicKeyPtr.get(C_POINTER, 0), builderPtr);
    assertThat(rcBuilderNew).isZero();

    // Create U256 values {5, 6, 7, 8} and {1, 2, 3, 4}
    MemorySegment clear0 = LIBRARY_ARENA.allocate(32);
    clear0.setAtIndex(C_LONG_LONG, 0, 5L);
    clear0.setAtIndex(C_LONG_LONG, 1, 6L);
    clear0.setAtIndex(C_LONG_LONG, 2, 7L);
    clear0.setAtIndex(C_LONG_LONG, 3, 8L);

    MemorySegment clear1 = LIBRARY_ARENA.allocate(32);
    clear1.setAtIndex(C_LONG_LONG, 0, 1L);
    clear1.setAtIndex(C_LONG_LONG, 1, 2L);
    clear1.setAtIndex(C_LONG_LONG, 2, 3L);
    clear1.setAtIndex(C_LONG_LONG, 3, 4L);

    int rcPush0 = compact_ciphertext_list_builder_push_u256(builderPtr.get(C_POINTER, 0), clear0);
    assertThat(rcPush0).isZero();

    int rcPush1 = compact_ciphertext_list_builder_push_u256(builderPtr.get(C_POINTER, 0), clear1);
    assertThat(rcPush1).isZero();

    int rcBuild = compact_ciphertext_list_builder_build(builderPtr.get(C_POINTER, 0), compactListPtr);
    assertThat(rcBuild).isZero();

    int rcExpand = compact_ciphertext_list_expand(compactListPtr.get(C_POINTER, 0), expanderPtr);
    assertThat(rcExpand).isZero();

    MemorySegment lenPtr = LIBRARY_ARENA.allocate(C_LONG);
    int rcLen = compact_ciphertext_list_expander_len(expanderPtr.get(C_POINTER, 0), lenPtr);
    assertThat(rcLen).isZero();
    assertThat(lenPtr.get(C_LONG, 0)).isEqualTo(2L);

    int rcGet0 = compact_ciphertext_list_expander_get_fhe_uint256(expanderPtr.get(C_POINTER, 0), 0, lhsPtr);
    assertThat(rcGet0).isZero();

    int rcGet1 = compact_ciphertext_list_expander_get_fhe_uint256(expanderPtr.get(C_POINTER, 0), 1, rhsPtr);
    assertThat(rcGet1).isZero();

    int rcSub = fhe_uint256_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKey, resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify result: {5-1, 6-2, 7-3, 8-4} = {4, 4, 4, 4}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(4L);

    // Clean up
    int rcDestroyExpander = compact_ciphertext_list_expander_destroy(expanderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyExpander).isZero();
    int rcDestroyBuilder = compact_ciphertext_list_builder_destroy(builderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyBuilder).isZero();
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyPublicKey = compact_public_key_destroy(publicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyPublicKey).isZero();
    int rcDestroyCompactList = compact_ciphertext_list_destroy(compactListPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompactList).isZero();
  }

  private void testInt32CompactPublicKey(MemorySegment clientKey, MemorySegment compressedPublicKey) {
    MemorySegment publicKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment compactListPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcDecompress = compressed_compact_public_key_decompress(compressedPublicKey, publicKeyPtr);
    assertThat(rcDecompress).isZero();

    // Create compact list builder
    MemorySegment builderPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment expanderPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcBuilderNew = compact_ciphertext_list_builder_new(publicKeyPtr.get(C_POINTER, 0), builderPtr);
    assertThat(rcBuilderNew).isZero();

    int clear0 = -9482394;
    int clear1 = 98712234;

    int rcPush0 = compact_ciphertext_list_builder_push_i32(builderPtr.get(C_POINTER, 0), clear0);
    assertThat(rcPush0).isZero();

    int rcPush1 = compact_ciphertext_list_builder_push_i32(builderPtr.get(C_POINTER, 0), clear1);
    assertThat(rcPush1).isZero();

    int rcBuild = compact_ciphertext_list_builder_build(builderPtr.get(C_POINTER, 0), compactListPtr);
    assertThat(rcBuild).isZero();

    int rcExpand = compact_ciphertext_list_expand(compactListPtr.get(C_POINTER, 0), expanderPtr);
    assertThat(rcExpand).isZero();

    MemorySegment lenPtr = LIBRARY_ARENA.allocate(C_LONG);
    int rcLen = compact_ciphertext_list_expander_len(expanderPtr.get(C_POINTER, 0), lenPtr);
    assertThat(rcLen).isZero();
    assertThat(lenPtr.get(C_LONG, 0)).isEqualTo(2L);

    int rcGet0 = compact_ciphertext_list_expander_get_fhe_int32(expanderPtr.get(C_POINTER, 0), 0, lhsPtr);
    assertThat(rcGet0).isZero();

    int rcGet1 = compact_ciphertext_list_expander_get_fhe_int32(expanderPtr.get(C_POINTER, 0), 1, rhsPtr);
    assertThat(rcGet1).isZero();

    int rcAdd = fhe_int32_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    MemorySegment resultClear = LIBRARY_ARENA.allocate(C_INT);
    int rcDecrypt = fhe_int32_decrypt(resultPtr.get(C_POINTER, 0), clientKey, resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify result: -9482394 + 98712234
    assertThat(resultClear.get(C_INT, 0)).isEqualTo(clear0 + clear1);

    // Clean up
    int rcDestroyExpander = compact_ciphertext_list_expander_destroy(expanderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyExpander).isZero();
    int rcDestroyBuilder = compact_ciphertext_list_builder_destroy(builderPtr.get(C_POINTER, 0));
    assertThat(rcDestroyBuilder).isZero();
    int rcDestroyLhs = fhe_int32_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_int32_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_int32_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyPublicKey = compact_public_key_destroy(publicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyPublicKey).isZero();
    int rcDestroyCompactList = compact_ciphertext_list_destroy(compactListPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCompactList).isZero();
  }
}
