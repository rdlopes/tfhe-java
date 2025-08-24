package io.github.rdlopes.tfhe.ffm.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("ffm")
class ConfigTest {

  private final MemorySegment configBuilderPtr = createPointer(C_POINTER);
  private final MemorySegment configPtr = createPointer(C_POINTER);

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
    MemorySegment clientKeyPtr = createPointer(C_POINTER);

    int rcClientKey = client_key_generate(configPtr.get(C_POINTER, 0), clientKeyPtr);
    assertThat(rcClientKey).isZero();
  }

  @Test
  void configCanGenerateAllKeys() {
    MemorySegment clientKeyPtr = createPointer(C_POINTER);
    MemorySegment serverKeyPtr = createPointer(C_POINTER);

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();
  }
}
