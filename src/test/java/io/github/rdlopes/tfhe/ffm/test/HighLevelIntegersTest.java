package io.github.rdlopes.tfhe.ffm.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("ffm")
class HighLevelIntegersTest {

  private final MemorySegment configBuilderPtr = createPointer(C_POINTER);
  private final MemorySegment configPtr = createPointer(C_POINTER);
  private final MemorySegment clientKeyPtr = createPointer(C_POINTER);
  private final MemorySegment serverKeyPtr = createPointer(C_POINTER);
  private final MemorySegment publicKeyPtr = createPointer(C_POINTER);

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
  void uint8ClientKeyTest() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 123;
    byte rhsClear = (byte) 14;

    int rcLhs = fhe_uint8_try_encrypt_with_client_key_u8(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint8_try_encrypt_with_client_key_u8(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    // Check addition
    {
      int rcAdd = fhe_uint8_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
      assertThat(rcAdd).isZero();

      MemorySegment clearResult = createPointer(C_CHAR);
      int rcDecrypt = fhe_uint8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      byte decryptedValue = clearResult.get(C_CHAR, 0);
      byte expectedValue = (byte) (lhsClear + rhsClear);
      assertThat(decryptedValue).isEqualTo(expectedValue);
    }

    // Check sum
    {
      MemorySegment sumResultPtr = createPointer(C_POINTER);
      MemorySegment dataArray = createPointer(C_POINTER, 2);
      dataArray.setAtIndex(C_POINTER, 0, lhsPtr.get(C_POINTER, 0));
      dataArray.setAtIndex(C_POINTER, 1, rhsPtr.get(C_POINTER, 0));

      int rcSum = fhe_uint8_sum(dataArray, 2, sumResultPtr);
      assertThat(rcSum).isZero();

      MemorySegment clearResult = createPointer(C_CHAR);
      int rcDecrypt = fhe_uint8_decrypt(sumResultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      byte decryptedValue = clearResult.get(C_CHAR, 0);
      byte expectedValue = (byte) (lhsClear + rhsClear);
      assertThat(decryptedValue).isEqualTo(expectedValue);

      int rcDestroySumResult = fhe_uint8_destroy(sumResultPtr.get(C_POINTER, 0));
      assertThat(rcDestroySumResult).isZero();
    }

    int rcDestroyLhs = fhe_uint8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void testUint8OverflowingAdd() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);
    MemorySegment overflowedPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 0xFF; // UINT8_MAX
    byte rhsClear = (byte) 1;

    int rcLhs = fhe_uint8_try_encrypt_with_client_key_u8(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint8_try_encrypt_with_client_key_u8(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcOverflowingAdd = fhe_uint8_overflowing_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0),
      resultPtr, overflowedPtr);
    assertThat(rcOverflowingAdd).isZero();

    MemorySegment clearResult = createPointer(C_CHAR);
    int rcDecrypt = fhe_uint8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    MemorySegment clearOverflowed = createPointer(C_BOOL);
    int rcDecryptOverflowed = fhe_bool_decrypt(overflowedPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearOverflowed);
    assertThat(rcDecryptOverflowed).isZero();

    assertThat(clearResult.get(C_CHAR, 0)).isEqualTo((byte) 0);
    assertThat(clearOverflowed.get(C_BOOL, 0)).isTrue();

    int rcDestroyLhs = fhe_uint8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyOverflowed = fhe_bool_destroy(overflowedPtr.get(C_POINTER, 0));
    assertThat(rcDestroyOverflowed).isZero();
  }

  @Test
  void testUint8OverflowingSub() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);
    MemorySegment overflowedPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 0;
    byte rhsClear = (byte) 1;

    int rcLhs = fhe_uint8_try_encrypt_with_client_key_u8(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint8_try_encrypt_with_client_key_u8(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcOverflowingSub = fhe_uint8_overflowing_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0),
      resultPtr, overflowedPtr);
    assertThat(rcOverflowingSub).isZero();

    MemorySegment clearResult = createPointer(C_CHAR);
    int rcDecrypt = fhe_uint8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    MemorySegment clearOverflowed = createPointer(C_BOOL);
    int rcDecryptOverflowed = fhe_bool_decrypt(overflowedPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearOverflowed);
    assertThat(rcDecryptOverflowed).isZero();

    byte expectedResult = (byte) (lhsClear - rhsClear); // Wraps around due to underflow
    assertThat(clearResult.get(C_CHAR, 0)).isEqualTo(expectedResult);
    assertThat(clearOverflowed.get(C_BOOL, 0)).isTrue();

