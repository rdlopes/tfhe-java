package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.CompactPublicKeyParameters.*;
import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateShortintCompactPublicKeyEncryptionParameters;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.*;

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
    this(allocateShortintCompactPublicKeyEncryptionParameters());

    shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension(pointer, (int) encryptionLweDimension);
    shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution(pointer, encryptionNoiseDistribution.pointer());
    shortintCompactPublicKeyEncryptionParametersMessageModulus(pointer, (int) messageModulus);
    shortintCompactPublicKeyEncryptionParametersCarryModulus(pointer, (int) carryModulus);
    shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent(pointer, (int) modulusPowerOf2Exponent);
    shortintCompactPublicKeyEncryptionParametersCastingParameters(pointer, castingParameters.pointer());
    shortintCompactPublicKeyEncryptionParametersZkScheme(pointer, zkScheme);
  }

  public long encryptionLweDimension() {
    return shortintCompactPublicKeyEncryptionParametersEncryptionLweDimension(pointer);
  }

  public DynamicDistribution encryptionNoiseDistribution() {
    return new DynamicDistribution(shortintCompactPublicKeyEncryptionParametersEncryptionNoiseDistribution(pointer));
  }

  public long messageModulus() {
    return shortintCompactPublicKeyEncryptionParametersMessageModulus(pointer);
  }

  public long carryModulus() {
    return shortintCompactPublicKeyEncryptionParametersCarryModulus(pointer);
  }

  public long modulusPowerOf2Exponent() {
    return shortintCompactPublicKeyEncryptionParametersModulusPowerOf2Exponent(pointer);
  }

  public ShortintPBSParameters castingParameters() {
    return new ShortintPBSParameters(shortintCompactPublicKeyEncryptionParametersCastingParameters(pointer));
  }

  public int zkScheme() {
    return shortintCompactPublicKeyEncryptionParametersZkScheme(pointer);
  }
}
