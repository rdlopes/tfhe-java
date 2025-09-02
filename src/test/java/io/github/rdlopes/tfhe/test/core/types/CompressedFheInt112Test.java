package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt112;
import io.github.rdlopes.tfhe.core.types.FheInt112;
import io.github.rdlopes.tfhe.core.types.I128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt112Test {
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
    CompressedFheInt112 compressed = CompressedFheInt112.encryptWithClientKey(I128.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt112 deserialized = CompressedFheInt112.deserialize(buffer, serverKey);
    FheInt112 decompressed = deserialized.decompress();
    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt112 original = FheInt112.encryptWithClientKey(I128.valueOf("100"), clientKey);
    CompressedFheInt112 compressed = original.compress();
    FheInt112 decompressed = compressed.decompress();
    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt112 original = CompressedFheInt112.encryptWithClientKey(I128.valueOf("100"), clientKey);
    CompressedFheInt112 cloned = original.clone();
    FheInt112 a = original.decompress();
    FheInt112 b = cloned.decompress();
    I128 da = a.decryptWithClientKey(clientKey);
    I128 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
