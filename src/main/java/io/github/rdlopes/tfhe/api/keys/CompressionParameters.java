package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeaderExtension;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeaderExtension.*;

public class CompressionParameters extends NativePointer {

  public static final CompressionParameters SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 = new CompressionParameters(SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64());
  public static final CompressionParameters SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());
  public static final CompressionParameters SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 = new CompressionParameters(SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  private CompressionParameters(MemorySegment address) {
    super(address, null);
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
    super(TfheHeaderExtension.CompressionParameters::allocate);
    TfheHeaderExtension.CompressionParameters.br_level(getAddress(), brLevel);
    TfheHeaderExtension.CompressionParameters.br_base_log(getAddress(), brBaseLog);
    TfheHeaderExtension.CompressionParameters.packing_ks_level(getAddress(), packingKsLevel);
    TfheHeaderExtension.CompressionParameters.packing_ks_base_log(getAddress(), packingKsBaseLog);
    TfheHeaderExtension.CompressionParameters.packing_ks_polynomial_size(getAddress(), packingKsPolynomialSize);
    TfheHeaderExtension.CompressionParameters.packing_ks_glwe_dimension(getAddress(), packingKsGlweDimension);
    TfheHeaderExtension.CompressionParameters.lwe_per_glwe(getAddress(), lwePerGlwe);
    TfheHeaderExtension.CompressionParameters.storage_log_modulus(getAddress(), storageLogModulus);
    TfheHeaderExtension.CompressionParameters.packing_ks_key_noise_distribution(getAddress(), packingKsKeyNoiseDistribution.getAddress());
  }
}
