package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {

  @Override
  public void initialize(int keySize, SecureRandom random) {
  }

  @Override
  public void initialize(AlgorithmParameterSpec params, SecureRandom random) throws InvalidAlgorithmParameterException {
    super.initialize(params, random);
  }

  @Override
  public KeyPair generateKeyPair() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    DynamicBuffer clientKeyBuffer = keySet.clientKey()
                                          .safeSerialize();
    DynamicBuffer serverKeyBuffer = keySet.serverKey()
                                          .safeSerialize();
    return new KeyPair(new TfhePublicKey(serverKeyBuffer), new TfhePrivateKey(clientKeyBuffer));
  }
}
