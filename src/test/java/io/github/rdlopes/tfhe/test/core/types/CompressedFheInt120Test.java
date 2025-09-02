package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheInt120;
import io.github.rdlopes.tfhe.core.types.FheInt120;
import io.github.rdlopes.tfhe.core.types.I128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheInt120Test {
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
    CompressedFheInt120 compressed = CompressedFheInt120.encryptWithClientKey(I128.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheInt120 deserialized = CompressedFheInt120.deserialize(buffer, serverKey);
    FheInt120 decompressed = deserialized.decompress();
    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheInt120 original = FheInt120.encryptWithClientKey(I128.valueOf("100"), clientKey);
    CompressedFheInt120 compressed = original.compress();
    FheInt120 decompressed = compressed.decompress();
    I128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(I128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheInt120 original = CompressedFheInt120.encryptWithClientKey(I128.valueOf("100"), clientKey);
    CompressedFheInt120 cloned = original.clone();
    FheInt120 a = original.decompress();
    FheInt120 b = cloned.decompress();
    I128 da = a.decryptWithClientKey(clientKey);
    I128 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
