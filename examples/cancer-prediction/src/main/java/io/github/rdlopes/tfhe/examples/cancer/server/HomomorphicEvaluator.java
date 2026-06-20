package io.github.rdlopes.tfhe.examples.cancer.server;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;
import io.github.rdlopes.tfhe.examples.cancer.model.EncryptedPatientRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/// Server-side homomorphic evaluator that computes cancer predictions
/// on encrypted patient data without ever decrypting it.
///
/// <p>The evaluation computes: {@code score = sum(WEIGHTS[i] * features[i]) + BIAS}
/// and classifies as malignant when {@code score >= 0}.
public final class HomomorphicEvaluator {

  private static final Logger logger = LoggerFactory.getLogger(HomomorphicEvaluator.class);

  private final ClientKey clientKey;

  /// Creates a new evaluator. The client key is used only for encrypting the initial bias
  /// scalar (a fast trivial encryption), not for decryption.
  ///
  /// @param clientKey the client key used for trivial bias encryption
  public HomomorphicEvaluator(ClientKey clientKey) {
    this.clientKey = clientKey;
  }

  /// Evaluates the cancer prediction model homomorphically on a list of encrypted records.
  ///
  /// @param records the encrypted patient records
  /// @param outScores output list that will receive the encrypted scores (caller owns lifecycle)
  /// @return list of encrypted boolean predictions (true = malignant)
  public List<FheBool> evaluate(List<EncryptedPatientRecord> records, List<FheInt32> outScores) {
    List<FheBool> predictions = new ArrayList<>();
    for (EncryptedPatientRecord record : records) {
      FheBool prediction = evaluateRecord(record, outScores);
      predictions.add(prediction);
    }
    return predictions;
  }

  private FheBool evaluateRecord(EncryptedPatientRecord record, List<FheInt32> outScores) {
    // Initialize score with bias using trivial (fast) client-key encryption
    FheInt32 score = FheInt32.encrypt(CancerModel.BIAS, clientKey);
    List<FheInt32> features = record.encryptedFeatures();

    for (int i = 0; i < CancerModel.WEIGHTS.length; i++) {
      FheInt32 term = features.get(i).multiplyScalar(CancerModel.WEIGHTS[i]);
      FheInt32 newScore = score.add(term);
      term.close();
      score.close();
      score = newScore;
    }

    FheBool prediction = score.greaterThanOrEqualToScalar(0);
    outScores.add(score);
    logger.debug("  Evaluated prediction for: {}", record.name());
    return prediction;
  }
}
