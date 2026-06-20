package io.github.rdlopes.tfhe.examples.perceptron.server;

import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedDataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.TrainedWeights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/// Verifies that the trained perceptron correctly classifies all training points
/// using the cleartext (decrypted) weights.
public final class ConvergenceVerifier {

  private static final Logger logger = LoggerFactory.getLogger(ConvergenceVerifier.class);

  /// Returns {@code true} if the trained model correctly classifies all points.
  ///
  /// @param dataset the encrypted dataset (carries cleartext raw values for verification)
  /// @param weights the decrypted trained weights
  /// @return whether the model correctly classifies all training points
  public boolean verify(List<EncryptedDataPoint> dataset, TrainedWeights weights) {
    boolean allCorrect = true;
    for (int i = 0; i < dataset.size(); i++) {
      EncryptedDataPoint point = dataset.get(i);
      int score = weights.w1() * point.rawX1() + weights.w2() * point.rawX2() + weights.bias();
      int predicted = score >= 0 ? 1 : 0;
      boolean correct = predicted == point.rawLabel();
      logger.info(
          "  Point {}: ({}, {}) | Label={} | Score={} | Predicted={} | {}",
          i + 1, point.rawX1(), point.rawX2(), point.rawLabel(), score, predicted,
          correct ? "SUCCESS" : "FAILED"
      );
      if (!correct) {
        allCorrect = false;
      }
    }
    return allCorrect;
  }
}
