package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class ModulusSwitchType extends GroupLayoutPointer {

  public ModulusSwitchType(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.ModulusSwitchType.layout());
  }

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    super(io.github.rdlopes.tfhe.ffm.ModulusSwitchType.layout());
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.tag(getAddress(), tag);
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.modulus_switch_noise_reduction_params(getAddress(), modulusSwitchNoiseReductionParams.getAddress());
  }

  public long getTag() {
    return io.github.rdlopes.tfhe.ffm.ModulusSwitchType.tag(getAddress());
  }

  public void setTag(long tag) {
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.tag(getAddress(), tag);
  }

  public ModulusSwitchNoiseReductionParams getModulusSwitchNoiseReductionParams() {
    return new ModulusSwitchNoiseReductionParams(io.github.rdlopes.tfhe.ffm.ModulusSwitchType.modulus_switch_noise_reduction_params(getAddress()));
  }

  public void setModulusSwitchNoiseReductionParams(ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    io.github.rdlopes.tfhe.ffm.ModulusSwitchType.modulus_switch_noise_reduction_params(getAddress(), modulusSwitchNoiseReductionParams.getAddress());
  }
}
