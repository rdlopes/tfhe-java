package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint168;
import io.github.rdlopes.tfhe.core.types.FheUint168;
import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint168Test {
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
    CompressedFheUint168 compressed = CompressedFheUint168.encryptWithClientKey(U256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint168 deserialized = CompressedFheUint168.deserialize(buffer, serverKey);
    FheUint168 decompressed = deserialized.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint168 original = FheUint168.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint168 compressed = original.compress();
    FheUint168 decompressed = compressed.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint168 original = CompressedFheUint168.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint168 cloned = original.clone();
    FheUint168 a = original.decompress();
    FheUint168 b = cloned.decompress();
    U256 da = a.decryptWithClientKey(clientKey);
    U256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
