package io.github.rdlopes.tfhe.jce;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;

class TfheProviderTest {

  @BeforeAll
  static void registerProvider() {
    Security.addProvider(new TfheProvider());
  }

  @Test
  void providesKeyPairGenerator() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("TFHE");
    assertThat(keyPairGenerator).isNotNull();
  }
}
