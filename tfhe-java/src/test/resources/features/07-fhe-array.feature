@asciidoc
@order-07
@core-api
@fhe-array
@gpu
Feature: Homomorphic Array Summation

  This chapter describes homomorphic array summation and batch operations on collections of ciphertexts in the TFHE-Java library.

  === Homomorphic Arrays
  TFHE-Java provides specialized collections for batch encryption and operations:
  * **Unsigned Arrays** (`FheUint8Array`, `FheUint16Array`, `FheUint32Array`, `FheUint64Array`, `FheUint128Array`, `FheUint256Array`).
  * **Signed Arrays** (`FheInt8Array`, `FheInt16Array`, `FheInt32Array`, `FheInt64Array`, `FheInt128Array`, `FheInt256Array`).

  === Parallel Summation
  Summing elements in a large array sequentially is slow because of the linear buildup of noise and sequential PBS runs.
  To optimize performance, TFHE-Java array summation uses parallel prefix-tree reduction under the hood, significantly speeding up the evaluation.

  === Code Example
  [source,java]
  ----
  byte[] inputData = {1, 2, 3, 4};
  FheUint8Array array = FheUint8Array.encrypt(inputData, clientKey);
  
  // Computes the sum of all elements in parallel
  FheUint8 sumResult = array.sum();
  byte plainSum = sumResult.decrypt(clientKey); // Decrypts to 10
  ----

  [TIP]
  ====
  Array summation handles boundary conditions (such as single-element arrays or empty arrays) automatically, returning a trivial ciphertext of value `0` on empty array sum.
  ====

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

  Scenario: Summing a FheInt8Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "1, -2, 3, -4" as a FheInt8Array
    Then the sum of the FheInt8Array decrypted using the ClientKey is -2

  Scenario: Summing a FheInt128Array
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of integers "100, -200, 300" as a FheInt128Array
    Then the sum of the FheInt128Array decrypted using the ClientKey is 200

  Scenario: Performing homomorphic element-wise addition on FheUint8Array arrays
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "1, 2, 3, 4" as a FheUint8Array
    And I encrypt the list of bytes "10, 20, 30, 40" as another FheUint8Array
    And I perform homomorphic element-wise addition of the two FheUint8Array arrays
    Then the element-wise array result decrypted using the ClientKey is "11, 22, 33, 44"

  Scenario: Performing homomorphic element-wise subtraction on FheUint8Array arrays
    Given a ClientKey and a PublicKey are initialized
    When I encrypt the list of bytes "10, 20, 30, 40" as a FheUint8Array
    And I encrypt the list of bytes "1, 2, 3, 4" as another FheUint8Array
    And I perform homomorphic element-wise subtraction of the two FheUint8Array arrays
    Then the element-wise array result decrypted using the ClientKey is "9, 18, 27, 36"

