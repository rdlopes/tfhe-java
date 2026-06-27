package io.github.rdlopes.tfhe.benchmarks;

import io.github.rdlopes.tfhe.api.TfheThreadingContext;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
public class TfheBenchmarks {

  private KeySet keySet;
  private PublicKey publicKey;
  private FheUint8 c1;
  private FheUint8 c2;
  private FheUint8 c3;
  private List<FheUint8> arrayToSum;

  @Setup(Level.Trial)
  public void setUp() {
    keySet = KeySet.builder().build();
    keySet.getServerKey().use();
    publicKey = new PublicKey(keySet.getClientKey());
    c1 = FheUint8.encrypt((byte) 5, publicKey);
    c2 = FheUint8.encrypt((byte) 10, publicKey);
    c3 = FheUint8.encrypt((byte) 0, publicKey);

    arrayToSum = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      arrayToSum.add(FheUint8.encrypt((byte) 1, publicKey));
    }
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    if (c1 != null) c1.close();
    if (c2 != null) c2.close();
    if (c3 != null) c3.close();
    if (arrayToSum != null) {
      for (FheUint8 c : arrayToSum) {
        c.close();
      }
    }
    if (publicKey != null) publicKey.close();
    if (keySet != null) keySet.close();
  }

  @Benchmark
  public Object benchmarkTrivialFfmCall() {
    return TfheHeader.tfhe_error_get_last();
  }

  @Benchmark
  public Object benchmarkAdditionWithAllocation() {
    FheUint8 res = c1.add(c2);
    res.destroy();
    return res;
  }

  @Benchmark
  public Object benchmarkAdditionInPlace() {
    c3.addAssign(c1);
    return c3;
  }

  @Benchmark
  public Object benchmarkSingleThreadedSum() {
    FheUint8 total = FheUint8.encrypt((byte) 0, publicKey);
    for (FheUint8 val : arrayToSum) {
      total.addAssign(val);
    }
    total.destroy();
    return total;
  }

  @Benchmark
  public Object benchmarkMultiThreadedSum() {
    FheUint8 total = FheUint8.encrypt((byte) 0, publicKey);
    try (TfheThreadingContext context = new TfheThreadingContext(4)) {
      context.setServerKey(keySet.getServerKey());
      context.run(() -> {
        for (FheUint8 val : arrayToSum) {
          total.addAssign(val);
        }
      });
    }
    total.destroy();
    return total;
  }
}
