package io.github.rdlopes.tfhe.examples.voting.client;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.examples.voting.model.ElectionResult;
import io.github.rdlopes.tfhe.examples.voting.model.ElectionTallies;

/// Client-side component responsible for decrypting the final election tallies
/// after the server has aggregated all valid ballots.
public final class ResultDecryptor {

  private final ClientKey clientKey;

  /// Creates a new result decryptor.
  ///
  /// @param clientKey the FHE client key used for decryption
  public ResultDecryptor(ClientKey clientKey) {
    this.clientKey = clientKey;
  }

  /// Decrypts the encrypted election tallies into a cleartext election result.
  ///
  /// @param tallies the encrypted running tallies from the server
  /// @return the cleartext election result
  public ElectionResult decrypt(ElectionTallies tallies) {
    return new ElectionResult(
        tallies.alice().decrypt(clientKey),
        tallies.bob().decrypt(clientKey),
        tallies.charlie().decrypt(clientKey),
        tallies.invalid().decrypt(clientKey)
    );
  }
}
