package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class ModulusSwitchNoiseReductionParamsAssert extends AbstractAssert<ModulusSwitchNoiseReductionParamsAssert, ModulusSwitchNoiseReductionParams> {

  public ModulusSwitchNoiseReductionParamsAssert(ModulusSwitchNoiseReductionParams actual) {
    super(actual, ModulusSwitchNoiseReductionParamsAssert.class);
  }

  public static ModulusSwitchNoiseReductionParamsAssert assertThat(ModulusSwitchNoiseReductionParams actual) {
    return new ModulusSwitchNoiseReductionParamsAssert(actual);
  }

  public ModulusSwitchNoiseReductionParamsAssert hasModulusSwitchZerosCount(int expected) {
    isNotNull();
    if (actual.getModulusSwitchZerosCount() != expected) {
      failWithMessage("Expected modulusSwitchZerosCount to be <%d> but was <%d>", expected, actual.getModulusSwitchZerosCount());
    }
    return this;
  }

  public ModulusSwitchNoiseReductionParamsAssert hasMsBound(double expected) {
    isNotNull();
    if (actual.getMsBound() != expected) {
      failWithMessage("Expected msBound to be <%f> but was <%f>", expected, actual.getMsBound());
    }
    return this;
  }

  public ModulusSwitchNoiseReductionParamsAssert hasMsRSigmaFactor(double expected) {
    isNotNull();
    if (actual.getMsRSigmaFactor() != expected) {
      failWithMessage("Expected msRSigmaFactor to be <%f> but was <%f>", expected, actual.getMsRSigmaFactor());
    }
    return this;
  }

  public ModulusSwitchNoiseReductionParamsAssert hasMsInputVariance(double expected) {
    isNotNull();
    if (actual.getMsInputVariance() != expected) {
      failWithMessage("Expected msInputVariance to be <%f> but was <%f>", expected, actual.getMsInputVariance());
    }
    return this;
  }

  public ModulusSwitchNoiseReductionParamsAssert hasFields(int expectedModulusSwitchZerosCount, double expectedMsBound, double expectedMsRSigmaFactor, double expectedMsInputVariance) {
    return hasModulusSwitchZerosCount(expectedModulusSwitchZerosCount)
      .hasMsBound(expectedMsBound)
      .hasMsRSigmaFactor(expectedMsRSigmaFactor)
      .hasMsInputVariance(expectedMsInputVariance);
  }
}
