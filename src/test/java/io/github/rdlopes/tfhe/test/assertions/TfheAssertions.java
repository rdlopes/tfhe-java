package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.*;
import io.github.rdlopes.tfhe.core.types.I2048;
import org.assertj.core.api.Assertions;

@SuppressWarnings("ClassEscapesDefinedScope")
public class TfheAssertions extends Assertions {
  public static I2048Assert assertThat(I2048 actual) {
    return new I2048Assert(actual);
  }

  public static ShortintPBSParametersTestAssert assertThat(ShortintPBSParameters actual) {
    return new ShortintPBSParametersTestAssert(actual);
  }

  public static GaussianAssert assertThat(Gaussian actual) {
    return new GaussianAssert(actual);
  }

  public static TUniformAssert assertThat(TUniform actual) {
    return new TUniformAssert(actual);
  }

  public static DynamicDistributionPayloadAssert assertThat(DynamicDistributionPayload actual) {
    return new DynamicDistributionPayloadAssert(actual);
  }

  public static DynamicDistributionAssert assertThat(DynamicDistribution actual) {
    return new DynamicDistributionAssert(actual);
  }

  public static ModulusSwitchTypeAssert assertThat(ModulusSwitchType actual) {
    return new ModulusSwitchTypeAssert(actual);
  }

  public static ModulusSwitchNoiseReductionParamsAssert assertThat(ModulusSwitchNoiseReductionParams actual) {
    return new ModulusSwitchNoiseReductionParamsAssert(actual);
  }

  public static ShortintCompactPublicKeyEncryptionParametersAssert assertThat(ShortintCompactPublicKeyEncryptionParameters actual) {
    return new ShortintCompactPublicKeyEncryptionParametersAssert(actual);
  }
}
