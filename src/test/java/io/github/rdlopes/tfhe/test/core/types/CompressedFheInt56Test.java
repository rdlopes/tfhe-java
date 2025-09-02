package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt56;
import io.github.rdlopes.tfhe.core.types.FheInt56;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt56Test {
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
  }

  @Test
  void encryptsSerializesAndDeserializes() {
    CompressedFheInt56 compressed = CompressedFheInt56.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt56 deserialized = CompressedFheInt56.deserialize(buffer, serverKey);
    FheInt56 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt56 original = FheInt56.encryptWithClientKey(100, clientKey);
    CompressedFheInt56 compressed = original.compress();
    FheInt56 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt56 original = CompressedFheInt56.encryptWithClientKey(100, clientKey);
    CompressedFheInt56 cloned = original.clone();
    FheInt56 a = original.decompress();
    FheInt56 b = cloned.decompress();
    long da = a.decryptWithClientKey(clientKey);
    long db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
