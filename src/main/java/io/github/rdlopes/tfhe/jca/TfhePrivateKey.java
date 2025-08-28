package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.PrivateKey;

public final class TfhePrivateKey extends TfheKey implements PrivateKey {

  public TfhePrivateKey(DynamicBuffer keyBuffer) {
    super(keyBuffer);
  }
}
