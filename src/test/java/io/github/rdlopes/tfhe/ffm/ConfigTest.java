package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.*;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class ConfigTest {

  private final MemorySegment configBuilderPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment configPtr = TfheWrapper.createPointer(C_POINTER);

  @BeforeAll
  static void beforeAll() {
    TfheWrapper.loadNativeLibrary();
  }

  @BeforeEach
  void setUp() {
    int rcDefault = config_builder_default(configBuilderPtr);
    assertThat(rcDefault).isZero();

    int rcBuild = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcBuild).isZero();
  }

  @AfterEach
  void tearDown() {
    // FIXME Causes crashes when running all tests
    // int rcDestroyBuilder = config_builder_destroy(configBuilderPtr.get(C_POINTER, 0));
    // assertThat(rcDestroyBuilder).isZero();
    // int rcDestroyConfig = config_destroy(configPtr.get(C_POINTER, 0));
    // assertThat(rcDestroyConfig).isZero();
  }

  @Test
  void configCanGenerateClientKey() {
    MemorySegment clientKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcClientKey = client_key_generate(configPtr.get(C_POINTER, 0), clientKeyPtr);
    assertThat(rcClientKey).isZero();
  }

  @Test
  void configCanGenerateAllKeys() {
    MemorySegment clientKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);
    MemorySegment serverKeyPtr = LIBRARY_ARENA.allocate(C_POINTER);

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();
  }
}
