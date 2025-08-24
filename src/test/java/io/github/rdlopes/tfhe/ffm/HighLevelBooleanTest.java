package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelBooleanTest {

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
  void clientKeyTest() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    boolean lhsClear = false;
    boolean rhsClear = true;

    int rcLhs = fhe_bool_try_encrypt_with_client_key_bool(lhsClear, clientKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_bool_try_encrypt_with_client_key_bool(rhsClear, clientKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcBitand = fhe_bool_bitand(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcBitand).isZero();

    MemorySegment clearResult = createPointer(C_BOOL);
    int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    boolean decryptedValue = clearResult.get(C_BOOL, 0);
    assertThat(decryptedValue).isEqualTo(false);

    int rcDestroyLhs = fhe_bool_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_bool_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void publicKeyTest() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    boolean lhsClear = false;
    boolean rhsClear = true;

    int rcLhs = fhe_bool_try_encrypt_with_public_key_bool(lhsClear, publicKeyPtr.get(C_POINTER, 0), lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_bool_try_encrypt_with_public_key_bool(rhsClear, publicKeyPtr.get(C_POINTER, 0), rhsPtr);
    assertThat(rcRhs).isZero();

    int rcBitand = fhe_bool_bitand(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcBitand).isZero();

    MemorySegment clearResult = createPointer(C_BOOL);
    int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    boolean decryptedValue = clearResult.get(C_BOOL, 0);
    assertThat(decryptedValue).isEqualTo(false);

    int rcDestroyLhs = fhe_bool_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_bool_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }

  @Test
  void trivialEncryptTest() {
    MemorySegment lhsPtr = createPointer(C_POINTER);
    MemorySegment rhsPtr = createPointer(C_POINTER);
    MemorySegment resultPtr = createPointer(C_POINTER);

    boolean lhsClear = false;
    boolean rhsClear = true;

    int rcLhs = fhe_bool_try_encrypt_trivial_bool(lhsClear, lhsPtr);
    assertThat(rcLhs).isZero();

    int rcRhs = fhe_bool_try_encrypt_trivial_bool(rhsClear, rhsPtr);
    assertThat(rcRhs).isZero();

    int rcBitand = fhe_bool_bitand(lhsPtr.get(C_POINTER, 0), rhsPtr.get(C_POINTER, 0), resultPtr);
    assertThat(rcBitand).isZero();

    MemorySegment clearResult = createPointer(C_BOOL);
    int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
    assertThat(rcDecrypt).isZero();

    boolean decryptedValue = clearResult.get(C_BOOL, 0);
    assertThat(decryptedValue).isEqualTo(false);

    int rcDestroyLhs = fhe_bool_destroy(lhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyLhs).isZero();
    int rcDestroyRhs = fhe_bool_destroy(rhsPtr.get(C_POINTER, 0));
    assertThat(rcDestroyRhs).isZero();
    int rcDestroyResult = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
    assertThat(rcDestroyResult).isZero();
  }
}
