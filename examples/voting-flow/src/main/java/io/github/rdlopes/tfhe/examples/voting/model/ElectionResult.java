package io.github.rdlopes.tfhe.examples.voting.model;

/// Holds the decrypted election tallies after the homomorphic aggregation is complete.
///
/// @param aliceTally the number of valid votes for Alice
/// @param bobTally the number of valid votes for Bob
/// @param charlieTally the number of valid votes for Charlie
/// @param invalidTally the number of invalid or spoiled ballots
public record ElectionResult(
    int aliceTally,
    int bobTally,
    int charlieTally,
    int invalidTally
) {}
