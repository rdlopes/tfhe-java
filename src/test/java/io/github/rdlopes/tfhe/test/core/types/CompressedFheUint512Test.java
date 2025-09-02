package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint512;
import io.github.rdlopes.tfhe.core.types.FheUint512;
import io.github.rdlopes.tfhe.core.types.U512;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("intensive")
class CompressedFheUint512Test {
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
    CompressedFheUint512 compressed = CompressedFheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint512 deserialized = CompressedFheUint512.deserialize(buffer, serverKey);
    FheUint512 decompressed = deserialized.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint512 original = FheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    CompressedFheUint512 compressed = original.compress();
    FheUint512 decompressed = compressed.decompress();
    U512 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U512.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint512 original = CompressedFheUint512.encryptWithClientKey(U512.valueOf("100"), clientKey);
    CompressedFheUint512 cloned = original.clone();
    FheUint512 a = original.decompress();
    FheUint512 b = cloned.decompress();
    U512 da = a.decryptWithClientKey(clientKey);
    U512 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
