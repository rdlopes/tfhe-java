
package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;
import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThatThrownBy;

class PublicKeyTest {
  private static final Logger logger = LoggerFactory.getLogger(PublicKeyTest.class);
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    keySet = config.generateKeys();
    keySet.serverKey()
          .setAsKey();
  }

  @AfterEach
  void tearDown() {
    keySet.destroy();
  }

  @Test
  void cannotSerialize() {
    logger.trace("cannotSerialize");

    PublicKey key = PublicKey.createFromClientKey(keySet.clientKey());

    assertThatThrownBy(key::serialize)
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("PublicKey cannot be serialized");
  }

  @Test
  void createsFromClientKey() {
    logger.trace("createsFromClientKey");

    PublicKey key = PublicKey.createFromClientKey(keySet.clientKey());

    assertThat(key).isNotNull();
    assertThat(key.getAddress()).isNotNull();
  }

}
