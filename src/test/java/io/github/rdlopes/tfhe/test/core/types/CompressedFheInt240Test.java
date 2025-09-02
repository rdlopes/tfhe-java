package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt240;
import io.github.rdlopes.tfhe.core.types.FheInt240;
import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt240Test {
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
    CompressedFheInt240 compressed = CompressedFheInt240.encryptWithClientKey(I256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt240 deserialized = CompressedFheInt240.deserialize(buffer, serverKey);
    FheInt240 decompressed = deserialized.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt240 original = FheInt240.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt240 compressed = original.compress();
    FheInt240 decompressed = compressed.decompress();
    I256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt240 original = CompressedFheInt240.encryptWithClientKey(I256.valueOf("100"), clientKey);
    CompressedFheInt240 cloned = original.clone();
    FheInt240 a = original.decompress();
    FheInt240 b = cloned.decompress();
    I256 da = a.decryptWithClientKey(clientKey);
    I256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
