package io.github.rdlopes.tfhe.examples.cancer.model;

/// Holds the decrypted result of a homomorphic cancer prediction for a single patient.
///
/// @param patientName the patient identifier
/// @param decryptedScore the raw homomorphic linear model score
/// @param predictedLabel the predicted label (0 = benign, 1 = malignant)
/// @param trueLabel the ground-truth label
/// @param correct whether the prediction matches the ground truth
public record PredictionResult(
    String patientName,
    int decryptedScore,
    int predictedLabel,
    int trueLabel,
    boolean correct
) {

  /// Returns a human-readable label string for the predicted class.
  public String predictedLabelName() {
    return predictedLabel == 1 ? "Malignant" : "Benign";
  }

  /// Returns a human-readable label string for the true class.
  public String trueLabelName() {
    return trueLabel == 1 ? "Malignant" : "Benign";
  }
}
