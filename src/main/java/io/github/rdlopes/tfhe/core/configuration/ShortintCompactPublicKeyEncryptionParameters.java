package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParametersBindings;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.CompactPublicKeyParametersBindings.*;

public record ShortintCompactPublicKeyEncryptionParameters(MemorySegment address) {

  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public ShortintCompactPublicKeyEncryptionParameters(
    long encryptionLweDimension,
    DynamicDistribution encryptionNoiseDistribution,
    long messageModulus,
    long carryModulus,
    long modulusPowerOf2Exponent,
    ShortintPBSParameters castingParameters,
    int zkScheme
  ) {
    this(ShortintCompactPublicKeyEncryptionParametersBindings.allocate());

    ShortintCompactPublicKeyEncryptionParametersBindings.encryptionLweDimension(address, (int) encryptionLweDimension);
    ShortintCompactPublicKeyEncryptionParametersBindings.encryptionNoiseDistribution(address, encryptionNoiseDistribution.address());
    ShortintCompactPublicKeyEncryptionParametersBindings.messageModulus(address, (int) messageModulus);
    ShortintCompactPublicKeyEncryptionParametersBindings.carryModulus(address, (int) carryModulus);
    ShortintCompactPublicKeyEncryptionParametersBindings.modulusPowerOf2Exponent(address, (int) modulusPowerOf2Exponent);
    ShortintCompactPublicKeyEncryptionParametersBindings.castingParameters(address, castingParameters.address());
    ShortintCompactPublicKeyEncryptionParametersBindings.zkScheme(address, zkScheme);
  }

  public long encryptionLweDimension() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.encryptionLweDimension(address);
  }

  public DynamicDistribution encryptionNoiseDistribution() {
    return new DynamicDistribution(ShortintCompactPublicKeyEncryptionParametersBindings.encryptionNoiseDistribution(address));
  }

  public long messageModulus() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.messageModulus(address);
  }

  public long carryModulus() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.carryModulus(address);
  }

  public long modulusPowerOf2Exponent() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.modulusPowerOf2Exponent(address);
  }

  public ShortintPBSParameters castingParameters() {
    return new ShortintPBSParameters(ShortintCompactPublicKeyEncryptionParametersBindings.castingParameters(address));
  }

  public int zkScheme() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.zkScheme(address);
  }
}
