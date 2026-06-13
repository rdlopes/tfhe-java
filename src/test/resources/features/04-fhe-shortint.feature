@asciidoc
@order-04
@fhe-shortint
Feature: Low-Level Shortint API and Programmable Bootstrapping (PBS)

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
