package io.github.rdlopes.tfhe.jca;

import java.security.PublicKey;

public class TfhePublicKey extends TfheKey implements PublicKey {
  private final boolean compressed;
  private final boolean serverKey;

  public TfhePublicKey(byte[] encoded, boolean compressed, boolean serverKey) {
    super(encoded);
    this.compressed = compressed;
    this.serverKey = serverKey;
  }

  public boolean isCompressed() {
    return compressed;
  }

  public boolean isServerKey() {
    return serverKey;
  }
}
