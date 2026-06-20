package io.github.rdlopes.tfhe.examples.cancer;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;
import io.github.rdlopes.tfhe.examples.cancer.client.PatientDataset;
import io.github.rdlopes.tfhe.examples.cancer.client.PatientEncryptor;
import io.github.rdlopes.tfhe.examples.cancer.client.PredictionDecryptor;
import io.github.rdlopes.tfhe.examples.cancer.model.EncryptedPatientRecord;
import io.github.rdlopes.tfhe.examples.cancer.model.PatientRecord;
import io.github.rdlopes.tfhe.examples.cancer.model.PredictionResult;
import io.github.rdlopes.tfhe.examples.cancer.server.HomomorphicEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/// Entry point for the Privacy-Preserving Cancer Prediction showcase.
///
/// <p>Demonstrates a client-server FHE pipeline:
/// <ol>
///   <li>Client generates FHE keys and encrypts patient records.</li>
///   <li>Server evaluates the cancer model homomorphically on encrypted data.</li>
///   <li>Client decrypts and verifies the predictions.</li>
/// </ol>
public final class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private Main() {}

  /// Runs the cancer prediction showcase.
  ///
  /// @param args command-line arguments (unused)
  @SuppressWarnings("java:S106")
  public static void main(String[] args) {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Cancer Prediction Showcase");
    logger.info("=================================================================");

    // 1. Client-side: key generation
    logger.info("[Client] Generating FHE keys...");
    long start = System.currentTimeMillis();
    try (KeySet keySet = KeySet.builder().build()) {
      keySet.getServerKey().use();
      logger.info("[Client] Keys generated in {} ms.", System.currentTimeMillis() - start);

      // 2. Client-side: load and encrypt patient records
      logger.info("[Client] Loading and encrypting patient records...");
      List<PatientRecord> records = PatientDataset.load();
      PatientEncryptor encryptor = new PatientEncryptor(keySet.getClientKey());
      List<EncryptedPatientRecord> encryptedRecords = encryptor.encrypt(records);

      // 3. Server-side: homomorphic evaluation
      logger.info("[Server] Evaluating cancer model homomorphically...");
      long evalStart = System.currentTimeMillis();
      List<FheInt32> encryptedScores = new ArrayList<>();
      HomomorphicEvaluator evaluator = new HomomorphicEvaluator(keySet.getClientKey());
      List<FheBool> encryptedPredictions = evaluator.evaluate(encryptedRecords, encryptedScores);
      logger.info("[Server] Evaluation completed in {} ms.", System.currentTimeMillis() - evalStart);

      // 4. Client-side: decrypt and verify
      logger.info("[Client] Decrypting and verifying results...");
      PredictionDecryptor decryptor = new PredictionDecryptor(keySet.getClientKey());
      List<PredictionResult> results = decryptor.decrypt(encryptedRecords, encryptedPredictions, encryptedScores);

      // 5. Verify all predictions are correct
      boolean allCorrect = results.stream().allMatch(PredictionResult::correct);
      if (allCorrect) {
        logger.info("Showcase completed successfully! All predictions match ground truth.");
      } else {
        logger.error("Showcase FAILED: Some predictions do not match ground truth.");
        System.exit(1);
      }

      // 6. Close resources
      for (FheBool p : encryptedPredictions) { p.close(); }
      for (FheInt32 s : encryptedScores) { s.close(); }
      for (EncryptedPatientRecord r : encryptedRecords) { r.close(); }
    }

    logger.info("Done.");
  }
}
