package io.github.rdlopes.tfhe.jca;

import java.security.Provider;

public final class TfheProvider extends Provider {

  private static final String PROVIDER_NAME = "TFHE";
  private static final String PROVIDER_VERSION = "1.0";
  private static final String PROVIDER_INFO = "TFHE Provider - Torus Fully Homomorphic Encryption";

  public TfheProvider() {
    super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
    registerServices();
  }

  private void registerServices() {
    putService(new Service(this, "KeyPairGenerator", "TFHE",
      "io.github.rdlopes.tfhe.jca.TfheKeyPairGenerator", null, null));
  }
}
