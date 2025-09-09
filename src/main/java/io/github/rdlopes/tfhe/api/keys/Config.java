package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.client_key_generate;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.generate_keys;

public class Config extends NativeValue {
  private final AtomicBoolean keysGenerated = new AtomicBoolean(false);

  Config() {
    //super(TfheHeader::config_destroy);
    super(null);
  }

  public KeySet generateKeys() {
    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }
    ClientKey clientKey = new ClientKey();
    ServerKey serverKey = new ServerKey();
    execute(() -> generate_keys(getValue(), clientKey.getAddress(), serverKey.getAddress()));
    keysGenerated.setRelease(true);
    return new KeySet(clientKey, serverKey);
  }

  public ClientKey generateClientKey() {
    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }
    ClientKey clientKey = new ClientKey();
    execute(() -> client_key_generate(getValue(), clientKey.getAddress()));
    keysGenerated.setRelease(true);
    return clientKey;
  }

}
