package io.github.rdlopes.tfhe.jce.spi;

import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class TfheKeyPairGeneratorTest {

  @Test
  void generatesKeyPair() throws InvalidAlgorithmParameterException {
    TfheKeyPairGenerator keyPairGenerator = new TfheKeyPairGenerator();
    keyPairGenerator.initialize(null, null);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
  }
}
