package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.client_key_generate;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.generate_keys;

public class Config extends AddressLayoutPointer {
  private final AtomicBoolean keysGenerated = new AtomicBoolean(false);

  public Config() {
    //super(Config.class, TfheWrapper::config_destroy);
    super();
  }

  public KeySet generateKeys() {
    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }

    ClientKey clientKey = new ClientKey();
    ServerKey serverKey = new ServerKey();

    executeWithErrorHandling(() -> generate_keys(getValue(), clientKey.getAddress(), serverKey.getAddress()));

    keysGenerated.setRelease(true);
    return new KeySet(clientKey, serverKey);
  }

  public ClientKey generateClientKey() {
    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }

    ClientKey clientKey = new ClientKey();
    executeWithErrorHandling(() -> client_key_generate(getValue(), clientKey.getAddress()));

    keysGenerated.setRelease(true);
    return clientKey;
  }
}
