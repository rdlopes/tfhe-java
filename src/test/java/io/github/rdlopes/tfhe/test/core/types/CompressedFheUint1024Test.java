package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint1024;
import io.github.rdlopes.tfhe.core.types.FheUint1024;
import io.github.rdlopes.tfhe.core.types.U1024;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint1024Test {
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
    CompressedFheUint1024 compressed = CompressedFheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint1024 deserialized = CompressedFheUint1024.deserialize(buffer, serverKey);
    FheUint1024 decompressed = deserialized.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint1024 original = FheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    CompressedFheUint1024 compressed = original.compress();
    FheUint1024 decompressed = compressed.decompress();
    U1024 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U1024.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint1024 original = CompressedFheUint1024.encryptWithClientKey(U1024.valueOf("100"), clientKey);
    CompressedFheUint1024 cloned = original.clone();
    FheUint1024 a = original.decompress();
    FheUint1024 b = cloned.decompress();
    U1024 da = a.decryptWithClientKey(clientKey);
    U1024 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
