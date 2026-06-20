package io.github.rdlopes.tfhe.examples.cancer.client;

import io.github.rdlopes.tfhe.examples.cancer.model.PatientRecord;

import java.util.List;

/// Factory providing the sample patient dataset used in this showcase.
/// Records are drawn from the enriched Breast Cancer Wisconsin dataset.
public final class PatientDataset {

  private PatientDataset() {}

  /// Returns the list of sample patient records for the showcase.
  public static List<PatientRecord> load() {
    return List.of(
        new PatientRecord("Patient-001", 5, 1, 1, 1, 2, 1, 3, 1, 1, 4, 2, 3, 0),  // Benign
        new PatientRecord("Patient-002", 8, 10, 10, 8, 7, 10, 9, 7, 1, 7, 7, 4, 1), // Malignant
        new PatientRecord("Patient-003", 3, 1, 1, 1, 2, 2, 3, 1, 1, 2, 5, 1, 0),   // Benign
        new PatientRecord("Patient-004", 4, 1, 1, 3, 2, 1, 3, 1, 1, 7, 2, 4, 0)    // Benign
    );
  }
}
