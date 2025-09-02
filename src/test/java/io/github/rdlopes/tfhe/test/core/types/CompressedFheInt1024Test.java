package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt1024;
import io.github.rdlopes.tfhe.core.types.FheInt1024;
import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt1024Test {
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
    CompressedFheInt1024 compressed = CompressedFheInt1024.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt1024 deserialized = CompressedFheInt1024.deserialize(buffer, serverKey);
    FheInt1024 decompressed = deserialized.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt1024 original = FheInt1024.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    CompressedFheInt1024 compressed = original.compress();
    FheInt1024 decompressed = compressed.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt1024 original = CompressedFheInt1024.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    CompressedFheInt1024 cloned = original.clone();
    FheInt1024 a = original.decompress();
    FheInt1024 b = cloned.decompress();
    I1024 da = a.decryptWithClientKey(clientKey);
    I1024 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
