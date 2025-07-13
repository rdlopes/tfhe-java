package io.github.rdlopes.tfhe;

import io.github.rdlopes.tfhe.lib.U128;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.lib.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheNativeTestHelper {

  public static void usingU128Scheme(int lhsValue, int rhsValue, U128SchemeTester tester) {
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment configBuilder = arena.allocate(C_POINTER);
      assertThat(config_builder_default(configBuilder)).isZero();

      MemorySegment config = arena.allocate(C_POINTER);
      assertThat(config_builder_build(configBuilder.get(C_POINTER, 0), config)).isZero();

      MemorySegment clientKey = arena.allocate(C_POINTER);
      MemorySegment serverKey = arena.allocate(C_POINTER);
      assertThat(generate_keys(config.get(C_POINTER, 0), clientKey, serverKey)).isZero();
      assertThat(set_server_key(serverKey.get(C_POINTER, 0))).isZero();

      MemorySegment lhs = U128.allocate(arena);
      U128.w0(lhs, lhsValue);
      U128.w1(lhs, 0);
      MemorySegment rhs = U128.allocate(arena);
      U128.w0(rhs, rhsValue);
      U128.w1(rhs, 0);
      MemorySegment encrypted1 = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(lhs, clientKey.get(C_POINTER, 0), encrypted1)).isZero();
      MemorySegment encrypted = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(rhs, clientKey.get(C_POINTER, 0), encrypted)).isZero();

      tester.test(arena, clientKey, serverKey, encrypted1, encrypted);
    }
  }

  public static void usingBooleanScheme(boolean lhsValue, boolean rhsValue, BooleanSchemeTester tester) {
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment clientKey = arena.allocate(C_POINTER);
      MemorySegment serverKey = arena.allocate(C_POINTER);
      assertThat(boolean_gen_keys_with_default_parameters(clientKey, serverKey)).isZero();

      MemorySegment lhsEncrypted = arena.allocate(C_POINTER);
      assertThat(boolean_client_key_encrypt(clientKey.get(C_POINTER, 0), lhsValue, lhsEncrypted)).isZero();
      MemorySegment rhsEncrypted = arena.allocate(C_POINTER);
      assertThat(boolean_client_key_encrypt(clientKey.get(C_POINTER, 0), rhsValue, rhsEncrypted)).isZero();

      tester.test(arena, clientKey, serverKey, lhsEncrypted, rhsEncrypted);
    }
  }

  @FunctionalInterface
  public interface U128SchemeTester {
    void test(Arena arena, MemorySegment clientKey, MemorySegment serverKey, MemorySegment lhs, MemorySegment rhs);
  }

  @FunctionalInterface
  public interface BooleanSchemeTester {
    void test(Arena arena, MemorySegment clientKey, MemorySegment serverKey, MemorySegment lhs, MemorySegment rhs);
  }

}
