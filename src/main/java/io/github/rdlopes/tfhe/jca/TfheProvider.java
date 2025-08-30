package io.github.rdlopes.tfhe.jca;

import java.security.Provider;

public final class TfheProvider extends Provider {
  public static final String TFHE_ALGORITHM = "TFHE";

  private static final String PROVIDER_VERSION = "1.0";
  private static final String PROVIDER_INFO = "TFHE Provider - Torus Fully Homomorphic Encryption";

  public TfheProvider() {
    super(TFHE_ALGORITHM, PROVIDER_VERSION, PROVIDER_INFO);
    registerServices();
  }

  private void registerServices() {
    putService(new Service(this, "KeyPairGenerator", TFHE_ALGORITHM,
      TfheKeyPairGenerator.class.getName(), null, null));
  }
}
