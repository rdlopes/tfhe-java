@asciidoc
@order-04
@core-api
@fhe-bool
Feature: Boolean Logic and Casting

  This chapter describes boolean logic operations and type-casting within the high-level integer API of the TFHE-Java library.

  == Encrypted Boolean Types (FheBool)
  An `FheBool` represents an encrypted boolean value (`true` or `false`). Under the hood, it is represented as a 1-bit ciphertext.

  == Supported Operations
  You can perform standard logic gates on `FheBool` ciphertexts:
  * **AND**: Returns the logical conjunction of two ciphertexts.
  * **OR**: Returns the logical disjunction of two ciphertexts.
  * **XOR**: Returns the logical exclusive disjunction of two ciphertexts.
  * **NOT**: Inverts the logical state of a ciphertext.

  === Code Example
  [source,java]
  ----
  FheBool a = FheBool.encrypt(true, clientKey);
  FheBool b = FheBool.encrypt(false, clientKey);
  FheBool result = a.and(b); // Encrypted false
  ----

  == Casting to Integers
  It is often necessary to convert boolean conditions into integer values for use in arithmetic expressions (e.g., selecting values without branching). You can cast an `FheBool` into an `FheUint8` ciphertext using the `.castIntoFheUint8()` method.

  [TIP]
  ====
  Casting boolean ciphertexts to integers is a powerful tool for branchless evaluations:
  `result = cond.castIntoFheUint8().multiply(value);`
  ====

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
