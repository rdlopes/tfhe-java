package io.github.rdlopes.tfhe.jca;

import java.security.Key;

public class TfheKey implements Key {
  private final byte[] encoded;

  public TfheKey(byte[] encoded) {
    this.encoded = encoded;
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
    return encoded;
  }

}
