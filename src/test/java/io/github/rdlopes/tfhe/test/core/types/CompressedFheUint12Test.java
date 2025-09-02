package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint12;
import io.github.rdlopes.tfhe.core.types.FheUint12;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint12Test {
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
    CompressedFheUint12 compressed = CompressedFheUint12.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint12 deserialized = CompressedFheUint12.deserialize(buffer, serverKey);
    FheUint12 decompressed = deserialized.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint12 original = FheUint12.encryptWithClientKey((short) 100, clientKey);
    CompressedFheUint12 compressed = original.compress();
    FheUint12 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint12 original = CompressedFheUint12.encryptWithClientKey((short) 100, clientKey);
    CompressedFheUint12 cloned = original.clone();
    FheUint12 a = original.decompress();
    FheUint12 b = cloned.decompress();
    short da = a.decryptWithClientKey(clientKey);
    short db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
