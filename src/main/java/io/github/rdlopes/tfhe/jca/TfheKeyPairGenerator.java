package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
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
    KeyPair keyPair;

    if (parameterSpec.serverKey()) {
      KeySet keySet = config.generateKeys();
      ClientKey clientKey = keySet.clientKey();
      ServerKey serverKey = keySet.serverKey();
      keyPair = new KeyPair(
        new TfheServerKey(serverKey.safeSerialize()),
        new TfhePrivateKey(clientKey.safeSerialize())
      );
      clientKey.destroy();
      serverKey.destroy();

    } else {
      ClientKey clientKey = config.generateClientKey();
      PublicKey publicKey = clientKey.generatePublicKey();
      keyPair = new KeyPair(
        new TfhePublicKey(publicKey.safeSerialize()),
        new TfhePrivateKey(clientKey.safeSerialize())
      );
      clientKey.destroy();
      publicKey.destroy();
    }

    return keyPair;
  }
}
