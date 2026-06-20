package io.github.rdlopes.tfhe.examples.cancer.model;

/// Represents a patient record with cleartext medical features for breast cancer screening.
///
/// Feature order follows the enriched Breast Cancer Wisconsin dataset:
/// clump thickness, cell size uniformity, cell shape uniformity, marginal adhesion,
/// single epithelial cell size, bare nuclei, bland chromatin, normal nucleoli,
/// mitoses, age scale (age / 10), genetic risk index, family history flag.
///
/// @param name the unique identifier or name of the patient
/// @param clumpThickness clump thickness (1-10)
/// @param uniformityCellSize uniformity of cell size (1-10)
/// @param uniformityCellShape uniformity of cell shape (1-10)
/// @param marginalAdhesion marginal adhesion (1-10)
/// @param singleEpithelialCellSize single epithelial cell size (1-10)
/// @param bareNuclei bare nuclei (1-10)
/// @param blandChromatin bland chromatin (1-10)
/// @param normalNucleoli normal nucleoli (1-10)
/// @param mitoses mitoses (1-10)
/// @param ageScale age divided by 10 (integer)
/// @param geneticRisk genetic risk index (integer)
/// @param familyHistory family history flag (0 or 1)
/// @param trueLabel ground-truth classification label (0 = benign, 1 = malignant)
public record PatientRecord(
    String name,
    int clumpThickness,
    int uniformityCellSize,
    int uniformityCellShape,
    int marginalAdhesion,
    int singleEpithelialCellSize,
    int bareNuclei,
    int blandChromatin,
    int normalNucleoli,
    int mitoses,
    int ageScale,
    int geneticRisk,
    int familyHistory,
    int trueLabel
) {

  /// Returns all feature values as an ordered array for homomorphic evaluation.
  public int[] features() {
    return new int[]{
        clumpThickness, uniformityCellSize, uniformityCellShape,
        marginalAdhesion, singleEpithelialCellSize, bareNuclei,
        blandChromatin, normalNucleoli, mitoses,
        ageScale, geneticRisk, familyHistory
    };
  }
}
