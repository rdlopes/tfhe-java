package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.*;
import io.github.rdlopes.tfhe.core.types.*;
import org.assertj.core.api.Assertions;

@SuppressWarnings("ClassEscapesDefinedScope")
public class TfheAssertions extends Assertions {
  public static I128Assert assertThat(I128 actual) {
    return new I128Assert(actual);
  }

  public static I256Assert assertThat(I256 actual) {
    return new I256Assert(actual);
  }

  public static I512Assert assertThat(I512 actual) {
    return new I512Assert(actual);
  }

  public static I1024Assert assertThat(I1024 actual) {
    return new I1024Assert(actual);
  }

  public static I2048Assert assertThat(I2048 actual) {
    return new I2048Assert(actual);
  }

  public static U128Assert assertThat(U128 actual) {
    return new U128Assert(actual);
  }

  public static U256Assert assertThat(U256 actual) {
    return new U256Assert(actual);
  }

  public static U512Assert assertThat(U512 actual) {
    return new U512Assert(actual);
  }

  public static U1024Assert assertThat(U1024 actual) {
    return new U1024Assert(actual);
  }

  public static U2048Assert assertThat(U2048 actual) {
    return new U2048Assert(actual);
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
