package io.github.rdlopes.tfhe.api.stream;

import io.github.rdlopes.tfhe.api.FheArithmetics;
import io.github.rdlopes.tfhe.api.FheCloneable;
import io.github.rdlopes.tfhe.api.FheComparison;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

/// Implementations of [Collector] for TFHE homomorphic types.
///
/// Reduces collections of encrypted ciphertexts using balanced tree reduction.
/// Tree reductions bound evaluation depth to O(log2 N), mitigating multiplicative
/// and additive noise depth while executing efficiently in parallel streams.
///
/// Intermediate native ciphertexts generated across tree stages are eagerly
/// destroyed to prevent off-heap memory exhaustion.
public final class FheCollectors {

  private FheCollectors() {}

  // ──────────────────────────────────────────────────────────────────────────
  // Generic tree reduction
  // ──────────────────────────────────────────────────────────────────────────

  /// Collects elements using balanced tree reduction with the given binary operator.
  /// Preserves input elements in memory (only intermediate reduction nodes are closed).
  public static <T extends AutoCloseable> Collector<T, ?, T> reducing(BinaryOperator<T> op) {
    return reducing(op, false);
  }

  /// Collects elements using balanced tree reduction.
  /// If `consumeInputs` is `true`, stream inputs are also closed as soon as they are combined.
  public static <T extends AutoCloseable> Collector<T, ?, T> reducing(BinaryOperator<T> op, boolean consumeInputs) {
    ServerKey serverKey = ServerKey.current();
    return Collector.of(
        () -> new TreeReductionAccumulator<>(op, consumeInputs, serverKey),
        TreeReductionAccumulator::add,
        TreeReductionAccumulator::combine,
        TreeReductionAccumulator::finish
    );
  }

  /// Collects elements using balanced tree reduction, returning an [Optional] containing
  /// the reduced result, or [Optional#empty()] if the stream was empty.
  public static <T extends AutoCloseable> Collector<T, ?, Optional<T>> reducingOptional(BinaryOperator<T> op) {
    return reducingOptional(op, false);
  }

  /// Collects elements using balanced tree reduction, returning an [Optional].
  public static <T extends AutoCloseable> Collector<T, ?, Optional<T>> reducingOptional(
      BinaryOperator<T> op, boolean consumeInputs) {
    ServerKey serverKey = ServerKey.current();
    return Collector.of(
        () -> new TreeReductionAccumulator<>(op, consumeInputs, serverKey),
        TreeReductionAccumulator::add,
        TreeReductionAccumulator::combine,
        acc -> Optional.ofNullable(acc.finish())
    );
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Summation
  // ──────────────────────────────────────────────────────────────────────────

  /// Computes the homomorphic sum of ciphertexts using tree reduction.
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> sum() {
    return sum(false);
  }

  /// Computes the homomorphic sum of ciphertexts using tree reduction.
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> sum(boolean consumeInputs) {
    return reducing(FheArithmetics::add, consumeInputs);
  }

  /// Computes the homomorphic sum of ciphertexts using tree reduction, returning an [Optional].
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> sumOptional() {
    return sumOptional(false);
  }

  /// Computes the homomorphic sum of ciphertexts using tree reduction, returning an [Optional].
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> sumOptional(
      boolean consumeInputs) {
    return reducingOptional(FheArithmetics::add, consumeInputs);
  }

  /// Computes the homomorphic sum starting from the specified `identity` element.
  @SuppressWarnings("unchecked")
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> sum(T identity) {
    Objects.requireNonNull(identity, "identity cannot be null");
    ServerKey serverKey = ServerKey.current();
    return Collector.of(
        () -> {
          var acc = new TreeReductionAccumulator<T>(FheArithmetics::add, false, serverKey);
          T initVal = identity instanceof FheCloneable<?> cloneable
              ? ((FheCloneable<T>) cloneable).clone()
              : identity;
          acc.addInitial(initVal, true);
          return acc;
        },
        TreeReductionAccumulator::add,
        TreeReductionAccumulator::combine,
        TreeReductionAccumulator::finish
    );
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Product
  // ──────────────────────────────────────────────────────────────────────────

  /// Computes the homomorphic product of ciphertexts using tree reduction.
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> product() {
    return product(false);
  }

  /// Computes the homomorphic product of ciphertexts using tree reduction.
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> product(boolean consumeInputs) {
    return reducing(FheArithmetics::multiply, consumeInputs);
  }

  /// Computes the homomorphic product of ciphertexts using tree reduction, returning an [Optional].
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> productOptional() {
    return productOptional(false);
  }

  /// Computes the homomorphic product of ciphertexts using tree reduction, returning an [Optional].
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> productOptional(
      boolean consumeInputs) {
    return reducingOptional(FheArithmetics::multiply, consumeInputs);
  }

  /// Computes the homomorphic product starting from the specified `identity` element.
  @SuppressWarnings("unchecked")
  public static <T extends FheArithmetics<?, T> & AutoCloseable> Collector<T, ?, T> product(T identity) {
    Objects.requireNonNull(identity, "identity cannot be null");
    ServerKey serverKey = ServerKey.current();
    return Collector.of(
        () -> {
          var acc = new TreeReductionAccumulator<T>(FheArithmetics::multiply, false, serverKey);
          T initVal = identity instanceof FheCloneable<?> cloneable
              ? ((FheCloneable<T>) cloneable).clone()
              : identity;
          acc.addInitial(initVal, true);
          return acc;
        },
        TreeReductionAccumulator::add,
        TreeReductionAccumulator::combine,
        TreeReductionAccumulator::finish
    );
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Min / Max
  // ──────────────────────────────────────────────────────────────────────────

  /// Finds the minimum ciphertext using tree-wise comparison.
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, T> min() {
    return min(false);
  }

  /// Finds the minimum ciphertext using tree-wise comparison.
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, T> min(boolean consumeInputs) {
    return reducing(FheComparison::min, consumeInputs);
  }

  /// Finds the minimum ciphertext using tree-wise comparison, returning an [Optional].
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> minOptional() {
    return minOptional(false);
  }

  /// Finds the minimum ciphertext using tree-wise comparison, returning an [Optional].
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> minOptional(
      boolean consumeInputs) {
    return reducingOptional(FheComparison::min, consumeInputs);
  }

  /// Finds the maximum ciphertext using tree-wise comparison.
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, T> max() {
    return max(false);
  }

  /// Finds the maximum ciphertext using tree-wise comparison.
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, T> max(boolean consumeInputs) {
    return reducing(FheComparison::max, consumeInputs);
  }

  /// Finds the maximum ciphertext using tree-wise comparison, returning an [Optional].
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> maxOptional() {
    return maxOptional(false);
  }

  /// Finds the maximum ciphertext using tree-wise comparison, returning an [Optional].
  public static <T extends FheComparison<?, T> & AutoCloseable> Collector<T, ?, Optional<T>> maxOptional(
      boolean consumeInputs) {
    return reducingOptional(FheComparison::max, consumeInputs);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Logic (FheBool)
  // ──────────────────────────────────────────────────────────────────────────

  /// Reduces a stream of [FheBool] using logical AND (`bitAnd`).
  public static Collector<FheBool, ?, FheBool> and() {
    return and(false);
  }

  /// Reduces a stream of [FheBool] using logical AND (`bitAnd`).
  public static Collector<FheBool, ?, FheBool> and(boolean consumeInputs) {
    return reducing(FheBool::bitAnd, consumeInputs);
  }

  /// Reduces a stream of [FheBool] using logical AND (`bitAnd`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> andOptional() {
    return andOptional(false);
  }

  /// Reduces a stream of [FheBool] using logical AND (`bitAnd`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> andOptional(boolean consumeInputs) {
    return reducingOptional(FheBool::bitAnd, consumeInputs);
  }

  /// Reduces a stream of [FheBool] using logical OR (`bitOr`).
  public static Collector<FheBool, ?, FheBool> or() {
    return or(false);
  }

  /// Reduces a stream of [FheBool] using logical OR (`bitOr`).
  public static Collector<FheBool, ?, FheBool> or(boolean consumeInputs) {
    return reducing(FheBool::bitOr, consumeInputs);
  }

  /// Reduces a stream of [FheBool] using logical OR (`bitOr`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> orOptional() {
    return orOptional(false);
  }

  /// Reduces a stream of [FheBool] using logical OR (`bitOr`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> orOptional(boolean consumeInputs) {
    return reducingOptional(FheBool::bitOr, consumeInputs);
  }

