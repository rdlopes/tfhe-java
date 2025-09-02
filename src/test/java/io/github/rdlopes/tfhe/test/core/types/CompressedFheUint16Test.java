package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint16;
import io.github.rdlopes.tfhe.core.types.FheUint16;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint16Test {
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
    CompressedFheUint16 compressed = CompressedFheUint16.encryptWithClientKey((short) 100, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint16 deserialized = CompressedFheUint16.deserialize(buffer, serverKey);
    FheUint16 decompressed = deserialized.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint16 original = FheUint16.encryptWithClientKey((short) 100, clientKey);
    CompressedFheUint16 compressed = original.compress();
    FheUint16 decompressed = compressed.decompress();
    short decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((short) 100);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint16 original = CompressedFheUint16.encryptWithClientKey((short) 100, clientKey);
    CompressedFheUint16 cloned = original.clone();
    FheUint16 a = original.decompress();
    FheUint16 b = cloned.decompress();
    short da = a.decryptWithClientKey(clientKey);
    short db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
