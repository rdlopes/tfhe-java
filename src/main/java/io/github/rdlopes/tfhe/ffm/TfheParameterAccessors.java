package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

/**
 * Contains all parameter accessor methods for getting and setting values in various parameter structures.
 * This class provides wrapper methods for Gaussian, TUniform, DynamicDistributionPayload,
 * ModulusSwitchNoiseReductionParams, ModulusSwitchType, and ShortintPBSParameters.
 */
public final class TfheParameterAccessors extends TfheNativeBase {
  private static final Logger logger = LoggerFactory.getLogger(TfheParameterAccessors.class);

  public static void dynamicDistributionTag(MemorySegment dynamicDistributionPointer, long tag) {
    logger.trace("dynamicDistributionTag - dynamicDistributionPointer: {}, tag: {}", dynamicDistributionPointer, tag);
    DynamicDistribution.tag(dynamicDistributionPointer, tag);
  }

  public static long dynamicDistributionTag(MemorySegment dynamicDistributionPointer) {
    logger.trace("dynamicDistributionTag - dynamicDistributionPointer: {}", dynamicDistributionPointer);
    return DynamicDistribution.tag(dynamicDistributionPointer);
  }

  public static void dynamicDistributionPayload(MemorySegment dynamicDistributionPointer, MemorySegment dynamicDistributionPayloadPointer) {
    logger.trace("dynamicDistributionPayload - dynamicDistributionPointer: {}, dynamicDistributionPayloadPointer: {}", dynamicDistributionPointer, dynamicDistributionPayloadPointer);
    DynamicDistribution.distribution(dynamicDistributionPointer, dynamicDistributionPayloadPointer);
  }

  public static MemorySegment dynamicDistributionPayload(MemorySegment dynamicDistributionPointer) {
    logger.trace("dynamicDistributionPayload - dynamicDistributionPointer: {}", dynamicDistributionPointer);
    return DynamicDistribution.distribution(dynamicDistributionPointer);
  }

  // Gaussian wrapper methods
  public static void gaussianStd(MemorySegment pointer, double stdDev) {
    logger.trace("gaussianStd - pointer: {}, stdDev: {}", pointer, stdDev);
    Gaussian.std(pointer, stdDev);
  }

  public static double gaussianStd(MemorySegment pointer) {
    logger.trace("gaussianStd - pointer: {}", pointer);
    return Gaussian.std(pointer);
  }

  // TUniform wrapper methods
  public static void tUniformBoundLog2(MemorySegment pointer, int boundLog2) {
    logger.trace("tUniformBoundLog2 - pointer: {}, boundLog2: {}", pointer, boundLog2);
    TUniform.bound_log2(pointer, boundLog2);
  }

  public static int tUniformBoundLog2(MemorySegment pointer) {
    logger.trace("tUniformBoundLog2 - pointer: {}", pointer);
    return TUniform.bound_log2(pointer);
  }

  // DynamicDistributionPayload wrapper methods
  public static void dynamicDistributionPayloadGaussian(MemorySegment pointer, MemorySegment gaussian) {
    logger.trace("dynamicDistributionPayloadGaussian - pointer: {}, gaussian: {}", pointer, gaussian);
    DynamicDistributionPayload.gaussian(pointer, gaussian);
  }

  public static void dynamicDistributionPayloadTUniform(MemorySegment pointer, MemorySegment tUniform) {
    logger.trace("dynamicDistributionPayloadTUniform - pointer: {}, tUniform: {}", pointer, tUniform);
    DynamicDistributionPayload.t_uniform(pointer, tUniform);
  }

  public static MemorySegment dynamicDistributionPayloadGaussian(MemorySegment pointer) {
    logger.trace("dynamicDistributionPayloadGaussian - pointer: {}", pointer);
    return DynamicDistributionPayload.gaussian(pointer);
  }

  public static MemorySegment dynamicDistributionPayloadTUniform(MemorySegment pointer) {
    logger.trace("dynamicDistributionPayloadTUniform - pointer: {}", pointer);
    return DynamicDistributionPayload.t_uniform(pointer);
  }

