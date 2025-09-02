package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint88;
import io.github.rdlopes.tfhe.core.types.FheUint88;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint88Test {
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
    CompressedFheUint88 compressed = CompressedFheUint88.encryptWithClientKey(U128.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint88 deserialized = CompressedFheUint88.deserialize(buffer, serverKey);
    FheUint88 decompressed = deserialized.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint88 original = FheUint88.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint88 compressed = original.compress();
    FheUint88 decompressed = compressed.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint88 original = CompressedFheUint88.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint88 cloned = original.clone();
    FheUint88 a = original.decompress();
    FheUint88 b = cloned.decompress();
    U128 da = a.decryptWithClientKey(clientKey);
    U128 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
