
package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;

class CompressedServerKeyTest {
  private static final Logger logger = LoggerFactory.getLogger(CompressedServerKeyTest.class);
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
  void serializesAndDeserializes() {
    logger.trace("serializesAndDeserializes");

    CompressedServerKey key = CompressedServerKey.createFromClientKey(keySet.clientKey());

    byte[] buffer = key.serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    CompressedServerKey deserialized = CompressedServerKey.deserialize(buffer);

    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getAddress()).isNotNull();
  }

  @Test
  void createsFromClientKey() {
    logger.trace("createsFromClientKey");

    CompressedServerKey key = CompressedServerKey.createFromClientKey(keySet.clientKey());

    assertThat(key).isNotNull();
    assertThat(key.getAddress()).isNotNull();
  }

}
