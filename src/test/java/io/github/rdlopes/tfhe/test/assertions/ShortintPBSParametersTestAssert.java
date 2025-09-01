package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchType;
import io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class ShortintPBSParametersTestAssert extends AbstractAssert<ShortintPBSParametersTestAssert, ShortintPBSParameters> {

  public ShortintPBSParametersTestAssert(ShortintPBSParameters actual) {
    super(actual, ShortintPBSParametersTestAssert.class);
  }

  public static ShortintPBSParametersTestAssert assertThat(ShortintPBSParameters actual) {
    return new ShortintPBSParametersTestAssert(actual);
  }

  public ShortintPBSParametersTestAssert hasLweDimension(long expected) {
    isNotNull();
    if (actual.getLweDimension() != expected) {
      failWithMessage("Expected lweDimension to be <%d> but was <%d>", expected, actual.getLweDimension());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasGlweDimension(long expected) {
    isNotNull();
    if (actual.getGlweDimension() != expected) {
      failWithMessage("Expected glweDimension to be <%d> but was <%d>", expected, actual.getGlweDimension());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasPolynomialSize(long expected) {
    isNotNull();
    if (actual.getPolynomialSize() != expected) {
      failWithMessage("Expected polynomialSize to be <%d> but was <%d>", expected, actual.getPolynomialSize());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasPbsBaseLog(long expected) {
    isNotNull();
    if (actual.getPbsBaseLog() != expected) {
      failWithMessage("Expected pbsBaseLog to be <%d> but was <%d>", expected, actual.getPbsBaseLog());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasPbsLevel(long expected) {
    isNotNull();
    if (actual.getPbsLevel() != expected) {
      failWithMessage("Expected pbsLevel to be <%d> but was <%d>", expected, actual.getPbsLevel());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasKsBaseLog(long expected) {
    isNotNull();
    if (actual.getKsBaseLog() != expected) {
      failWithMessage("Expected ksBaseLog to be <%d> but was <%d>", expected, actual.getKsBaseLog());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasKsLevel(long expected) {
    isNotNull();
    if (actual.getKsLevel() != expected) {
      failWithMessage("Expected ksLevel to be <%d> but was <%d>", expected, actual.getKsLevel());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasMessageModulus(long expected) {
    isNotNull();
    if (actual.getMessageModulus() != expected) {
      failWithMessage("Expected messageModulus to be <%d> but was <%d>", expected, actual.getMessageModulus());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasCarryModulus(long expected) {
    isNotNull();
    if (actual.getCarryModulus() != expected) {
      failWithMessage("Expected carryModulus to be <%d> but was <%d>", expected, actual.getCarryModulus());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasMaxNoiseLevel(long expected) {
    isNotNull();
    if (actual.getMaxNoiseLevel() != expected) {
      failWithMessage("Expected maxNoiseLevel to be <%d> but was <%d>", expected, actual.getMaxNoiseLevel());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasLog2pFail(double expected) {
    isNotNull();
    if (actual.getLog2pFail() != expected) {
      failWithMessage("Expected log2pFail to be <%f> but was <%f>", expected, actual.getLog2pFail());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasModulusPowerOf2Exponent(long expected) {
    isNotNull();
    if (actual.getModulusPowerOf2Exponent() != expected) {
      failWithMessage("Expected modulusPowerOf2Exponent to be <%d> but was <%d>", expected, actual.getModulusPowerOf2Exponent());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasEncryptionKeyChoice(int expected) {
    isNotNull();
    if (actual.getEncryptionKeyChoice() != expected) {
      failWithMessage("Expected encryptionKeyChoice to be <%d> but was <%d>", expected, actual.getEncryptionKeyChoice());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasLweNoiseDistribution(DynamicDistribution expected) {
    isNotNull();
    DynamicDistribution actualNoise = actual.getLweNoiseDistribution();
    if (actualNoise == null && expected != null) {
      failWithMessage("Expected lweNoiseDistribution to be <%s> but was null", expected);
    } else if (actualNoise != null && expected == null) {
      failWithMessage("Expected lweNoiseDistribution to be null but was <%s>", actualNoise);
    } else if (actualNoise != null) {
      DynamicDistributionAssert.assertThat(actualNoise)
                               .hasFields(expected.getTag(), expected.getDistribution());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasGlweNoiseDistribution(DynamicDistribution expected) {
    isNotNull();
    DynamicDistribution actualNoise = actual.getGlweNoiseDistribution();
    if (actualNoise == null && expected != null) {
      failWithMessage("Expected glweNoiseDistribution to be <%s> but was null", expected);
    } else if (actualNoise != null && expected == null) {
      failWithMessage("Expected glweNoiseDistribution to be null but was <%s>", actualNoise);
    } else if (actualNoise != null) {
      DynamicDistributionAssert.assertThat(actualNoise)
                               .hasFields(expected.getTag(), expected.getDistribution());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasModulusSwitchNoiseReductionParams(ModulusSwitchType expected) {
    isNotNull();
    ModulusSwitchType actualParams = actual.getModulusSwitchNoiseReductionParams();
    if (actualParams == null && expected != null) {
      failWithMessage("Expected modulusSwitchNoiseReductionParams to be <%s> but was null", expected);
    } else if (actualParams != null && expected == null) {
      failWithMessage("Expected modulusSwitchNoiseReductionParams to be null but was <%s>", actualParams);
    } else if (actualParams != null) {
      ModulusSwitchTypeAssert.assertThat(actualParams)
                             .hasFields(expected.getTag(), expected.getModulusSwitchNoiseReductionParams());
    }
    return this;
  }

  public ShortintPBSParametersTestAssert hasFields(
    long lweDimension,
    long glweDimension,
    long polynomialSize,
    long pbsBaseLog,
    long pbsLevel,
    long ksBaseLog,
    long ksLevel,
    long messageModulus,
    long carryModulus,
    long maxNoiseLevel,
    double log2pFail,
    long modulusPowerOf2Exponent,
    int encryptionKeyChoice,
    DynamicDistribution lweNoise,
    DynamicDistribution glweNoise,
    ModulusSwitchType modulusSwitch
  ) {
    return hasLweDimension(lweDimension)
      .hasGlweDimension(glweDimension)
      .hasPolynomialSize(polynomialSize)
      .hasPbsBaseLog(pbsBaseLog)
      .hasPbsLevel(pbsLevel)
      .hasKsBaseLog(ksBaseLog)
      .hasKsLevel(ksLevel)
      .hasMessageModulus(messageModulus)
      .hasCarryModulus(carryModulus)
      .hasMaxNoiseLevel(maxNoiseLevel)
      .hasLog2pFail(log2pFail)
      .hasModulusPowerOf2Exponent(modulusPowerOf2Exponent)
      .hasEncryptionKeyChoice(encryptionKeyChoice)
      .hasLweNoiseDistribution(lweNoise)
      .hasGlweNoiseDistribution(glweNoise)
      .hasModulusSwitchNoiseReductionParams(modulusSwitch);
  }
}
