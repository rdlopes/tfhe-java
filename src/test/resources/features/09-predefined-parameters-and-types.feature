@asciidoc
@order-09
@appendix
Feature: Predefined Parameters and Cryptographic Types

  [appendix]
  == Predefined Parameters and Cryptographic Types

  === Predefined Shortint Parameters
  TFHE-Java provides preset parameter configurations in the `CustomParameters` enum. These configurations define the security level, message precision, and carry bits.
  The naming convention is structured as follows:
  `SHORTINT_PARAM_MESSAGE_X_CARRY_Y_KS_PBS_DIST_LEVEL`

  [cols="3,1,1,2,2", options="header"]
  |===
  | Parameter Preset | Message Bits | Carry Bits | Noise Distribution | Security Level
  | `SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128` | 2 | 2 | TUniform | 128-bit
  | `SHORTINT_V1_0_PARAM_MESSAGE_1_CARRY_1_KS_PBS_GAUSSIAN_2M128` | 1 | 1 | Gaussian | 128-bit
  | `SHORTINT_V1_0_PARAM_MESSAGE_3_CARRY_3_KS_PBS_GAUSSIAN_2M128` | 3 | 3 | Gaussian | 128-bit
  | `SHORTINT_V1_0_PARAM_MESSAGE_4_CARRY_4_KS_PBS_GAUSSIAN_2M128` | 4 | 4 | Gaussian | 128-bit
  |===

  === Choosing the Right Parameters
  When configuring a TFHE keyset, developers must balance security, performance, and noise tolerance:
  * **Message Bits**: Specifies the number of bits available for storing plaintext integers (1 to 8 bits). Higher message bits allow representing larger values directly but increase bootstrap/PBS computation time.
  * **Carry Bits**: Specifies the extra space allocated for arithmetic overflow bits. Carry bits prevent noise overflow during leveled operations, delaying the need for a bootstrapping noise cleanup.
  * **Noise Distribution**:
    - **Centered Normal (Gaussian)**: Standard mathematical distribution used for cryptographically secure, production-grade applications.
    - **Tweaked Uniform (TUniform)**: Bounded noise distribution optimized for testing, offering faster operations under slightly modified noise bounds.
  * **Zero-Knowledge Proofs (ZKP) CRS**:
    - The Common Reference String (CRS) generated on the client-side allows producing zero-knowledge proofs.
    - The number of blocks verified determines the CRS generation time and key size. Serializing and caching the CRS on the client-side avoids initialization overhead on startup.

  === High-Level Cryptographic Types
  TFHE-Java supports signed and unsigned integer types, booleans, and arrays:

  [cols="2,2,1,3", options="header"]
  |===
  | FHE Type | Plain Java Type | Bit Width | Signedness
  | `FheBool` | `boolean` | 1 | Unsigned
  | `FheUint8` | `byte` / `int` | 8 | Unsigned
  | `FheUint16` | `short` / `int` | 16 | Unsigned
  | `FheUint32` | `int` | 32 | Unsigned
  | `FheUint64` | `long` | 64 | Unsigned
  | `FheUint128` | `BigInteger` | 128 | Unsigned
  | `FheUint256` | `BigInteger` | 256 | Unsigned
  | `FheInt8` | `byte` | 8 | Signed
  | `FheInt16` | `short` | 16 | Signed
  | `FheInt32` | `int` | 32 | Signed
  | `FheInt64` | `long` | 64 | Signed
  | `FheInt128` | `BigInteger` | 128 | Signed
  | `FheInt256` | `BigInteger` | 256 | Signed
  |===

  Scenario: Root
