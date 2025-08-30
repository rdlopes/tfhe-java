package io.github.rdlopes.tfhe.test.jca;

import io.github.rdlopes.tfhe.core.configuration.CompressionParameters;
import io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters;
import io.github.rdlopes.tfhe.jca.TfheKeyPairGenerator;
import io.github.rdlopes.tfhe.jca.TfheParameterSpec;
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
  void generatesKeyPairsWithDifferentSpecs() throws Exception {
    TfheKeyPairGenerator generator = new TfheKeyPairGenerator();

    generator.initialize(TfheParameterSpec.defaultSpec(), null);
    KeyPair firstKeyPair = generator.generateKeyPair();

    generator.initialize(new TfheParameterSpec(
      ShortintPBSParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128,
      CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128,
      null
    ), null);
    KeyPair secondKeyPair = generator.generateKeyPair();

    assertThat(firstKeyPair).isNotNull();
    assertThat(secondKeyPair).isNotNull();
    assertThat(firstKeyPair).isNotSameAs(secondKeyPair);
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
