package io.github.rdlopes.tfhe.jce.spi;

import java.security.PublicKey;

public class TfhePublicKey extends TfheKey implements PublicKey {
  private final byte[] serverKeyEncoded;

  public TfhePublicKey(byte[] classicalPublicKeyEncoded, byte[] serverKeyEncoded) {
    super(classicalPublicKeyEncoded, true);
    this.serverKeyEncoded = serverKeyEncoded;
  }

  public byte[] getServerKeyEncoded() {
    return serverKeyEncoded;
  }
}
