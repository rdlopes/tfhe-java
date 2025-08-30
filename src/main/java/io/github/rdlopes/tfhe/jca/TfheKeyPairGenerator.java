package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedCompactPublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {
  private TfheParameterSpec parameterSpec = TfheParameterSpec.defaultSpec();

  @Override
  public void initialize(int keySize, SecureRandom random) {
    throw new InvalidParameterException("Only TfheParameterSpec is supported");
  }

  @Override
  public void initialize(AlgorithmParameterSpec params, SecureRandom random) throws InvalidAlgorithmParameterException {
    if (Objects.requireNonNull(params) instanceof TfheParameterSpec paramsSpec) {
      this.parameterSpec = paramsSpec;
    } else {
      throw new InvalidAlgorithmParameterException("Only TfheParameterSpec is supported");
    }
  }

  @Override
  public KeyPair generateKeyPair() {
    Config config = buildConfiguration();

    ClientKey clientKey = config.generateClientKey();
    DynamicBufferView clientKeyBuffer = clientKey.serialize();
    byte[] clientKeyBytes = clientKeyBuffer.toByteArray();
    TfhePrivateKey tfhePrivateKey = new TfhePrivateKey(clientKeyBytes);

    CompressedCompactPublicKey compressedCompactPublicKey = clientKey.newCompressedCompactPublicKey();
    DynamicBufferView compressedCompactPublicKeyBuffer = compressedCompactPublicKey.serialize();
    byte[] compressedCompactPublicKeyBytes = compressedCompactPublicKeyBuffer.toByteArray();
    TfhePublicKey tfhePublicKey = new TfhePublicKey(compressedCompactPublicKeyBytes);

    return new KeyPair(tfhePublicKey, tfhePrivateKey);
  }

  private Config buildConfiguration() {
    ConfigBuilder configBuilder = new ConfigBuilder();

    if (parameterSpec.encryption() != null) {
      configBuilder.useCustomParameters(parameterSpec.encryption());
    }
    if (parameterSpec.compression() != null) {
      configBuilder.enableCompression(parameterSpec.compression());
    }
    if (parameterSpec.compactPublicKeyEncryption() != null) {
      configBuilder.useDedicatedCompactPublicKeyParameters(parameterSpec.compactPublicKeyEncryption());
    }

    return configBuilder.build();
  }
}
