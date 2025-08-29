package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.core.configuration.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigBuilderTest {

  private ConfigBuilder configBuilder;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
  }

  @AfterEach
  void tearDown() {
    // crashes the JVM
    //configBuilder.destroy();
  }

  @Test
  void buildsWithDefaults() {
    Config config = configBuilder.build();
    assertThat(config).isNotNull();

    config.destroy();
  }

  @Test
  void buildsWithCustomParameters() {
    Config config = configBuilder
      .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();

    config.destroy();
  }

  @Test
  void buildsWithDedicatedCompactPublicKeyParameters() {
    Config config = configBuilder
      .useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();

    config.destroy();
  }

  @Test
  void buildsWithCustomCompressionParameters() {
    Config config = configBuilder
      .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();

    config.destroy();
  }

  @Test
  void buildsWithAllTweaks() {
    Config config = configBuilder
      .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();

    config.destroy();
  }

  @Test
  void clones() {
    ConfigBuilder clone = configBuilder.clone();

    assertThat(clone).isNotNull();
    clone.destroy();
  }
}
