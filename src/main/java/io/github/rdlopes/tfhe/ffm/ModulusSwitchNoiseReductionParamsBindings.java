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

  public static void modulusSwitchZerosCount(MemorySegment address, int modulusSwitchZerosCount) {
    logger.trace("modulusSwitchZerosCount - address: {}, modulusSwitchZerosCount: {}", address, modulusSwitchZerosCount);
    ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(address, modulusSwitchZerosCount);
  }

  public static void msBound(MemorySegment address, double msBound) {
    logger.trace("msBound - address: {}, msBound: {}", address, msBound);
    ModulusSwitchNoiseReductionParams.ms_bound(address, msBound);
  }

  public static void msRSigmaFactor(MemorySegment address, double msRSigmaFactor) {
    logger.trace("msRSigmaFactor - address: {}, msRSigmaFactor: {}", address, msRSigmaFactor);
    ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(address, msRSigmaFactor);
  }

  public static void msInputVariance(MemorySegment address, double msInputVariance) {
    logger.trace("msInputVariance - address: {}, msInputVariance: {}", address, msInputVariance);
    ModulusSwitchNoiseReductionParams.ms_input_variance(address, msInputVariance);
  }

  public static int modulusSwitchZerosCount(MemorySegment address) {
    logger.trace("modulusSwitchZerosCount - address: {}", address);
    return ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(address);
  }

  public static double msBound(MemorySegment address) {
    logger.trace("msBound - address: {}", address);
    return ModulusSwitchNoiseReductionParams.ms_bound(address);
  }

  public static double msRSigmaFactor(MemorySegment address) {
    logger.trace("msRSigmaFactor - address: {}", address);
    return ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(address);
  }

  public static double msInputVariance(MemorySegment address) {
    logger.trace("msInputVariance - address: {}", address);
    return ModulusSwitchNoiseReductionParams.ms_input_variance(address);
  }
}
