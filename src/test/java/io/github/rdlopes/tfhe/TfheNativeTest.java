package io.github.rdlopes.tfhe;

import io.github.rdlopes.tfhe.lib.U128;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.TfheNativeTestHelper.usingBooleanScheme;
import static io.github.rdlopes.tfhe.TfheNativeTestHelper.usingU128Scheme;
import static io.github.rdlopes.tfhe.lib.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheNativeTest {

  @Test
  void addsTwoEncryptedIntegers() {
    usingU128Scheme(42, 57,
      (arena, clientKey, _, lhsEncrypted, rhsEncrypted) -> {

        MemorySegment resultEncrypted = U128.allocate(arena);
        assertThat(fhe_uint128_add(lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

        MemorySegment result = U128.allocate(arena);
        assertThat(fhe_uint128_decrypt(resultEncrypted.get(C_POINTER, 0), clientKey.get(C_POINTER, 0), result)).isZero();
        assertThat(U128.w0(result)).isEqualTo(99);
        assertThat(U128.w1(result)).isEqualTo(0);
      });
  }

  @Test
  void xorTwoEncryptedBooleans() {
    usingBooleanScheme(true, true,
      (arena, clientKey, serverKey, lhsEncrypted, rhsEncrypted) -> {

        MemorySegment resultEncrypted = arena.allocate(C_POINTER);
        assertThat(boolean_server_key_xor(serverKey.get(C_POINTER, 0), lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

        MemorySegment result = arena.allocate(C_BOOL);
        assertThat(boolean_client_key_decrypt(clientKey.get(C_POINTER, 0), resultEncrypted.get(C_POINTER, 0), result)).isZero();

        boolean resultValue = result.get(C_BOOL, 0);
        assertThat(resultValue).isFalse();
      });
  }
}
