@asciidoc
@order-02
@user-manual
@standard-workflow
Feature: Standard TFHE Workflow

  This chapter describes the standard workflow for performing homomorphic computations using the high-level integer API of the TFHE-Java library.

  === The 5-Step Lifecycle
  A standard Torus FHE computation follows five fundamental steps:
  [plantuml, standard-workflow, svg]
  ....
  !pragma layout smetana
  left to right direction
  skinparam dpi 150
  skinparam PackageStyle rect
  skinparam shadowing false
  skinparam DefaultFontName "Helvetica"
  skinparam DefaultFontSize 12
  skinparam RoundCorner 8

  skinparam rectangle {
      BackgroundColor #F4F6F9
      BorderColor #CBD5E1
      FontColor #1E293B
  }

  rectangle "1. Key Generation" as K
  rectangle "2. Encryption" as E
  rectangle "3. Transmission" as T
  rectangle "4. Homomorphic Computation" as C
  rectangle "5. Decryption" as D

  K -[#94A3B8]-> E
  E -[#94A3B8]-> T
  T -[#94A3B8]-> C
  C -[#94A3B8]-> D
  ....


  === 1. Key Generation (Client-Side)
  The client builds a `KeySet` which bundles a private `ClientKey` with a `CompressedServerKey`.
  The `CompressedServerKey` is the canonical server key — it can produce both a CPU `ServerKey`
  and a GPU `CudaServerKey`, and is the key to send to the server.
  [source,java]
  ----
  KeySet keys = KeySet.builder().build();
  ClientKey clientKey = keys.getClientKey();
  CompressedServerKey compressedKey = keys.getCompressedServerKey();

  // Activate for local computation (CPU, or CPU+GPU with -Dtfhe.gpu=true)
  compressedKey.use();
  ----

  The `ServerKey` (CPU-only, decompressed view) remains accessible for use as a conformant key
  when deserializing ciphertexts:
  [source,java]
  ----
  ServerKey serverKey = keys.getServerKey(); // CPU key for ciphertext deserialization
  ----

  Here are the unit tests validating key set generation and customization:
  [source,java]
  ----
  include::../../src/test/java/io/github/rdlopes/tfhe/api/keys/KeySetTest.java[tags=keyset_builder]
  ----

  === 2. Encryption (Client-Side)
  The client encrypts private values into type-safe ciphertexts using the `ClientKey` or a derived `PublicKey`.
  [source,java]
  ----
  FheUint32 encrypted = FheUint32.encrypt(42, clientKey);
  ----

  === 3. Secure Transmission
  The client serializes the ciphertexts and the **`CompressedServerKey`** — not the `ServerKey` — into byte buffers.
  Sending the `CompressedServerKey` allows the server to activate GPU acceleration independently.
  The secret `ClientKey` never leaves the client.
  [source,java]
  ----
  DynamicBuffer serializedData      = encrypted.serialize();
  DynamicBuffer serializedServerKey = keys.getCompressedServerKey().serialize();
  ----

  === 4. Homomorphic Computation (Server-Side)
  The server deserializes the `CompressedServerKey` and calls `.use()` to activate it.
  This single call transparently sets both the CPU and GPU server keys based on the JVM configuration.
  The server then deserializes ciphertexts using a decompressed CPU `ServerKey` as the conformant key,
  and executes arithmetic, bitwise, or comparison operations.
  [source,java]
  ----
  CompressedServerKey serverKey = CompressedServerKey.deserialize(serializedServerKey);
  serverKey.use(); // activates CPU, or CPU+GPU when -Dtfhe.gpu=true

  ServerKey conformantKey = serverKey.decompress(); // needed for ciphertext deserialization
  FheUint32 val1 = FheUint32.deserialize(serializedData1, conformantKey);
  FheUint32 val2 = FheUint32.deserialize(serializedData2, conformantKey);

  FheUint32 result = val1.add(val2);
  ----

  === 5. Result Decryption (Client-Side)
  The server serializes the result and returns it to the client. The client deserializes the ciphertext
  using its own `ServerKey` (from `keys.getServerKey()`) and decrypts it using the private `ClientKey`.
  [source,java]
  ----
  FheUint32 finalResult = FheUint32.deserialize(serializedResult, keys.getServerKey());
  int plainValue = finalResult.decrypt(clientKey);
  ----

  === GPU Acceleration
  No code changes are required to enable GPU acceleration. Simply set the `tfhe.gpu` system property:
  [source,bash]
  ----
  java -Dtfhe.gpu=true -jar my-app.jar
  ----
  Both `KeySet.getCompressedServerKey().use()` (client-side) and `CompressedServerKey.deserialize(...).use()` (server-side)
  will then automatically activate the CUDA server key in addition to the CPU server key.

  === Performance & Multi-Threading Warnings
  While homomorphic evaluations are CPU-intensive, managing native threads correctly is critical to optimize performance and prevent contention.

  [WARNING]
  ====
  * **Rayon Thread Pool Contention**: The underlying Rust library (`tfhe-rs`) manages multi-threading via Rayon. Spawning multiple unmanaged Java threads that call homomorphic operations concurrently leads to severe thread contention and overhead.
  * **TfheThreadingContext**: Always wrap parallel homomorphic workloads in a `TfheThreadingContext` block. This context configures the server key and boundaries for native thread execution safely:
+
[source,java]
----
try (TfheThreadingContext threadContext = new TfheThreadingContext(numThreads)) {
  threadContext.setServerKey(keys.getServerKey());
  threadContext.run(() -> {
    // Execute parallel operations securely
  });
}
----
  ====

  Scenario: End-to-End Client-Server Homomorphic Computation
    Given a standard configuration is prepared
    And the keys are generated
    When I encrypt 42 as a FheUint32 ciphertext on the client side
    And I serialize the client data and server key for transmission
    And I deserialize them on the server side
    And I perform homomorphic addition of the value with itself on the server side
    And I serialize the computation result
    And I deserialize and decrypt the result on the client side
    Then the final decrypted plaintext result is 84
