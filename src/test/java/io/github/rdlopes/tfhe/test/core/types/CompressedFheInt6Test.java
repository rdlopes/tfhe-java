package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt6;
import io.github.rdlopes.tfhe.core.types.FheInt6;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt6Test {
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
    CompressedFheInt6 compressed = CompressedFheInt6.encryptWithClientKey((byte) 31, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt6 deserialized = CompressedFheInt6.deserialize(buffer, serverKey);
    FheInt6 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt6 original = FheInt6.encryptWithClientKey((byte) 31, clientKey);
    CompressedFheInt6 compressed = original.compress();
    FheInt6 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 31);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt6 original = CompressedFheInt6.encryptWithClientKey((byte) 31, clientKey);
    CompressedFheInt6 cloned = original.clone();
    FheInt6 a = original.decompress();
    FheInt6 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
