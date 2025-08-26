package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ModulusSwitchTypeBindings;

import java.lang.foreign.MemorySegment;

public record ModulusSwitchType(MemorySegment address) {

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    this(ModulusSwitchTypeBindings.allocate());

    ModulusSwitchTypeBindings.tag(address, tag);
    ModulusSwitchTypeBindings.modulusSwitchNoiseReductionParams(address, modulusSwitchNoiseReductionParams.address());
  }

  public long tag() {
    return ModulusSwitchTypeBindings.tag(address);
  }

  public ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams() {
    return new ModulusSwitchNoiseReductionParams(ModulusSwitchTypeBindings.modulusSwitchNoiseReductionParams(address));
  }
}
