package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt8;
import io.github.rdlopes.tfhe.core.types.FheInt8;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt8Test {
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
    CompressedFheInt8 compressed = CompressedFheInt8.encryptWithClientKey((byte) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt8 deserialized = CompressedFheInt8.deserialize(buffer, serverKey);
    FheInt8 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt8 original = FheInt8.encryptWithClientKey((byte) 100, clientKey);
    CompressedFheInt8 compressed = original.compress();
    FheInt8 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt8 original = CompressedFheInt8.encryptWithClientKey((byte) 100, clientKey);
    CompressedFheInt8 cloned = original.clone();
    FheInt8 a = original.decompress();
    FheInt8 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
