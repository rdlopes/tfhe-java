package io.github.rdlopes.tfhe.jca;

import java.security.PrivateKey;

public final class TfhePrivateKey extends TfheKey implements PrivateKey {

  public TfhePrivateKey(byte[] encoded) {
    super(encoded);
  }
}
