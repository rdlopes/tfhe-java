package io.github.rdlopes.tfhe.examples.perceptron.client;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.examples.perceptron.model.EncryptedWeights;
import io.github.rdlopes.tfhe.examples.perceptron.model.TrainedWeights;

/// Client-side component responsible for decrypting the trained perceptron weights
/// after the homomorphic training loop completes on the server.
public final class WeightDecryptor {

  private final ClientKey clientKey;

  /// Creates a new decryptor using the given FHE client key.
  ///
  /// @param clientKey the client key used for decryption
  public WeightDecryptor(ClientKey clientKey) {
    this.clientKey = clientKey;
  }

  /// Decrypts the encrypted trained weights.
  ///
  /// @param weights the encrypted weight parameters from the server
  /// @return the cleartext trained weights ready for verification
  public TrainedWeights decrypt(EncryptedWeights weights) {
    return new TrainedWeights(
        weights.w1().decrypt(clientKey),
        weights.w2().decrypt(clientKey),
        weights.bias().decrypt(clientKey)
    );
  }
}
