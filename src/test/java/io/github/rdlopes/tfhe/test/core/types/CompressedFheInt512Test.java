package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt512;
import io.github.rdlopes.tfhe.core.types.FheInt512;
import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheInt512Test {
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
    CompressedFheInt512 compressed = CompressedFheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt512 deserialized = CompressedFheInt512.deserialize(buffer, serverKey);
    FheInt512 decompressed = deserialized.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt512 original = FheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    CompressedFheInt512 compressed = original.compress();
    FheInt512 decompressed = compressed.decompress();
    I1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I1024.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt512 original = CompressedFheInt512.encryptWithClientKey(I1024.valueOf("100"), clientKey);
    CompressedFheInt512 cloned = original.clone();
    FheInt512 a = original.decompress();
    FheInt512 b = cloned.decompress();
    I1024 da = a.decryptWithClientKey(clientKey);
    I1024 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
