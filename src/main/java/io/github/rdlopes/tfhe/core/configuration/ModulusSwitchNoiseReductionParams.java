package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParamsBindings;

import java.lang.foreign.MemorySegment;

public record ModulusSwitchNoiseReductionParams(MemorySegment pointer) {

  public ModulusSwitchNoiseReductionParams(
    int modulusSwitchZerosCount,
    double msBound,
    double msRSigmaFactor,
    double msInputVariance
  ) {
    this(ModulusSwitchNoiseReductionParamsBindings.allocate());

    ModulusSwitchNoiseReductionParamsBindings.modulusSwitchZerosCount(pointer, modulusSwitchZerosCount);
    ModulusSwitchNoiseReductionParamsBindings.msBound(pointer, msBound);
    ModulusSwitchNoiseReductionParamsBindings.msRSigmaFactor(pointer, msRSigmaFactor);
    ModulusSwitchNoiseReductionParamsBindings.msInputVariance(pointer, msInputVariance);
  }

  public int modulusSwitchZerosCount() {
    return ModulusSwitchNoiseReductionParamsBindings.modulusSwitchZerosCount(pointer);
  }

  public double msBound() {
    return ModulusSwitchNoiseReductionParamsBindings.msBound(pointer);
  }

  public double msRSigmaFactor() {
    return ModulusSwitchNoiseReductionParamsBindings.msRSigmaFactor(pointer);
  }

  public double msInputVariance() {
    return ModulusSwitchNoiseReductionParamsBindings.msInputVariance(pointer);
  }
}
