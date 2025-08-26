package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.ffm.ConfigBindings;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;

import java.lang.foreign.MemorySegment;
import java.security.KeyPair;
import java.util.concurrent.atomic.AtomicBoolean;

public record Config(MemorySegment address, AtomicBoolean keysGenerated) {

  Config() {
    this(ConfigBindings.allocate(), new AtomicBoolean(false));
  }

  public KeyPair generateKeys() {
    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }

    ClientKey clientKey = new ClientKey();
    ServerKey serverKey = new ServerKey();

    ConfigBindings.generateKeys(address, clientKey.address(), serverKey.address());

    keysGenerated.setRelease(true);
    return new KeyPair(new TfhePublicKey(serverKey), new TfhePrivateKey(clientKey));
  }
}
