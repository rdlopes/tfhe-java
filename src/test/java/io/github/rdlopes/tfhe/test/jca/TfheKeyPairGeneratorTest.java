package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.jca.TfheKeyPairGenerator;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class TfheKeyPairGeneratorTest {

  @Test
  void testGenerateKeyPair() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    assertThat(keyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
  }

  @Test
  void testMultipleKeyPairGeneration() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    KeyPair keyPair1 = generator.generateKeyPair();
    KeyPair keyPair2 = generator.generateKeyPair();

    assertThat(keyPair1).isNotNull();
    assertThat(keyPair2).isNotNull();
    assertThat(keyPair1).isNotSameAs(keyPair2);
    assertThat(keyPair1.getPublic()).isNotSameAs(keyPair2.getPublic());
    assertThat(keyPair1.getPrivate()).isNotSameAs(keyPair2.getPrivate());
  }

}
