package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt136;
import io.github.rdlopes.tfhe.core.types.FheInt136;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt136Test {
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
    CompressedFheInt136 compressed = CompressedFheInt136.encryptWithClientKey(I256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt136 deserialized = CompressedFheInt136.deserialize(buffer, serverKey);
    FheInt136 decompressed = deserialized.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt136 original = FheInt136.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt136 compressed = original.compress();
    FheInt136 decompressed = compressed.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt136 original = CompressedFheInt136.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt136 cloned = original.clone();
    FheInt136 a = original.decompress();
    FheInt136 b = cloned.decompress();
    I256 da = a.decryptWithClientKey(clientKey);
    I256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
