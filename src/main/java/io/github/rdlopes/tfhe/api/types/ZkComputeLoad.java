package io.github.rdlopes.tfhe.api.types;

public enum ZkComputeLoad {
  PROOF(0),
  VERIFY(1);

  private final int value;

  ZkComputeLoad(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
