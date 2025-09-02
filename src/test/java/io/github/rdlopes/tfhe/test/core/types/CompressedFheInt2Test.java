package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt2;
import io.github.rdlopes.tfhe.core.types.FheInt2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt2Test {
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
    CompressedFheInt2 compressed = CompressedFheInt2.encryptWithClientKey((byte) 1, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt2 deserialized = CompressedFheInt2.deserialize(buffer, serverKey);
    FheInt2 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt2 original = FheInt2.encryptWithClientKey((byte) 1, clientKey);
    CompressedFheInt2 compressed = original.compress();
    FheInt2 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 1);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt2 original = CompressedFheInt2.encryptWithClientKey((byte) 1, clientKey);
    CompressedFheInt2 cloned = original.clone();
    FheInt2 a = original.decompress();
    FheInt2 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
