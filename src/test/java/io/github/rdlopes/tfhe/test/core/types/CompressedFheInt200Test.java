package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt200;
import io.github.rdlopes.tfhe.core.types.FheInt200;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt200Test {
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
    CompressedFheInt200 compressed = CompressedFheInt200.encryptWithClientKey(I256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt200 deserialized = CompressedFheInt200.deserialize(buffer, serverKey);
    FheInt200 decompressed = deserialized.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt200 original = FheInt200.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt200 compressed = original.compress();
    FheInt200 decompressed = compressed.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt200 original = CompressedFheInt200.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt200 cloned = original.clone();
    FheInt200 a = original.decompress();
    FheInt200 b = cloned.decompress();
    I256 da = a.decryptWithClientKey(clientKey);
    I256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
