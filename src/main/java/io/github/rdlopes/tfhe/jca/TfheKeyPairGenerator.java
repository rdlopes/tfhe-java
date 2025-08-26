package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;

import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;

public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {

  @Override
  public void initialize(int keySize, SecureRandom random) {
  }

  @Override
  public KeyPair generateKeyPair() {
    Config config = new ConfigBuilder().build();
    return config.generateKeys();
  }
}
