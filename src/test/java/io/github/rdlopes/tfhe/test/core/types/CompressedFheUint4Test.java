package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint4;
import io.github.rdlopes.tfhe.core.types.FheUint4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint4Test {
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
    CompressedFheUint4 compressed = CompressedFheUint4.encryptWithClientKey((byte) 10, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint4 deserialized = CompressedFheUint4.deserialize(buffer, serverKey);
    FheUint4 decompressed = deserialized.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void decompressesRoundTrip() {
    FheUint4 original = FheUint4.encryptWithClientKey((byte) 10, clientKey);
    CompressedFheUint4 compressed = original.compress();
    FheUint4 decompressed = compressed.decompress();
    byte decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo((byte) 10);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint4 original = CompressedFheUint4.encryptWithClientKey((byte) 10, clientKey);
    CompressedFheUint4 cloned = original.clone();
    FheUint4 a = original.decompress();
    FheUint4 b = cloned.decompress();
    byte da = a.decryptWithClientKey(clientKey);
    byte db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
