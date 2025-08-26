package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.CompressionParametersBindings;
import io.github.rdlopes.tfhe.ffm.TfheHeaderExtension;

import java.lang.foreign.MemorySegment;

public record CompressionParameters(MemorySegment pointer) {

  public static final CompressionParameters SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public static final CompressionParameters SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64());

  public static final CompressionParameters SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public static final CompressionParameters SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public static final CompressionParameters SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  public static final CompressionParameters SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 =
    new CompressionParameters(CompressionParametersBindings.SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

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
    this(CompressionParametersBindings.allocate());

    TfheHeaderExtension.CompressionParameters.br_level(pointer, brLevel);
    TfheHeaderExtension.CompressionParameters.br_base_log(pointer, brBaseLog);
    TfheHeaderExtension.CompressionParameters.packing_ks_level(pointer, packingKsLevel);
    TfheHeaderExtension.CompressionParameters.packing_ks_base_log(pointer, packingKsBaseLog);
    TfheHeaderExtension.CompressionParameters.packing_ks_polynomial_size(pointer, packingKsPolynomialSize);
    TfheHeaderExtension.CompressionParameters.packing_ks_glwe_dimension(pointer, packingKsGlweDimension);
    TfheHeaderExtension.CompressionParameters.lwe_per_glwe(pointer, lwePerGlwe);
    TfheHeaderExtension.CompressionParameters.storage_log_modulus(pointer, storageLogModulus);
    TfheHeaderExtension.CompressionParameters.packing_ks_key_noise_distribution(pointer, packingKsKeyNoiseDistribution.pointer());
  }

  public long brLevel() {
    return TfheHeaderExtension.CompressionParameters.br_level(pointer);
  }

  public long brBaseLog() {
    return TfheHeaderExtension.CompressionParameters.br_base_log(pointer);
  }

  public long packingKsLevel() {
    return TfheHeaderExtension.CompressionParameters.packing_ks_level(pointer);
  }

  public long packingKsBaseLog() {
    return TfheHeaderExtension.CompressionParameters.packing_ks_base_log(pointer);
  }

  public long packingKsPolynomialSize() {
    return TfheHeaderExtension.CompressionParameters.packing_ks_polynomial_size(pointer);
  }

  public long packingKsGlweDimension() {
    return TfheHeaderExtension.CompressionParameters.packing_ks_glwe_dimension(pointer);
  }

  public long lwePerGlwe() {
    return TfheHeaderExtension.CompressionParameters.lwe_per_glwe(pointer);
  }

  public long storageLogModulus() {
    return TfheHeaderExtension.CompressionParameters.storage_log_modulus(pointer);
  }

  public DynamicDistribution packingKsKeyNoiseDistribution() {
    return new DynamicDistribution(TfheHeaderExtension.CompressionParameters.packing_ks_key_noise_distribution(pointer));
  }

}