  /// Reduces a stream of [FheBool] using logical XOR (`bitXor`).
  public static Collector<FheBool, ?, FheBool> xor() {
    return xor(false);
  }

  /// Reduces a stream of [FheBool] using logical XOR (`bitXor`).
  public static Collector<FheBool, ?, FheBool> xor(boolean consumeInputs) {
    return reducing(FheBool::bitXor, consumeInputs);
  }

  /// Reduces a stream of [FheBool] using logical XOR (`bitXor`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> xorOptional() {
    return xorOptional(false);
  }

  /// Reduces a stream of [FheBool] using logical XOR (`bitXor`), returning an [Optional].
  public static Collector<FheBool, ?, Optional<FheBool>> xorOptional(boolean consumeInputs) {
    return reducingOptional(FheBool::bitXor, consumeInputs);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Counting
  // ──────────────────────────────────────────────────────────────────────────

  /// Counts the number of items satisfying the homomorphic predicate, returning an encrypted [FheUint32].
  public static <T> Collector<T, ?, FheUint32> counting(Function<T, FheBool> predicate) {
    Objects.requireNonNull(predicate, "predicate cannot be null");
    ServerKey serverKey = ServerKey.current();
    return Collector.of(
        () -> new TreeReductionAccumulator<FheUint32>(FheArithmetics::add, true, serverKey),
        (acc, item) -> {
          acc.ensureServerKey();
          FheBool cond = predicate.apply(item);
          try (FheUint32 one = FheUint32.encrypt(1);
               FheUint32 zero = FheUint32.encrypt(0)) {
            FheUint32 inc = FheUint32.ifThenElse(cond, one, zero);
            acc.add(inc);
          } finally {
            cond.destroy();
          }
        },
        TreeReductionAccumulator::combine,
        acc -> {
          FheUint32 res = acc.finish();
          return res != null ? res : FheUint32.encrypt(0);
        }
    );
  }

  /// Counts the number of true boolean values in a stream of [FheBool], returning an encrypted [FheUint32].
  public static Collector<FheBool, ?, FheUint32> countTrue() {
    return counting(Function.identity());
  }
}
