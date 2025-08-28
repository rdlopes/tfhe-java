package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.jca.TfheProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;

class TfheProviderTest {

  @BeforeEach
  void setUp() {
    Security.addProvider(new TfheProvider());
  }

  @Test
  void registersProvider() {
    Provider provider = Security.getProvider("TFHE");

    assertThat(provider).isNotNull();
    assertThat(provider.getName()).isEqualTo("TFHE");
    assertThat(provider.getVersionStr()).isEqualTo("1.0");
    assertThat(provider.getInfo()).isEqualTo("TFHE Provider - Torus Fully Homomorphic Encryption");
  }

  @Test
  void providesKeyPairGenerator() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("TFHE");

    assertThat(keyPairGenerator).isNotNull();
    assertThat(keyPairGenerator.getAlgorithm()).isEqualTo("TFHE");
  }
}
