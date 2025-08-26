package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class ShortintCompactPublicKeyEncryptionParametersBindings extends CompactPublicKeyParametersBindings {
  private static final Logger logger = LoggerFactory.getLogger(ShortintCompactPublicKeyEncryptionParametersBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(ShortintCompactPublicKeyEncryptionParameters.layout());
  }

  public static void encryptionLweDimension(MemorySegment pointer, int encryptionLweDimension) {
    logger.trace("encryptionLweDimension - pointer: {}, encryptionLweDimension: {}", pointer, encryptionLweDimension);
    ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(pointer, encryptionLweDimension);
  }

  public static void encryptionNoiseDistribution(MemorySegment pointer, MemorySegment encryptionNoiseDistribution) {
    logger.trace("encryptionNoiseDistribution - pointer: {}, encryptionNoiseDistribution: {}", pointer, encryptionNoiseDistribution);
    ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(pointer, encryptionNoiseDistribution);
  }

  public static void messageModulus(MemorySegment pointer, int messageModulus) {
    logger.trace("messageModulus - pointer: {}, messageModulus: {}", pointer, messageModulus);
    ShortintCompactPublicKeyEncryptionParameters.message_modulus(pointer, messageModulus);
  }

  public static void carryModulus(MemorySegment pointer, int carryModulus) {
    logger.trace("carryModulus - pointer: {}, carryModulus: {}", pointer, carryModulus);
    ShortintCompactPublicKeyEncryptionParameters.carry_modulus(pointer, carryModulus);
  }

  public static void modulusPowerOf2Exponent(MemorySegment pointer, int modulusPowerOf2Exponent) {
    logger.trace("modulusPowerOf2Exponent - pointer: {}, modulusPowerOf2Exponent: {}", pointer, modulusPowerOf2Exponent);
    ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(pointer, modulusPowerOf2Exponent);
  }

  public static void castingParameters(MemorySegment pointer, MemorySegment castingParameters) {
    logger.trace("castingParameters - pointer: {}, castingParameters: {}", pointer, castingParameters);
    ShortintCompactPublicKeyEncryptionParameters.casting_parameters(pointer, castingParameters);
  }

  public static void zkScheme(MemorySegment pointer, int zkScheme) {
    logger.trace("zkScheme - pointer: {}, zkScheme: {}", pointer, zkScheme);
    ShortintCompactPublicKeyEncryptionParameters.zk_scheme(pointer, zkScheme);
  }

  public static long encryptionLweDimension(MemorySegment pointer) {
    logger.trace("encryptionLweDimension - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(pointer);
  }

  public static MemorySegment encryptionNoiseDistribution(MemorySegment pointer) {
    logger.trace("encryptionNoiseDistribution - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(pointer);
  }

  public static long messageModulus(MemorySegment pointer) {
    logger.trace("messageModulus - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.message_modulus(pointer);
  }

  public static long carryModulus(MemorySegment pointer) {
    logger.trace("carryModulus - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.carry_modulus(pointer);
  }

  public static long modulusPowerOf2Exponent(MemorySegment pointer) {
    logger.trace("modulusPowerOf2Exponent - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(pointer);
  }

  public static MemorySegment castingParameters(MemorySegment pointer) {
    logger.trace("castingParameters - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.casting_parameters(pointer);
  }

  public static int zkScheme(MemorySegment pointer) {
    logger.trace("zkScheme - pointer: {}", pointer);
    return ShortintCompactPublicKeyEncryptionParameters.zk_scheme(pointer);
  }
}
