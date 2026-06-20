package io.github.rdlopes.tfhe.examples.voting.model;

import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.FheUint8;

/// Holds the encrypted running tallies for each candidate and the invalid ballot count.
/// The tallies are homomorphically incremented for each verified ballot.
/// Implements {@link AutoCloseable} to release native memory.
public final class ElectionTallies implements AutoCloseable {

  private final FheUint8 alice;
  private final FheUint8 bob;
  private final FheUint8 charlie;
  private final FheUint8 invalid;

  /// Initializes all tallies to zero using public-key encryption.
  ///
  /// @param publicKey the FHE public key used for trivial zero encryption
  public ElectionTallies(PublicKey publicKey) {
    this.alice = FheUint8.encrypt((byte) 0, publicKey);
    this.bob = FheUint8.encrypt((byte) 0, publicKey);
    this.charlie = FheUint8.encrypt((byte) 0, publicKey);
    this.invalid = FheUint8.encrypt((byte) 0, publicKey);
  }

  /// Returns the encrypted tally for Alice.
  public FheUint8 alice() { return alice; }
  /// Returns the encrypted tally for Bob.
  public FheUint8 bob() { return bob; }
  /// Returns the encrypted tally for Charlie.
  public FheUint8 charlie() { return charlie; }
  /// Returns the encrypted tally for invalid ballots.
  public FheUint8 invalid() { return invalid; }

  @Override
  public void close() {
    alice.close();
    bob.close();
    charlie.close();
    invalid.close();
  }
}
