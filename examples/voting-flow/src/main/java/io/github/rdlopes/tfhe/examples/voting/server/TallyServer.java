package io.github.rdlopes.tfhe.examples.voting.server;

import io.github.rdlopes.tfhe.api.TfheThreadingContext;
import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListExpander;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.ProvenCompactCiphertextList;
import io.github.rdlopes.tfhe.examples.voting.model.Ballot;
import io.github.rdlopes.tfhe.examples.voting.model.ElectionTallies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/// Server-side tally aggregator that processes ballots homomorphically.
///
/// <p>For each ballot:
/// <ol>
///   <li>Deserializes the ballot.</li>
///   <li>Verifies the ZK proof of conformance using the CRS.</li>
///   <li>Expands the ciphertext list to individual encrypted choices.</li>
///   <li>Rejects double-votes: a ballot is valid iff exactly one choice is true.</li>
///   <li>Homomorphically adds the contribution to the running tallies.</li>
/// </ol>
///
/// All operations run inside a {@link TfheThreadingContext} to parallelize server-side FHE work.
public final class TallyServer {

  private static final Logger logger = LoggerFactory.getLogger(TallyServer.class);
  private static final int NUM_THREADS = 2;

  private final CompactPublicKey compactPublicKey;
  private final CompactPkeCrs crs;
  private final ServerKey serverKey;

  /// Creates a new tally server.
  ///
  /// @param compactPublicKey the compact public key for ZK proof verification
  /// @param crs the Common Reference String for ZK proof verification
  /// @param serverKey the FHE server key used inside the threading context
  public TallyServer(CompactPublicKey compactPublicKey, CompactPkeCrs crs, ServerKey serverKey) {
    this.compactPublicKey = compactPublicKey;
    this.crs = crs;
    this.serverKey = serverKey;
  }

  /// Processes all ballots and accumulates the results into the given tallies.
  ///
  /// @param ballots the list of sealed ballots to process
  /// @param tallies the election tallies to update (mutated in-place)
  /// @param zero an encrypted zero used for invalid ballot contribution
  /// @param one an encrypted one used for invalid ballot contribution
  public void aggregate(
      List<Ballot> ballots,
      ElectionTallies tallies,
      FheUint8 zero,
      FheUint8 one
  ) {
    try (TfheThreadingContext ctx = new TfheThreadingContext(NUM_THREADS)) {
      ctx.setServerKey(serverKey);
      ctx.run(() -> {
        for (Ballot ballot : ballots) {
          processBallot(ballot, tallies, zero, one);
        }
      });
    }
    logger.info("[Server] Aggregated {} ballots.", ballots.size());
  }

  private void processBallot(
      Ballot ballot,
      ElectionTallies tallies,
      FheUint8 zero,
      FheUint8 one
  ) {
    try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(ballot.serializedData());
         ProvenCompactCiphertextList proven = ProvenCompactCiphertextList.deserializeConformant(
             buffer, compactPublicKey, crs);
         CompactCiphertextListExpander expander = proven.verifyAndExpand(
             crs, compactPublicKey, ballot.metadata());
         FheBool choiceA = expander.get(0, FheBool.class);
         FheBool choiceB = expander.get(1, FheBool.class);
         FheBool choiceC = expander.get(2, FheBool.class);
         FheUint8 choiceAU8 = choiceA.castInto(FheUint8.class);
         FheUint8 choiceBU8 = choiceB.castInto(FheUint8.class);
         FheUint8 choiceCU8 = choiceC.castInto(FheUint8.class);
         FheUint8 sumAB = choiceAU8.add(choiceBU8);
         FheUint8 totalChoices = sumAB.add(choiceCU8);
         FheBool isValid = totalChoices.equalToScalar((byte) 1);
         FheUint8 contribA = FheUint8.ifThenElse(isValid, choiceAU8, zero);
         FheUint8 contribB = FheUint8.ifThenElse(isValid, choiceBU8, zero);
         FheUint8 contribC = FheUint8.ifThenElse(isValid, choiceCU8, zero);
         FheUint8 isValidU8 = isValid.castInto(FheUint8.class);
         FheUint8 isInvalidU8 = one.subtract(isValidU8)) {

      tallies.alice().addAssign(contribA);
      tallies.bob().addAssign(contribB);
      tallies.charlie().addAssign(contribC);
      tallies.invalid().addAssign(isInvalidU8);

      logger.debug("  Processed ballot: {}", ballot);
    }
  }
}
