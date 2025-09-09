
package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.assertions.TfheAssertions.assertThat;

class CompactPublicKeyTest {
  private static final Logger logger = LoggerFactory.getLogger(CompactPublicKeyTest.class);
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

    CompactPublicKey key = CompactPublicKey.createFromClientKey(keySet.clientKey());

    byte[] buffer = key.serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    CompactPublicKey deserialized = CompactPublicKey.deserialize(buffer);

    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getAddress()).isNotNull();
  }

  @Test
  void createsFromClientKey() {
    logger.trace("createsFromClientKey");

    CompactPublicKey key = CompactPublicKey.createFromClientKey(keySet.clientKey());

    assertThat(key).isNotNull();
    assertThat(key.getAddress()).isNotNull();
  }

}
