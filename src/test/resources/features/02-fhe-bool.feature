@asciidoc
@order-02
@fhe-bool
Feature: Boolean Logic and Casting

  Scenario: Encrypting and decrypting boolean values
    Given a ClientKey and a PublicKey are initialized
    When I encrypt true as a FheBool ciphertext
    Then the FheBool ciphertext decrypted using the ClientKey is true

  Scenario: Performing AND logic operation
    Given a ClientKey and a PublicKey are initialized
    When I encrypt true as a FheBool ciphertext
    And I encrypt false as another FheBool ciphertext
    Then the bitwise AND of these FheBool ciphertexts decrypted using the ClientKey is false

  Scenario: Performing OR logic operation
    Given a ClientKey and a PublicKey are initialized
    When I encrypt true as a FheBool ciphertext
    And I encrypt false as another FheBool ciphertext
    Then the bitwise OR of these FheBool ciphertexts decrypted using the ClientKey is true

  Scenario: Performing XOR logic operation
    Given a ClientKey and a PublicKey are initialized
    When I encrypt true as a FheBool ciphertext
    And I encrypt true as another FheBool ciphertext
    Then the bitwise XOR of these FheBool ciphertexts decrypted using the ClientKey is false

  Scenario: Casting FheBool to FheUint8
    Given a ClientKey and a PublicKey are initialized
    When I encrypt true as a FheBool ciphertext
    And I cast the FheBool ciphertext into a FheUint8 ciphertext
    Then the FheUint8 ciphertext decrypted using the ClientKey is 1
