package io.github.rdlopes.tfhe.examples.perceptron.server;

import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedDataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedWeights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/// Server-side component that trains a single-layer perceptron entirely on encrypted data
/// using homomorphic operations.
///
/// <p>For each training point and each epoch:
/// <ol>
///   <li>Computes activation: {@code a = w1*x1 + w2*x2 + bias}</li>
///   <li>Predicts: {@code y_hat = (a >= 0) ? 1 : 0}</li>
///   <li>Computes error: {@code e = label - y_hat}</li>
///   <li>Updates weights: {@code w1 += e*x1}, {@code w2 += e*x2}, {@code bias += e}</li>
/// </ol>
public final class PerceptronTrainer {

  private static final Logger logger = LoggerFactory.getLogger(PerceptronTrainer.class);
  private static final int EPOCHS = 8;

  /// Runs the homomorphic training loop, mutating the given encrypted weights in-place.
  ///
  /// @param dataset the encrypted training dataset
  /// @param weights the encrypted weights to train (mutated in-place)
  public void train(List<EncryptedDataPoint> dataset, EncryptedWeights weights) {
    logger.info("[Server] Starting homomorphic training for {} epochs...", EPOCHS);
    long trainStart = System.currentTimeMillis();
    for (int epoch = 0; epoch < EPOCHS; epoch++) {
      long epochStart = System.currentTimeMillis();
      for (EncryptedDataPoint point : dataset) {
        trainOnPoint(point, weights);
      }
      logger.info("[Server] Epoch {} completed in {} ms.", epoch + 1, System.currentTimeMillis() - epochStart);
    }
    logger.info("[Server] Training completed in {} ms.", System.currentTimeMillis() - trainStart);
  }

  private void trainOnPoint(EncryptedDataPoint point, EncryptedWeights weights) {
    // Compute activation: a = w1*x1 + w2*x2 + bias
    FheInt32 term1 = weights.w1().multiply(point.x1());
    FheInt32 term2 = weights.w2().multiply(point.x2());
    FheInt32 sum = term1.add(term2);
    term1.close();
    term2.close();
    FheInt32 activation = sum.add(weights.bias());
    sum.close();

    // Predict: y_hat = (activation >= 0)
    FheBool predBool = activation.greaterThanOrEqualToScalar(0);
    activation.close();
    FheInt32 predInt = predBool.castInto(FheInt32.class);
    predBool.close();

    // Error: e = label - y_hat
    FheInt32 error = point.label().subtract(predInt);
    predInt.close();

    // Update: w1 += e*x1, w2 += e*x2, bias += e
    FheInt32 deltaW1 = error.multiply(point.x1());
    FheInt32 deltaW2 = error.multiply(point.x2());
    weights.w1().addAssign(deltaW1);
    weights.w2().addAssign(deltaW2);
    weights.bias().addAssign(error);
    deltaW1.close();
    deltaW2.close();
    error.close();
  }
}
