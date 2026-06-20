package io.github.rdlopes.tfhe.examples.perceptron.client;

import io.github.rdlopes.tfhe.examples.perceptron.model.DataPoint;

import java.util.List;

/// Factory providing the sample 2D training dataset for the perceptron showcase.
public final class TrainingDataset {

  private TrainingDataset() {}

  /// Returns the fixed list of 2D data points used for homomorphic perceptron training.
  public static List<DataPoint> load() {
    return List.of(
        new DataPoint(10, 15, 1),
        new DataPoint(-15, -20, 0),
        new DataPoint(20, 45, 0),
        new DataPoint(5, 5, 1),
        new DataPoint(-10, -30, 1),
        new DataPoint(-5, -5, 0),
        new DataPoint(0, 0, 1),
        new DataPoint(30, 70, 0),
        new DataPoint(-25, -45, 0),
        new DataPoint(15, 25, 1)
    );
  }
}
