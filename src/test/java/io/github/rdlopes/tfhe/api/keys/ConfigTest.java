package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {
  private Config config;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    config = configBuilder.build();
  }

  @Test
  void initializesKeyPair() {
    ClientKey clientKey = new ClientKey();
    ServerKey serverKey = new ServerKey();

    assertThatCode(() -> config.initialize(clientKey, serverKey))
      .doesNotThrowAnyException();
  }

  @Test
  void throwsWhenInitializedTwice() {
    ClientKey clientKey = new ClientKey();
    ServerKey serverKey = new ServerKey();

    assertThatCode(() -> config.initialize(clientKey, serverKey))
      .doesNotThrowAnyException();

    assertThatThrownBy(() -> config.initialize(clientKey, serverKey))
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }

}
