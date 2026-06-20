package io.github.rdlopes.tfhe.examples.perceptron.client;

import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32;
import io.github.rdlopes.tfhe.examples.perceptron.model.DataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedDataPoint;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedWeights;

import java.util.ArrayList;
import java.util.List;

/// Client-side component responsible for encrypting the training dataset and initial weights
/// before sending them to the training server.
public final class DataEncryptor {

  private final PublicKey publicKey;

  /// Creates a new encryptor using the given FHE public key.
  ///
  /// @param publicKey the public key used for encryption
  public DataEncryptor(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  /// Encrypts a list of training data points.
  ///
  /// @param points the cleartext data points to encrypt
  /// @return a list of encrypted data points ready for homomorphic training
  public List<EncryptedDataPoint> encryptDataset(List<DataPoint> points) {
    List<EncryptedDataPoint> result = new ArrayList<>();
    for (DataPoint p : points) {
      result.add(new EncryptedDataPoint(
          FheInt32.encrypt(p.x1(), publicKey),
          FheInt32.encrypt(p.x2(), publicKey),
          FheInt32.encrypt(p.label(), publicKey),
          p.x1(), p.x2(), p.label()
      ));
    }
    return result;
  }

  /// Creates the initial encrypted weights and bias (all zeros).
  ///
  /// @return encrypted weights initialized to zero
  public EncryptedWeights initializeWeights() {
    return new EncryptedWeights(
        FheInt32.encrypt(0, publicKey),
        FheInt32.encrypt(0, publicKey),
        FheInt32.encrypt(0, publicKey)
    );
  }
}
