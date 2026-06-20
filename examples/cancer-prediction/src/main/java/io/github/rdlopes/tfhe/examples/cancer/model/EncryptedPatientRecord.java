package io.github.rdlopes.tfhe.examples.cancer.model;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// Represents a patient record whose medical features have been encrypted
/// using the FHE client key, ready for server-side homomorphic evaluation.
/// Implements {@link AutoCloseable} to ensure native memory resources are released.
public final class EncryptedPatientRecord implements AutoCloseable {

  private final String name;
  private final List<FheInt32> encryptedFeatures;
  private final int trueLabel;

  /// Encrypts all features of a cleartext {@link PatientRecord} using the given client key.
  ///
  /// @param record the cleartext patient record to encrypt
  /// @param clientKey the FHE client key used for encryption
  public EncryptedPatientRecord(PatientRecord record, ClientKey clientKey) {
    this.name = record.name();
    this.trueLabel = record.trueLabel();
    List<FheInt32> features = new ArrayList<>();
    for (int value : record.features()) {
      features.add(FheInt32.encrypt(value, clientKey));
    }
    this.encryptedFeatures = Collections.unmodifiableList(features);
  }

  /// Returns the patient name.
  public String name() {
    return name;
  }

  /// Returns the ground-truth classification label (0 = benign, 1 = malignant).
  public int trueLabel() {
    return trueLabel;
  }

  /// Returns the ordered list of encrypted feature ciphertexts.
  public List<FheInt32> encryptedFeatures() {
    return encryptedFeatures;
  }

  @Override
  public void close() {
    for (FheInt32 feature : encryptedFeatures) {
      feature.close();
    }
  }
}
