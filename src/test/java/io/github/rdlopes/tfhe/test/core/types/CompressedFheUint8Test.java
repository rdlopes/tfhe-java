package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint8;
import io.github.rdlopes.tfhe.core.types.FheUint8;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint8Test {
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
    CompressedFheUint8 compressed = CompressedFheUint8.encryptWithClientKey((byte) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint8 deserialized = CompressedFheUint8.deserialize(buffer, serverKey);
    FheUint8 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint8 original = FheUint8.encryptWithClientKey((byte) 100, clientKey);
    CompressedFheUint8 compressed = original.compress();
    FheUint8 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint8 original = CompressedFheUint8.encryptWithClientKey((byte) 100, clientKey);
    CompressedFheUint8 cloned = original.clone();
    FheUint8 a = original.decompress();
    FheUint8 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
