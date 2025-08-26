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

  public static void lweDimension(MemorySegment address, long lweDimension) {
    logger.trace("lweDimension - address: {}, lweDimension: {}", address, lweDimension);
    ShortintPBSParameters.lwe_dimension(address, lweDimension);
  }

  public static void glweDimension(MemorySegment address, long glweDimension) {
    logger.trace("glweDimension - address: {}, glweDimension: {}", address, glweDimension);
    ShortintPBSParameters.glwe_dimension(address, glweDimension);
  }

  public static void polynomialSize(MemorySegment address, long polynomialSize) {
    logger.trace("polynomialSize - address: {}, polynomialSize: {}", address, polynomialSize);
    ShortintPBSParameters.polynomial_size(address, polynomialSize);
  }

  public static void lweNoiseDistribution(MemorySegment address, MemorySegment lweNoiseDistribution) {
    logger.trace("lweNoiseDistribution - address: {}, lweNoiseDistribution: {}", address, lweNoiseDistribution);
    ShortintPBSParameters.lwe_noise_distribution(address, lweNoiseDistribution);
  }

  public static void glweNoiseDistribution(MemorySegment address, MemorySegment glweNoiseDistribution) {
    logger.trace("glweNoiseDistribution - address: {}, glweNoiseDistribution: {}", address, glweNoiseDistribution);
    ShortintPBSParameters.glwe_noise_distribution(address, glweNoiseDistribution);
  }

  public static void pbsBaseLog(MemorySegment address, long pbsBaseLog) {
    logger.trace("pbsBaseLog - address: {}, pbsBaseLog: {}", address, pbsBaseLog);
    ShortintPBSParameters.pbs_base_log(address, pbsBaseLog);
  }

  public static void pbsLevel(MemorySegment address, long pbsLevel) {
    logger.trace("pbsLevel - address: {}, pbsLevel: {}", address, pbsLevel);
    ShortintPBSParameters.pbs_level(address, pbsLevel);
  }

  public static void ksBaseLog(MemorySegment address, long ksBaseLog) {
    logger.trace("ksBaseLog - address: {}, ksBaseLog: {}", address, ksBaseLog);
    ShortintPBSParameters.ks_base_log(address, ksBaseLog);
  }

  public static void ksLevel(MemorySegment address, long ksLevel) {
    logger.trace("ksLevel - address: {}, ksLevel: {}", address, ksLevel);
    ShortintPBSParameters.ks_level(address, ksLevel);
  }

  public static void messageModulus(MemorySegment address, long messageModulus) {
    logger.trace("messageModulus - address: {}, messageModulus: {}", address, messageModulus);
    ShortintPBSParameters.message_modulus(address, messageModulus);
  }

  public static void carryModulus(MemorySegment address, long carryModulus) {
    logger.trace("carryModulus - address: {}, carryModulus: {}", address, carryModulus);
    ShortintPBSParameters.carry_modulus(address, carryModulus);
  }

  public static void maxNoiseLevel(MemorySegment address, long maxNoiseLevel) {
    logger.trace("maxNoiseLevel - address: {}, maxNoiseLevel: {}", address, maxNoiseLevel);
    ShortintPBSParameters.max_noise_level(address, maxNoiseLevel);
  }

  public static void log2pFail(MemorySegment address, double log2pFail) {
    logger.trace("log2pFail - address: {}, log2pFail: {}", address, log2pFail);
    ShortintPBSParameters.log2_p_fail(address, log2pFail);
  }

  public static void modulusPowerOf2Exponent(MemorySegment address, long modulusPowerOf2Exponent) {
    logger.trace("modulusPowerOf2Exponent - address: {}, modulusPowerOf2Exponent: {}", address, modulusPowerOf2Exponent);
    ShortintPBSParameters.modulus_power_of_2_exponent(address, modulusPowerOf2Exponent);
  }

  public static void encryptionKeyChoice(MemorySegment address, int encryptionKeyChoice) {
    logger.trace("encryptionKeyChoice - address: {}, encryptionKeyChoice: {}", address, encryptionKeyChoice);
    ShortintPBSParameters.encryption_key_choice(address, encryptionKeyChoice);
  }

  public static void modulusSwitchNoiseReductionParams(MemorySegment address, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("modulusSwitchNoiseReductionParams - address: {}, modulusSwitchNoiseReductionParams: {}", address, modulusSwitchNoiseReductionParams);
    ShortintPBSParameters.modulus_switch_noise_reduction_params(address, modulusSwitchNoiseReductionParams);
  }

  public static long lweDimension(MemorySegment address) {
    logger.trace("lweDimension - address: {}", address);
    return ShortintPBSParameters.lwe_dimension(address);
  }

  public static long glweDimension(MemorySegment address) {
    logger.trace("glweDimension - address: {}", address);
    return ShortintPBSParameters.glwe_dimension(address);
  }

  public static long polynomialSize(MemorySegment address) {
    logger.trace("polynomialSize - address: {}", address);
    return ShortintPBSParameters.polynomial_size(address);
  }

  public static MemorySegment lweNoiseDistribution(MemorySegment address) {
    logger.trace("lweNoiseDistribution - address: {}", address);
    return ShortintPBSParameters.lwe_noise_distribution(address);
  }

  public static MemorySegment glweNoiseDistribution(MemorySegment address) {
    logger.trace("glweNoiseDistribution - address: {}", address);
    return ShortintPBSParameters.glwe_noise_distribution(address);
  }

  public static long pbsBaseLog(MemorySegment address) {
    logger.trace("pbsBaseLog - address: {}", address);
    return ShortintPBSParameters.pbs_base_log(address);
  }

  public static long pbsLevel(MemorySegment address) {
    logger.trace("pbsLevel - address: {}", address);
    return ShortintPBSParameters.pbs_level(address);
  }

  public static long ksBaseLog(MemorySegment address) {
    logger.trace("ksBaseLog - address: {}", address);
    return ShortintPBSParameters.ks_base_log(address);
  }

  public static long ksLevel(MemorySegment address) {
    logger.trace("ksLevel - address: {}", address);
    return ShortintPBSParameters.ks_level(address);
  }

  public static long messageModulus(MemorySegment address) {
    logger.trace("messageModulus - address: {}", address);
    return ShortintPBSParameters.message_modulus(address);
  }

  public static long carryModulus(MemorySegment address) {
    logger.trace("carryModulus - address: {}", address);
    return ShortintPBSParameters.carry_modulus(address);
  }

  public static long maxNoiseLevel(MemorySegment address) {
    logger.trace("maxNoiseLevel - address: {}", address);
    return ShortintPBSParameters.max_noise_level(address);
  }

  public static double log2pFail(MemorySegment address) {
    logger.trace("log2pFail - address: {}", address);
    return ShortintPBSParameters.log2_p_fail(address);
  }

  public static long modulusPowerOf2Exponent(MemorySegment address) {
    logger.trace("modulusPowerOf2Exponent - address: {}", address);
    return ShortintPBSParameters.modulus_power_of_2_exponent(address);
  }

  public static int encryptionKeyChoice(MemorySegment address) {
    logger.trace("encryptionKeyChoice - address: {}", address);
    return ShortintPBSParameters.encryption_key_choice(address);
  }

  public static MemorySegment modulusSwitchNoiseReductionParams(MemorySegment address) {
    logger.trace("modulusSwitchNoiseReductionParams - address: {}", address);
    return ShortintPBSParameters.modulus_switch_noise_reduction_params(address);
  }
}
