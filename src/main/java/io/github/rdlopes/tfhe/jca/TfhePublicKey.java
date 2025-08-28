package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.PublicKey;

public class TfhePublicKey extends TfheKey implements PublicKey {

  public TfhePublicKey(DynamicBuffer keyBuffer) {
    super(keyBuffer);
  }
}
