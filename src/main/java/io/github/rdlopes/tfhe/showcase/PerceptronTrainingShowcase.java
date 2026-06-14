package io.github.rdlopes.tfhe.showcase;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheInt32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PerceptronTrainingShowcase {

  private static final Logger logger = LoggerFactory.getLogger(PerceptronTrainingShowcase.class);

  private record Point(FheInt32 x1, FheInt32 x2, FheInt32 label, int rawX1, int rawX2, int rawLabel) {}

  static void main() {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Perceptron Training Showcase");
    logger.info("=================================================================");

    // tag::perceptron_setup[]
    // 1. Setup keys
    logger.info("Generating FHE keys...");
    long startTime = System.currentTimeMillis();
    KeySet keySet = KeySet.builder().build();
    keySet.getServerKey().use();
    PublicKey publicKey = new PublicKey(keySet.getClientKey());
    logger.info("Keys generated in {} ms.", System.currentTimeMillis() - startTime);

    // 2. Set up dataset
    logger.info("Preparing and encrypting training dataset...");
    int[][] rawData = {
        {10, 15, 1},
        {-15, -20, 0},
        {20, 45, 0},
        {5, 5, 1},
        {-10, -30, 1},
        {-5, -5, 0},
        {0, 0, 1},
        {30, 70, 0},
        {-25, -45, 0},
        {15, 25, 1}
    };

    List<Point> dataset = new ArrayList<>();
    List<FheInt32> trackedObjects = new ArrayList<>();

    for (int[] row : rawData) {
      int x1Val = row[0];
      int x2Val = row[1];
      int labelVal = row[2];

      FheInt32 x1Enc = FheInt32.encrypt(x1Val, publicKey);
      FheInt32 x2Enc = FheInt32.encrypt(x2Val, publicKey);
      FheInt32 labelEnc = FheInt32.encrypt(labelVal, publicKey);

      trackedObjects.add(x1Enc);
      trackedObjects.add(x2Enc);
      trackedObjects.add(labelEnc);

      dataset.add(new Point(x1Enc, x2Enc, labelEnc, x1Val, x2Val, labelVal));
    }
    logger.info("Dataset of {} points encrypted and loaded.", dataset.size());

    // 3. Initialize weights and bias
    logger.info("Initializing weights and bias to 0 (encrypted)...");
    FheInt32 w1 = FheInt32.encrypt(0, publicKey);
    FheInt32 w2 = FheInt32.encrypt(0, publicKey);
    FheInt32 b = FheInt32.encrypt(0, publicKey);

    trackedObjects.add(w1);
    trackedObjects.add(w2);
    trackedObjects.add(b);
    // end::perceptron_setup[]

    // tag::perceptron_training[]
    // 4. Training Loop (8 epochs)
    int epochs = 8;
    logger.info("Starting homomorphic training for {} epochs...", epochs);
    long trainStart = System.currentTimeMillis();
    for (int epoch = 0; epoch < epochs; epoch++) {
      long epochStart = System.currentTimeMillis();
      for (Point point : dataset) {
        // Compute activation: a = w1 * x1 + w2 * x2 + b
        FheInt32 term1 = w1.multiply(point.x1);
        FheInt32 term2 = w2.multiply(point.x2);
        FheInt32 sum = term1.add(term2);
        FheInt32 activation = sum.add(b);
        
        // Predict y_hat = (activation >= 0)
        FheBool predBool = activation.greaterThanOrEqualToScalar(0);
        FheInt32 predInt = predBool.castInto(FheInt32.class);
        
        // Error: e = label - y_hat
        FheInt32 error = point.label.subtract(predInt);
        
        // Update weights:
        // w1 = w1 + e * x1
        // w2 = w2 + e * x2
        // b = b + e
        FheInt32 deltaW1 = error.multiply(point.x1);
        FheInt32 deltaW2 = error.multiply(point.x2);
        
        w1.addAssign(deltaW1);
        w2.addAssign(deltaW2);
        b.addAssign(error);
        
        // Clean up intermediate ciphertexts
        term1.destroy();
        term2.destroy();
        sum.destroy();
        activation.destroy();
        predBool.destroy();
        predInt.destroy();
        error.destroy();
        deltaW1.destroy();
        deltaW2.destroy();
      }
      logger.info("  Epoch {} completed in {} ms.", epoch + 1, System.currentTimeMillis() - epochStart);
    }
    logger.info("Training completed in {} ms.", System.currentTimeMillis() - trainStart);
    // end::perceptron_training[]

    // 5. Decrypt and verify convergence
    logger.info("Decrypting trained weights and bias...");
    int finalW1 = w1.decrypt(keySet.getClientKey());
    int finalW2 = w2.decrypt(keySet.getClientKey());
    int finalB = b.decrypt(keySet.getClientKey());

    logger.info("\n--- Training Results ---");
    logger.info("Trained Weights: w1 = {}, w2 = {}", finalW1, finalW2);
    logger.info("Trained Bias:    b = {}", finalB);
    logger.info("------------------------\n");

    logger.info("Verifying model classifications on training dataset:");
    boolean allCorrect = true;
    for (int i = 0; i < dataset.size(); i++) {
      Point point = dataset.get(i);
      int score = finalW1 * point.rawX1 + finalW2 * point.rawX2 + finalB;
      int prediction = score >= 0 ? 1 : 0;
      boolean correct = prediction == point.rawLabel;
      logger.info("  Point {}: ({}, {}) | Label: {} | Score: {} | Predicted: {} | {}",
          i + 1, point.rawX1, point.rawX2, point.rawLabel, score, prediction, correct ? "SUCCESS" : "FAILED");
      if (!correct) {
        allCorrect = false;
      }
    }

    if (allCorrect) {
      logger.info("\nShowcase completed successfully! Perceptron successfully converged and separates all points.");
    } else {
      logger.error("\nShowcase failed: Perceptron did not separate all points correctly.");
      System.exit(1);
    }

    // 6. Clean up
    logger.info("Cleaning up FHE objects...");
    for (FheInt32 obj : trackedObjects) {
      try {
        obj.destroy();
      } catch (Exception ignored) {}
    }
    try {
      publicKey.destroy();
    } catch (Exception ignored) {}
    logger.info("Done.");
  }
}
