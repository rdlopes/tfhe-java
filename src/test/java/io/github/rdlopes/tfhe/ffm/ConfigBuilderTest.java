package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.*;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class ConfigBuilderTest {

  private MemorySegment configBuilderPtr;
  private MemorySegment configPtr;

  @BeforeAll
  static void beforeAll() {
    TfheWrapper.loadNativeLibrary();
  }

  @BeforeEach
  void setUp() {
    configBuilderPtr = TfheWrapper.createPointer(C_POINTER);
    configPtr = TfheWrapper.createPointer(C_POINTER);

    int rcDefault = config_builder_default(configBuilderPtr);
    assertThat(rcDefault).isZero();
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
  void buildsConfigWithDefaultParameters() {
    int rcBuild = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcBuild).isZero();
  }

  @Test
  void buildsConfigWithCustomParameters() {
    int rcCustom = config_builder_use_custom_parameters(configBuilderPtr, SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
    assertThat(rcCustom).isZero();

    int rcBuild = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcBuild).isZero();
  }

  @Test
  void buildsConfigWithDedicatedCompactPublicKeyParameters() {
    int rcDedicated = use_dedicated_compact_public_key_parameters(configBuilderPtr, SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
    assertThat(rcDedicated).isZero();

    int rcBuild = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcBuild).isZero();
  }

}
