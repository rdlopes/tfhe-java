package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheUint104;
import io.github.rdlopes.tfhe.core.types.FheUint104;
import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheUint104Test {
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
    CompressedFheUint104 compressed = CompressedFheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheUint104 deserialized = CompressedFheUint104.deserialize(buffer, serverKey);
    FheUint104 decompressed = deserialized.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void decompressesRoundTrip() {
    FheUint104 original = FheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint104 compressed = original.compress();
    FheUint104 decompressed = compressed.decompress();
    U128 decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(U128.valueOf("100"));
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheUint104 original = CompressedFheUint104.encryptWithClientKey(U128.valueOf("100"), clientKey);
    CompressedFheUint104 cloned = original.clone();
    FheUint104 a = original.decompress();
    FheUint104 b = cloned.decompress();
    U128 da = a.decryptWithClientKey(clientKey);
    U128 db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
