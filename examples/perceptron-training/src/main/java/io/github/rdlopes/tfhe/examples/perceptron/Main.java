package io.github.rdlopes.tfhe.examples.perceptron;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.examples.perceptron.client.DataEncryptor;
import io.github.rdlopes.tfhe.examples.perceptron.client.TrainingDataset;
import io.github.rdlopes.tfhe.examples.perceptron.client.WeightDecryptor;
import io.github.rdlopes.tfhe.examples.perceptron.model.DataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedDataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedWeights;
import io.github.rdlopes.tfhe.examples.perceptron.model.TrainedWeights;
import io.github.rdlopes.tfhe.examples.perceptron.server.ConvergenceVerifier;
import io.github.rdlopes.tfhe.examples.perceptron.server.PerceptronTrainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/// Entry point for the Privacy-Preserving Perceptron Training showcase.
///
/// <p>Demonstrates homomorphic machine learning:
/// <ol>
///   <li>Client encrypts the training dataset and initial weights.</li>
///   <li>Server trains the perceptron homomorphically over 8 epochs.</li>
///   <li>Client decrypts the trained weights and verifies convergence.</li>
/// </ol>
public final class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private Main() {}

  /// Runs the perceptron training showcase.
  ///
  /// @param args command-line arguments (unused)
  @SuppressWarnings("java:S106")
  public static void main(String[] args) {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Perceptron Training Showcase");
    logger.info("=================================================================");

    // 1. Client-side: key generation
    logger.info("[Client] Generating FHE keys...");
    long start = System.currentTimeMillis();
    try (KeySet keySet = KeySet.builder().build();
         PublicKey publicKey = new PublicKey(keySet.getClientKey())) {
      keySet.getServerKey().use();
      logger.info("[Client] Keys generated in {} ms.", System.currentTimeMillis() - start);

      // 2. Client-side: load and encrypt training data
      logger.info("[Client] Loading and encrypting training dataset...");
      List<DataPoint> rawData = TrainingDataset.load();
      DataEncryptor encryptor = new DataEncryptor(publicKey);
      List<EncryptedDataPoint> encryptedDataset = encryptor.encryptDataset(rawData);
      EncryptedWeights encryptedWeights = encryptor.initializeWeights();
      logger.info("[Client] Encrypted {} data points.", encryptedDataset.size());

      // 3. Server-side: homomorphic training
      new PerceptronTrainer().train(encryptedDataset, encryptedWeights);

      // 4. Client-side: decrypt trained weights
      logger.info("[Client] Decrypting trained weights...");
      TrainedWeights trained = new WeightDecryptor(keySet.getClientKey()).decrypt(encryptedWeights);
      logger.info("[Client] Trained weights: w1={}, w2={}, bias={}", trained.w1(), trained.w2(), trained.bias());

      // 5. Verify convergence
      logger.info("[Client] Verifying convergence on training data...");
      boolean converged = new ConvergenceVerifier().verify(encryptedDataset, trained);
      if (converged) {
        logger.info("Showcase completed successfully! Perceptron converged on all training points.");
      } else {
        logger.error("Showcase FAILED: Perceptron did not converge.");
        System.exit(1);
      }

      // 6. Close resources
      encryptedWeights.close();
      for (EncryptedDataPoint p : encryptedDataset) { p.close(); }
    }

    logger.info("Done.");
  }
}
