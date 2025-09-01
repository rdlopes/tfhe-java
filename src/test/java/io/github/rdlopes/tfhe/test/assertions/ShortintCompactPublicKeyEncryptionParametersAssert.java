package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters;
import io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class ShortintCompactPublicKeyEncryptionParametersAssert extends AbstractAssert<ShortintCompactPublicKeyEncryptionParametersAssert, ShortintCompactPublicKeyEncryptionParameters> {

  public ShortintCompactPublicKeyEncryptionParametersAssert(ShortintCompactPublicKeyEncryptionParameters actual) {
    super(actual, ShortintCompactPublicKeyEncryptionParametersAssert.class);
  }

  public static ShortintCompactPublicKeyEncryptionParametersAssert assertThat(ShortintCompactPublicKeyEncryptionParameters actual) {
    return new ShortintCompactPublicKeyEncryptionParametersAssert(actual);
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasEncryptionLweDimension(long expected) {
    isNotNull();
    if (actual.getEncryptionLweDimension() != expected) {
      failWithMessage("Expected encryptionLweDimension to be <%d> but was <%d>", expected, actual.getEncryptionLweDimension());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasMessageModulus(long expected) {
    isNotNull();
    if (actual.getMessageModulus() != expected) {
      failWithMessage("Expected messageModulus to be <%d> but was <%d>", expected, actual.getMessageModulus());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasCarryModulus(long expected) {
    isNotNull();
    if (actual.getCarryModulus() != expected) {
      failWithMessage("Expected carryModulus to be <%d> but was <%d>", expected, actual.getCarryModulus());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasModulusPowerOf2Exponent(long expected) {
    isNotNull();
    if (actual.getModulusPowerOf2Exponent() != expected) {
      failWithMessage("Expected modulusPowerOf2Exponent to be <%d> but was <%d>", expected, actual.getModulusPowerOf2Exponent());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasZkScheme(int expected) {
    isNotNull();
    if (actual.getZkScheme() != expected) {
      failWithMessage("Expected zkScheme to be <%d> but was <%d>", expected, actual.getZkScheme());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasEncryptionNoiseDistribution(DynamicDistribution expected) {
    isNotNull();
    DynamicDistribution actualNoise = actual.getEncryptionNoiseDistribution();
    if (actualNoise == null && expected != null) {
      failWithMessage("Expected encryptionNoiseDistribution to be <%s> but was null", expected);
    } else if (actualNoise != null && expected == null) {
      failWithMessage("Expected encryptionNoiseDistribution to be null but was <%s>", actualNoise);
    } else if (actualNoise != null) {
      DynamicDistributionAssert.assertThat(actualNoise)
                               .hasFields(expected.getTag(), expected.getDistribution());
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasCastingParameters(ShortintPBSParameters expected) {
    isNotNull();
    ShortintPBSParameters actualCasting = actual.getCastingParameters();
    if (actualCasting == null && expected != null) {
      failWithMessage("Expected castingParameters to be not null but was null");
    } else if (actualCasting != null && expected == null) {
      failWithMessage("Expected castingParameters to be null but was <%s>", actualCasting);
    }
    return this;
  }

  public ShortintCompactPublicKeyEncryptionParametersAssert hasFields(
    long encryptionLweDimension,
    long messageModulus,
    long carryModulus,
    long modulusPowerOf2Exponent,
    int zkScheme,
    DynamicDistribution encryptionNoise,
    ShortintPBSParameters castingParameters
  ) {
    return hasEncryptionLweDimension(encryptionLweDimension)
      .hasMessageModulus(messageModulus)
      .hasCarryModulus(carryModulus)
      .hasModulusPowerOf2Exponent(modulusPowerOf2Exponent)
      .hasZkScheme(zkScheme)
      .hasEncryptionNoiseDistribution(encryptionNoise)
      .hasCastingParameters(castingParameters);
  }
}
