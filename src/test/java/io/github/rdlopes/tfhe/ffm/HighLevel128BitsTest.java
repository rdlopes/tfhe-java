package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.*;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevel128BitsTest {

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
    // FIXME Causes crashes when running all tests
    // int rcDestroyBuilder = config_builder_destroy(configBuilderPtr.get(C_POINTER, 0));
    // assertThat(rcDestroyBuilder).isZero();
    // int rcDestroyConfig = config_destroy(configPtr.get(C_POINTER, 0));
    // assertThat(rcDestroyConfig).isZero();
  }

  @Test
  void uint128ClientKeyTest() {
    MemorySegment lhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment rhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

    // Create U128 values: lhs = {10, 20}, rhs = {1, 2}
    MemorySegment lhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(lhsClear, 10L);
    U128.w1(lhsClear, 20L);

    MemorySegment rhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(rhsClear, 1L);
    U128.w1(rhsClear, 2L);

    int rcLhs = fhe_uint128_try_encrypt_with_client_key_u128(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint128_try_encrypt_with_client_key_u128(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcSub = fhe_uint128_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    MemorySegment resultClear = U128.allocate(LIBRARY_ARENA);
    int rcDecrypt = fhe_uint128_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Expected: {10, 20} - {1, 2} = {9, 18}
    assertThat(U128.w0(resultClear)).isEqualTo(9L);
    assertThat(U128.w1(resultClear)).isEqualTo(18L);

    int rcDestroyLhs = fhe_uint128_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint128_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint128_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void uint128EncryptTrivialTest() {
    MemorySegment lhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment rhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

    // Create U128 values: lhs = {10, 20}, rhs = {1, 2}
    MemorySegment lhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(lhsClear, 10L);
    U128.w1(lhsClear, 20L);

    MemorySegment rhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(rhsClear, 1L);
    U128.w1(rhsClear, 2L);

    int rcLhs = fhe_uint128_try_encrypt_trivial_u128(lhsClear, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint128_try_encrypt_trivial_u128(rhsClear, rhsPtr);
    assertThat(rcRhs).isZero();

    int rcSub = fhe_uint128_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    MemorySegment resultClear = U128.allocate(LIBRARY_ARENA);
    int rcDecrypt = fhe_uint128_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Expected: {10, 20} - {1, 2} = {9, 18}
    assertThat(U128.w0(resultClear)).isEqualTo(9L);
    assertThat(U128.w1(resultClear)).isEqualTo(18L);

    int rcDestroyLhs = fhe_uint128_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint128_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint128_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void uint128PublicKeyTest() {
    MemorySegment lhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment rhsPtr = TfheWrapper.createPointer(C_POINTER);
    MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

    // Create U128 values: lhs = {10, 20}, rhs = {1, 2}
    MemorySegment lhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(lhsClear, 10L);
    U128.w1(lhsClear, 20L);

    MemorySegment rhsClear = U128.allocate(LIBRARY_ARENA);
    U128.w0(rhsClear, 1L);
    U128.w1(rhsClear, 2L);

    int rcLhs = fhe_uint128_try_encrypt_with_public_key_u128(lhsClear, publicKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint128_try_encrypt_with_public_key_u128(rhsClear, publicKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Note: C test uses addition for public key test
    int rcAdd = fhe_uint128_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcAdd).isZero();

    MemorySegment resultClear = U128.allocate(LIBRARY_ARENA);
    int rcDecrypt = fhe_uint128_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), resultClear);
    assertThat(rcDecrypt).isZero();

    // Expected: {10, 20} + {1, 2} = {11, 22}
    assertThat(U128.w0(resultClear)).isEqualTo(11L);
    assertThat(U128.w1(resultClear)).isEqualTo(22L);

    int rcDestroyLhs = fhe_uint128_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint128_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint128_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }
}
