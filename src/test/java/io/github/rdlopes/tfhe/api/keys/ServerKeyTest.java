
package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;
import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThatThrownBy;

class ServerKeyTest {
  private static final Logger logger = LoggerFactory.getLogger(ServerKeyTest.class);
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
  void serializesButCannotDeserialize() {
    logger.trace("serializesButCannotDeserialize");

    byte[] buffer = keySet.serverKey()
                          .serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    assertThatThrownBy(() -> ServerKey.deserialize(buffer))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("ServerKey cannot be deserialized");
  }

}
