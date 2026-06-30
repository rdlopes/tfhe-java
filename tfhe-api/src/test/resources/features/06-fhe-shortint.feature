@asciidoc
@order-06
@low-level-api
@fhe-shortint
Feature: Low-Level Shortint API and Programmable Bootstrapping (PBS)

  This chapter describes the low-level `shortint` API, which offers fine-grained control over noise levels, carrying capacity, and Programmable Bootstrapping (PBS).

  === The shortint Representation
  Unlike the high-level API which automatically manages noise and carries, the `shortint` API exposes individual ciphertexts parameterized with:
  * **Message Modulus** (\(M\)): The size of the message space (number of bits we can store).
  * **Carry Modulus** (\(C\)): Additional space to hold carries during additions.

  === Smart vs. Unchecked Operations
  The `shortint` API exposes two operation modes:
  1. **Smart Operations**: Automatically check if the ciphertext has accumulated too much noise. If so, a Programmable Bootstrapping (PBS) is automatically run on the server to clean the noise and reset the ciphertext to a nominal noise state.
  2. **Unchecked Operations**: Directly compute modular arithmetic without verifying noise bounds. They are much faster but will yield incorrect decryption results if the noise exceeds the parameters' tolerance limits.

  === Programmable Bootstrapping (PBS)
  A Programmable Bootstrapping allows computing an arbitrary function \(f(x)\) on an encrypted value \(x\) while cleaning its noise. It is configured using a **Lookup Table (LUT)**:
  * **Univariate PBS**: Evaluates a function \(f(x)\) on a single ciphertext block.
  * **Bivariate PBS**: Evaluates a function \(f(x, y)\) on two ciphertext blocks.

  === Code Example
  [source,java]
  ----
  // Univariate lookup table for f(x) = (x + 1) % 4
  LookupTable table = serverKey.generateLookupTable(x -> (x + 1) % 4);
  ShortintCiphertext result = serverKey.applyLookupTable(ciphertext, table);
  ----

  [IMPORTANT]
  ====
  The `shortint` API is recommended for building custom circuits, optimized state machines, or complex non-linear functions (like activation functions in neural networks).
  ====

  === Cloning Ciphertexts
  Both `ShortintCiphertext` and `ShortintCompressedCiphertext` support deep copying using native Rust cloning operations (`shortint_ciphertext_clone` and `shortint_compressed_ciphertext_clone`).
  This avoids the CPU and memory overhead of the traditional serialization/deserialization workaround.

  [source,java]
  ----
  // Deep clone a ciphertext natively
  ShortintCiphertext copy = ciphertext.clone();
  ----

  Scenario: Initializing client, server, and public keys with custom parameters
    Given a shortint configuration is prepared with custom parameters
    Then the shortint client, server, and public keys are initialized successfully

  Scenario: Encrypting and decrypting shortint ciphertexts
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    Then the shortint ciphertext decrypted using the client key is 2

  Scenario: Public key encryption of shortint ciphertexts
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 3 as a shortint ciphertext using the public key
    Then the shortint ciphertext decrypted using the client key is 3

  Scenario: Serializing and deserializing shortint keys and ciphertexts
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 1 as a shortint ciphertext
    Then serializing and deserializing the client key, server key, public key, and ciphertext yields identical decryption results

  Scenario: Compressing and decompressing shortint ciphertexts
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a compressed shortint ciphertext
    And I decompress the compressed shortint ciphertext
    Then the decompressed shortint ciphertext decrypted using the client key is 2

  Scenario: Performing smart arithmetic operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 1 as another shortint ciphertext
    Then the smart sum of these ciphertexts is 3
    And the smart difference of these ciphertexts is 1
    And the smart product of these ciphertexts is 2
    And the smart division of these ciphertexts is 2
    And the smart negation of the second ciphertext is 3

  Scenario: Performing smart scalar arithmetic operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    Then the smart scalar sum with 1 is 3
    And the smart scalar difference with 1 is 1
    And the smart scalar product with 1 is 2

  Scenario: Performing unchecked arithmetic operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 1 as another shortint ciphertext
    Then the unchecked sum of these ciphertexts is 3
    And the unchecked difference of these ciphertexts is 1
    And the unchecked product of these ciphertexts is 2
    And the unchecked negation of the second ciphertext is 3

  Scenario: Performing unchecked scalar arithmetic operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    Then the unchecked scalar sum with 1 is 3
    And the unchecked scalar difference with 1 is 1
    And the unchecked scalar product with 1 is 2
    And the unchecked scalar division by 2 is 1
    And the unchecked scalar modulo by 3 is 2

  Scenario: Performing bitwise operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 3 as another shortint ciphertext
    Then the smart bitwise AND of these ciphertexts is 2
    And the smart bitwise OR of these ciphertexts is 3
    And the smart bitwise XOR of these ciphertexts is 1

  Scenario: Performing shift operations
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 1 as a shortint ciphertext
    Then shifting the first ciphertext left by 1 yields 2
    And shifting the result right by 1 yields 1

  Scenario: Performing comparisons
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 3 as another shortint ciphertext
    Then checking if the first is less than the second yields true
    And checking if the first is less than 1 yields false
    And checking if they are equal yields false
    And checking if the first is equal to 2 yields true

  Scenario: Performing univariate programmable bootstrapping
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I generate a univariate lookup table for f(x) = (x + 1) % 4
    Then evaluating the lookup table on the ciphertext yields 3

  Scenario: Performing bivariate programmable bootstrapping
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I encrypt 1 as another shortint ciphertext
    And I generate a bivariate lookup table for f(x, y) = (x + y) % 4
    Then evaluating the bivariate lookup table on the ciphertexts yields 3

  Scenario: Cloning shortint ciphertexts and compressed ciphertexts
    Given a shortint configuration is prepared with custom parameters
    When I encrypt 2 as a shortint ciphertext
    And I clone the latest shortint ciphertext
    Then the cloned shortint ciphertext decrypted using the client key is 2
    When I encrypt 3 as a compressed shortint ciphertext
    And I clone the latest compressed shortint ciphertext
    And I decompress the cloned compressed shortint ciphertext
    Then the decompressed shortint ciphertext decrypted using the client key is 3
