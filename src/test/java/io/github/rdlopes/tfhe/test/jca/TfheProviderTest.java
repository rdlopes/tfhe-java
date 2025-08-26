package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.jca.TfheProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Provider;
import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;

class TfheProviderTest {

  @BeforeEach
  void setUp() {
    Security.addProvider(new TfheProvider());
  }

  @Test
  void testProviderRegistration() {
    Provider provider = Security.getProvider("TFHE");
    assertThat(provider).isNotNull();
    assertThat(provider.getName()).isEqualTo("TFHE");
    assertThat(provider.getVersionStr()).isEqualTo("1.0");
    assertThat(provider.getInfo()).isEqualTo("TFHE Provider - Torus Fully Homomorphic Encryption");
  }

}
