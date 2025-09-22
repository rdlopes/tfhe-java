package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigBuilderTest {

  private ConfigBuilder configBuilder;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
  }

  @Test
  void buildsConfig() {
    Config config = configBuilder.build();
    assertThat(config).isNotNull();
  }

  @Test
  void clones() {
    ConfigBuilder clone = configBuilder.clone();
    assertThat(clone).isNotNull();
  }

}
