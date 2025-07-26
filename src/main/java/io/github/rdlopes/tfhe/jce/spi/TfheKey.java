package io.github.rdlopes.tfhe.jce.spi;

import io.github.rdlopes.tfhe.jce.TfheProvider;

import java.security.Key;

public class TfheKey implements Key {

  private final byte[] encodedKey;
  private final String format;

  public TfheKey(byte[] encodedKey, boolean compressed) {
    this.encodedKey = encodedKey;
    this.format = compressed ? "COMPRESSED" : "RAW";
  }

  @Override
  public String getAlgorithm() {
    return TfheProvider.ALGORITHM;
  }

  @Override
  public String getFormat() {
    return format;
  }

  @Override
  public byte[] getEncoded() {
    return encodedKey;
  }
}
