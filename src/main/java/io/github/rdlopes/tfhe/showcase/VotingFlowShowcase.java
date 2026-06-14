package io.github.rdlopes.tfhe.showcase;

import io.github.rdlopes.tfhe.api.TfheThreadingContext;
import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;

public class VotingFlowShowcase {

  private static final Logger logger = LoggerFactory.getLogger(VotingFlowShowcase.class);
  
  // tag::voting_tallies[]
  public static final class ElectionTallies implements AutoCloseable {
    public FheUint8 alice;
    public FheUint8 bob;
    public FheUint8 charlie;
    public FheUint8 invalid;

    public ElectionTallies(PublicKey publicKey) {
      this.alice = FheUint8.encrypt((byte) 0, publicKey);
      this.bob = FheUint8.encrypt((byte) 0, publicKey);
      this.charlie = FheUint8.encrypt((byte) 0, publicKey);
      this.invalid = FheUint8.encrypt((byte) 0, publicKey);
    }
    
    @Override
    public void close() {
      if (alice != null) alice.close();
      if (bob != null) bob.close();
      if (charlie != null) charlie.close();
      if (invalid != null) invalid.close();
    }
  }
  // end::voting_tallies[]

  public record Ballot(byte[] serializedData, byte[] metadata) {}

  public static void main(String[] args) {
    logger.info("=================================================================");
    logger.info("Starting TFHE Privacy-Preserving Voting Flow Showcase");
    logger.info("=================================================================");

    // tag::voting_setup[]
    // 1. Setup election parameters & CRS
    logger.info("Preparing election cryptographic setup & CRS...");
    long startTime = System.currentTimeMillis();
    ConfigBuilder crsBuilder = new ConfigBuilder();
    execute(() -> use_dedicated_compact_public_key_parameters(
        crsBuilder.getAddress(),
        CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
    ));
    Config crsConfig = crsBuilder.build();
    
    try (CompactPkeCrs crs = new CompactPkeCrs(crsConfig, 8);
         KeySet keySet = KeySet.builder()
                               .useCompactKeyEncryptionParameters(
                                 CompactPublicKeyEncryptionParameters
                                   .SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                               .build();
         CompactPublicKey compactPublicKey = new CompactPublicKey(keySet.getClientKey());
         PublicKey publicKey = new PublicKey(keySet.getClientKey());
         ElectionTallies tallies = new ElectionTallies(publicKey);
         FheUint8 zero = FheUint8.encrypt((byte) 0, publicKey);
         FheUint8 one = FheUint8.encrypt((byte) 1, publicKey)) {
      
      keySet.getServerKey().use();
      logger.info("Setup completed in {} ms.", System.currentTimeMillis() - startTime);
      // end::voting_setup[]
      
      // 2. Submit ballots (encrypted & ZK-proven)
      logger.info("Submitting voter ballots with ZK proofs...");
      List<Ballot> ballots = new ArrayList<>();
      
      class BallotBuilder {
        // tag::voting_ballots[]
        void vote(String voterName, boolean choiceA, boolean choiceB, boolean choiceC) {
          CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);
          builder.push(choiceA);
          builder.push(choiceB);
          builder.push(choiceC);
          byte[] metadata = voterName.getBytes();
          try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF);
               DynamicBuffer serialized = provenList.serialize()) {
            ballots.add(new Ballot(serialized.toByteArray(), metadata));
            logger.info("  Ballot submitted for voter: {}", voterName);
          }
        }
        // end::voting_ballots[]
      }
      BallotBuilder bb = new BallotBuilder();
      
      bb.vote("Alice", true, false, false);
      bb.vote("Bob", false, true, false);
      bb.vote("Charlie", false, false, true);
      bb.vote("Dave", true, false, false);
      bb.vote("Eve", false, false, false);
      bb.vote("Frank", true, true, false);
      bb.vote("Grace", false, false, true);
      
      logger.info("Submitted {} ballots.", ballots.size());
      
      // tag::voting_tally[]
      // 3. Tally Server Processes & Aggregates Ballots inside Rayon Context
      logger.info("Tally server processing and aggregating ballots homomorphically...");
      long tallyStart = System.currentTimeMillis();
      
      try (TfheThreadingContext threadContext = new TfheThreadingContext(2)) {
        threadContext.setServerKey(keySet.getServerKey());
        threadContext.run(() -> {
          for (Ballot ballot : ballots) {
            try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(ballot.serializedData());
                 ProvenCompactCiphertextList provenList = ProvenCompactCiphertextList.deserializeConformant(buffer, compactPublicKey, crs);
                 CompactCiphertextListExpander expander = provenList.verifyAndExpand(crs, compactPublicKey, ballot.metadata());
                 FheBool choiceA = expander.get(0, FheBool.class);
                 FheBool choiceB = expander.get(1, FheBool.class);
                 FheBool choiceC = expander.get(2, FheBool.class);
                 FheUint8 choiceAU8 = choiceA.castInto(FheUint8.class);
                 FheUint8 choiceBU8 = choiceB.castInto(FheUint8.class);
                 FheUint8 choiceCU8 = choiceC.castInto(FheUint8.class);
                 FheUint8 sumAB = choiceAU8.add(choiceBU8);
                 FheUint8 sum = sumAB.add(choiceCU8);
                 FheBool isValid = sum.equalToScalar((byte) 1);
                 FheUint8 contribA = FheUint8.ifThenElse(isValid, choiceAU8, zero);
                 FheUint8 contribB = FheUint8.ifThenElse(isValid, choiceBU8, zero);
                 FheUint8 contribC = FheUint8.ifThenElse(isValid, choiceCU8, zero);
                 FheUint8 isValidU8 = isValid.castInto(FheUint8.class);
                 FheUint8 isInvalidU8 = one.subtract(isValidU8)) {
              
              tallies.alice.addAssign(contribA);
              tallies.bob.addAssign(contribB);
              tallies.charlie.addAssign(contribC);
              tallies.invalid.addAssign(isInvalidU8);
            }
          }
        });
      }
      
      logger.info("Tally aggregation completed in {} ms.", System.currentTimeMillis() - tallyStart);
      // end::voting_tally[]
      
      // 4. Decrypt and verify results
      logger.info("Decrypting and verifying election results...");
      byte finalAlice = tallies.alice.decrypt(keySet.getClientKey());
      byte finalBob = tallies.bob.decrypt(keySet.getClientKey());
      byte finalCharlie = tallies.charlie.decrypt(keySet.getClientKey());
      byte finalInvalid = tallies.invalid.decrypt(keySet.getClientKey());
      
      logger.info("\n--- Election Tally Results ---");
      logger.info("Candidate 0 (Alice choice):   {} votes", finalAlice);
      logger.info("Candidate 1 (Bob choice):     {} votes", finalBob);
      logger.info("Candidate 2 (Charlie choice): {} votes", finalCharlie);
      logger.info("Invalid / Spoiled Ballots:    {} votes", finalInvalid);
      logger.info("------------------------------\n");
      
      boolean correct = (finalAlice == 2 && finalBob == 1 && finalCharlie == 2 && finalInvalid == 2);
      if (correct) {
        logger.info("Showcase completed successfully! Election results decrypted and verified correctly.");
      } else {
        logger.error("Showcase failed: Election results do not match expected tallies.");
        System.exit(1);
      }
    }

    logger.info("Done.");
  }
}
