package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint160;
import io.github.rdlopes.tfhe.core.types.FheUint160;
import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint160Test {
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
    CompressedFheUint160 compressed = CompressedFheUint160.encryptWithClientKey(U256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint160 deserialized = CompressedFheUint160.deserialize(buffer, serverKey);
    FheUint160 decompressed = deserialized.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint160 original = FheUint160.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint160 compressed = original.compress();
    FheUint160 decompressed = compressed.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint160 original = CompressedFheUint160.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint160 cloned = original.clone();
    FheUint160 a = original.decompress();
    FheUint160 b = cloned.decompress();
    U256 da = a.decryptWithClientKey(clientKey);
    U256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
