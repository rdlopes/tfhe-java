package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.CompressedFheBool;
import io.github.rdlopes.tfhe.core.types.FheBool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedFheBoolTest {
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
    CompressedFheBool compressed = CompressedFheBool.encryptWithClientKey(true, clientKey);
    DynamicBufferView buffer = compressed.serialize();
    CompressedFheBool deserialized = CompressedFheBool.deserialize(buffer, serverKey);
    FheBool decompressed = deserialized.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void decompressesRoundTrip() {
    FheBool original = FheBool.encryptWithClientKey(true, clientKey);
    CompressedFheBool compressed = original.compress();
    FheBool decompressed = compressed.decompress();
    boolean decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(true);
  }

  @Test
  void clonesSuccessfully() {
    CompressedFheBool original = CompressedFheBool.encryptWithClientKey(true, clientKey);
    CompressedFheBool cloned = original.clone();
    FheBool a = original.decompress();
    FheBool b = cloned.decompress();
    boolean da = a.decryptWithClientKey(clientKey);
    boolean db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
