package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateModulusSwitchNoiseReductionParams;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.*;

public record ModulusSwitchNoiseReductionParams(MemorySegment pointer) {

  public ModulusSwitchNoiseReductionParams(
    int modulusSwitchZerosCount,
    double msBound,
    double msRSigmaFactor,
    double msInputVariance
  ) {
    this(allocateModulusSwitchNoiseReductionParams());

    modulusSwitchNoiseReductionParamsModulusSwitchZerosCount(pointer, modulusSwitchZerosCount);
    modulusSwitchNoiseReductionParamsMsBound(pointer, msBound);
    modulusSwitchNoiseReductionParamsMsRSigmaFactor(pointer, msRSigmaFactor);
    modulusSwitchNoiseReductionParamsMsInputVariance(pointer, msInputVariance);
  }

  public int modulusSwitchZerosCount() {
    return modulusSwitchNoiseReductionParamsModulusSwitchZerosCount(pointer);
  }

  public double msBound() {
    return modulusSwitchNoiseReductionParamsMsBound(pointer);
  }

  public double msRSigmaFactor() {
    return modulusSwitchNoiseReductionParamsMsRSigmaFactor(pointer);
  }

  public double msInputVariance() {
    return modulusSwitchNoiseReductionParamsMsInputVariance(pointer);
  }
}
