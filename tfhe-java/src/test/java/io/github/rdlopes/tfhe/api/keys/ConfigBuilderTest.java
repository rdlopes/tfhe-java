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

// tag::builder_tests[]
  @Test
  void buildsConfig() {
    Config config = configBuilder.build();
    assertThat(config).isNotNull();

    org.assertj.core.api.Assertions.assertThatThrownBy(() -> configBuilder.build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("This builder has already been built/consumed.");
  }

  @Test
  void clones() {
    ConfigBuilder clone = configBuilder.clone();
    assertThat(clone).isNotNull();
  }
// end::builder_tests[]

}
