package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.jca.*;
import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.spec.AlgorithmParameterSpec;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TfheKeyPairGeneratorTest {

  @Test
  void generatesKeyPairWithDefaultSpec() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    assertThat(keyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
  }

  @Test
  void generatesDifferentKeyPairs() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    KeyPair keyPair1 = generator.generateKeyPair();
    KeyPair keyPair2 = generator.generateKeyPair();

    assertThat(keyPair1).isNotNull();
    assertThat(keyPair2).isNotNull();
    assertThat(keyPair1).isNotSameAs(keyPair2);
    assertThat(keyPair1.getPublic()).isNotSameAs(keyPair2.getPublic());
    assertThat(keyPair1.getPrivate()).isNotSameAs(keyPair2.getPrivate());
  }

  @Test
  void generatesClientAndPublicKeyPair() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();
    TfheParameterSpec clientKeySpec = new TfheParameterSpec(false);
    generator.initialize(clientKeySpec, null);

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    assertThat(keyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
  }

  @Test
  void generatesClientAndServerKeyPair() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();
    TfheParameterSpec serverKeySpec = new TfheParameterSpec(true);
    generator.initialize(serverKeySpec, null);

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfheServerKey.class);
    assertThat(keyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
  }

  @Test
  void generatesKeyPairsWithDifferentSpecs() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    generator.initialize(new TfheParameterSpec(false), null);
    KeyPair clientKeyPair = generator.generateKeyPair();

    generator.initialize(new TfheParameterSpec(true), null);
    KeyPair serverKeyPair = generator.generateKeyPair();

    assertThat(clientKeyPair).isNotNull();
    assertThat(serverKeyPair).isNotNull();
    assertThat(clientKeyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    assertThat(serverKeyPair.getPublic()).isInstanceOf(TfheServerKey.class);
    assertThat(clientKeyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
    assertThat(serverKeyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
  }

  @Test
  void throwsWithInvalidParameterSpec() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();
    AlgorithmParameterSpec invalidSpec = new AlgorithmParameterSpec() {
    };

    assertThatThrownBy(() -> generator.initialize(invalidSpec, null))
      .isInstanceOf(InvalidAlgorithmParameterException.class)
      .hasMessage("Only TfheParameterSpec is supported");
  }

  @Test
  void throwsWithNullParameterSpec() {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    assertThatThrownBy(() -> generator.initialize(null, null))
      .isInstanceOf(NullPointerException.class);
  }

}
