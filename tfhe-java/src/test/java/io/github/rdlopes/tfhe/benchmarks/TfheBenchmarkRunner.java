package io.github.rdlopes.tfhe.benchmarks;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Tag("intensive")
@SuppressWarnings("java:S3577")
class TfheBenchmarkRunner {

  @Test
  void runBenchmarks() {
    Options opt = new OptionsBuilder()
        .include(TfheBenchmarks.class.getSimpleName())
        .forks(1)
        .build();
    assertDoesNotThrow(() -> new Runner(opt).run());
  }
}
