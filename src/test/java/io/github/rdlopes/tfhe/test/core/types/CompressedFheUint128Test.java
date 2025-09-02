package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint128;
import io.github.rdlopes.tfhe.core.types.FheUint128;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint128Test {
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
    CompressedFheUint128 compressed = CompressedFheUint128.encryptWithClientKey(U128.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint128 deserialized = CompressedFheUint128.deserialize(buffer, serverKey);
    FheUint128 decompressed = deserialized.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint128 original = FheUint128.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint128 compressed = original.compress();
    FheUint128 decompressed = compressed.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint128 original = CompressedFheUint128.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint128 cloned = original.clone();
    FheUint128 a = original.decompress();
    FheUint128 b = cloned.decompress();
    U128 da = a.decryptWithClientKey(clientKey);
    U128 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
