package io.github.rdlopes.tfhe.jce;

import io.github.rdlopes.tfhe.jce.spi.TfheKeyPairGenerator;

import java.io.Serial;
import java.security.Provider;
import java.util.List;
import java.util.Map;

public final class TfheProvider extends Provider {

  public static final String ALGORITHM = "TFHE";
  public static final String ALGORITHM_INFO = "Torus Fully Homomorphic Encryption";
  public static final String VERSION = "1.0";

  @Serial
  private static final long serialVersionUID = 1L;

  public TfheProvider() {
    super(ALGORITHM, VERSION, "%s v%s - %s".formatted(ALGORITHM, VERSION, ALGORITHM_INFO));
    putService(new Service("KeyPairGenerator", TfheKeyPairGenerator.class));
  }

  private class Service extends Provider.Service {

    /**
     * Construct a new service.
     *
     * @param type         the type of this service
     * @param serviceClass the class implementing this service
     * @throws NullPointerException if provider, type, algorithm, or className is {@code null}
     */
    public Service(String type, Class<?> serviceClass) {
      super(TfheProvider.this, type, ALGORITHM, serviceClass.getName(), List.of(), Map.of());
    }
  }

}
