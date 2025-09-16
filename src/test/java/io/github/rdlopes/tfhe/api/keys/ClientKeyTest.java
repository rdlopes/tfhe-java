
package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;

class ClientKeyTest {
  private static final Logger logger = LoggerFactory.getLogger(ClientKeyTest.class);
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    keySet = config.generateKeys();
    keySet.serverKey()
          .set();
  }

  @AfterEach
  void tearDown() {
    keySet.destroy();
  }

  @Test
  void serializesAndDeserializes() {
    logger.trace("serializesAndDeserializes");

    byte[] buffer = keySet.clientKey()
                          .serialize();
    ClientKey deserialized = ClientKey.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();
  }

}
