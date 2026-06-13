package io.github.rdlopes.tfhe.benchmarks;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Tag("intensive")
public class TfheBenchmarkRunner {

  @Test
  public void runBenchmarks() throws Exception {
    Options opt = new OptionsBuilder()
        .include(TfheBenchmarks.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }
}
