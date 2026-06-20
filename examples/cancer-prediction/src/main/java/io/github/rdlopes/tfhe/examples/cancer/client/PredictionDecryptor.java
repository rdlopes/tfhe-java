package io.github.rdlopes.tfhe.examples.cancer.client;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;
import io.github.rdlopes.tfhe.examples.cancer.model.EncryptedPatientRecord;
import io.github.rdlopes.tfhe.examples.cancer.model.PredictionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/// Client-side component responsible for decrypting encrypted prediction results
/// received from the evaluation server and verifying their correctness.
public final class PredictionDecryptor {

  private static final Logger logger = LoggerFactory.getLogger(PredictionDecryptor.class);

  private final ClientKey clientKey;

  /// Creates a new decryptor using the given FHE client key.
  ///
  /// @param clientKey the client key used for decryption
  public PredictionDecryptor(ClientKey clientKey) {
    this.clientKey = clientKey;
  }

  /// Decrypts the encrypted predictions and scores, producing human-readable results.
  ///
  /// @param records the original encrypted records (for ground-truth comparison)
  /// @param encryptedPredictions the encrypted boolean predictions from the server
  /// @param encryptedScores the encrypted linear model scores from the server
  /// @return a list of decrypted prediction results with correctness flags
  public List<PredictionResult> decrypt(
      List<EncryptedPatientRecord> records,
      List<FheBool> encryptedPredictions,
      List<FheInt32> encryptedScores
  ) {
    List<PredictionResult> results = new ArrayList<>();
    for (int i = 0; i < records.size(); i++) {
      EncryptedPatientRecord record = records.get(i);
      boolean predicted = encryptedPredictions.get(i).decrypt(clientKey);
      int score = encryptedScores.get(i).decrypt(clientKey);
      int predictedLabel = predicted ? 1 : 0;
      boolean correct = predictedLabel == record.trueLabel();
      PredictionResult result = new PredictionResult(
          record.name(), score, predictedLabel, record.trueLabel(), correct
      );
      logger.info(
          "  {}: Score={} | Predicted={} | True={} | {}",
          result.patientName(), result.decryptedScore(),
          result.predictedLabelName(), result.trueLabelName(),
          result.correct() ? "SUCCESS" : "FAILED"
      );
      results.add(result);
    }
    return results;
  }
}
