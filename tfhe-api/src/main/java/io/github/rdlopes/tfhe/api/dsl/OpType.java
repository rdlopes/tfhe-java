package io.github.rdlopes.tfhe.api.dsl;

/// Enumeration of arithmetic, bitwise, and comparison operations supported in homomorphic ASTs.
public enum OpType {
  ADD(true, 0),
  SUB(false, 0),
  MUL(true, 1),
  DIV(false, 2),
  REM(false, 2),
  BIT_AND(true, 1),
  BIT_OR(true, 1),
  BIT_XOR(true, 1),
  SHL(false, 1),
  SHR(false, 1),
  ROTL(false, 1),
  ROTR(false, 1),
  MIN(true, 1),
  MAX(true, 1),
  NEGATE(false, 0),
  BIT_NOT(false, 0);

  private final boolean associative;
  private final int basePbsCost;

  OpType(boolean associative, int basePbsCost) {
    this.associative = associative;
    this.basePbsCost = basePbsCost;
  }

  /// Returns whether this operator is associative (e.g. `(a + b) + c == a + (b + c)`).
  public boolean isAssociative() {
    return associative;
  }

  /// Returns estimated relative PBS (programmable bootstrapping) cost unit.
  public int basePbsCost() {
    return basePbsCost;
  }
}
