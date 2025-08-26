package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.keys.ClientKey;

import java.security.PrivateKey;

public record TfhePrivateKey(ClientKey clientKey) implements PrivateKey {
  public TfhePrivateKey {
    if (clientKey == null) {
      throw new IllegalArgumentException("ClientKey cannot be null");
    }
  }

  @Override
  public ClientKey clientKey() {
    return clientKey;
  }

  @Override
  public String getAlgorithm() {
    return "TFHE";
  }

  @Override
  public String getFormat() {
    return "RAW";
  }

  @Override
  public byte[] getEncoded() {
    return clientKey.serialize()
                    .view()
                    .toByteArray();
  }
}
