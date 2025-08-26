package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class ShortintPBSParametersBindings extends ConfigurationParametersBindings {
  private static final Logger logger = LoggerFactory.getLogger(ShortintPBSParametersBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(ShortintPBSParameters.layout());
  }

  public static void lweDimension(MemorySegment pointer, int lweDimension) {
    logger.trace("lweDimension - pointer: {}, lweDimension: {}", pointer, lweDimension);
    ShortintPBSParameters.lwe_dimension(pointer, lweDimension);
  }

  public static void glweDimension(MemorySegment pointer, int glweDimension) {
    logger.trace("glweDimension - pointer: {}, glweDimension: {}", pointer, glweDimension);
    ShortintPBSParameters.glwe_dimension(pointer, glweDimension);
  }

  public static void polynomialSize(MemorySegment pointer, int polynomialSize) {
    logger.trace("polynomialSize - pointer: {}, polynomialSize: {}", pointer, polynomialSize);
    ShortintPBSParameters.polynomial_size(pointer, polynomialSize);
  }

  public static void lweNoiseDistribution(MemorySegment pointer, MemorySegment lweNoiseDistribution) {
    logger.trace("lweNoiseDistribution - pointer: {}, lweNoiseDistribution: {}", pointer, lweNoiseDistribution);
    ShortintPBSParameters.lwe_noise_distribution(pointer, lweNoiseDistribution);
  }

  public static void glweNoiseDistribution(MemorySegment pointer, MemorySegment glweNoiseDistribution) {
    logger.trace("glweNoiseDistribution - pointer: {}, glweNoiseDistribution: {}", pointer, glweNoiseDistribution);
    ShortintPBSParameters.glwe_noise_distribution(pointer, glweNoiseDistribution);
  }

  public static void pbsBaseLog(MemorySegment pointer, int pbsBaseLog) {
    logger.trace("pbsBaseLog - pointer: {}, pbsBaseLog: {}", pointer, pbsBaseLog);
    ShortintPBSParameters.pbs_base_log(pointer, pbsBaseLog);
  }

  public static void pbsLevel(MemorySegment pointer, int pbsLevel) {
    logger.trace("pbsLevel - pointer: {}, pbsLevel: {}", pointer, pbsLevel);
    ShortintPBSParameters.pbs_level(pointer, pbsLevel);
  }

  public static void ksBaseLog(MemorySegment pointer, int ksBaseLog) {
    logger.trace("ksBaseLog - pointer: {}, ksBaseLog: {}", pointer, ksBaseLog);
    ShortintPBSParameters.ks_base_log(pointer, ksBaseLog);
  }

  public static void ksLevel(MemorySegment pointer, int ksLevel) {
    logger.trace("ksLevel - pointer: {}, ksLevel: {}", pointer, ksLevel);
    ShortintPBSParameters.ks_level(pointer, ksLevel);
  }

  public static void messageModulus(MemorySegment pointer, int messageModulus) {
    logger.trace("messageModulus - pointer: {}, messageModulus: {}", pointer, messageModulus);
    ShortintPBSParameters.message_modulus(pointer, messageModulus);
  }

  public static void carryModulus(MemorySegment pointer, int carryModulus) {
    logger.trace("carryModulus - pointer: {}, carryModulus: {}", pointer, carryModulus);
    ShortintPBSParameters.carry_modulus(pointer, carryModulus);
  }

  public static void maxNoiseLevel(MemorySegment pointer, long maxNoiseLevel) {
    logger.trace("maxNoiseLevel - pointer: {}, maxNoiseLevel: {}", pointer, maxNoiseLevel);
    ShortintPBSParameters.max_noise_level(pointer, maxNoiseLevel);
  }

  public static void log2pFail(MemorySegment pointer, double log2pFail) {
    logger.trace("log2pFail - pointer: {}, log2pFail: {}", pointer, log2pFail);
    ShortintPBSParameters.log2_p_fail(pointer, log2pFail);
  }

  public static void modulusPowerOf2Exponent(MemorySegment pointer, int modulusPowerOf2Exponent) {
    logger.trace("modulusPowerOf2Exponent - pointer: {}, modulusPowerOf2Exponent: {}", pointer, modulusPowerOf2Exponent);
    ShortintPBSParameters.modulus_power_of_2_exponent(pointer, modulusPowerOf2Exponent);
  }

  public static void encryptionKeyChoice(MemorySegment pointer, int encryptionKeyChoice) {
    logger.trace("encryptionKeyChoice - pointer: {}, encryptionKeyChoice: {}", pointer, encryptionKeyChoice);
    ShortintPBSParameters.encryption_key_choice(pointer, encryptionKeyChoice);
  }

  public static void modulusSwitchNoiseReductionParams(MemorySegment pointer, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("modulusSwitchNoiseReductionParams - pointer: {}, modulusSwitchNoiseReductionParams: {}", pointer, modulusSwitchNoiseReductionParams);
    ShortintPBSParameters.modulus_switch_noise_reduction_params(pointer, modulusSwitchNoiseReductionParams);
  }

  public static long lweDimension(MemorySegment pointer) {
    logger.trace("lweDimension - pointer: {}", pointer);
    return ShortintPBSParameters.lwe_dimension(pointer);
  }

  public static long glweDimension(MemorySegment pointer) {
    logger.trace("glweDimension - pointer: {}", pointer);
    return ShortintPBSParameters.glwe_dimension(pointer);
  }

  public static long polynomialSize(MemorySegment pointer) {
    logger.trace("polynomialSize - pointer: {}", pointer);
    return ShortintPBSParameters.polynomial_size(pointer);
  }

  public static MemorySegment lweNoiseDistribution(MemorySegment pointer) {
    logger.trace("lweNoiseDistribution - pointer: {}", pointer);
    return ShortintPBSParameters.lwe_noise_distribution(pointer);
  }

  public static MemorySegment glweNoiseDistribution(MemorySegment pointer) {
    logger.trace("glweNoiseDistribution - pointer: {}", pointer);
    return ShortintPBSParameters.glwe_noise_distribution(pointer);
  }

  public static long pbsBaseLog(MemorySegment pointer) {
    logger.trace("pbsBaseLog - pointer: {}", pointer);
    return ShortintPBSParameters.pbs_base_log(pointer);
  }

  public static long pbsLevel(MemorySegment pointer) {
    logger.trace("pbsLevel - pointer: {}", pointer);
    return ShortintPBSParameters.pbs_level(pointer);
  }

  public static long ksBaseLog(MemorySegment pointer) {
    logger.trace("ksBaseLog - pointer: {}", pointer);
    return ShortintPBSParameters.ks_base_log(pointer);
  }

  public static long ksLevel(MemorySegment pointer) {
    logger.trace("ksLevel - pointer: {}", pointer);
    return ShortintPBSParameters.ks_level(pointer);
  }

  public static long messageModulus(MemorySegment pointer) {
    logger.trace("messageModulus - pointer: {}", pointer);
    return ShortintPBSParameters.message_modulus(pointer);
  }

  public static long carryModulus(MemorySegment pointer) {
    logger.trace("carryModulus - pointer: {}", pointer);
    return ShortintPBSParameters.carry_modulus(pointer);
  }

  public static long maxNoiseLevel(MemorySegment pointer) {
    logger.trace("maxNoiseLevel - pointer: {}", pointer);
    return ShortintPBSParameters.max_noise_level(pointer);
  }

  public static double log2pFail(MemorySegment pointer) {
    logger.trace("log2pFail - pointer: {}", pointer);
    return ShortintPBSParameters.log2_p_fail(pointer);
  }

  public static long modulusPowerOf2Exponent(MemorySegment pointer) {
    logger.trace("modulusPowerOf2Exponent - pointer: {}", pointer);
    return ShortintPBSParameters.modulus_power_of_2_exponent(pointer);
  }

  public static int encryptionKeyChoice(MemorySegment pointer) {
    logger.trace("encryptionKeyChoice - pointer: {}", pointer);
    return ShortintPBSParameters.encryption_key_choice(pointer);
  }

  public static MemorySegment modulusSwitchNoiseReductionParams(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParams - pointer: {}", pointer);
    return ShortintPBSParameters.modulus_switch_noise_reduction_params(pointer);
  }
}
