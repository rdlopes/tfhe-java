package ai.zama.tfhe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static ai.zama.tfhe.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevel256BitsTest {

  private final MemorySegment configBuilderPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment configPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment clientKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment serverKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
  private final MemorySegment publicKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);

  @BeforeEach
  void setUp() {
    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();

    int rcPublicKey = public_key_new(clientKeyPtr.get(C_POINTER, 0), publicKeyPtr);
    assertThat(rcPublicKey).isZero();

    int rcSetServerKey = set_server_key(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();
  }

  @AfterEach
  void tearDown() {
    int rcDestroyClientKey = client_key_destroy(clientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyPublicKey = public_key_destroy(publicKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyPublicKey).isZero();
    int rcDestroyServerKey = server_key_destroy(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
  }

  @Test
  void uint256ClientKeyTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment castResultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // U256 lhs_clear = {1, 2, 3, 4};
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32); // 4 * 8 bytes = 32 bytes for U256
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    // U256 rhs_clear = {5, 6, 7, 8};
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    // Encrypt lhs with client key
    int rcLhs = fhe_uint256_try_encrypt_with_client_key_u256(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    // Encrypt rhs with client key
    int rcRhs = fhe_uint256_try_encrypt_with_client_key_u256(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform addition: lhs + rhs = result
    int rcAdd = fhe_uint256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    // Decrypt result
    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify addition: {1,2,3,4} + {5,6,7,8} = {6,8,10,12}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(6L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(8L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(10L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(12L);

    // Test casting to uint64
    int rcCast = fhe_uint256_cast_into_fhe_uint64(resultPtr.get(C_POINTER, 0), castResultPtr);
    assertThat(rcCast).isZero();

    MemorySegment u64Clear = LIBRARY_ARENA.allocate(C_LONG_LONG);
    int rcDecryptCast = fhe_uint64_decrypt(castResultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), u64Clear);
    assertThat(rcDecryptCast).isZero();
    assertThat(u64Clear.get(C_LONG_LONG, 0)).isEqualTo(6L);

    // Cleanup
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyCast = fhe_uint64_destroy(castResultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCast).isZero();
  }

  @Test
  void uint256EncryptTrivialTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // U256 lhs_clear = {1, 2, 3, 4};
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    // U256 rhs_clear = {5, 6, 7, 8};
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    // Encrypt trivial
    int rcLhs = fhe_uint256_try_encrypt_trivial_u256(lhsClear, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint256_try_encrypt_trivial_u256(rhsClear, rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform addition
    int rcAdd = fhe_uint256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    // Decrypt result
    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify addition: {1,2,3,4} + {5,6,7,8} = {6,8,10,12}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(6L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(8L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(10L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(12L);

    // Cleanup
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void uint256PublicKeyTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // U256 lhs_clear = {5, 6, 7, 8};
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    // U256 rhs_clear = {1, 2, 3, 4};
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    // Encrypt with public key
    int rcLhs = fhe_uint256_try_encrypt_with_public_key_u256(lhsClear, publicKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint256_try_encrypt_with_public_key_u256(rhsClear, publicKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform subtraction: lhs - rhs = result
    int rcSub = fhe_uint256_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    // Decrypt result
    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_uint256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify subtraction: {5,6,7,8} - {1,2,3,4} = {4,4,4,4}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(4L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(4L);

    // Cleanup
    int rcDestroyLhs = fhe_uint256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void int256ClientKeyTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment castResultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // I256 lhs_clear = {1, 0, 0, 0}; // This is +1
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 0L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 0L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 0L);

    // I256 rhs_clear = {UINT64_MAX, UINT64_MAX, UINT64_MAX, UINT64_MAX}; // This is -1
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, -1L); // UINT64_MAX as signed is -1
    rhsClear.setAtIndex(C_LONG_LONG, 1, -1L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, -1L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, -1L);

    // Encrypt with client key
    int rcLhs = fhe_int256_try_encrypt_with_client_key_i256(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_int256_try_encrypt_with_client_key_i256(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform addition: 1 + (-1) = 0
    int rcAdd = fhe_int256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_int256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // We did 1 + (-1), so we expect 0
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(0L);

    int rcDestroyResult = fhe_int256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();

    // Perform subtraction: 1 - (-1) = 2
    int rcSub = fhe_int256_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    rcDecrypt = fhe_int256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // We did 1 - (-1), so we expect 2
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(2L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(0L);

    // Test casting to int64
    int rcCast = fhe_int256_cast_into_fhe_int64(resultPtr.get(C_POINTER, 0), castResultPtr);
    assertThat(rcCast).isZero();

    MemorySegment i64Clear = LIBRARY_ARENA.allocate(C_LONG_LONG);
    int rcDecryptCast = fhe_int64_decrypt(castResultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), i64Clear);
    assertThat(rcDecryptCast).isZero();
    assertThat(i64Clear.get(C_LONG_LONG, 0)).isEqualTo(2L);

    // Cleanup
    int rcDestroyLhs = fhe_int256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_int256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    rcDestroyResult = fhe_int256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyCast = fhe_int64_destroy(castResultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyCast).isZero();
  }

  @Test
  void int256EncryptTrivialTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // I256 lhs_clear = {1, 2, 3, 4};
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 2L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 3L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 4L);

    // I256 rhs_clear = {5, 6, 7, 8};
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, 5L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, 6L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, 7L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, 8L);

    // Encrypt trivial
    int rcLhs = fhe_int256_try_encrypt_trivial_i256(lhsClear, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_int256_try_encrypt_trivial_i256(rhsClear, rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform addition
    int rcAdd = fhe_int256_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    // Decrypt result
    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_int256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify addition: {1,2,3,4} + {5,6,7,8} = {6,8,10,12}
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(6L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(8L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(10L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(12L);

    // Cleanup
    int rcDestroyLhs = fhe_int256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_int256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_int256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void int256PublicKeyTest() {

    MemorySegment lhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment rhsPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment resultPtr = LIBRARY_ARENA.allocate(C_POINTER);

    // I256 lhs_clear = {1, 0, 0, 0}; // This is +1
    MemorySegment lhsClear = LIBRARY_ARENA.allocate(32);
    lhsClear.setAtIndex(C_LONG_LONG, 0, 1L);
    lhsClear.setAtIndex(C_LONG_LONG, 1, 0L);
    lhsClear.setAtIndex(C_LONG_LONG, 2, 0L);
    lhsClear.setAtIndex(C_LONG_LONG, 3, 0L);

    // I256 rhs_clear = {UINT64_MAX, UINT64_MAX, UINT64_MAX, UINT64_MAX}; // This is -1
    MemorySegment rhsClear = LIBRARY_ARENA.allocate(32);
    rhsClear.setAtIndex(C_LONG_LONG, 0, -1L);
    rhsClear.setAtIndex(C_LONG_LONG, 1, -1L);
    rhsClear.setAtIndex(C_LONG_LONG, 2, -1L);
    rhsClear.setAtIndex(C_LONG_LONG, 3, -1L);

    // Encrypt with public key
    int rcLhs = fhe_int256_try_encrypt_with_public_key_i256(lhsClear, publicKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_int256_try_encrypt_with_public_key_i256(rhsClear, publicKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform subtraction: 1 - (-1) = 2
    int rcSub = fhe_int256_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    // Decrypt result
    MemorySegment resultClear = LIBRARY_ARENA.allocate(32);
    int rcDecrypt = fhe_int256_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // We did 1 - (-1), so we expect 2
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 0)).isEqualTo(2L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 1)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 2)).isEqualTo(0L);
    assertThat(resultClear.getAtIndex(C_LONG_LONG, 3)).isEqualTo(0L);

    // Cleanup
    int rcDestroyLhs = fhe_int256_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_int256_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_int256_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }
}
