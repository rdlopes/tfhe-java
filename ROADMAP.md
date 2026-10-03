# TFHE-Java Strategic Roadmap

This document outlines the actionable enhancement roadmap for **TFHE-Java** (`tfhe-java`) to achieve full feature parity with Zama's **TFHE-rs** (v1.x), optimize JVM runtime ergonomics, and enable enterprise confidential computing workloads.

---

## Roadmap Overview & Phases

```
┌────────────────────────────────────────────────────────────────────────┐
│  Phase 1: High-Level Cryptographic Completeness & Immediate Fixes      │
│  • Radix Programmable Bootstrapping (LUT on FheIntegers)               │
│  • Native Signed Array Operations (remove unsigned cast overhead)      │
│  • Parameter Sets Alignment & Synchronization                          │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│  Phase 2: JVM Ergonomics & Ecosystem Integration                       │
│  • Java Stream Collectors (FheCollectors)                              │
│  • Expression Evaluator / Noise-Aware AST Builder                      │
│  • Java 21 LTS Compatibility / Multi-Release Architecture              │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│  Phase 3: Hardware Acceleration & Advanced Protocols                   │
│  • GPU / CUDA Acceleration Profile (tfhe-native-cuda)                  │
│  • Threshold FHE (tTFHE) & Multi-Party KMS Decryption                  │
│  • Enterprise Spring Boot / Micronaut Confidential Computing Starters  │
└────────────────────────────────────────────────────────────────────────┘
```

---

## Phase 1: High-Level Cryptographic Completeness & Immediate Optimizations

### 1.1 High-Level Integer Programmable Bootstrapping (Radix LUTs)
* **Status**: Proposed
* **Priority**: High
* **Target Modules**: `tfhe-core`, `tfhe-api`

#### Rationale
Currently, Programmable Bootstrapping (PBS) via lookup tables is fully supported at the low-level `ShortintCiphertext` layer (`ShortintServerKey.generateLookupTable(...)`), but is not directly accessible on high-level integer types (`FheUint*`, `FheInt*`). In modern `tfhe-rs`, evaluating arbitrary univariate functions (activation functions, non-linear mathematical transforms, custom cryptographic primitives) across multi-block radix integers is a standard high-level operation.

#### Description & Scope
- Expose univariate and bivariate Radix LUT generation and evaluation from `tfhe-rs` `integer` module through the C-API (`high-level-c-api`).
- Extend `FheOps` and `FheTypeHandles` in `tfhe-core` to dispatch downcalls to `fhe_*_apply_lookup_table`.
- Add public API methods on `AbstractFheType<T, V>`:
  ```java
  public T applyLookupTable(LongUnaryOperator mapping);
  public T applyBivariateLookupTable(T other, LongBinaryOperator mapping);
  ```
- Support automatic decomposition across radix blocks and carry resolution.

---

### 1.2 Native Signed Array Equality and Slicing
* **Status**: Proposed
* **Priority**: Medium
* **Target Modules**: `tfhe-api`, `tfhe-native`

#### Rationale
Currently, `containsArray` and `equalsArray` in signed array types (`FheInt*Array`) are simulated by temporarily casting all elements to their unsigned counterparts (`FheUint*Array`), invoking the native C function, and destroying intermediate native allocations. This introduces unnecessary memory allocations and downcall overhead.

#### Description & Scope
- Update `scripts/InitializeNativeLibraries.java` and `tfhe-c-api` patch to directly export signed slice equality (`fhe_int*_array_eq`) and substring matching (`fhe_int*_array_contains`).
- Refactor `AbstractFheArray` to invoke direct native handles for signed array comparisons, eliminating intermediate dynamic casting.

---

### 1.3 Upstream Parameter Set Synchronization
* **Status**: Ongoing
* **Priority**: Medium
* **Target Modules**: `tfhe-api` (`io.github.rdlopes.tfhe.api.keys`)

#### Rationale
`tfhe-rs` regularly updates cryptographic parameter profiles (e.g. `CompactPublicKeyEncryptionParameters`, `CustomParameters`, `CompressionParameters`) to balance noise growth, security levels (128-bit vs. 64-bit), and key sizes across releases V0.11 through V1.8. Keeping these enums synchronized with upstream releases ensures zero-gap cross-compatibility when exchanging serialized ciphertexts between Rust and Java environments.

#### Description & Scope
- Automate extraction of predefined parameters during `scripts/GenerateBindings.java` execution.
- Maintain parameter profile test matrices in Cucumber living documentation (`10-predefined-parameters-and-types.feature`).
- Ensure all PKE parameters (`SHORTINT_V1_*_PARAM_PKE_...`) have 1:1 parity with Zama releases.

---

## Phase 2: JVM Ergonomics & Ecosystem Integration

### 2.1 Java 21 LTS Compatibility & Multi-Release Architecture
* **Status**: Proposed
* **Priority**: High
* **Target Modules**: `pom.xml`, `tfhe-core`

#### Rationale
`tfhe-java` currently targets OpenJDK 25 (`temurin-25`). While this leverages the latest Foreign Function & Memory (FFM) API refinements, many enterprise organizations require Long-Term Support (LTS) runtimes (specifically Java 21 LTS). Broadening support to Java 21 LTS unlocks massive production adoption.

#### Description & Scope
- Evaluate Multi-Release JAR packaging (`META-INF/versions/21`, `META-INF/versions/25`) or a dedicated `java-21` compatibility branch.
- Abstract minor differences between Java 21 FFM (preview / JEP 442) and Java 22+ finalized FFM (JEP 454).
- Ensure maven compilation profiles (`-Pjdk21`, `-Pjdk25`) pass full test suites cleanly.

