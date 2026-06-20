package io.github.rdlopes.tfhe.examples.voting.client;

import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListBuilder;
import io.github.rdlopes.tfhe.api.types.ProvenCompactCiphertextList;
import io.github.rdlopes.tfhe.api.types.ZkComputeLoad;
import io.github.rdlopes.tfhe.examples.voting.model.Ballot;
import io.github.rdlopes.tfhe.examples.voting.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/// Client-side component responsible for casting votes as ZK-proven sealed ballots.
/// Each ballot contains three encrypted boolean choices and a zero-knowledge proof
/// of conformance, ensuring the ballot structure is valid without revealing the vote.
public final class BallotCaster {

  private static final Logger logger = LoggerFactory.getLogger(BallotCaster.class);

  private final CompactPublicKey compactPublicKey;
  private final CompactPkeCrs crs;

  /// Creates a new ballot caster.
  ///
  /// @param compactPublicKey the compact public key for ballot encryption
  /// @param crs the Common Reference String for ZK proof generation
  public BallotCaster(CompactPublicKey compactPublicKey, CompactPkeCrs crs) {
    this.compactPublicKey = compactPublicKey;
    this.crs = crs;
  }

  /// Casts a list of votes as ZK-proven sealed ballots.
  ///
  /// @param votes the votes to cast
  /// @return the list of sealed ballots ready for submission to the tally server
  public List<Ballot> castBallots(List<Vote> votes) {
    List<Ballot> ballots = new ArrayList<>();
    for (Vote vote : votes) {
      ballots.add(castBallot(vote));
    }
    return ballots;
  }

  private Ballot castBallot(Vote vote) {
    byte[] metadata = vote.voterName().getBytes();
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);
    builder.push(vote.choiceAlice());
    builder.push(vote.choiceBob());
    builder.push(vote.choiceCharlie());
    try (ProvenCompactCiphertextList proven = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF);
         DynamicBuffer serialized = proven.serialize()) {
      logger.info("  Ballot cast for: {}", vote.voterName());
      return new Ballot(serialized.toByteArray(), metadata);
    }
  }
}
