package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt2048;
import io.github.rdlopes.tfhe.core.types.FheInt2048;
import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt2048Test {
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
    CompressedFheInt2048 compressed = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt2048 deserialized = CompressedFheInt2048.deserialize(buffer, serverKey);
    FheInt2048 decompressed = deserialized.decompress();
    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I2048.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt2048 original = FheInt2048.encryptWithClientKey(I2048.valueOf("100"), clientKey);
    CompressedFheInt2048 compressed = original.compress();
    FheInt2048 decompressed = compressed.decompress();
    I2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I2048.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt2048 original = CompressedFheInt2048.encryptWithClientKey(I2048.valueOf("100"), clientKey);
    CompressedFheInt2048 cloned = original.clone();
    FheInt2048 a = original.decompress();
    FheInt2048 b = cloned.decompress();
    I2048 da = a.decryptWithClientKey(clientKey);
    I2048 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