  // ModulusSwitchNoiseReductionParams wrapper methods
  public static void modulusSwitchNoiseReductionParamsModulusSwitchZerosCount(MemorySegment pointer, int modulusSwitchZerosCount) {
    logger.trace("modulusSwitchNoiseReductionParamsModulusSwitchZerosCount - pointer: {}, modulusSwitchZerosCount: {}", pointer, modulusSwitchZerosCount);
    ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(pointer, modulusSwitchZerosCount);
  }

  public static void modulusSwitchNoiseReductionParamsMsBound(MemorySegment pointer, double msBound) {
    logger.trace("modulusSwitchNoiseReductionParamsMsBound - pointer: {}, msBound: {}", pointer, msBound);
    ModulusSwitchNoiseReductionParams.ms_bound(pointer, msBound);
  }

  public static void modulusSwitchNoiseReductionParamsMsRSigmaFactor(MemorySegment pointer, double msRSigmaFactor) {
    logger.trace("modulusSwitchNoiseReductionParamsMsRSigmaFactor - pointer: {}, msRSigmaFactor: {}", pointer, msRSigmaFactor);
    ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(pointer, msRSigmaFactor);
  }

  public static void modulusSwitchNoiseReductionParamsMsInputVariance(MemorySegment pointer, double msInputVariance) {
    logger.trace("modulusSwitchNoiseReductionParamsMsInputVariance - pointer: {}, msInputVariance: {}", pointer, msInputVariance);
    ModulusSwitchNoiseReductionParams.ms_input_variance(pointer, msInputVariance);
  }

  public static int modulusSwitchNoiseReductionParamsModulusSwitchZerosCount(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParamsModulusSwitchZerosCount - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.modulus_switch_zeros_count(pointer);
  }

  public static double modulusSwitchNoiseReductionParamsMsBound(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParamsMsBound - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_bound(pointer);
  }

  public static double modulusSwitchNoiseReductionParamsMsRSigmaFactor(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParamsMsRSigmaFactor - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_r_sigma_factor(pointer);
  }

  public static double modulusSwitchNoiseReductionParamsMsInputVariance(MemorySegment pointer) {
    logger.trace("modulusSwitchNoiseReductionParamsMsInputVariance - pointer: {}", pointer);
    return ModulusSwitchNoiseReductionParams.ms_input_variance(pointer);
  }

  // ModulusSwitchType wrapper methods
  public static void modulusSwitchTypeTag(MemorySegment pointer, long tag) {
    logger.trace("modulusSwitchTypeTag - pointer: {}, tag: {}", pointer, tag);
    ModulusSwitchType.tag(pointer, tag);
  }

  public static void modulusSwitchTypeModulusSwitchNoiseReductionParams(MemorySegment pointer, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("modulusSwitchTypeModulusSwitchNoiseReductionParams - pointer: {}, modulusSwitchNoiseReductionParams: {}", pointer, modulusSwitchNoiseReductionParams);
    ModulusSwitchType.modulus_switch_noise_reduction_params(pointer, modulusSwitchNoiseReductionParams);
  }

  public static long modulusSwitchTypeTag(MemorySegment pointer) {
    logger.trace("modulusSwitchTypeTag - pointer: {}", pointer);
    return ModulusSwitchType.tag(pointer);
  }

  public static MemorySegment modulusSwitchTypeModulusSwitchNoiseReductionParams(MemorySegment pointer) {
    logger.trace("modulusSwitchTypeModulusSwitchNoiseReductionParams - pointer: {}", pointer);
    return ModulusSwitchType.modulus_switch_noise_reduction_params(pointer);
  }

  // ShortintPBSParameters wrapper methods
  public static void shortintPBSParametersLweDimension(MemorySegment pointer, int lweDimension) {
    logger.trace("shortintPBSParametersLweDimension - pointer: {}, lweDimension: {}", pointer, lweDimension);
    ShortintPBSParameters.lwe_dimension(pointer, lweDimension);
  }

