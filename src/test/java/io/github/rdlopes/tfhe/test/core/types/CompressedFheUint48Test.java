package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint48;
import io.github.rdlopes.tfhe.core.types.FheUint48;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint48Test {
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
    CompressedFheUint48 compressed = CompressedFheUint48.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint48 deserialized = CompressedFheUint48.deserialize(buffer, serverKey);
    FheUint48 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint48 original = FheUint48.encryptWithClientKey(100, clientKey);
    CompressedFheUint48 compressed = original.compress();
    FheUint48 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint48 original = CompressedFheUint48.encryptWithClientKey(100, clientKey);
    CompressedFheUint48 cloned = original.clone();
    FheUint48 a = original.decompress();
    FheUint48 b = cloned.decompress();
    long da = a.decryptWithClientKey(clientKey);
    long db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
