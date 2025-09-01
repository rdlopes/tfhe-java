package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchType;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class ModulusSwitchTypeAssert extends AbstractAssert<ModulusSwitchTypeAssert, ModulusSwitchType> {

  public ModulusSwitchTypeAssert(ModulusSwitchType actual) {
    super(actual, ModulusSwitchTypeAssert.class);
  }

  public static ModulusSwitchTypeAssert assertThat(ModulusSwitchType actual) {
    return new ModulusSwitchTypeAssert(actual);
  }

  public ModulusSwitchTypeAssert hasTag(long expected) {
    isNotNull();
    if (actual.getTag() != expected) {
      failWithMessage("Expected tag to be <%d> but was <%d>", expected, actual.getTag());
    }
    return this;
  }

  public ModulusSwitchTypeAssert hasModulusSwitchNoiseReductionParams(ModulusSwitchNoiseReductionParams expected) {
    isNotNull();
    ModulusSwitchNoiseReductionParams actualParams = actual.getModulusSwitchNoiseReductionParams();
    if (actualParams == null && expected != null) {
      failWithMessage("Expected modulusSwitchNoiseReductionParams to be <%s> but was null", expected);
    } else if (actualParams != null && expected == null) {
      failWithMessage("Expected modulusSwitchNoiseReductionParams to be null but was <%s>", actualParams);
    } else if (actualParams != null) {
      ModulusSwitchNoiseReductionParamsAssert.assertThat(actualParams)
                                             .hasFields(
                                               expected.getModulusSwitchZerosCount(),
                                               expected.getMsBound(),
                                               expected.getMsRSigmaFactor(),
                                               expected.getMsInputVariance()
                                             );
    }
    return this;
  }

  public ModulusSwitchTypeAssert hasFields(long expectedTag, ModulusSwitchNoiseReductionParams expectedParams) {
    return hasTag(expectedTag)
      .hasModulusSwitchNoiseReductionParams(expectedParams);
  }
}
