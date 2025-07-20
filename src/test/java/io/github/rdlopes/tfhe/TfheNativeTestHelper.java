package io.github.rdlopes.tfhe;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static ai.zama.tfhe.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheNativeTestHelper {

  public static void doWithKeys(Tester tester) {
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment configBuilder = arena.allocate(C_POINTER);
      assertThat(config_builder_default(configBuilder)).isZero();

      MemorySegment config = arena.allocate(C_POINTER);
      assertThat(config_builder_build(configBuilder.get(C_POINTER, 0), config)).isZero();

      MemorySegment clientKey = arena.allocate(C_POINTER);
      MemorySegment serverKey = arena.allocate(C_POINTER);
      assertThat(generate_keys(config.get(C_POINTER, 0), clientKey, serverKey)).isZero();
      assertThat(set_server_key(serverKey.get(C_POINTER, 0))).isZero();

      try {
        tester.test(arena, clientKey, serverKey);
      } finally {
        assertThat(server_key_destroy(serverKey.get(C_POINTER, 0))).isZero();
        assertThat(client_key_destroy(clientKey.get(C_POINTER, 0))).isZero();
      }
    }
  }

  @FunctionalInterface
  public interface Tester {
    void test(Arena arena, MemorySegment clientKey, MemorySegment serverKey);
  }

}