    int rcDestroyLhs = fhe_uint8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyOverflowed = fhe_bool_destroy(overflowedPtr.get(C_POINTER, 0));
    assertThat(rcDestroyOverflowed).isZero();
  }

  @Test
  void testUint8OverflowingMul() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);
    MemorySegment overflowedPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 123;
    byte rhsClear = (byte) 3;

    int rcLhs = fhe_uint8_try_encrypt_with_client_key_u8(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint8_try_encrypt_with_client_key_u8(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcOverflowingMul = fhe_uint8_overflowing_mul(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0),
      resultPtr, overflowedPtr);
    assertThat(rcOverflowingMul).isZero();

    MemorySegment clearResult = createPointer(C_CHAR);
    int rcDecrypt = fhe_uint8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    MemorySegment clearOverflowed = createPointer(C_BOOL);
    int rcDecryptOverflowed = fhe_bool_decrypt(overflowedPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearOverflowed);
    assertThat(rcDecryptOverflowed).isZero();

    byte expectedResult = (byte) (lhsClear * rhsClear); // Wraps around due to overflow
    assertThat(clearResult.get(C_CHAR, 0)).isEqualTo(expectedResult);
    assertThat(clearOverflowed.get(C_BOOL, 0)).isTrue();

    int rcDestroyLhs = fhe_uint8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyOverflowed = fhe_bool_destroy(overflowedPtr.get(C_POINTER, 0));
    assertThat(rcDestroyOverflowed).isZero();
  }

  @Test
  void testInt8OverflowingAdd() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);
    MemorySegment overflowedPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 127; // INT8_MAX
    byte rhsClear = (byte) 1;

    int rcLhs = fhe_int8_try_encrypt_with_client_key_i8(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_int8_try_encrypt_with_client_key_i8(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcOverflowingAdd = fhe_int8_overflowing_add(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0),
      resultPtr, overflowedPtr);
    assertThat(rcOverflowingAdd).isZero();

    MemorySegment clearResult = createPointer(C_CHAR);
    int rcDecrypt = fhe_int8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    MemorySegment clearOverflowed = createPointer(C_BOOL);
    int rcDecryptOverflowed = fhe_bool_decrypt(overflowedPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearOverflowed);
    assertThat(rcDecryptOverflowed).isZero();

    // INT8_MAX + 1 = INT8_MIN in two's complement
    byte expectedResult = (byte) -128; // INT8_MIN
    assertThat(clearResult.get(C_CHAR, 0)).isEqualTo(expectedResult);
    assertThat(clearOverflowed.get(C_BOOL, 0)).isTrue();

    int rcDestroyLhs = fhe_int8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_int8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_int8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
    int rcDestroyOverflowed = fhe_bool_destroy(overflowedPtr.get(C_POINTER, 0));
    assertThat(rcDestroyOverflowed).isZero();
  }

  @Test
  void uint8PublicKeyTest() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    byte lhsClear = (byte) 123;
    byte rhsClear = (byte) 14;

    int rcLhs = fhe_uint8_try_encrypt_with_public_key_u8(lhsClear, publicKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_uint8_try_encrypt_with_public_key_u8(rhsClear, publicKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcSub = fhe_uint8_sub(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcSub).isZero();

    MemorySegment clearResult = createPointer(C_CHAR);
    int rcDecrypt = fhe_uint8_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    byte decryptedValue = clearResult.get(C_CHAR, 0);
    byte expectedValue = (byte) (lhsClear - rhsClear);
    assertThat(decryptedValue).isEqualTo(expectedValue);

    int rcDestroyLhs = fhe_uint8_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_uint8_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_uint8_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void testTryDecryptTrivial() {
    short clear = (short) (65535 - 2); // UINT16_MAX - 2

    MemorySegment trivialPtr = createPointer(C_POINTER);
    int rcTrivial = fhe_uint16_try_encrypt_trivial_u16(clear, trivialPtr);
    assertThat(rcTrivial).isZero();

    MemorySegment nonTrivialPtr = createPointer(C_POINTER);
    int rcNonTrivial = fhe_uint16_try_encrypt_with_client_key_u16(clear, clientKeyPtr.get(C_POINTER, 0), nonTrivialPtr);
    assertThat(rcNonTrivial).isZero();

    // Example of decrypting a trivial ciphertext
    MemorySegment decryptedResult = createPointer(C_SHORT);
    int rcDecryptTrivial = fhe_uint16_try_decrypt_trivial(trivialPtr.get(C_POINTER, 0), decryptedResult);
    assertThat(rcDecryptTrivial).isZero();
    assertThat(decryptedResult.get(C_SHORT, 0)).isEqualTo(clear);

    // Example of trying to trivial decrypt a ciphertext that is not trivial
    int rcDecryptNonTrivial = fhe_uint16_try_decrypt_trivial(nonTrivialPtr.get(C_POINTER, 0), decryptedResult);
    assertThat(rcDecryptNonTrivial).isEqualTo(1); // Returns error

    int rcDestroyTrivial = fhe_uint16_destroy(trivialPtr.get(C_POINTER, 0));
    assertThat(rcDestroyTrivial).isZero();
    int rcDestroyNonTrivial = fhe_uint16_destroy(nonTrivialPtr.get(C_POINTER, 0));
    assertThat(rcDestroyNonTrivial).isZero();
  }
}
