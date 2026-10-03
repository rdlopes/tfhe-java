# TFHE-Java Strategic Roadmap

This document outlines the actionable enhancement roadmap for **TFHE-Java** (`tfhe-java`) to achieve full feature parity with Zama's **TFHE-rs** (v1.x), optimize JVM runtime ergonomics, and enable enterprise confidential computing workloads.

---

## Roadmap Overview & Phases

```
┌────────────────────────────────────────────────────────────────────────┐
│  Phase 1: High-Level Cryptographic Completeness & Immediate Fixes      │
│  [COMPLETED]                                                           │
│  • Radix Programmable Bootstrapping (LUT on FheIntegers)               │
│  • Native Signed Array Operations (zero-cast FFM pointer segment pass) │
│  • Parameter Sets Alignment & Parity (100% TfheHeader parity)         │
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

## Completed: Phase 1 (High-Level Cryptographic Completeness & Parity)

The first phase of the roadmap focused on high-level feature parity, memory optimization, and parameter synchronization without modifying upstream `tfhe-rs`:

### 1.1 High-Level Integer Programmable Bootstrapping (Radix LUTs)
* **Status**: Completed
* **Implemented in**: `tfhe-core`, `tfhe-api` ([`FheInteger`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/FheInteger.java), [`AbstractFheType`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/AbstractFheType.java))
* **Summary**:
  - Implemented `applyLookupTable(LongUnaryOperator)` and `applyBivariateLookupTable(T other, LongBinaryOperator)` (with bounded domain overloads) on all radix integers.
  - Uses binary decision-tree CMUX decomposition (`ifThenElse`) across radix values with automatic scoped destruction of intermediate nodes in `finally` blocks, preserving zero modification to `tfhe-rs`.
  - Validated by unit tests in [`FheLookupTableTest`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/test/java/io/github/rdlopes/tfhe/api/types/FheLookupTableTest.java).

### 1.2 Native Signed Array Zero-Cast Operations
* **Status**: Completed
* **Implemented in**: `tfhe-api` ([`AbstractFheArray`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/AbstractFheArray.java), [`FheInt8Array`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/types/FheInt8Array.java), [`FheInt128Array`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/types/FheInt128Array.java), and extended arrays)
* **Summary**:
  - Unified both signed (`FheInt*Array`) and unsigned (`FheUint*Array`) implementations under a modernized `AbstractFheArray`.
  - Eliminated element-wise `castInto` overhead by directly passing contiguous off-heap pointer segments to `TfheHeader::fhe_uint*_array_*`, leveraging identical ciphertext radix layouts.
  - Large bitwidth arrays (`FheInt160Array` to `FheInt2048Array`) compare elements natively without intermediate casting.
  - Validated by unit tests in [`FheArraySignedTest`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/test/java/io/github/rdlopes/tfhe/api/types/FheArraySignedTest.java).

### 1.3 Upstream Parameter Set Synchronization
* **Status**: Completed
* **Implemented in**: `tfhe-api` ([`CustomParameters`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/keys/CustomParameters.java), [`CompactPublicKeyEncryptionParameters`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/keys/CompactPublicKeyEncryptionParameters.java), [`CompressionParameters`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/keys/CompressionParameters.java))
* **Summary**:
  - Added all missing `SHORTINT_V1_5_*` and `SHORTINT_V1_6_*` parameter sets (144 parameters).
  - Achieved 100% parity across all 596 `SHORTINT_` static methods declared in `TfheHeader`.
  - Added reflection-based test in [`ParametersTest`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/test/java/io/github/rdlopes/tfhe/api/keys/ParametersTest.java) ensuring every header parameter maps to an enum with a valid, non-zero memory segment.

---

## Active Roadmap: Phase 2 & Phase 3

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
- Provide custom Jackson serializers and deserializers for `AbstractFheType`, `CompactCiphertextList`, and `ProvenCompactCiphertextList` phases.
- Provide Spring Web filter / interceptor that sets and unsets thread-local `ServerKey` for homomorphic request processing.

---

## Tracking & Prioritization Matrix

| Roadmap Item | Complexity | Impact | Target Milestone | Status |
| :--- | :---: | :---: | :---: | :---: |
| **1.1 Radix Programmable Bootstrapping** | High | High | `v0.23.0` | **Completed** |
| **1.2 Native Signed Array Optimization** | Medium | Medium | `v0.23.0` | **Completed** |
| **1.3 Upstream Parameter Sets Sync** | Low | High | `v0.23.0` | **Completed** |
| **2.1 Java 21 LTS Compatibility Support** | High | High | `v0.24.0` | Proposed |
| **2.2 Functional Stream & Collector APIs** | Medium | High | `v0.24.0` | Proposed |
| **2.3 Noise-Aware Expression Evaluator** | High | Medium | `v0.25.0` | Proposed |
| **3.1 GPU / CUDA Native Acceleration Profile** | High | High | `v0.25.0` | Proposed |
| **3.2 Threshold FHE (tTFHE) Bindings** | High | High | `v1.0.0` | Proposed |
| **3.3 Enterprise Framework Starters** | Low | Medium | `v1.0.0` | Proposed |
