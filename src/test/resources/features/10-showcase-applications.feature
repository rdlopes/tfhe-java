@asciidoc
@order-10
@user-manual
@showcase
Feature: Real-World Showcase Applications

  This chapter describes real-world applications of Torus Fully Homomorphic Encryption (TFHE) implemented in the showcase suite of the TFHE-Java library.

  == Privacy-Preserving Breast Cancer Prediction
  Clinical and genomic data represent highly sensitive information. In cloud-based diagnostics, patients and healthcare providers want to perform classification without exposing raw clinical features to the cloud server.

  TFHE-Java enables secure, privacy-preserving classification by running inference on **encrypted patient features** using a model pre-trained offline.

  === Enriched Breast Cancer Dataset
  We use the **Breast Cancer Wisconsin (Original)** dataset from the UCI Machine Learning Repository, containing 9 cellular features assessed on a scale of 1 to 10.
  To showcase the combination of cellular and demographic sensitive data, we enrich the dataset with three synthetic features:
  * **Age Scale**: Patient's age divided by 10 (scale 2 to 9).
  * **Genetic Risk Factor**: A score representing genomic mutation indicators (scale 1 to 10).
  * **Family History**: A score representing familial cancer incidence (scale 1 to 10).

  === Model Optimization & Integer Weights
  A classifier is trained offline using Gradient Descent. To allow highly efficient evaluation in FHE, the continuous model weights are scaled and rounded to integer values (Best Scale: 8, yielding a classification accuracy of **97.51%**):
  * **Integer Weights**: `[0, 3, 1, 1, -2, 2, -1, 1, 0, 1, 3, 2]`
  * **Integer Bias**: `-31`

  The score is computed homomorphically as:
  \[score = \sum_{i=1}^{12} w_i \cdot x_i + bias\]

  A prediction of malignancy is homomorphically made if \(score \ge 0\).

  === 1. Setup and Encryption
  The client initializes the cryptographic key pair, derives a `PublicKey`, and encrypts all 12 patient features:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/CancerPredictionShowcase.java[tags=cancer_setup]
  ----

  === 2. Homomorphic Classification
  The server receives the encrypted features and evaluates the linear classification score and sign activation homomorphically using scalar multiplications, avoiding noise-heavy ciphertext-to-ciphertext operations:
  [source,java]
  ----
  include::../../src/main/java/io/github/rdlopes/tfhe/showcase/CancerPredictionShowcase.java[tags=cancer_inference]
  ----

  === Expected Execution Logs
  The showcase outputs the following console logs during execution, demonstrating the key generation, encryption, homomorphic inference, and final client decryption:
  [source,text]
  ----
  Starting TFHE Privacy-Preserving Cancer Prediction Showcase
  =================================================================
  Initializing FHE keys (Client-side)...
  Keys initialized in 345 ms.
  Encrypting sensitive patient records...
    Encrypted patient data for: Patient-001
    Encrypted patient data for: Patient-002
    Encrypted patient data for: Patient-003
    Encrypted patient data for: Patient-004
  Evaluating cancer predictions homomorphically (Server-side)...
  Homomorphic evaluation completed in 410 ms.
  Decrypting and verifying results (Client-side)...
    Patient-001: Decrypted Score = -87 | Predicted = Benign | True Label = Benign | Verification: SUCCESS
    Patient-002: Decrypted Score = 170 | Predicted = Malignant | True Label = Malignant | Verification: SUCCESS
    Patient-003: Decrypted Score = -14 | Predicted = Benign | True Label = Benign | Verification: SUCCESS
    Patient-004: Decrypted Score = -156 | Predicted = Benign | True Label = Benign | Verification: SUCCESS
  Showcase completed successfully! FHE cancer prediction matches ground truth values.
  ----

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

  Scenario: Running the Privacy-Preserving Cancer Prediction Showcase
    Given the TFHE cancer prediction model is loaded with pre-trained weights
    When I execute the cancer prediction showcase pipeline
    Then the verification logs confirm correct predictions matches ground truth

  Scenario: Running the Privacy-Preserving Voting Flow Showcase
    Given the TFHE election setup is prepared with ZK-proven ballots
    When I execute the voting flow showcase pipeline
    Then the verification logs confirm correct tallies for all candidates


