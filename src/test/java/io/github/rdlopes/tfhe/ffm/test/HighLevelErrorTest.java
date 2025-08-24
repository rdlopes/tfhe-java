package io.github.rdlopes.tfhe.ffm.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelErrorTest {

  private final MemorySegment configBuilderPtr = createPointer(C_POINTER);
  private final MemorySegment configPtr = createPointer(C_POINTER);
  private final MemorySegment clientKeyPtr = createPointer(C_POINTER);
  private final MemorySegment serverKeyPtr = createPointer(C_POINTER);

  @BeforeEach
  void setUp() {
    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();

    tfhe_error_clear();
  }

  @AfterEach
  void tearDown() {
    // Ensure server key is unset to prevent state leakage between tests
    unset_server_key();

    int rcDestroyClientKey = client_key_destroy(clientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyServerKey = server_key_destroy(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
  }

  @Test
  void errorHandlingTest() {
    // Disable automatic error prints to test error handling
    tfhe_error_disable_automatic_prints();

    // Note: We intentionally DON'T set the server key to test error handling
    // The setUp() method generates keys but doesn't call set_server_key() for this test

    // Create test values
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    // Create U128 structures {10, 20} and {1, 2}
    MemorySegment clearLhs = createPointer(16); // 2 * 8 bytes
    clearLhs.set(C_LONG, 0, 10L);
    clearLhs.set(C_LONG, 8, 20L);

    MemorySegment clearRhs = createPointer(16);
    clearRhs.set(C_LONG, 0, 1L);
    clearRhs.set(C_LONG, 8, 2L);

    int rcLhs = fhe_uint128_try_encrypt_with_client_key_u128(clearLhs, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint128_try_encrypt_with_client_key_u128(clearRhs, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Check that there's no error initially
    MemorySegment lastErrorPtr = tfhe_error_get_last();
    if (lastErrorPtr != MemorySegment.NULL) {
      String lastError = lastErrorPtr.getString(0);
      assertThat(lastError).isEqualTo("no error");
    }

    // Attempt subtraction without server key - this should fail
    int rcSub = fhe_uint128_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isEqualTo(1); // Should return error code 1

    // Check the error message
    MemorySegment errorPtr = tfhe_error_get_last();
    assertThat(errorPtr).isNotEqualTo(MemorySegment.NULL);

    String errorMessage = errorPtr.getString(0);
    assertThat(errorMessage).isNotNull();
    assertThat(errorMessage).isNotEmpty();
    assertThat(errorMessage).doesNotContain("no error");

    // Log error message for debugging (following C test pattern)
    System.out.println("Error message received from tfhe-rs: '" + errorMessage + "'");

    // Clean up the ciphertexts
    int rcDestroyLhs = fhe_uint128_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint128_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
  }

  @Test
  void errorHandlingWithServerKeySet() {
    // Test that operations work correctly when server key is properly set
    tfhe_error_disable_automatic_prints();

    // Properly set the server key for this test
    int rcSetServerKey = set_server_key(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();

    // Create test values
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    // Create U128 structures {10, 20} and {1, 2}
    MemorySegment clearLhs = createPointer(16);
    clearLhs.set(C_LONG, 0, 10L);
    clearLhs.set(C_LONG, 8, 20L);

    MemorySegment clearRhs = createPointer(16);
    clearRhs.set(C_LONG, 0, 1L);
    clearRhs.set(C_LONG, 8, 2L);

    int rcLhs = fhe_uint128_try_encrypt_with_client_key_u128(clearLhs, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint128_try_encrypt_with_client_key_u128(clearRhs, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Subtraction should work now
    int rcSub = fhe_uint128_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    // Verify the result
    MemorySegment resultClear = createPointer(16);
    int rcDecrypt = fhe_uint128_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Result should be {10-1, 20-2} = {9, 18}
    assertThat(resultClear.get(C_LONG, 0)).isEqualTo(9L);
    assertThat(resultClear.get(C_LONG, 8)).isEqualTo(18L);

    // Clean up
    int rcDestroyLhs = fhe_uint128_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint128_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint128_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }
}
