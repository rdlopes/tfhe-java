package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParamsBindings;

import java.lang.foreign.MemorySegment;

public record ModulusSwitchNoiseReductionParams(MemorySegment address) {

  public ModulusSwitchNoiseReductionParams(
    int modulusSwitchZerosCount,
    double msBound,
    double msRSigmaFactor,
    double msInputVariance
  ) {
    this(ModulusSwitchNoiseReductionParamsBindings.allocate());

    ModulusSwitchNoiseReductionParamsBindings.modulusSwitchZerosCount(address, modulusSwitchZerosCount);
    ModulusSwitchNoiseReductionParamsBindings.msBound(address, msBound);
    ModulusSwitchNoiseReductionParamsBindings.msRSigmaFactor(address, msRSigmaFactor);
    ModulusSwitchNoiseReductionParamsBindings.msInputVariance(address, msInputVariance);
  }

  public int modulusSwitchZerosCount() {
    return ModulusSwitchNoiseReductionParamsBindings.modulusSwitchZerosCount(address);
  }

  public double msBound() {
    return ModulusSwitchNoiseReductionParamsBindings.msBound(address);
  }

  public double msRSigmaFactor() {
    return ModulusSwitchNoiseReductionParamsBindings.msRSigmaFactor(address);
  }

  public double msInputVariance() {
    return ModulusSwitchNoiseReductionParamsBindings.msInputVariance(address);
  }
}
