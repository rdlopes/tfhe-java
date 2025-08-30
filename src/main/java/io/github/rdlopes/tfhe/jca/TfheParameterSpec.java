package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.CompressionParameters;
import io.github.rdlopes.tfhe.core.configuration.ShortintCompactPublicKeyEncryptionParameters;
import io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters;

import java.security.spec.AlgorithmParameterSpec;

public record TfheParameterSpec(
  ShortintPBSParameters encryption,
  CompressionParameters compression,
  ShortintCompactPublicKeyEncryptionParameters compactPublicKeyEncryption
) implements AlgorithmParameterSpec {

  public static TfheParameterSpec defaultSpec() {
    return new TfheParameterSpec(null, null, null);
  }
}
