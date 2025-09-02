package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt12;
import io.github.rdlopes.tfhe.core.types.FheInt12;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt12Test {
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
    CompressedFheInt12 compressed = CompressedFheInt12.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt12 deserialized = CompressedFheInt12.deserialize(buffer, serverKey);
    FheInt12 decompressed = deserialized.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheInt12 original = FheInt12.encryptWithClientKey((short) 100, clientKey);
    CompressedFheInt12 compressed = original.compress();
    FheInt12 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt12 original = CompressedFheInt12.encryptWithClientKey((short) 100, clientKey);
    CompressedFheInt12 cloned = original.clone();
    FheInt12 a = original.decompress();
    FheInt12 b = cloned.decompress();
    short da = a.decryptWithClientKey(clientKey);
    short db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
