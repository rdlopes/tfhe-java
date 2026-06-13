@asciidoc
@order-10
@user-manual
@showcase
Feature: Real-World Showcase Applications

  This chapter describes real-world applications of Torus Fully Homomorphic Encryption (TFHE) implemented in the showcase suite of the TFHE-Java library.

  [NOTE]
  ====
  Other showcases, such as the Privacy-Preserving Breast Cancer Prediction (`CancerPredictionShowcase`) and Perceptron Training (`PerceptronTrainingShowcase`), are available in the showcase suite of the repository but are not detailed in this chapter.
  ====

  == Privacy-Preserving Voting Flow
  Electronic voting requires voter anonymity, ballot confidentiality, and verification of ballot validity. In a fully homomorphic voting protocol, votes must be tally-aggregated without decrypting individual choices, and spoiled or double votes must be rejected.

  TFHE-Java enables secure, privacy-preserving voting by combining **Zero-Knowledge Proofs (ZKPs)** for ballot conformance verification on the server, and **Rayon multi-threading** to process ballots in parallel.

  === 1. Cryptographic Setup and CRS
  The election coordinator initializes the Common Reference String (CRS) and the public keys:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[lines=60-78]
  ----

  === 2. Ballot Packing and Proving (Voter-Side)
  Voters pack their selections (e.g. choice A, B, C) and generate a Zero-Knowledge Proof to prove they only voted for a valid candidate choice without revealing their vote:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[lines=84-97]
  ----

  === 3. Parallel Tally Aggregation (Server-Side)
  The server verifies the ZKPs and aggregates valid votes in parallel inside a Rayon threading context:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[lines=111-167]
  ----

  Scenario: Running the Privacy-Preserving Voting Flow Showcase
    Given the TFHE election setup is prepared with ZK-proven ballots
    When I execute the voting flow showcase pipeline
    Then the verification logs confirm correct tallies for all candidates


