package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class ModulusSwitchTypeBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ModulusSwitchTypeBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(ModulusSwitchType.layout());
  }

  public static void tag(MemorySegment pointer, long tag) {
    logger.trace("tag - pointer: {}, tag: {}", pointer, tag);
    ModulusSwitchType.tag(pointer, tag);
  }

  public static void modulusSwitchNoiseReductionParams(MemorySegment pointer, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("modulusSwitchNoiseReductionParams - pointer: {}, modulusSwitchNoiseReductionParams: {}", pointer, modulusSwitchNoiseReductionParams);
    ModulusSwitchType.modulus_switch_noise_reduction_params(pointer, modulusSwitchNoiseReductionParams);
  }

  public static long tag(MemorySegment pointer) {
    logger.trace("tag - pointer: {}", pointer);
    return ModulusSwitchType.tag(pointer);
  }

  public static MemorySegment modulusSwitchNoiseReductionParams(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParams - pointer: {}", pointer);
    return ModulusSwitchType.modulus_switch_noise_reduction_params(pointer);
  }
}
