package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.github.rdlopes.tfhe.core.configuration.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigBuilderTest {

  private ConfigBuilder configBuilder;
  private Config config;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
  }

  @AfterEach
  void tearDown() {
    configBuilder.cleanNativeResources();
    Optional.ofNullable(config)
            .ifPresent(Config::cleanNativeResources);
  }

  @Test
  void buildsWithDefaults() {
    config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithCustomParameters() {
    configBuilder.useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithDedicatedCompactPublicKeyParameters() {
    configBuilder.useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithCustomCompressionParameters() {
    configBuilder.enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void buildsWithAllTweaks() {
    configBuilder.useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    configBuilder.enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    configBuilder.useDedicatedCompactPublicKeyParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void clones() {
    ConfigBuilder clone = configBuilder.clone();
    assertThat(clone).isNotNull();
    clone.cleanNativeResources();
  }
}
