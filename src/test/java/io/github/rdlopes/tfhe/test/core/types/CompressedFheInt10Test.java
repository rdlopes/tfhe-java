package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt10;
import io.github.rdlopes.tfhe.core.types.FheInt10;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt10Test {
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
    CompressedFheInt10 compressed = CompressedFheInt10.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt10 deserialized = CompressedFheInt10.deserialize(buffer, serverKey);
    FheInt10 decompressed = deserialized.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt10 original = FheInt10.encryptWithClientKey((short) 100, clientKey);
    CompressedFheInt10 compressed = original.compress();
    FheInt10 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt10 original = CompressedFheInt10.encryptWithClientKey((short) 100, clientKey);
    CompressedFheInt10 cloned = original.clone();
    FheInt10 a = original.decompress();
    FheInt10 b = cloned.decompress();
    short da = a.decryptWithClientKey(clientKey);
    short db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
