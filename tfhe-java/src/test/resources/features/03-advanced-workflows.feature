@asciidoc
@order-03
@user-manual
@advanced-workflows
Feature: Advanced Workflows and Noise Management

  This chapter describes advanced TFHE workflows, including compressed ciphertexts, trivial ciphertexts, the shortint API, and noise management.

  === Compressed Ciphertext Workflow
  For bandwidth-constrained environments, you can generate keys and compress ciphertexts before sending them.
  This significantly reduces transmission size. On the server, the compressed ciphertext is deserialized and decompressed before evaluation.

  Here are the unit tests validating key setups with compression support enabled:
  [source,java]
  ----
  include::../../src/test/java/io/github/rdlopes/tfhe/api/keys/KeySetTest.java[tags=keyset_advanced_builders]
  ----

  === Trivial Ciphertext Workflow
  A *trivial ciphertext* is created without actual encryption using a public value.
  It is useful for mixing encrypted variables and public plaintext constants in evaluations without generating encryption noise or requiring the private client key.
  [source,java]
  ----
  FheUint32 trivial = FheUint32.encrypt(42); // Trivial encryption
  ----

  === Shortint API Workflow
  For applications requiring fine-grained control over message vs. carry bits allocation:
  - You initialize custom parameters detailing message/carry size.
  - You execute **Smart** operations (which automatically run Programmable Bootstrapping (PBS) via the ServerKey to reduce noise) or **Unchecked** operations (raw, fast operations without noise management).

  === Noise Management and PBS
  Noise accumulates during homomorphic operations. The high-level integer API automatically manages noise via bootstrapping (`PBS`) using the `ServerKey`.
  In the `shortint` API, noise management is directly exposed:
  * **Leveled Operations**: Fast operations (addition, scalar multiplication) that increase noise.
  * **PBS Operations**: Reduces noise back to nominal levels, allowing infinite consecutive operations.

  Scenario: Compressed Ciphertext Workflow
    Given a standard configuration is prepared
    And the keys are generated
    When I encrypt 100 directly into a compressed FheUint32 ciphertext on the client side
    And I serialize the compressed ciphertext
    And I deserialize and decompress the ciphertext on the server side
    And I perform homomorphic addition of the decompressed value with 10 on the server side
    Then the decrypted result is 110

  Scenario: Trivial Ciphertext Workflow
    Given a standard configuration is prepared
    And the keys are generated
    When I encrypt 100 as a FheUint32 ciphertext on the client side
    And I create a trivial FheUint32 ciphertext from public value 50
    And I perform homomorphic addition of the encrypted value and the trivial ciphertext
    Then the decrypted result is 150

  Scenario: Shortint API Workflow
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 1 as another shortint ciphertext
    Then the smart sum of these ciphertexts is 3
