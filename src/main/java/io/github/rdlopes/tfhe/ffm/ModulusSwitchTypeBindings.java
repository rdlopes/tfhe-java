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

  public static void tag(MemorySegment address, long tag) {
    logger.trace("tag - address: {}, tag: {}", address, tag);
    ModulusSwitchType.tag(address, tag);
  }

  public static void modulusSwitchNoiseReductionParams(MemorySegment address, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("modulusSwitchNoiseReductionParams - address: {}, modulusSwitchNoiseReductionParams: {}", address, modulusSwitchNoiseReductionParams);
    ModulusSwitchType.modulus_switch_noise_reduction_params(address, modulusSwitchNoiseReductionParams);
  }

  public static long tag(MemorySegment address) {
    logger.trace("tag - address: {}", address);
    return ModulusSwitchType.tag(address);
  }

  public static MemorySegment modulusSwitchNoiseReductionParams(MemorySegment address) {
    logger.trace("modulusSwitchNoiseReductionParams - address: {}", address);
    return ModulusSwitchType.modulus_switch_noise_reduction_params(address);
  }
}
