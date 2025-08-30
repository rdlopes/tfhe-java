package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.CompressionParameters;

import java.security.spec.AlgorithmParameterSpec;

public record TfheParameterSpec(
  boolean isServerKey,
  CompressionParameters compressionParameters
) implements AlgorithmParameterSpec {

  public TfheParameterSpec(boolean serverKey) {
    this(serverKey, CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
  }

  public boolean isCompressed() {
    return compressionParameters != null;
  }
}
