package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt4;
import io.github.rdlopes.tfhe.core.types.FheInt4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt4Test {
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
    CompressedFheInt4 compressed = CompressedFheInt4.encryptWithClientKey((byte) 5, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt4 deserialized = CompressedFheInt4.deserialize(buffer, serverKey);
    FheInt4 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt4 original = FheInt4.encryptWithClientKey((byte) 5, clientKey);
    CompressedFheInt4 compressed = original.compress();
    FheInt4 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 5);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt4 original = CompressedFheInt4.encryptWithClientKey((byte) 5, clientKey);
    CompressedFheInt4 cloned = original.clone();
    FheInt4 a = original.decompress();
    FheInt4 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
