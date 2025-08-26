package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.core.configuration.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigBuilderTest {

  @Test
  void buildsWithDefaults() {
    Config config = new ConfigBuilder()
      .build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithCustomParameters() {
    Config config = new ConfigBuilder()
      .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithDedicatedCompactPublicKeyParameters() {
    Config config = new ConfigBuilder()
      .useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithCustomCompressionParameters() {
    Config config = new ConfigBuilder()
      .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithAllTweaks() {
    Config config = new ConfigBuilder()
      .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
      .build();
    assertThat(config).isNotNull();
  }

  @Test
  void clones() {
    ConfigBuilder configBuilder = new ConfigBuilder()
      .clone();
    assertThat(configBuilder).isNotNull();
  }
}
