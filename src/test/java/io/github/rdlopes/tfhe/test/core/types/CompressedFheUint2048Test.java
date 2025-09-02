package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint2048;
import io.github.rdlopes.tfhe.core.types.FheUint2048;
import io.github.rdlopes.tfhe.core.types.U2048;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint2048Test {
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
    CompressedFheUint2048 compressed = CompressedFheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint2048 deserialized = CompressedFheUint2048.deserialize(buffer, serverKey);
    FheUint2048 decompressed = deserialized.decompress();
    U2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint2048 original = FheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    CompressedFheUint2048 compressed = original.compress();
    FheUint2048 decompressed = compressed.decompress();
    U2048 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U2048.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint2048 original = CompressedFheUint2048.encryptWithClientKey(U2048.valueOf("100"), clientKey);
    CompressedFheUint2048 cloned = original.clone();
    FheUint2048 a = original.decompress();
    FheUint2048 b = cloned.decompress();
    U2048 da = a.decryptWithClientKey(clientKey);
    U2048 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
