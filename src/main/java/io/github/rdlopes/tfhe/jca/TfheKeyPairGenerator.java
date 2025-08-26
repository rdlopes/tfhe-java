package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;

import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;

/**
 * KeyPairGenerator implementation for TFHE (Torus Fully Homomorphic Encryption) algorithm.
 * This class generates TFHE key pairs consisting of a client key (private) and server key (public).
 */
public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {

  /**
   * Initializes the key pair generator for a given key size.
   *
   * @param keysize the key size (ignored for TFHE as it uses predetermined parameters)
   * @param random  the source of randomness (ignored for TFHE as it uses internal randomness)
   */
  @Override
  public void initialize(int keysize, SecureRandom random) {
    // TFHE uses predetermined configuration parameters, so keysize and random are not used
    // This method is required by the KeyPairGeneratorSpi interface but no initialization is needed
  }

  /**
   * Generates a new TFHE key pair.
   *
   * @return a KeyPair containing a TfhePublicKey (server key) and TfhePrivateKey (client key)
   */
  @Override
  public KeyPair generateKeyPair() {
    Config config = new ConfigBuilder().build();
    return config.generateKeys();
  }
}
