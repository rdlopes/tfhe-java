package io.github.rdlopes.tfhe.api.keys;

public record KeySet(ClientKey clientKey, ServerKey serverKey) {

  public void destroy() {
    clientKey.destroy();
    serverKey.destroy();
  }
}
