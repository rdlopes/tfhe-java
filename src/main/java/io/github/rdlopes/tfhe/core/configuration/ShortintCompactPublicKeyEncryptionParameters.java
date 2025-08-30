package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ShortintCompactPublicKeyEncryptionParameters extends GroupLayoutPointer {

  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final ShortintCompactPublicKeyEncryptionParameters SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new ShortintCompactPublicKeyEncryptionParameters(SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public ShortintCompactPublicKeyEncryptionParameters(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.layout());
  }

  public ShortintCompactPublicKeyEncryptionParameters(
    long encryptionLweDimension,
    DynamicDistribution encryptionNoiseDistribution,
    long messageModulus,
    long carryModulus,
    long modulusPowerOf2Exponent,
    ShortintPBSParameters castingParameters,
    int zkScheme
  ) {
    super(io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.layout());
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(getAddress(), (int) encryptionLweDimension);
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(getAddress(), encryptionNoiseDistribution.getAddress());
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.message_modulus(getAddress(), (int) messageModulus);
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.carry_modulus(getAddress(), (int) carryModulus);
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(getAddress(), (int) modulusPowerOf2Exponent);
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.casting_parameters(getAddress(), castingParameters.getAddress());
    io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.zk_scheme(getAddress(), zkScheme);
  }

  public long getEncryptionLweDimension() {
    return io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.encryption_lwe_dimension(getAddress());
  }

  public DynamicDistribution getEncryptionNoiseDistribution() {
    return new DynamicDistribution(io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.encryption_noise_distribution(getAddress()));
  }

  public long getMessageModulus() {
    return io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.message_modulus(getAddress());
  }

  public long getCarryModulus() {
    return io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.carry_modulus(getAddress());
  }

  public long getModulusPowerOf2Exponent() {
    return io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.modulus_power_of_2_exponent(getAddress());
  }

  public ShortintPBSParameters getCastingParameters() {
    return new ShortintPBSParameters(io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.casting_parameters(getAddress()));
  }

  public int getZkScheme() {
    return io.github.rdlopes.tfhe.ffm.ShortintCompactPublicKeyEncryptionParameters.zk_scheme(getAddress());
  }
}