  public static void shortintPBSParametersGlweDimension(MemorySegment pointer, int glweDimension) {
    logger.trace("shortintPBSParametersGlweDimension - pointer: {}, glweDimension: {}", pointer, glweDimension);
    ShortintPBSParameters.glwe_dimension(pointer, glweDimension);
  }

  public static void shortintPBSParametersPolynomialSize(MemorySegment pointer, int polynomialSize) {
    logger.trace("shortintPBSParametersPolynomialSize - pointer: {}, polynomialSize: {}", pointer, polynomialSize);
    ShortintPBSParameters.polynomial_size(pointer, polynomialSize);
  }

  public static void shortintPBSParametersLweNoiseDistribution(MemorySegment pointer, MemorySegment lweNoiseDistribution) {
    logger.trace("shortintPBSParametersLweNoiseDistribution - pointer: {}, lweNoiseDistribution: {}", pointer, lweNoiseDistribution);
    ShortintPBSParameters.lwe_noise_distribution(pointer, lweNoiseDistribution);
  }

  public static void shortintPBSParametersGlweNoiseDistribution(MemorySegment pointer, MemorySegment glweNoiseDistribution) {
    logger.trace("shortintPBSParametersGlweNoiseDistribution - pointer: {}, glweNoiseDistribution: {}", pointer, glweNoiseDistribution);
    ShortintPBSParameters.glwe_noise_distribution(pointer, glweNoiseDistribution);
  }

  public static void shortintPBSParametersPbsBaseLog(MemorySegment pointer, int pbsBaseLog) {
    logger.trace("shortintPBSParametersPbsBaseLog - pointer: {}, pbsBaseLog: {}", pointer, pbsBaseLog);
    ShortintPBSParameters.pbs_base_log(pointer, pbsBaseLog);
  }

  public static void shortintPBSParametersPbsLevel(MemorySegment pointer, int pbsLevel) {
    logger.trace("shortintPBSParametersPbsLevel - pointer: {}, pbsLevel: {}", pointer, pbsLevel);
    ShortintPBSParameters.pbs_level(pointer, pbsLevel);
  }

  public static void shortintPBSParametersKsBaseLog(MemorySegment pointer, int ksBaseLog) {
    logger.trace("shortintPBSParametersKsBaseLog - pointer: {}, ksBaseLog: {}", pointer, ksBaseLog);
    ShortintPBSParameters.ks_base_log(pointer, ksBaseLog);
  }

  public static void shortintPBSParametersKsLevel(MemorySegment pointer, int ksLevel) {
    logger.trace("shortintPBSParametersKsLevel - pointer: {}, ksLevel: {}", pointer, ksLevel);
    ShortintPBSParameters.ks_level(pointer, ksLevel);
  }

  public static void shortintPBSParametersMessageModulus(MemorySegment pointer, int messageModulus) {
    logger.trace("shortintPBSParametersMessageModulus - pointer: {}, messageModulus: {}", pointer, messageModulus);
    ShortintPBSParameters.message_modulus(pointer, messageModulus);
  }

  public static void shortintPBSParametersCarryModulus(MemorySegment pointer, int carryModulus) {
    logger.trace("shortintPBSParametersCarryModulus - pointer: {}, carryModulus: {}", pointer, carryModulus);
    ShortintPBSParameters.carry_modulus(pointer, carryModulus);
  }

  public static void shortintPBSParametersMaxNoiseLevel(MemorySegment pointer, long maxNoiseLevel) {
    logger.trace("shortintPBSParametersMaxNoiseLevel - pointer: {}, maxNoiseLevel: {}", pointer, maxNoiseLevel);
    ShortintPBSParameters.max_noise_level(pointer, maxNoiseLevel);
  }

  public static void shortintPBSParametersLog2pFail(MemorySegment pointer, double log2pFail) {
    logger.trace("shortintPBSParametersLog2pFail - pointer: {}, log2pFail: {}", pointer, log2pFail);
    ShortintPBSParameters.log2_p_fail(pointer, log2pFail);
  }

  public static void shortintPBSParametersModulusPowerOf2Exponent(MemorySegment pointer, int modulusPowerOf2Exponent) {
    logger.trace("shortintPBSParametersModulusPowerOf2Exponent - pointer: {}, modulusPowerOf2Exponent: {}", pointer, modulusPowerOf2Exponent);
    ShortintPBSParameters.modulus_power_of_2_exponent(pointer, modulusPowerOf2Exponent);
  }

