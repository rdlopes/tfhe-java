package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateModulusSwitchType;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.modulusSwitchTypeModulusSwitchNoiseReductionParams;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.modulusSwitchTypeTag;

public record ModulusSwitchType(MemorySegment pointer) {

  public ModulusSwitchType(long tag, ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams) {
    this(allocateModulusSwitchType());

    modulusSwitchTypeTag(pointer, tag);
    modulusSwitchTypeModulusSwitchNoiseReductionParams(pointer, modulusSwitchNoiseReductionParams.pointer());
  }

  public long tag() {
    return modulusSwitchTypeTag(pointer);
  }

  public ModulusSwitchNoiseReductionParams modulusSwitchNoiseReductionParams() {
    return new ModulusSwitchNoiseReductionParams(modulusSwitchTypeModulusSwitchNoiseReductionParams(pointer));
  }
}
