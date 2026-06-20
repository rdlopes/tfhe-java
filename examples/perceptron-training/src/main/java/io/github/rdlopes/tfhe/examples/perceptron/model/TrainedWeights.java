package io.github.rdlopes.tfhe.examples.perceptron.model;

/// Holds the cleartext weights and bias after decryption, ready for verification.
///
/// @param w1 trained weight for the first coordinate
/// @param w2 trained weight for the second coordinate
/// @param bias trained bias
public record TrainedWeights(int w1, int w2, int bias) {}
