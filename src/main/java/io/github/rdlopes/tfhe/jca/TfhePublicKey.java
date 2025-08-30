package io.github.rdlopes.tfhe.jca;

import java.security.PublicKey;

public class TfhePublicKey extends TfheKey implements PublicKey {

  public TfhePublicKey(byte[] encoded) {
    super(encoded);
  }
}
