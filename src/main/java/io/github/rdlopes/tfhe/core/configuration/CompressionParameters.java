package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import java.lang.foreign.MemorySegment;

public class CompressionParameters extends GroupLayoutPointer {

  public static final CompressionParameters SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(TfheWrapper.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 = new CompressionParameters(TfheWrapper.SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64());
  public static final CompressionParameters SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(TfheWrapper.SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(TfheWrapper.SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(TfheWrapper.SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(TfheWrapper.SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public CompressionParameters(MemorySegment address) {
    super(address, TfheWrapper.CompressionParameters.layout());
  }

  public CompressionParameters(
    long brLevel,
    long brBaseLog,
    long packingKsLevel,
    long packingKsBaseLog,
    long packingKsPolynomialSize,
    long packingKsGlweDimension,
    long lwePerGlwe,
    long storageLogModulus,
    DynamicDistribution packingKsKeyNoiseDistribution
  ) {
    super(TfheWrapper.CompressionParameters.layout());
    setBrLevel(brLevel);
    setBrBaseLog(brBaseLog);
    setPackingKsLevel(packingKsLevel);
    setPackingKsBaseLog(packingKsBaseLog);
    setPackingKsPolynomialSize(packingKsPolynomialSize);
    setPackingKsGlweDimension(packingKsGlweDimension);
    setLwePerGlwe(lwePerGlwe);
    setStorageLogModulus(storageLogModulus);
    setPackingKsKeyNoiseDistribution(packingKsKeyNoiseDistribution);
  }

  public long getBrLevel() {
    return TfheWrapper.CompressionParameters.br_level(getAddress());
  }

  public void setBrLevel(long brLevel) {
    TfheWrapper.CompressionParameters.br_level(getAddress(), brLevel);
  }

  public long getBrBaseLog() {
    return TfheWrapper.CompressionParameters.br_base_log(getAddress());
  }

  public void setBrBaseLog(long brBaseLog) {
    TfheWrapper.CompressionParameters.br_base_log(getAddress(), brBaseLog);
  }

  public long getPackingKsLevel() {
    return TfheWrapper.CompressionParameters.packing_ks_level(getAddress());
  }

  public void setPackingKsLevel(long packingKsLevel) {
    TfheWrapper.CompressionParameters.packing_ks_level(getAddress(), packingKsLevel);
  }

  public long getPackingKsBaseLog() {
    return TfheWrapper.CompressionParameters.packing_ks_base_log(getAddress());
  }

  public void setPackingKsBaseLog(long packingKsBaseLog) {
    TfheWrapper.CompressionParameters.packing_ks_base_log(getAddress(), packingKsBaseLog);
  }

  public long getPackingKsPolynomialSize() {
    return TfheWrapper.CompressionParameters.packing_ks_polynomial_size(getAddress());
  }

  public void setPackingKsPolynomialSize(long packingKsPolynomialSize) {
    TfheWrapper.CompressionParameters.packing_ks_polynomial_size(getAddress(), packingKsPolynomialSize);
  }

  public long getPackingKsGlweDimension() {
    return TfheWrapper.CompressionParameters.packing_ks_glwe_dimension(getAddress());
  }

  public void setPackingKsGlweDimension(long packingKsGlweDimension) {
    TfheWrapper.CompressionParameters.packing_ks_glwe_dimension(getAddress(), packingKsGlweDimension);
  }

  public long getLwePerGlwe() {
    return TfheWrapper.CompressionParameters.lwe_per_glwe(getAddress());
  }

  public void setLwePerGlwe(long lwePerGlwe) {
    TfheWrapper.CompressionParameters.lwe_per_glwe(getAddress(), lwePerGlwe);
  }

  public long getStorageLogModulus() {
    return TfheWrapper.CompressionParameters.storage_log_modulus(getAddress());
  }

  public void setStorageLogModulus(long storageLogModulus) {
    TfheWrapper.CompressionParameters.storage_log_modulus(getAddress(), storageLogModulus);
  }

  public DynamicDistribution getPackingKsKeyNoiseDistribution() {
    return new DynamicDistribution(TfheWrapper.CompressionParameters.packing_ks_key_noise_distribution(getAddress()));
  }

  public void setPackingKsKeyNoiseDistribution(DynamicDistribution packingKsKeyNoiseDistribution) {
    TfheWrapper.CompressionParameters.packing_ks_key_noise_distribution(getAddress(), packingKsKeyNoiseDistribution.getAddress());
  }

}
