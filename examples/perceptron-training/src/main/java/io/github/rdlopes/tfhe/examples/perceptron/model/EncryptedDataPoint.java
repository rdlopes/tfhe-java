package io.github.rdlopes.tfhe.examples.perceptron.model;

import io.github.rdlopes.tfhe.api.types.extended.FheInt32;

/// A training data point whose coordinates and label are encrypted under FHE.
/// Holds references to the original cleartext values for post-training verification.
/// Implements {@link AutoCloseable} to release native memory.
public final class EncryptedDataPoint implements AutoCloseable {

  private final FheInt32 x1;
  private final FheInt32 x2;
  private final FheInt32 label;
  private final int rawX1;
  private final int rawX2;
  private final int rawLabel;

  /// Creates an encrypted data point from pre-encrypted FHE values and original cleartext.
  ///
  /// @param x1 encrypted first coordinate
  /// @param x2 encrypted second coordinate
  /// @param label encrypted label
  /// @param rawX1 cleartext first coordinate
  /// @param rawX2 cleartext second coordinate
  /// @param rawLabel cleartext label
  public EncryptedDataPoint(
      FheInt32 x1, FheInt32 x2, FheInt32 label,
      int rawX1, int rawX2, int rawLabel
  ) {
    this.x1 = x1;
    this.x2 = x2;
    this.label = label;
    this.rawX1 = rawX1;
    this.rawX2 = rawX2;
    this.rawLabel = rawLabel;
  }

  /// Returns the encrypted first coordinate.
  public FheInt32 x1() { return x1; }
  /// Returns the encrypted second coordinate.
  public FheInt32 x2() { return x2; }
  /// Returns the encrypted label.
  public FheInt32 label() { return label; }
  /// Returns the cleartext first coordinate.
  public int rawX1() { return rawX1; }
  /// Returns the cleartext second coordinate.
  public int rawX2() { return rawX2; }
  /// Returns the cleartext label.
  public int rawLabel() { return rawLabel; }

  @Override
  public void close() {
    x1.close();
    x2.close();
    label.close();
  }
}
