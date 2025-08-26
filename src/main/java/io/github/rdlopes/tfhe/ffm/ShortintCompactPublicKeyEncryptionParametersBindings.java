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

  public static void encryptionLweDimension(MemorySegment address, long encryptionLweDimension) {
    logger.trace("encryptionLweDimension - address: {}, encryptionLweDimension: {}", address, encryptionLweDimension);
    ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(address, encryptionLweDimension);
  }

  public static void encryptionNoiseDistribution(MemorySegment address, MemorySegment encryptionNoiseDistribution) {
    logger.trace("encryptionNoiseDistribution - address: {}, encryptionNoiseDistribution: {}", address, encryptionNoiseDistribution);
    ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(address, encryptionNoiseDistribution);
  }

  public static void messageModulus(MemorySegment address, long messageModulus) {
    logger.trace("messageModulus - address: {}, messageModulus: {}", address, messageModulus);
    ShortintCompactPublicKeyEncryptionParameters.message_modulus(address, messageModulus);
  }

  public static void carryModulus(MemorySegment address, long carryModulus) {
    logger.trace("carryModulus - address: {}, carryModulus: {}", address, carryModulus);
    ShortintCompactPublicKeyEncryptionParameters.carry_modulus(address, carryModulus);
  }

  public static void modulusPowerOf2Exponent(MemorySegment address, long modulusPowerOf2Exponent) {
    logger.trace("modulusPowerOf2Exponent - address: {}, modulusPowerOf2Exponent: {}", address, modulusPowerOf2Exponent);
    ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(address, modulusPowerOf2Exponent);
  }

  public static void castingParameters(MemorySegment address, MemorySegment castingParameters) {
    logger.trace("castingParameters - address: {}, castingParameters: {}", address, castingParameters);
    ShortintCompactPublicKeyEncryptionParameters.casting_parameters(address, castingParameters);
  }

  public static void zkScheme(MemorySegment address, int zkScheme) {
    logger.trace("zkScheme - address: {}, zkScheme: {}", address, zkScheme);
    ShortintCompactPublicKeyEncryptionParameters.zk_scheme(address, zkScheme);
  }

  public static long encryptionLweDimension(MemorySegment address) {
    logger.trace("encryptionLweDimension - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(address);
  }

  public static MemorySegment encryptionNoiseDistribution(MemorySegment address) {
    logger.trace("encryptionNoiseDistribution - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(address);
  }

  public static long messageModulus(MemorySegment address) {
    logger.trace("messageModulus - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.message_modulus(address);
  }

  public static long carryModulus(MemorySegment address) {
    logger.trace("carryModulus - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.carry_modulus(address);
  }

  public static long modulusPowerOf2Exponent(MemorySegment address) {
    logger.trace("modulusPowerOf2Exponent - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(address);
  }

  public static MemorySegment castingParameters(MemorySegment address) {
    logger.trace("castingParameters - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.casting_parameters(address);
  }

  public static int zkScheme(MemorySegment address) {
    logger.trace("zkScheme - address: {}", address);
    return ShortintCompactPublicKeyEncryptionParameters.zk_scheme(address);
  }
}
