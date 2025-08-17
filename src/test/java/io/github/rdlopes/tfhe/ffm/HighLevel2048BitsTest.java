package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.*;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevel2048BitsTest {

  private final MemorySegment configBuilderPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment configPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment clientKeyPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment serverKeyPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment publicKeyPtr = TfheWrapper.createPointer(C_POINTER);

  @BeforeAll
  static void beforeAll() {
    TfheWrapper.loadNativeLibrary();
  }

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
  void uint2048ClientKeyTest() {

    MemorySegment lhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment rhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

    // U2048 lhs_clear with 32 words (32 * 8 bytes = 256 bytes for U2048)
    MemorySegment lhsClear = TfheWrapper.createPointer(256);
    // U2048 rhs_clear with 32 words
    MemorySegment rhsClear = TfheWrapper.createPointer(256);

    // Fill lhs_clear with incremental values: words[i] = i
    for (int i = 0; i < 32; i++) {
      lhsClear.setAtIndex(C_LONG_LONG, i, i);
    }

    // Fill rhs_clear with decremental values: words[i] = UINT64_MAX - i
    for (int i = 0; i < 32; i++) {
      long value = Long.parseUnsignedLong("18446744073709551615") - i; // UINT64_MAX - i
      rhsClear.setAtIndex(C_LONG_LONG, i, value);
    }

    // Encrypt lhs with client key
    int rcLhs = fhe_uint2048_try_encrypt_with_client_key_u2048(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    // Encrypt rhs with client key
    int rcRhs = fhe_uint2048_try_encrypt_with_client_key_u2048(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Perform equality comparison: lhs == rhs = result (should be false)
    int rcEq = fhe_uint2048_eq(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcEq).isZero();

    // Decrypt result
    MemorySegment resultClear = TfheWrapper.createPointer(C_BOOL);
    int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Verify equality result is false (since the two U2048 values are different)
    boolean equalityResult = resultClear.get(C_BOOL, 0);
    assertThat(equalityResult).isFalse();

    // Cleanup
    int rcDestroyLhs = fhe_uint2048_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint2048_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }
}
