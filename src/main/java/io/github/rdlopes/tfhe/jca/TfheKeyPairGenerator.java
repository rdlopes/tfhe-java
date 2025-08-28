package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {
  private TfheParameterSpec parameterSpec = new TfheParameterSpec(false);

  @Override
  public void initialize(int keySize, SecureRandom random) {
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
    Config config = new ConfigBuilder().build();

    PublicKey publicKey;
    PrivateKey privateKey;

    if (parameterSpec.serverKey()) {
      KeySet keySet = config.generateKeys();
      privateKey = new TfhePrivateKey(keySet.clientKey()
                                            .safeSerialize());
      publicKey = new TfheServerKey(keySet.serverKey()
                                          .safeSerialize());
    } else {
      ClientKey clientKey = config.generateClientKey();
      privateKey = new TfhePrivateKey(clientKey.safeSerialize());
      publicKey = new TfhePublicKey(clientKey.generatePublicKey()
                                             .safeSerialize());
    }

    return new KeyPair(publicKey, privateKey);
  }
}
