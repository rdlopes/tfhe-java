package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.PublicKey;

public class TfheServerKey extends TfheKey implements PublicKey {

  public TfheServerKey(DynamicBuffer keyBuffer) {
    super(keyBuffer);
  }
}
