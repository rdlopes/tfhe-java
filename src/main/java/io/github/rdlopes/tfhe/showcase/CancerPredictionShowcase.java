package io.github.rdlopes.tfhe.showcase;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheInt32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CancerPredictionShowcase {

  private static final Logger logger = LoggerFactory.getLogger(CancerPredictionShowcase.class);

  // Pre-trained integer weights and bias from the enriched Breast Cancer Wisconsin dataset
  // Feature order:
  // 0: Clump Thickness, 1: Uniformity of Cell Size, 2: Uniformity of Cell Shape,
  // 3: Marginal Adhesion, 4: Single Epithelial Cell Size, 5: Bare Nuclei,
  // 6: Bland Chromatin, 7: Normal Nucleoli, 8: Mitoses,
  // 9: Age Scale (age / 10), 10: Genetic Risk, 11: Family History
  private static final int[] WEIGHTS = {2, 12, 6, 5, -8, 12, -3, 6, 1, -29, 9, 10};
  private static final int BIAS = -46;

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
  ) {}

  public static class EncryptedPatientRecord {
    public final String name;
    public final List<FheInt32> encryptedFeatures = new ArrayList<>();
    public final int trueLabel;

    public EncryptedPatientRecord(PatientRecord record, ClientKey clientKey) {
      this.name = record.name();
      this.trueLabel = record.trueLabel();
      
      // Encrypt each feature using the client key
      encryptedFeatures.add(FheInt32.encrypt(record.clumpThickness(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.uniformityCellSize(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.uniformityCellShape(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.marginalAdhesion(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.singleEpithelialCellSize(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.bareNuclei(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.blandChromatin(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.normalNucleoli(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.mitoses(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.ageScale(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.geneticRisk(), clientKey));
      encryptedFeatures.add(FheInt32.encrypt(record.familyHistory(), clientKey));
    }

    public void destroy() {
      for (FheInt32 feature : encryptedFeatures) {
        if (feature != null) {
          feature.destroy();
        }
      }
    }
  }

  public static void main(String[] args) {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Cancer Prediction Showcase");
    logger.info("=================================================================");

    // tag::cancer_setup[]
    // 1. Initialize keys on client-side
    logger.info("Initializing FHE keys (Client-side)...");
    long keyGenStart = System.currentTimeMillis();
    KeySet keySet = KeySet.builder().build();
    keySet.getServerKey().use();
    PublicKey publicKey = new PublicKey(keySet.getClientKey());
    logger.info("Keys initialized in {} ms.", System.currentTimeMillis() - keyGenStart);

    // 2. Prepare mock database with sensitive features
    List<PatientRecord> records = new ArrayList<>();
    records.add(new PatientRecord("Patient-001", 5, 1, 1, 1, 2, 1, 3, 1, 1, 4, 2, 3, 0)); // Benign
    records.add(new PatientRecord("Patient-002", 8, 10, 10, 8, 7, 10, 9, 7, 1, 7, 7, 4, 1)); // Malignant
    records.add(new PatientRecord("Patient-003", 3, 1, 1, 1, 2, 2, 3, 1, 1, 2, 5, 1, 0)); // Benign
    records.add(new PatientRecord("Patient-004", 4, 1, 1, 3, 2, 1, 3, 1, 1, 7, 2, 4, 0)); // Benign

    logger.info("Encrypting sensitive patient records...");
    List<EncryptedPatientRecord> encryptedRecords = new ArrayList<>();
    for (PatientRecord r : records) {
      encryptedRecords.add(new EncryptedPatientRecord(r, keySet.getClientKey()));
      logger.info("  Encrypted patient data for: {}", r.name());
    }
    // end::cancer_setup[]

    // tag::cancer_inference[]
    // 3. Perform homomorphic evaluation on tally server
    logger.info("Evaluating cancer predictions homomorphically (Server-side)...");
    long evalStart = System.currentTimeMillis();

    List<FheBool> encryptedPredictions = new ArrayList<>();
    List<FheInt32> scores = new ArrayList<>();

    for (EncryptedPatientRecord er : encryptedRecords) {
      // Initialize with bias (ClientKey encryption is much faster)
      FheInt32 score = FheInt32.encrypt(BIAS, keySet.getClientKey());
      
      // Homomorphically compute score = sum(w_i * x_i) + bias
      for (int i = 0; i < WEIGHTS.length; i++) {
        int weight = WEIGHTS[i];
        if (weight != 0) {
          FheInt32 term = er.encryptedFeatures.get(i).multiplyScalar(weight);
          FheInt32 oldScore = score;
          score = oldScore.add(term);
          
          // Clean up intermediate ciphertexts to prevent memory leaks
          term.destroy();
          oldScore.destroy();
        }
      }
      
      // Classify: malignant if score >= 0
      FheBool pred = score.greaterThanOrEqualToScalar(0);
      
      scores.add(score);
      encryptedPredictions.add(pred);
    }
    logger.info("Homomorphic evaluation completed in {} ms.", System.currentTimeMillis() - evalStart);
    // end::cancer_inference[]

    // 4. Decrypt and verify outcomes (Client-side)
    logger.info("Decrypting and verifying results (Client-side)...");
    boolean allCorrect = true;
    for (int i = 0; i < records.size(); i++) {
      PatientRecord r = records.get(i);
      FheBool encryptedPred = encryptedPredictions.get(i);
      FheInt32 score = scores.get(i);
      
      boolean pred = encryptedPred.decrypt(keySet.getClientKey());
      int decryptedScore = score.decrypt(keySet.getClientKey());
      
      int predInt = pred ? 1 : 0;
      boolean correct = (predInt == r.trueLabel());
      
      logger.info("  {}: Decrypted Score = {} | Predicted = {} | True Label = {} | Verification: {}",
          r.name(), decryptedScore, predInt == 1 ? "Malignant" : "Benign", r.trueLabel() == 1 ? "Malignant" : "Benign",
          correct ? "SUCCESS" : "FAILED");
      
      if (!correct) {
        allCorrect = false;
      }
    }

    if (allCorrect) {
      logger.info("Showcase completed successfully! FHE cancer prediction matches ground truth values.");
    } else {
      logger.error("Showcase failed: Decrypted predictions do not match true labels.");
      System.exit(1);
    }

    // 5. Native resource cleanup
    logger.info("Cleaning up native FHE resources...");
    for (EncryptedPatientRecord er : encryptedRecords) {
      er.destroy();
    }
    for (FheBool p : encryptedPredictions) {
      p.destroy();
    }
    for (FheInt32 s : scores) {
      s.destroy();
    }
    publicKey.destroy();
    logger.info("Done.");
  }
}
