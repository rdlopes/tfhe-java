@asciidoc
@order-06
@compact-list
Feature: Compact and Compressed Ciphertext Lists with Zero-Knowledge Proofs

  Scenario: Building, serializing, and expanding a CompactCiphertextList
    Given a ClientKey and a PublicKey are initialized
    When I build a packed compact ciphertext list with:
      | boolean | true          |
      | byte    | 42            |
      | int     | 123456        |
      | long    | 9876543210    |
      | U256    | 1000000000000 |
    And I serialize and deserialize the compact ciphertext list
    Then the expanded list has 5 elements of kinds FheBool, FheInt8, FheInt32, FheInt64, and FheUint256 respectively
    And the decrypted values match the original inputs

  Scenario: Building, serializing, and unpacking a CompressedCiphertextList
    Given a compressed-enabled ClientKey and ServerKey are initialized
    When I compress and build a list with values:
      | boolean | true          |
      | byte    | 42            |
      | int     | 123456        |
      | U256    | 1000000000000 |
    And I serialize and deserialize the compressed ciphertext list
    Then the unpacked list has 4 elements of kinds FheBool, FheUint8, FheInt32, and FheUint256 respectively
    And the decrypted values match the original inputs

  Scenario: Serializing and deserializing a CompactPkeCrs
    Given a compact PKE CRS is created
    Then serializing and deserializing the CRS yields a valid CRS

  Scenario: Building, verifying, and expanding a ProvenCompactCiphertextList
    Given a compact PKE CRS is created
    And a ClientKey and a PublicKey are initialized
    When I build a proven compact ciphertext list with:
      | boolean | true |
      | byte    | 42   |
      | int     | 12   |
    And I serialize and deserialize the proven compact ciphertext list
    Then verifying and expanding the list with metadata "1,2,3,4" succeeds
    And the decrypted elements match the original values

  Scenario: Deserializing a ProvenCompactCiphertextList with conformance check
    Given a compact PKE CRS is created
    And a ClientKey and a PublicKey are initialized
    When I build a proven compact ciphertext list with:
      | boolean | false |
      | byte    | 7     |
    And I deserialize the list with conformance parameters
    Then verifying and expanding the conformant list with metadata "9,8,7" succeeds
    And the decrypted elements match the original values
