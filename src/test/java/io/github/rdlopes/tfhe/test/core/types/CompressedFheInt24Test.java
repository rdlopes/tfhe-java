package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt24;
import io.github.rdlopes.tfhe.core.types.FheInt24;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt24Test {
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
    CompressedFheInt24 compressed = CompressedFheInt24.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt24 deserialized = CompressedFheInt24.deserialize(buffer, serverKey);
    FheInt24 decompressed = deserialized.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt24 original = FheInt24.encryptWithClientKey(100, clientKey);
    CompressedFheInt24 compressed = original.compress();
    FheInt24 decompressed = compressed.decompress();
    int decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt24 original = CompressedFheInt24.encryptWithClientKey(100, clientKey);
    CompressedFheInt24 cloned = original.clone();
    FheInt24 a = original.decompress();
    FheInt24 b = cloned.decompress();
    int da = a.decryptWithClientKey(clientKey);
    int db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
