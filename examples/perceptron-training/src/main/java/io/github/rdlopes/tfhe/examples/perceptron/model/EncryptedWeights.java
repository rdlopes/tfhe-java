package io.github.rdlopes.tfhe.examples.perceptron.model;

import io.github.rdlopes.tfhe.api.types.extended.FheInt32;

/// Holds the encrypted weight and bias parameters of the single-layer perceptron.
/// These are mutated in-place during the homomorphic training loop.
/// Implements {@link AutoCloseable} to release native memory.
public final class EncryptedWeights implements AutoCloseable {

  private final FheInt32 w1;
  private final FheInt32 w2;
  private final FheInt32 bias;

  /// Creates encrypted weights from pre-encrypted FHE values.
  ///
  /// @param w1 encrypted weight for the first coordinate
  /// @param w2 encrypted weight for the second coordinate
  /// @param bias encrypted bias term
  public EncryptedWeights(FheInt32 w1, FheInt32 w2, FheInt32 bias) {
    this.w1 = w1;
    this.w2 = w2;
    this.bias = bias;
  }

  /// Returns the encrypted weight for the first coordinate.
  public FheInt32 w1() { return w1; }
  /// Returns the encrypted weight for the second coordinate.
  public FheInt32 w2() { return w2; }
  /// Returns the encrypted bias.
  public FheInt32 bias() { return bias; }

  @Override
  public void close() {
    w1.close();
    w2.close();
    bias.close();
  }
}
