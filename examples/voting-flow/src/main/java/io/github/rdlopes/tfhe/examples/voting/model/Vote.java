package io.github.rdlopes.tfhe.examples.voting.model;

/// Represents a voter's cleartext vote.
/// A valid vote has exactly one choice set to {@code true}.
///
/// @param voterName the voter's identifier
/// @param choiceAlice whether the voter chose Alice
/// @param choiceBob whether the voter chose Bob
/// @param choiceCharlie whether the voter chose Charlie
public record Vote(
    String voterName,
    boolean choiceAlice,
    boolean choiceBob,
    boolean choiceCharlie
) {

  /// Creates a vote for a specific candidate.
  ///
  /// @param voterName the voter's identifier
  /// @param candidate the chosen candidate
  /// @return a vote with exactly one choice set to true
  public static Vote forCandidate(String voterName, Candidate candidate) {
    return new Vote(
        voterName,
        candidate == Candidate.ALICE,
        candidate == Candidate.BOB,
        candidate == Candidate.CHARLIE
    );
  }

  /// Creates an invalid vote (no candidate chosen, simulating a spoiled ballot).
  ///
  /// @param voterName the voter's identifier
  /// @return a vote with all choices set to false
  public static Vote invalid(String voterName) {
    return new Vote(voterName, false, false, false);
  }

  /// Creates an invalid vote (two candidates chosen simultaneously).
  ///
  /// @param voterName the voter's identifier
  /// @param c1 first choice
  /// @param c2 second choice
  /// @return a vote with two choices set to true
  public static Vote doubleVote(String voterName, Candidate c1, Candidate c2) {
    return new Vote(
        voterName,
        c1 == Candidate.ALICE || c2 == Candidate.ALICE,
        c1 == Candidate.BOB || c2 == Candidate.BOB,
        c1 == Candidate.CHARLIE || c2 == Candidate.CHARLIE
    );
  }
}
