package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class ModulusSwitchNoiseReductionParams extends GroupLayoutPointer {

  public ModulusSwitchNoiseReductionParams(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.layout());
  }

  public ModulusSwitchNoiseReductionParams(
    int modulusSwitchZerosCount,
    double msBound,
    double msRSigmaFactor,
    double msInputVariance
  ) {
    super(
      io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.layout()
    );
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(getAddress(), modulusSwitchZerosCount);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_bound(getAddress(), msBound);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(getAddress(), msRSigmaFactor);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_input_variance(getAddress(), msInputVariance);
  }

  public int getModulusSwitchZerosCount() {
    return io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(getAddress());
  }

  public double getMsBound() {
    return io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_bound(getAddress());
  }

  public double getMsRSigmaFactor() {
    return io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(getAddress());
  }

  public double getMsInputVariance() {
    return io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_input_variance(getAddress());
  }
}
