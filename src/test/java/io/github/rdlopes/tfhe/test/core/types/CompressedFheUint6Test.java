package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint6;
import io.github.rdlopes.tfhe.core.types.FheUint6;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint6Test {
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
    CompressedFheUint6 compressed = CompressedFheUint6.encryptWithClientKey((byte) 63, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint6 deserialized = CompressedFheUint6.deserialize(buffer, serverKey);
    FheUint6 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint6 original = FheUint6.encryptWithClientKey((byte) 63, clientKey);
    CompressedFheUint6 compressed = original.compress();
    FheUint6 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 63);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint6 original = CompressedFheUint6.encryptWithClientKey((byte) 63, clientKey);
    CompressedFheUint6 cloned = original.clone();
    FheUint6 a = original.decompress();
    FheUint6 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