---

### 2.2 Functional Stream & Collector APIs (`FheCollectors`)
* **Status**: Proposed
* **Priority**: Medium
* **Target Modules**: `tfhe-api`

#### Rationale
Data processing pipelines in Java naturally rely on `java.util.stream.Stream`. Performing homomorphic reductions (summation, finding min/max, conditional filtering) over collections of ciphertexts currently requires manual iterative loops.

#### Description & Scope
- Implement `FheCollectors` utility providing parallel-ready Java `Collector` implementations:
  ```java
  FheUint32 total = transactions.stream()
      .map(Transaction::amount)
      .collect(FheCollectors.sum());

  FheUint32 maximum = transactions.stream()
      .map(Transaction::amount)
      .collect(FheCollectors.max());
  ```
- Support tree-reduction algorithms to minimize sequential multiplicative/additive noise depth.

---

### 2.3 Noise-Aware Expression Evaluator / AST Builder
* **Status**: Proposed
* **Priority**: Low / Exploratory
* **Target Modules**: `tfhe-api`

#### Rationale
Because Java lacks operator overloading, complex homomorphic expressions result in verbose call chains (`a.multiply(b).add(c.divide(d))`). In FHE, the order of evaluation strongly impacts noise accumulation and the required number of programmable bootstrappings (PBS).

#### Description & Scope
- Introduce a lazy evaluation DSL:
  ```java
  FheExpression.of(a).times(b).plus(c).eval();
  ```
- Optimize AST evaluation order (e.g., performing scalar additions and linear operations before costly PBS multiplications).
- Automatically recycle intermediate off-heap memory buffers using scoped memory arenas.

---

## Phase 3: Hardware Acceleration & Advanced Protocols

### 3.1 GPU / CUDA Native Acceleration Profile (`tfhe-native-cuda`)
* **Status**: Proposed
* **Priority**: High
* **Target Modules**: `tfhe-native`, `scripts/InitializeNativeLibraries.java`

#### Rationale
Homomorphic operations (especially multi-bit PBS and high-bitwidth integer multiplications) are compute-heavy. `tfhe-rs` provides state-of-the-art GPU acceleration via CUDA (`--features=gpu`). Providing a CUDA-enabled native library allows Java server applications to achieve orders-of-magnitude faster throughput.

#### Description & Scope
- Introduce a Maven profile `-Pcuda` in `scripts/InitializeNativeLibraries.java` that compiles `tfhe-rs` with `--features=gpu,high-level-c-api`.
- Create a dedicated artifact `tfhe-native-cuda` containing CUDA-linked shared libraries (`libtfhe_cuda.so`, `tfhe_cuda.dll`).
- Implement dynamic runtime fallback in `NativeLibrary.load()`: use GPU acceleration if CUDA drivers are present, falling back to CPU vector instructions (AVX-512 / NEON) otherwise.

---

### 3.2 Threshold FHE (tTFHE) & Distributed Key Generation Bindings
* **Status**: Proposed
* **Priority**: Medium
* **Target Modules**: `tfhe-core`, `tfhe-api`

#### Rationale
In decentralized, privacy-preserving systems and multi-cloud architectures, data cannot be decrypted by any single entity. Zama's `tfhe-rs` contains cryptographic building blocks for threshold FHE, where private keys are split among $n$ nodes and $(t, n)$ partial decryptions must be combined.

#### Description & Scope
- Track and expose Zama's threshold FHE C-API bindings as they stabilize.
- Implement Java wrappers for:
  - Distributed Key Generation (DKG) configuration.
  - Partial decryption shares (`DecryptionShare`).
  - Aggregation of shares to reconstruct plaintexts.

---

### 3.3 Spring Boot & Micronaut Integration Starters
* **Status**: Proposed
* **Priority**: Medium
* **Target Modules**: `examples/`, new `tfhe-spring-boot-starter`

#### Rationale
Most enterprise Java deployments run on Spring Boot or Micronaut. Providing turnkey autoconfiguration for server key injection, thread-local evaluation contexts, and Jackson/JSON serializers for compact ciphertexts simplifies production adoption.

#### Description & Scope
- Auto-configure `ServerKey` and `TfheThreadingContext` based on `application.yml` properties.
- Provide custom Jackson serializers and deserializers for `AbstractFheType`, `CompactCiphertextList`, and `ProvenCompactCiphertextList`.
- Provide Spring Web filter / interceptor that sets and unsets thread-local `ServerKey` for homomorphic request processing.

---

## Tracking & Prioritization Matrix

| Roadmap Item | Complexity | Impact | Target Milestone |
| :--- | :---: | :---: | :---: |
| **1.1 Radix Programmable Bootstrapping** | High | High | `v0.24.0` |
| **1.2 Native Signed Array Optimization** | Medium | Medium | `v0.24.0` |
| **1.3 Upstream Parameter Sets Sync** | Low | High | `v0.24.0` |
| **2.1 Java 21 LTS Compatibility Support** | High | High | `v0.25.0` |
| **2.2 Functional Stream & Collector APIs** | Medium | High | `v0.25.0` |
| **2.3 Noise-Aware Expression Evaluator** | High | Medium | `v0.26.0` |
| **3.1 GPU / CUDA Native Acceleration Profile** | High | High | `v0.26.0` |
| **3.2 Threshold FHE (tTFHE) Bindings** | High | High | `v1.0.0` |
| **3.3 Enterprise Framework Starters** | Low | Medium | `v1.0.0` |
