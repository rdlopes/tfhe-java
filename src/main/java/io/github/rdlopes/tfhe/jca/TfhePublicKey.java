package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.keys.ServerKey;

import java.security.PublicKey;

public record TfhePublicKey(ServerKey serverKey) implements PublicKey {
  public TfhePublicKey {
    if (serverKey == null) {
      throw new IllegalArgumentException("ServerKey cannot be null");
    }
  }

  @Override
  public ServerKey serverKey() {
    return serverKey;
  }

  @Override
  public String getAlgorithm() {
    return "TFHE";
  }

  @Override
  public String getFormat() {
    return null;
  }

  @Override
  public byte[] getEncoded() {
    return null;
  }
}
