package io.github.rdlopes.tfhe.examples.voting.model;

import java.util.Arrays;

/// Represents a sealed, ZK-proven ballot ready for server-side tally aggregation.
/// The ballot is serialized to bytes for transmission and contains voter metadata
/// used during ZK proof verification.
///
/// @param serializedData the serialized ProvenCompactCiphertextList bytes
/// @param metadata voter-supplied metadata (voter name bytes) used in proof verification
public record Ballot(byte[] serializedData, byte[] metadata) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Ballot other)) return false;
    return Arrays.equals(serializedData, other.serializedData)
        && Arrays.equals(metadata, other.metadata);
  }

  @Override
  public int hashCode() {
    return 31 * Arrays.hashCode(serializedData) + Arrays.hashCode(metadata);
  }

  @Override
  public String toString() {
    return "Ballot[metadata=" + new String(metadata) + "]";
  }
}