  public static void shortintPBSParametersEncryptionKeyChoice(MemorySegment pointer, int encryptionKeyChoice) {
    logger.trace("shortintPBSParametersEncryptionKeyChoice - pointer: {}, encryptionKeyChoice: {}", pointer, encryptionKeyChoice);
    ShortintPBSParameters.encryption_key_choice(pointer, encryptionKeyChoice);
  }

  public static void shortintPBSParametersModulusSwitchNoiseReductionParams(MemorySegment pointer, MemorySegment modulusSwitchNoiseReductionParams) {
    logger.trace("shortintPBSParametersModulusSwitchNoiseReductionParams - pointer: {}, modulusSwitchNoiseReductionParams: {}", pointer, modulusSwitchNoiseReductionParams);
    ShortintPBSParameters.modulus_switch_noise_reduction_params(pointer, modulusSwitchNoiseReductionParams);
  }

  public static long shortintPBSParametersLweDimension(MemorySegment pointer) {
    logger.trace("shortintPBSParametersLweDimension - pointer: {}", pointer);
    return ShortintPBSParameters.lwe_dimension(pointer);
  }

  public static long shortintPBSParametersGlweDimension(MemorySegment pointer) {
    logger.trace("shortintPBSParametersGlweDimension - pointer: {}", pointer);
    return ShortintPBSParameters.glwe_dimension(pointer);
  }

  public static long shortintPBSParametersPolynomialSize(MemorySegment pointer) {
    logger.trace("shortintPBSParametersPolynomialSize - pointer: {}", pointer);
    return ShortintPBSParameters.polynomial_size(pointer);
  }

  public static MemorySegment shortintPBSParametersLweNoiseDistribution(MemorySegment pointer) {
    logger.trace("shortintPBSParametersLweNoiseDistribution - pointer: {}", pointer);
    return ShortintPBSParameters.lwe_noise_distribution(pointer);
  }

  public static MemorySegment shortintPBSParametersGlweNoiseDistribution(MemorySegment pointer) {
    logger.trace("shortintPBSParametersGlweNoiseDistribution - pointer: {}", pointer);
    return ShortintPBSParameters.glwe_noise_distribution(pointer);
  }

  public static long shortintPBSParametersPbsBaseLog(MemorySegment pointer) {
    logger.trace("shortintPBSParametersPbsBaseLog - pointer: {}", pointer);
    return ShortintPBSParameters.pbs_base_log(pointer);
  }

  public static long shortintPBSParametersPbsLevel(MemorySegment pointer) {
    logger.trace("shortintPBSParametersPbsLevel - pointer: {}", pointer);
    return ShortintPBSParameters.pbs_level(pointer);
  }

  public static long shortintPBSParametersKsBaseLog(MemorySegment pointer) {
    logger.trace("shortintPBSParametersKsBaseLog - pointer: {}", pointer);
    return ShortintPBSParameters.ks_base_log(pointer);
  }

  public static long shortintPBSParametersKsLevel(MemorySegment pointer) {
    logger.trace("shortintPBSParametersKsLevel - pointer: {}", pointer);
    return ShortintPBSParameters.ks_level(pointer);
  }

  public static long shortintPBSParametersMessageModulus(MemorySegment pointer) {
    logger.trace("shortintPBSParametersMessageModulus - pointer: {}", pointer);
    return ShortintPBSParameters.message_modulus(pointer);
  }

  public static long shortintPBSParametersCarryModulus(MemorySegment pointer) {
    logger.trace("shortintPBSParametersCarryModulus - pointer: {}", pointer);
    return ShortintPBSParameters.carry_modulus(pointer);
  }

  public static double shortintPBSParametersMaxNoiseLevel(MemorySegment pointer) {
    logger.trace("shortintPBSParametersMaxNoiseLevel - pointer: {}", pointer);
    return ShortintPBSParameters.max_noise_level(pointer);
  }

