package io.github.rdlopes.tfhe.examples.voting;

import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.examples.voting.client.BallotCaster;
import io.github.rdlopes.tfhe.examples.voting.client.ElectionSetup;
import io.github.rdlopes.tfhe.examples.voting.client.ResultDecryptor;
import io.github.rdlopes.tfhe.examples.voting.model.Ballot;
import io.github.rdlopes.tfhe.examples.voting.model.Candidate;
import io.github.rdlopes.tfhe.examples.voting.model.ElectionResult;
import io.github.rdlopes.tfhe.examples.voting.model.ElectionTallies;
import io.github.rdlopes.tfhe.examples.voting.model.Vote;
import io.github.rdlopes.tfhe.examples.voting.server.TallyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/// Entry point for the Privacy-Preserving Voting Flow showcase.
///
/// <p>Demonstrates a complete FHE-based e-voting pipeline:
/// <ol>
///   <li>Election setup: CRS, key generation, compact public key initialization.</li>
///   <li>Ballot casting: voters encrypt their choices and generate ZK proofs.</li>
///   <li>Tally aggregation: server verifies ZK proofs and accumulates votes homomorphically.</li>
///   <li>Result decryption: election authority decrypts and verifies the final tallies.</li>
/// </ol>
public final class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private Main() {}

  /// Runs the voting flow showcase.
  ///
  /// @param args command-line arguments (unused)
  @SuppressWarnings("java:S106")
  public static void main(String[] args) {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Voting Flow Showcase");
    logger.info("=================================================================");

    try (ElectionSetup setup = new ElectionSetup()) {
      logger.info("[Setup] Election cryptographic setup completed.");

      // 1. Define the votes
      List<Vote> votes = List.of(
          Vote.forCandidate("Alice", Candidate.ALICE),
          Vote.forCandidate("Bob", Candidate.BOB),
          Vote.forCandidate("Charlie", Candidate.CHARLIE),
          Vote.forCandidate("Dave", Candidate.ALICE),
          Vote.invalid("Eve"),
          Vote.doubleVote("Frank", Candidate.ALICE, Candidate.BOB),
          Vote.forCandidate("Grace", Candidate.CHARLIE)
      );

      // 2. Voters cast their ballots
      logger.info("[Client] Casting {} ballots...", votes.size());
      BallotCaster caster = new BallotCaster(setup.compactPublicKey(), setup.crs());
      List<Ballot> ballots = caster.castBallots(votes);
      logger.info("[Client] All ballots cast and ZK-proven.");

      // 3. Server aggregates the tallies
      logger.info("[Server] Aggregating ballots homomorphically...");
      long tallyStart = System.currentTimeMillis();
      try (ElectionTallies tallies = new ElectionTallies(setup.publicKey());
           FheUint8 zero = FheUint8.encrypt((byte) 0, setup.publicKey());
           FheUint8 one = FheUint8.encrypt((byte) 1, setup.publicKey())) {
        TallyServer server = new TallyServer(
            setup.compactPublicKey(), setup.crs(), setup.keySet().getServerKey()
        );
        server.aggregate(ballots, tallies, zero, one);
        logger.info("[Server] Tally completed in {} ms.", System.currentTimeMillis() - tallyStart);

        // 4. Client decrypts and verifies
        logger.info("[Client] Decrypting and verifying election results...");
        ElectionResult result = new ResultDecryptor(setup.keySet().getClientKey()).decrypt(tallies);

        logger.info("--- Election Results ---");
        logger.info("  Alice:   {} votes", result.aliceTally());
        logger.info("  Bob:     {} votes", result.bobTally());
        logger.info("  Charlie: {} votes", result.charlieTally());
        logger.info("  Invalid: {} ballots", result.invalidTally());

        // Expected: Alice=2, Bob=1, Charlie=2, Invalid=2
        boolean correct = result.aliceTally() == 2
            && result.bobTally() == 1
            && result.charlieTally() == 2
            && result.invalidTally() == 2;

        if (correct) {
          logger.info("Showcase completed successfully! Election results verified.");
        } else {
          logger.error("Showcase FAILED: Election results do not match expected tallies.");
          System.exit(1);
        }
      }
    }

    logger.info("Done.");
  }
}
