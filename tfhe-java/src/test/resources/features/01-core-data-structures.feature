@asciidoc
@order-01
@user-manual
@core-structures
@gpu
Feature: Core Data Structures and Cryptographic Primitives

  This chapter explains the core data structures and cryptographic primitives of the TFHE-Java library.
  TFHE-Java translates native `tfhe-rs` primitives into type-safe, resource-managed Java classes.

  === Cryptographic Primitives Overview
  The security and evaluation lifecycle in Torus FHE relies on two main categories of objects:
  1. **Keys**: Private keys for encryption/decryption, public keys for third-party encryption, and server keys for homomorphic evaluations.
  2. **Ciphertexts**: Encrypted data blocks representing booleans, signed/unsigned integers, arrays, or compact lists.

  === Key Types Reference

  [cols="2,1,1,4", options="header"]
  |===
  | Class | Secret? | Shareable? | Role

  | `ClientKey`
  | ✅ Yes
  | ❌ Never
  | Root of trust. Used by the client to **encrypt** plaintext values and **decrypt** computation results.
  Never leaves the client.

  | `CompressedServerKey`
  | ❌ No
  | ✅ Yes — send to server
  | The **canonical server key**. Compact representation from which both the CPU `ServerKey` and GPU `CudaServerKey` can be derived.
  Call `.use()` to activate FHE operations transparently (CPU, or CPU+GPU with `-Dtfhe.gpu=true`).
  **Always serialize this key** when transmitting to a remote server.

  | `ServerKey`
  | ❌ No
  | ✅ Yes
  | Decompressed **CPU-only** evaluation key. Obtained from `CompressedServerKey` via `.decompress()` or from `KeySet.getServerKey()`.
  Used as a conformant key when deserializing ciphertexts.
  Does **not** support GPU activation — use `CompressedServerKey.use()` for that.

  | `PublicKey`
  | ❌ No
  | ✅ Yes
  | Derived from `ClientKey`. Enables **third parties to encrypt data** without possessing the secret key.

  | `CompressedCompactPublicKey`
  | ❌ No
  | ✅ Yes
  | Compact representation of a `CompactPublicKey`. Used in zero-knowledge proof workflows and compact ciphertext lists.
  |===

  [IMPORTANT]
  ====
  The **`ClientKey`** is the root of trust and must remain private on the client side.
  The **`CompressedServerKey`** is the key to transmit to the server — not the `ServerKey`.
  Sending a `CompressedServerKey` allows the server to enable GPU acceleration independently,
  without any changes to the client's code or the serialized format.
  ====

  === Core Ciphertext Types
  TFHE-Java provides types corresponding to standard Java primitives:
  * **Value Types** (`FheBool`, `FheInt*`, `FheUint*`): Wrapper classes representing encrypted values.
  * **Shortint Types** (`ShortintCiphertext`): Small integers with parameterized precision (1 to 8 bits).
  * **Compressed Types** (`CompressedFheBool`, `CompressedFheInt*`, `CompressedFheUint*`): Bandwidth-efficient ciphertext representations.
  * **Array Types** (`FheBoolArray`, `FheInt*Array`, `FheUint*Array`): Batch ciphertext collections.

  === Native Memory Safety & Resource Management
  Since the underlying cryptographic operations are executed in Rust via Java's Foreign Function & Memory (FFM) API, all FHE objects allocate off-heap native memory. Failing to release these resources will result in native memory leaks.

  TFHE-Java enforces memory safety through:
  * **AutoCloseable Buffers**: Classes like `DynamicBuffer` and `ProvenCompactCiphertextList` implement `AutoCloseable` and should be managed using the `try-with-resources` pattern.
  * **Manual Clean-up**: In-memory ciphertexts (e.g., `FheUint8`, `FheInt32`, `ShortintCiphertext`) expose a `.destroy()` method that explicitly frees the underlying native pointer.

  [IMPORTANT]
  ====
  Always call `.destroy()` on intermediate ciphertexts created inside loops (such as neural network training epochs or inference sweeps) to prevent native RAM exhaustion.
  ====

  === Configurations and Key Generation
  Before performing encryption, you must configure security parameters using the `ConfigBuilder`.
  The configuration determines parameters like LWE dimension, polynomial size, noise distribution, and security level.

  === Configuration Builder
  To construct a standard or custom configuration:
  [source,java]
  ----
  Config config = new ConfigBuilder().build();
  ----

  Below are the unit tests validating the configuration builder's behavior:
  [source,java]
  ----
  include::../../src/test/java/io/github/rdlopes/tfhe/api/keys/ConfigBuilderTest.java[tags=builder_tests]
  ----

  === Client and Server Keys
  A `KeySet` bundles a `ClientKey` and a `CompressedServerKey` together.
  The `CompressedServerKey` is the canonical server key that should be serialized and sent to the server.
  Here is an example showing how the client key serializes and deserializes:
  [source,java]
  ----
  include::../../src/test/java/io/github/rdlopes/tfhe/api/keys/ClientKeyTest.java[tags=client_key_serialization]
  ----

  Scenario: Initializing a configuration and generating a KeySet
    Given a standard configuration is prepared
    When the keys are generated
    Then the ClientKey and CompressedServerKey are initialized and ready
