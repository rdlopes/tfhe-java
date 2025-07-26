package io.github.rdlopes.tfhe.jce.spi;

import java.security.PrivateKey;

public class TfhePrivateKey extends TfheKey implements PrivateKey {

  public TfhePrivateKey(byte[] clientKeyEncoded) {
    super(clientKeyEncoded, false);
  }

}
