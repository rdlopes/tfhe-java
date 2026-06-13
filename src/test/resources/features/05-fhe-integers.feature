@asciidoc
@order-05
@core-api
@fhe-integers
Feature: High-Level Integer API (FheInt* and FheUint*)

  This chapter describes the high-level integer API of the TFHE-Java library, which supports type-safe homomorphic arithmetic, bitwise, comparison, and conditional operations on signed and unsigned integers of varying bit widths.

  === Supported Integer Types
  TFHE-Java provides complete type-safe wrappers for both signed and unsigned integers corresponding to standard and extended bit widths:
  * **Signed Integers** (`FheInt2` through `FheInt2048`): Represent values in 2's complement format.
  * **Unsigned Integers** (`FheUint2` through `FheUint2048`): Represent positive values from \(0\) to \(2^k - 1\) for a \(k\)-bit integer.

  For widths up to 64 bits, standard Java primitives (`byte`, `short`, `int`, `long`) are used for encryption/decryption. For larger widths (128 to 2048 bits), the library interfaces with `BigInteger` wrappers (e.g., `U128`, `U256`).

  === Homomorphic Operations
  The high-level integer classes implement a uniform set of interfaces:

  === Arithmetic Operations
  * **Addition** (`add` / `addScalar` / `addAssign` / `addScalarAssign`): Computes the sum of two ciphertexts, or a ciphertext and a public constant.
  * **Subtraction** (`subtract` / `subtractScalar` / `subtractAssign` / `subtractScalarAssign`): Computes the difference.
  * **Multiplication** (`multiply` / `multiplyScalar` / `multiplyAssign` / `multiplyScalarAssign`): Computes the product.
  * **Division and Modulo** (`divide` / `modulo` / `divideScalar` / `moduloScalar`): Computes modular division and remainder.

  [WARNING]
  ====
  Division and modulo operations in FHE are computationally expensive because they require homomorphic comparisons and programmable bootstrapping. Avoid them in latency-critical loops.
  ====

  === Bitwise & Shift Operations
  * **Bitwise Gates** (`bitAnd`, `bitOr`, `bitXor`, `bitNot`): Computes element-wise logical operations.
  * **Shifts** (`shiftLeft`, `shiftRight`): Shifts bits left or right (arithmetic shift for signed, logical shift for unsigned).

  === Comparison Operations
  * **Equality** (`equalTo`, `notEqualTo`): Checks if values are equal or not.
  * **Relational** (`lessThan`, `lessThanOrEqualTo`, `greaterThan`, `greaterThanOrEqualTo`): Checks ordering relations. These return an `FheBool` ciphertext.

  === Conditional Selection (Multiplexing)
  * **Ternary Operator** (`ifThenElse`): Homomorphically selects a value based on an encrypted boolean condition without branching:
    [source,java]
    ----
    FheUint8 result = FheUint8.ifThenElse(condition, thenValue, elseValue);
    ----
    Under the hood, this evaluates \(condition \cdot thenValue + (1 - condition) \cdot elseValue\) securely.

  === Overflow Semantics
  All operations on a \(k\)-bit integer are computed modulo \(2^k\). Any computation exceeding this range will wrap around, matching standard Java integer overflow behavior.

  === Code Example
  [source,java]
  ----
  FheUint8 a = FheUint8.encrypt((byte) 200, clientKey);
  FheUint8 b = FheUint8.encrypt((byte) 60, clientKey);
  FheUint8 sum = a.add(b); // Encrypted 4 (due to overflow: 260 mod 256)
  ----

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
