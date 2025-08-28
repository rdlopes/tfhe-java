package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.Key;

public class TfheKey implements Key {
  protected final DynamicBuffer keyBuffer;

  public TfheKey(DynamicBuffer keyBuffer) {
    this.keyBuffer = keyBuffer;
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
    return keyBuffer.view()
                    .toByteArray();
  }
}
