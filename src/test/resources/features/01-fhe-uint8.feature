@asciidoc
@order-01
@fhe-uint8
Feature: Unsigned 8-bit Integer Arithmetics

  Scenario: Encrypting and decrypting unsigned 8-bit integers
    Given a ClientKey and a PublicKey are initialized
    When I encrypt 42 as a FheUint8 ciphertext
    Then the FheUint8 ciphertext decrypted using the ClientKey is 42

  Scenario: Adding two FheUint8 ciphertexts
    Given a ClientKey and a PublicKey are initialized
    When I encrypt 5 as a FheUint8 ciphertext
    And I encrypt 10 as another FheUint8 ciphertext
    Then the sum of these FheUint8 ciphertexts decrypted using the ClientKey is 15

  Scenario: Subtracting two FheUint8 ciphertexts
    Given a ClientKey and a PublicKey are initialized
    When I encrypt 20 as a FheUint8 ciphertext
    And I encrypt 7 as another FheUint8 ciphertext
    Then the difference of these FheUint8 ciphertexts decrypted using the ClientKey is 13

  Scenario: Multiplying two FheUint8 ciphertexts
    Given a ClientKey and a PublicKey are initialized
    When I encrypt 6 as a FheUint8 ciphertext
    And I encrypt 7 as another FheUint8 ciphertext
    Then the product of these FheUint8 ciphertexts decrypted using the ClientKey is 42
