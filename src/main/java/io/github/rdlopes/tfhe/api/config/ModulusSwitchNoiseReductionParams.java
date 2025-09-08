package io.github.rdlopes.tfhe.api.config;

import io.github.rdlopes.tfhe.ffm.NativePointer;

public class ModulusSwitchNoiseReductionParams extends NativePointer {

  public ModulusSwitchNoiseReductionParams(
    int modulusSwitchZerosCount,
    double msBound,
    double msRSigmaFactor,
    double msInputVariance
  ) {
    super(io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams::allocate);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(getAddress(), modulusSwitchZerosCount);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_bound(getAddress(), msBound);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(getAddress(), msRSigmaFactor);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchNoiseReductionParams.ms_input_variance(getAddress(), msInputVariance);
  }
}
