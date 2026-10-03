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
└──────────────────────────────────────────────────────────────────┬─────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────┴─────┐
│  Phase 2: JVM Ergonomics & Ecosystem Integration                       │
│  [COMPLETED]                                                           │
│  • Java Stream Collectors (FheCollectors) with Tree Reduction          │
│  • Expression Evaluator / Noise-Aware AST Optimizer & Builder          │
└──────────────────────────────────────────────────────────────────┬─────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────┴─────┐
│  Phase 3: Advanced Protocols & Threshold FHE (tTFHE)                   │
│  • Ciphertext Noise Squashing & Compression Primitives                 │
│  • Partial Decryption Shares (DecryptionShare) & Quorum Aggregation    │
│  • Distributed Key Generation (DKG) & Verified KeySet Protocol         │
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

## Completed: Phase 2 (JVM Ergonomics & Ecosystem Integration)

The second phase brings idiomatic Java patterns, stream collectors, and AST algebraic optimization into `tfhe-api` while targeting Java 25 as the modern baseline:

### 2.1 Functional Stream & Collector APIs (`FheCollectors`)
* **Status**: Completed
* **Implemented in**: `tfhe-api` ([`FheCollectors`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/stream/FheCollectors.java), [`TreeReductionAccumulator`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/stream/TreeReductionAccumulator.java))
* **Summary**:
  - Implemented parallel-ready Java stream collectors: `sum()`, `product()`, `min()`, `max()`, `and()`, `or()`, `xor()`, `countTrue()`, `counting(predicate)`, and `reducing(identity, op)`.
  - Engineered logarithmic stack tree-reduction (`TreeReductionAccumulator`) reducing circuit depth from $O(N)$ to $O(\log N)$, dramatically lowering multiplicative noise growth.
  - Implemented eager native off-heap ciphertext recycling during reduction folds to prevent memory exhaustion on large stream inputs.
  - Validated by comprehensive unit tests in [`FheCollectorsTest`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/test/java/io/github/rdlopes/tfhe/api/stream/FheCollectorsTest.java).

### 2.2 Noise-Aware Expression Evaluator / AST Builder
* **Status**: Completed
* **Implemented in**: `tfhe-api` ([`FheExpression`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/dsl/FheExpression.java), [`FheNode`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/dsl/FheNode.java), [`AstOptimizer`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/dsl/AstOptimizer.java), [`AstEvaluator`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/main/java/io/github/rdlopes/tfhe/api/dsl/AstEvaluator.java))
* **Summary**:
  - Introduced fluent expression DSL with sealed AST hierarchy (`FheNode.LeafNode`, `ScalarNode`, `UnaryNode`, `BinaryNode`, `TernaryNode`).
  - Added AST optimization pass:
    - **Constant Folding**: Pure scalar subtrees evaluated eagerly at compile-time.
    - **Identity Simplification**: `x + 0 -> x`, `x * 1 -> x`, `x * 0 -> 0`.
    - **Scalar Grouping**: Reordering associative scalar operations `(x + s1) + s2 -> x + (s1 + s2)` to minimize native ciphertext-scalar dispatches.
    - **Associative Tree Rebalancing**: Linear chains transformed into balanced binary trees minimizing noise depth.
  - Automatic intermediate off-heap buffer reclamation during evaluation via `closeIfIntermediate()`.
  - Validated by unit tests in [`FheExpressionTest`](file:///D:/rdlopes/tfhe-java/tfhe-api/src/test/java/io/github/rdlopes/tfhe/api/dsl/FheExpressionTest.java).

---

## Active Roadmap: Phase 3 (Advanced Protocols & Threshold FHE)

### 3.1 Ciphertext Noise Squashing & Packing Primitives
* **Status**: Proposed
* **Priority**: High
* **Target Modules**: `tfhe-core`, `tfhe-api`

#### Rationale
Before homomorphically evaluated ciphertexts can be safely passed to multi-party threshold decryption or compact storage networks, their noise must be bounded and squashed (modulus transition from 64-bit to 128-bit) to prevent side-channel leakage through noise variations.

#### Description & Scope
- Expose upstream `squash_noise` operations for `FheUint*` and `FheBool` types.
- Introduce Java types `SquashedNoiseFheUint`, `SquashedNoiseFheBool`, and `CompressedSquashedNoiseCiphertextList`.
- Implement off-heap lifecycle management, serialization, and deserialization through `DynamicBuffer`.

---

### 3.2 Threshold FHE (tTFHE) Partial Decryption & Quorum Aggregation
* **Status**: Proposed
* **Priority**: High
* **Target Modules**: `tfhe-core`, `tfhe-api`

#### Rationale
In decentralized, privacy-preserving systems, confidential smart contracts (e.g. fhEVM), and multi-cloud KMS architectures, data cannot be decrypted by any single entity. Secret keys are split among $n$ nodes and a threshold quorum of $t$ partial decryptions must be combined to reconstruct plaintexts without reconstructing the private key.

#### Description & Scope
- Track and expose Zama's threshold FHE C-API bindings as they stabilize.
- Implement Java abstractions:
  - `ClientKeyShare`: Private key share possessed by an individual KMS node.
  - `DecryptionShare`: Partial decryption share computed over a squashed ciphertext.
  - `DecryptionShareAggregator`: Quorum combiner collecting $t$-of-$n$ shares to reconstruct plaintexts.
- Add comprehensive multi-party workflow unit tests and living documentation.

---

### 3.3 Distributed Key Generation (DKG) & KeySet Verification Protocols
* **Status**: Proposed
* **Priority**: Medium
* **Target Modules**: `tfhe-core`, `tfhe-api`

#### Rationale
Threshold operations require verifiable key distribution so that individual nodes can verify that collective public keys and server evaluation keys were correctly generated from standard domain separators according to the [Threshold FHE specification](https://eprint.iacr.org/2025/699).

#### Description & Scope
- Bind `CompressedXofKeySet` and verifiable generation using domain separators (`TFHEKGen`, `TFHE_GEN`).
- Expose Java configuration objects for threshold committee parameters ($t$, $n$, security bounds).

---

## Tracking & Prioritization Matrix

| Roadmap Item | Complexity | Impact | Target Milestone | Status |
| :--- | :---: | :---: | :---: | :---: |
| **1.1 Radix Programmable Bootstrapping** | High | High | `v0.23.0` | **Completed** |
| **1.2 Native Signed Array Optimization** | Medium | Medium | `v0.23.0` | **Completed** |
| **1.3 Upstream Parameter Sets Sync** | Low | High | `v0.23.0` | **Completed** |
| **2.1 Functional Stream & Collector APIs** | Medium | High | `v0.24.0` | **Completed** |
| **2.2 Noise-Aware Expression Evaluator** | High | Medium | `v0.24.0` | **Completed** |
| **3.1 Noise Squashing & Packing Primitives** | Medium | High | `v0.25.0` | Proposed |
| **3.2 Threshold Decryption & Share Aggregator** | High | High | `v0.25.0` | Proposed |
| **3.3 DKG & Verifiable KeySet Protocols** | Medium | Medium | `v1.0.0` | Proposed |
