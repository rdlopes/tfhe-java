package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.jca.TfheKeyPairGenerator;
import io.github.rdlopes.tfhe.jca.TfheParameterSpec;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;
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
    TfheParameterSpec spec = new TfheParameterSpec(false);
    generator.initialize(spec, null);

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    TfhePublicKey publicKey = (TfhePublicKey) keyPair.getPublic();
    assertThat(publicKey.isServerKey()).isFalse();
  }

  @Test
  void generatesClientAndServerKeyPair() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();
    TfheParameterSpec spec = new TfheParameterSpec(true);
    generator.initialize(spec, null);

    KeyPair keyPair = generator.generateKeyPair();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    TfhePublicKey publicKey = (TfhePublicKey) keyPair.getPublic();
    assertThat(publicKey.isServerKey()).isTrue();
  }

  @Test
  void generatesKeyPairsWithDifferentSpecs() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    generator.initialize(new TfheParameterSpec(true), null);
    KeyPair clientServerKeyPair = generator.generateKeyPair();

    generator.initialize(new TfheParameterSpec(false), null);
    KeyPair clientPublicKeyPair = generator.generateKeyPair();

    assertThat(clientServerKeyPair).isNotNull();
    assertThat(clientPublicKeyPair).isNotNull();
    assertThat(clientPublicKeyPair.getPublic()).isInstanceOf(TfhePublicKey.class);
    assertThat(clientPublicKeyPair.getPrivate()).isInstanceOf(TfhePrivateKey.class);
    TfhePublicKey serverKey = (TfhePublicKey) clientServerKeyPair.getPublic();
    TfhePublicKey publicKey = (TfhePublicKey) clientPublicKeyPair.getPublic();
    assertThat(serverKey.isServerKey()).isTrue();
    assertThat(publicKey.isServerKey()).isFalse();
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
