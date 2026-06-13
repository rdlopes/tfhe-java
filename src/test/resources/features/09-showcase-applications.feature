@asciidoc
@order-09
@user-manual
@showcase
Feature: Real-World Showcase Applications

  This chapter describes real-world applications of Torus Fully Homomorphic Encryption (TFHE) implemented in the showcase suite of the TFHE-Java library.
  These showcases demonstrate how to combine core cryptographic primitives, keys, ciphertexts, and multi-threading contexts to build privacy-preserving pipelines.

  === Privacy-Preserving Breast Cancer Prediction

  Medical diagnostics often require evaluating machine learning models on sensitive patient data without exposing patient records or the model's proprietary weights. Fully Homomorphic Encryption allows a medical server to evaluate a pre-trained model directly on encrypted features.

  This showcase implements the homomorphic evaluation of a pre-trained linear classification model using the enriched Breast Cancer Wisconsin dataset. The model evaluates 12 patient features (such as clump thickness, uniformity of cell size, bare nuclei, family history, etc.) against pre-trained integer weights and a bias.

  === 1. Cryptographic Setup (Client-Side)
  The client initializes the cryptographic parameters, generates the FHE keys, and encrypts the patient records:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/CancerPredictionShowcase.java[tags=cancer_setup]
  ----

  === 2. Homomorphic Evaluation (Server-Side)
  The server performs homomorphic inference by computing the dot product of the encrypted patient features and the pre-trained integer weights, adds the bias, and applies a homomorphic threshold (score >= 0):
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/CancerPredictionShowcase.java[tags=cancer_inference]
  ----

  === Privacy-Preserving Voting Flow

  Electronic voting requires voter anonymity, ballot confidentiality, and verification of ballot validity. In a fully homomorphic voting protocol, votes must be tally-aggregated without decrypting individual choices, and spoiled or double votes must be rejected.

  TFHE-Java enables secure, privacy-preserving voting by combining **Zero-Knowledge Proofs (ZKPs)** for ballot conformance verification on the server, and **Rayon multi-threading** to process ballots in parallel.

  === 1. Cryptographic Setup and CRS
  The election coordinator initializes the Common Reference String (CRS) and the public keys:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[tags=voting_setup]
  ----

  === 2. Ballot Packing and Proving (Voter-Side)
  Voters pack their selections (e.g. choice A, B, C) and generate a Zero-Knowledge Proof to prove they only voted for a valid candidate choice without revealing their vote:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[tags=voting_ballots]
  ----

  === 3. Parallel Tally Aggregation (Server-Side)
  The server verifies the ZKPs and aggregates valid votes in parallel inside a Rayon threading context:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/VotingFlowShowcase.java[tags=voting_tally]
  ----

  === Privacy-Preserving Perceptron Training

  Machine learning models can be trained directly on encrypted datasets. While training is more computationally intensive than inference, it guarantees that training data remains completely confidential.

  This showcase trains a single-layer perceptron homomorphically using a 2D dataset. It updates model weights and bias over multiple training epochs by evaluating activation, prediction, error, and weight deltas homomorphically.

  === 1. Cryptographic Setup and Dataset Encryption
  The client generates keys and encrypts the training dataset:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/PerceptronTrainingShowcase.java[tags=perceptron_setup]
  ----

  === 2. Homomorphic Training Loop
  The training server runs the training loop over multiple epochs, updating weights and bias homomorphically for each training point:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/PerceptronTrainingShowcase.java[tags=perceptron_training]
  ----

  Scenario: Running the Privacy-Preserving Breast Cancer Prediction Showcase
    Given the TFHE breast cancer prediction setup is prepared with encrypted patient records
    When I execute the breast cancer prediction showcase pipeline
    Then the verification logs confirm correct predictions matching ground truth values
