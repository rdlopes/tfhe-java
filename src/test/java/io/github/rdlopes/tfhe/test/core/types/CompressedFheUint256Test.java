package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint256;
import io.github.rdlopes.tfhe.core.types.FheUint256;
import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint256Test {
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
    CompressedFheUint256 compressed = CompressedFheUint256.encryptWithClientKey(U256.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint256 deserialized = CompressedFheUint256.deserialize(buffer, serverKey);
    FheUint256 decompressed = deserialized.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint256 original = FheUint256.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint256 compressed = original.compress();
    FheUint256 decompressed = compressed.decompress();
    U256 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U256.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint256 original = CompressedFheUint256.encryptWithClientKey(U256.valueOf("100"), clientKey);
    CompressedFheUint256 cloned = original.clone();
    FheUint256 a = original.decompress();
    FheUint256 b = cloned.decompress();
    U256 da = a.decryptWithClientKey(clientKey);
    U256 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
