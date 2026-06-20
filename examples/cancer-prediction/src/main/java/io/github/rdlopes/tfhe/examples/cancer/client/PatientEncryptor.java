package io.github.rdlopes.tfhe.examples.cancer.client;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.examples.cancer.model.EncryptedPatientRecord;
import io.github.rdlopes.tfhe.examples.cancer.model.PatientRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/// Client-side component responsible for encrypting patient records
/// before sending them to the evaluation server.
public final class PatientEncryptor {

  private static final Logger logger = LoggerFactory.getLogger(PatientEncryptor.class);

  private final ClientKey clientKey;

  /// Creates a new encryptor using the given FHE client key.
  ///
  /// @param clientKey the client key used for encryption
  public PatientEncryptor(ClientKey clientKey) {
    this.clientKey = clientKey;
  }

  /// Encrypts a list of patient records.
  ///
  /// @param records the cleartext patient records to encrypt
  /// @return a list of encrypted patient records ready for server-side evaluation
  public List<EncryptedPatientRecord> encrypt(List<PatientRecord> records) {
    List<EncryptedPatientRecord> encrypted = new ArrayList<>();
    for (PatientRecord record : records) {
      encrypted.add(new EncryptedPatientRecord(record, clientKey));
      logger.info("  Encrypted patient data for: {}", record.name());
    }
    return encrypted;
  }
}
