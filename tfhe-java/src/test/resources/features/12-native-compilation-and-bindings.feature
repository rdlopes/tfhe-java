@asciidoc
@order-12
@section-NativeCompilation
@appendix
Feature: Native Compilation and Binding Generation

  === Dynamic C-API Compilation (Rust)
  The core cryptographic backend of TFHE-Java is built on top of `tfhe-rs`, a high-performance homomorphic encryption library written in Rust. Since Java cannot invoke Rust code directly without C-compatible linkage, `tfhe-rs` is compiled into a C-compatible shared library (`.dll`, `.so`, or `.dylib`) under a specialized profile.

  The library compilation targets the following feature flags via `cargo`:
  * **`boolean-c-api`**: C-bindings for boolean gate evaluations (AND, OR, XOR, etc.).
  * **`shortint-c-api`**: C-bindings for small, parameterized precision integer evaluation.
  * **`high-level-c-api`**: High-level FHE integer types and operation interfaces.
  * **`zk-pok`**: Support for client-side Zero-Knowledge Proofs of ballot conformance.
  * **`experimental-force_fft_algo_dif4`**: Enables experimental fast Fourier transform (FFT) acceleration algorithms.

  === Panama Binding Extraction via `jextract`
  To call the compiled C-compatible functions without manual JNI wrappers, TFHE-Java uses Project Panama's `jextract` tool. 
  `jextract` parses the generated C header (`tfhe.h`) and generates corresponding Java classes (`TfheHeader.java`) containing memory layouts, function descriptors, and native downcall method handles.

  The extraction pipeline involves:
  1. **Dump Includes**: Running `jextract --dump-includes jextract-includes.txt tfhe.h` to identify all exported symbols.
  2. **Filtering**: Using `grep` to filter out non-TFHE symbols, saving only target cryptographic functions to `jextract-includes-filtered.txt`.
  3. **Binding Generation**: Generating the Java binding sources using the filter:
     `jextract @jextract-includes-filtered.txt tfhe.h --header-class-name TfheHeader --target-package io.github.rdlopes.tfhe.ffm --output <target>`

  === Local Build Execution (Maven `local` Profile)
  For developers compiling the native wrapper locally, the Maven `local` profile automates the build chain. Executing:
  ```powershell
  .\mvnw verify -Plocal
  ```
  Triggers the following phases:
  1. Clones the `tfhe-rs` repository into `.native/tfhe-rs`.
  2. Executes `cargo build` with target CPU optimizations (`RUSTFLAGS="-C target-cpu=native"`).
  3. Dumps and filters the FFM includes.
  4. Runs `jextract` to output bindings.
  5. Copies the compiled libraries and licenses into resources.

  Scenario: Root

