package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParametersBindings;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.CompactPublicKeyParametersBindings.*;

public record ShortintCompactPublicKeyEncryptionParameters(MemorySegment pointer) {

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

    ShortintCompactPublicKeyEncryptionParametersBindings.encryptionLweDimension(pointer, (int) encryptionLweDimension);
    ShortintCompactPublicKeyEncryptionParametersBindings.encryptionNoiseDistribution(pointer, encryptionNoiseDistribution.pointer());
    ShortintCompactPublicKeyEncryptionParametersBindings.messageModulus(pointer, (int) messageModulus);
    ShortintCompactPublicKeyEncryptionParametersBindings.carryModulus(pointer, (int) carryModulus);
    ShortintCompactPublicKeyEncryptionParametersBindings.modulusPowerOf2Exponent(pointer, (int) modulusPowerOf2Exponent);
    ShortintCompactPublicKeyEncryptionParametersBindings.castingParameters(pointer, castingParameters.pointer());
    ShortintCompactPublicKeyEncryptionParametersBindings.zkScheme(pointer, zkScheme);
  }

  public long encryptionLweDimension() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.encryptionLweDimension(pointer);
  }

  public DynamicDistribution encryptionNoiseDistribution() {
    return new DynamicDistribution(ShortintCompactPublicKeyEncryptionParametersBindings.encryptionNoiseDistribution(pointer));
  }

  public long messageModulus() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.messageModulus(pointer);
  }

  public long carryModulus() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.carryModulus(pointer);
  }

  public long modulusPowerOf2Exponent() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.modulusPowerOf2Exponent(pointer);
  }

  public ShortintPBSParameters castingParameters() {
    return new ShortintPBSParameters(ShortintCompactPublicKeyEncryptionParametersBindings.castingParameters(pointer));
  }

  public int zkScheme() {
    return ShortintCompactPublicKeyEncryptionParametersBindings.zkScheme(pointer);
  }
}
