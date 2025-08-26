package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ModulusSwitchTypeBindings;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.ModulusSwitchTypeBindings.allocate;

public record ModulusSwitchType(MemorySegment pointer) {

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    this(allocate());

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
