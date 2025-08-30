package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.core.configuration.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigBuilderTest {

  @Test
  void buildsWithDefaults() {
    assertThat(new ConfigBuilder().build()).isNotNull();
  }

  @Test
  void buildsWithCustomParameters() {
    ConfigBuilder builder = new ConfigBuilder();
    builder.useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    assertThat(builder.build()).isNotNull();
  }

  @Test
  void buildsWithDedicatedCompactPublicKeyParameters() {
    ConfigBuilder builder = new ConfigBuilder();
    builder.useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    assertThat(builder.build()).isNotNull();
  }

  @Test
  void buildsWithCustomCompressionParameters() {
    ConfigBuilder builder = new ConfigBuilder();
    builder.enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    assertThat(builder.build()).isNotNull();
  }

  @Test
  void buildsWithAllTweaks() {
    ConfigBuilder builder = new ConfigBuilder();
    builder.useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    builder.enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    builder.useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    assertThat(builder.build()).isNotNull();
  }

  @Test
  void clones() {
    assertThat(new ConfigBuilder().clone()).isNotNull();
  }
}
