package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class ModulusSwitchNoiseReductionParamsBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ModulusSwitchNoiseReductionParamsBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(ModulusSwitchNoiseReductionParams.layout());
  }

  public static void modulusSwitchZerosCount(MemorySegment pointer, int modulusSwitchZerosCount) {
    logger.trace("modulusSwitchZerosCount - pointer: {}, modulusSwitchZerosCount: {}", pointer, modulusSwitchZerosCount);
    ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(pointer, modulusSwitchZerosCount);
  }

  public static void msBound(MemorySegment pointer, double msBound) {
    logger.trace("msBound - pointer: {}, msBound: {}", pointer, msBound);
    ModulusSwitchNoiseReductionParams.ms_bound(pointer, msBound);
  }

  public static void msRSigmaFactor(MemorySegment pointer, double msRSigmaFactor) {
    logger.trace("msRSigmaFactor - pointer: {}, msRSigmaFactor: {}", pointer, msRSigmaFactor);
    ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(pointer, msRSigmaFactor);
  }

  public static void msInputVariance(MemorySegment pointer, double msInputVariance) {
    logger.trace("msInputVariance - pointer: {}, msInputVariance: {}", pointer, msInputVariance);
    ModulusSwitchNoiseReductionParams.ms_input_variance(pointer, msInputVariance);
  }

  public static int modulusSwitchZerosCount(MemorySegment pointer) {
    logger.trace("modulusSwitchZerosCount - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(pointer);
  }

  public static double msBound(MemorySegment pointer) {
    logger.trace("msBound - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_bound(pointer);
  }

  public static double msRSigmaFactor(MemorySegment pointer) {
    logger.trace("msRSigmaFactor - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(pointer);
  }

  public static double msInputVariance(MemorySegment pointer) {
    logger.trace("msInputVariance - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_input_variance(pointer);
  }
}
