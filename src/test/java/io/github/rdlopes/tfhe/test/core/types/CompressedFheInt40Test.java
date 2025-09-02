package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt40;
import io.github.rdlopes.tfhe.core.types.FheInt40;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt40Test {
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
    CompressedFheInt40 compressed = CompressedFheInt40.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt40 deserialized = CompressedFheInt40.deserialize(buffer, serverKey);
    FheInt40 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt40 original = FheInt40.encryptWithClientKey(100, clientKey);
    CompressedFheInt40 compressed = original.compress();
    FheInt40 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt40 original = CompressedFheInt40.encryptWithClientKey(100, clientKey);
    CompressedFheInt40 cloned = original.clone();
    FheInt40 a = original.decompress();
    FheInt40 b = cloned.decompress();
    long da = a.decryptWithClientKey(clientKey);
    long db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
