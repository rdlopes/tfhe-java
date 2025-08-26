package io.github.rdlopes.tfhe.jca;

import java.security.Provider;

/**
 * Security Provider for TFHE (Torus Fully Homomorphic Encryption) cryptographic operations.
 * This provider registers cryptographic services for the TFHE encryption scheme.
 */
public final class TfheProvider extends Provider {

  private static final String PROVIDER_NAME = "TFHE";
  private static final String PROVIDER_VERSION = "1.0";
  private static final String PROVIDER_INFO = "TFHE Provider - Torus Fully Homomorphic Encryption";

  /**
   * Creates a new TfheProvider instance.
   */
  public TfheProvider() {
    super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
    registerServices();
  }

  /**
   * Registers all cryptographic services provided by this provider.
   */
  private void registerServices() {
    // Register KeyPairGenerator service for TFHE algorithm
    putService(new Service(this, "KeyPairGenerator", "TFHE",
      "io.github.rdlopes.tfhe.jca.TfheKeyPairGenerator", null, null));
  }
}
