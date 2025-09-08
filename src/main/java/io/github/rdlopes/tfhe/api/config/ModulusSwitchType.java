package io.github.rdlopes.tfhe.api.config;

import io.github.rdlopes.tfhe.ffm.NativePointer;

public class ModulusSwitchType extends NativePointer {

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    super(io.github.rdlopes.tfhe.ffm.ModulusSwitchType::allocate);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.tag(getAddress(), tag);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.modulus_switch_noise_reduction_params(getAddress(), modulusSwitchNoiseReductionParams.getAddress());
  }
}
