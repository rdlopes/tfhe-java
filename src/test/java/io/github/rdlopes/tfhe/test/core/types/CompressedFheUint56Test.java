package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint56;
import io.github.rdlopes.tfhe.core.types.FheUint56;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint56Test {
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
    CompressedFheUint56 compressed = CompressedFheUint56.encryptWithClientKey(100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint56 deserialized = CompressedFheUint56.deserialize(buffer, serverKey);
    FheUint56 decompressed = deserialized.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint56 original = FheUint56.encryptWithClientKey(100, clientKey);
    CompressedFheUint56 compressed = original.compress();
    FheUint56 decompressed = compressed.decompress();
    long decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint56 original = CompressedFheUint56.encryptWithClientKey(100, clientKey);
    CompressedFheUint56 cloned = original.clone();
    FheUint56 a = original.decompress();
    FheUint56 b = cloned.decompress();
    long da = a.decryptWithClientKey(clientKey);
    long db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
