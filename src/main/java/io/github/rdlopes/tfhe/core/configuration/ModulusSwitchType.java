package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ModulusSwitchTypeBindings;

import java.lang.foreign.MemorySegment;

public record ModulusSwitchType(MemorySegment pointer) {

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    this(ModulusSwitchTypeBindings.allocate());

    ModulusSwitchTypeBindings.tag(pointer, tag);
    ModulusSwitchTypeBindings.modulusSwitchNoiseReductionParams(pointer, modulusSwitchNoiseReductionParams.pointer());
  }

  public long tag() {
    return ModulusSwitchTypeBindings.tag(pointer);
  }

  public ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams() {
    return new ModulusSwitchNoiseReductionParams(ModulusSwitchTypeBindings.modulusSwitchNoiseReductionParams(pointer));
  }
}
