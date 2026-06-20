package io.github.rdlopes.tfhe.examples.perceptron.model;

/// A 2D training data point with a binary classification label.
///
/// @param x1 first coordinate
/// @param x2 second coordinate
/// @param label binary classification label (0 or 1)
public record DataPoint(int x1, int x2, int label) {}