  public static double shortintPBSParametersLog2pFail(MemorySegment pointer) {
    logger.trace("shortintPBSParametersLog2pFail - pointer: {}", pointer);
    return ShortintPBSParameters.log2_p_fail(pointer);
  }

  public static long shortintPBSParametersModulusPowerOf2Exponent(MemorySegment pointer) {
    logger.trace("shortintPBSParametersModulusPowerOf2Exponent - pointer: {}", pointer);
    return ShortintPBSParameters.modulus_power_of_2_exponent(pointer);
  }

  public static int shortintPBSParametersEncryptionKeyChoice(MemorySegment pointer) {
    logger.trace("shortintPBSParametersEncryptionKeyChoice - pointer: {}", pointer);
    return ShortintPBSParameters.encryption_key_choice(pointer);
  }

  public static MemorySegment shortintPBSParametersModulusSwitchNoiseReductionParams(MemorySegment pointer) {
    logger.trace("shortintPBSParametersModulusSwitchNoiseReductionParams - pointer: {}", pointer);
    return ShortintPBSParameters.modulus_switch_noise_reduction_params(pointer);
  }

  // ShortintCompactPublicKeyEncryptionParameters wrapper methods
  public static void shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension(MemorySegment pointer, int encryptionLweDimension) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension - pointer: {}, encryptionLweDimension: {}", pointer, encryptionLweDimension);
    ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(pointer, encryptionLweDimension);
  }

  public static void shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution(MemorySegment pointer, MemorySegment encryptionNoiseDistribution) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution - pointer: {}, encryptionNoiseDistribution: {}", pointer, encryptionNoiseDistribution);
    ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(pointer, encryptionNoiseDistribution);
  }

  public static void shortintCompactPublicKeyEncryptionParametersMessageModulus(MemorySegment pointer, int messageModulus) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersMessageModulus - pointer: {}, messageModulus: {}", pointer, messageModulus);
    ShortintCompactPublicKeyEncryptionParameters.message_modulus(pointer, messageModulus);
  }

  public static void shortintCompactPublicKeyEncryptionParametersCarryModulus(MemorySegment pointer, int carryModulus) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersCarryModulus - pointer: {}, carryModulus: {}", pointer, carryModulus);
    ShortintCompactPublicKeyEncryptionParameters.carry_modulus(pointer, carryModulus);
  }

  public static void shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent(MemorySegment pointer, int modulusPowerOf2Exponent) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent - pointer: {}, modulusPowerOf2Exponent: {}", pointer, modulusPowerOf2Exponent);
    ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(pointer, modulusPowerOf2Exponent);
  }

  public static void shortintCompactPublicKeyEncryptionParametersCastingParameters(MemorySegment pointer, MemorySegment castingParameters) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersCastingParameters - pointer: {}, castingParameters: {}", pointer, castingParameters);
    ShortintCompactPublicKeyEncryptionParameters.casting_parameters(pointer, castingParameters);
  }

  public static void shortintCompactPublicKeyEncryptionParametersZkScheme(MemorySegment pointer, int zkScheme) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersZkScheme - pointer: {}, zkScheme: {}", pointer, zkScheme);
    ShortintCompactPublicKeyEncryptionParameters.zk_scheme(pointer, zkScheme);
  }

  public static long shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(pointer);
  }

  public static MemorySegment shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(pointer);
  }

  public static long shortintCompactPublicKeyEncryptionParametersMessageModulus(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersMessageModulus - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.message_modulus(pointer);
  }

  public static long shortintCompactPublicKeyEncryptionParametersCarryModulus(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersCarryModulus - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.carry_modulus(pointer);
  }

  public static long shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(pointer);
  }

  public static MemorySegment shortintCompactPublicKeyEncryptionParametersCastingParameters(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersCastingParameters - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.casting_parameters(pointer);
  }

  public static int shortintCompactPublicKeyEncryptionParametersZkScheme(MemorySegment pointer) {
    logger.trace("shortintCompactPublicKeyEncryptionParametersZkScheme - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.zk_scheme(pointer);
  }

}
