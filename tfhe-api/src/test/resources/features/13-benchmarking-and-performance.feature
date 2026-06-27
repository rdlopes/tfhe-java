@asciidoc
@order-13
@section-Benchmarking
@appendix
Feature: Performance Optimization and Benchmarking

  === JMH Microbenchmark Harness
  Due to the high computational complexity of homomorphic encryption operations (such as programmable bootstrapping and key switching), TFHE-Java includes a microbenchmark suite using the **Java Microbenchmark Harness (JMH)**. 
  The benchmarks are located under `src/test/java/io/github/rdlopes/tfhe/benchmarks` and measure average execution times in microseconds.

  === Execution Configuration
  To produce stable, statistically sound performance measurements, benchmarks are configured with the following parameters:
  * **Forks**: `1` (runs in a single JVM fork process).
  * **Warmup**: `2` iterations of `2` seconds each (allowing the JVM JIT compiler to optimize code paths before measuring).
  * **Measurement**: `3` iterations of `2` seconds each.
  * **Benchmark Mode**: `Mode.AverageTime` (measures the average duration per operation).

  === Included Benchmarks
  The microbenchmark suite measures different stages of Panama downcalls and FHE integer math:
  * **`benchmarkTrivialFfmCall`**: Measures the baseline overhead of a Project Panama downcall without doing any math (invokes `tfhe_error_get_last`).
  * **`benchmarkAdditionWithAllocation`**: Measures a standard homomorphic addition (`c1.add(c2)`) that allocates a new off-heap memory segment for the resulting ciphertext.
  * **`benchmarkAdditionInPlace`**: Measures an in-place homomorphic addition (`c3.addAssign(c1)`) which mutates the existing memory segment.
  * **`benchmarkSingleThreadedSum`**: Accumulates an array of 10 FHE ciphertexts sequentially on a single thread.
  * **`benchmarkMultiThreadedSum`**: Accumulates an array of 10 FHE ciphertexts in parallel using a multi-threaded server-key context.

  === Performance Optimization Best Practices
  Developers can improve homomorphic execution performance using two main techniques:
  
  ==== 1. In-Place Operations (Avoid Allocations)
  Creating new ciphertexts requires allocating off-heap memory through the Project Panama segment allocator. This introduces significant JVM heap and native allocator overhead. 
  Wherever possible, prefer in-place mutable operations (e.g. `addAssign`, `subAssign`, `mulAssign`) over allocating ones (`add`, `sub`, `mul`).
  
  ==== 2. Multi-Threaded Server-Key Context (`TfheThreadingContext`)
  Homomorphic operations on the server-key (like parallel tallies in voting or training epochs in ML) can be parallelized.
  By wrapping operations in a `TfheThreadingContext` block, the library initializes a native Rayon thread pool on the Rust side, enabling thread-safe parallel evaluation of FHE ciphertexts.

  === Running Benchmarks
  Because FHE benchmarks are highly CPU-intensive, they are tagged with `@Tag("intensive")` and are excluded from the standard build lifecycle by default (using surefire configuration).
  To run the benchmarks explicitly:
  1. Set the Maven profile to include intensive tests, or
  2. Run the `TfheBenchmarkRunner` class directly from your IDE.

  Scenario: Root

