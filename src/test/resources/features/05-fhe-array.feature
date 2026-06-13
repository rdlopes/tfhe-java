@asciidoc
@order-05
@fhe-array
Feature: Homomorphic Array Summation

  Scenario: Summing a FheUint8Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "1, 2, 3, 4" as a FheUint8Array
    Then the sum of the FheUint8Array decrypted using the ClientKey is 10

  Scenario: Summing an empty FheUint8Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt an empty list of bytes as a FheUint8Array
    Then the sum of the FheUint8Array decrypted using the ClientKey is 0

  Scenario: Summing a single-element FheUint8Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "42" as a FheUint8Array
    Then the sum of the FheUint8Array decrypted using the ClientKey is 42

  Scenario: Summing a FheUint8Array encrypted with a PublicKey
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "5, 10, 15" as a FheUint8Array using the PublicKey
    Then the sum of the FheUint8Array decrypted using the ClientKey is 30

  Scenario: Summing a FheUint128Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of integers "100, 200, 300" as a FheUint128Array
    Then the sum of the FheUint128Array decrypted using the ClientKey is 600

  Scenario: Summing an empty FheUint128Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt an empty list of integers as a FheUint128Array
    Then the sum of the FheUint128Array decrypted using the ClientKey is 0

  Scenario: Summing a single-element FheUint128Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of integers "1000" as a FheUint128Array
    Then the sum of the FheUint128Array decrypted using the ClientKey is 1000

  Scenario: Summing a FheUint128Array encrypted with a PublicKey
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of integers "50, 75, 25" as a FheUint128Array using the PublicKey
    Then the sum of the FheUint128Array decrypted using the ClientKey is 150
