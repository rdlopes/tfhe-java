package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint2;
import io.github.rdlopes.tfhe.core.types.FheUint2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint2Test {
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
    CompressedFheUint2 compressed = CompressedFheUint2.encryptWithClientKey((byte) 3, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint2 deserialized = CompressedFheUint2.deserialize(buffer, serverKey);
    FheUint2 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint2 original = FheUint2.encryptWithClientKey((byte) 3, clientKey);
    CompressedFheUint2 compressed = original.compress();
    FheUint2 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 3);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint2 original = CompressedFheUint2.encryptWithClientKey((byte) 3, clientKey);
    CompressedFheUint2 cloned = original.clone();
    FheUint2 a = original.decompress();
    FheUint2 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
