package io.github.rdlopes.tfhe.api.dsl;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.types.FheBool;

import java.util.Objects;

/// Fluent expression builder for composing, algebraically optimizing, and evaluating homomorphic formulas.
///
/// Example:
/// ```java
/// FheUint32 result = FheExpression.of(a)
///     .times(b)
///     .plusScalar(5)
///     .optimize()
///     .eval();
/// ```
public final class FheExpression<V, T extends AbstractFheType<V, T, ?>> {

  private final FheNode<V, T> root;

  private FheExpression(FheNode<V, T> root) {
    this.root = Objects.requireNonNull(root, "root cannot be null");
  }

  /// Creates an expression starting from an encrypted ciphertext leaf.
  public static <V, T extends AbstractFheType<V, T, ?>> FheExpression<V, T> of(T ciphertext) {
    return new FheExpression<>(new FheNode.LeafNode<>(ciphertext));
  }

  /// Returns the underlying AST root node.
  public FheNode<V, T> node() {
    return root;
  }

  /// Returns the tree depth of the expression.
  public int depth() {
    return root.depth();
  }

  /// Estimates the number of programmable bootstrappings (PBS) required to evaluate this expression.
  public int estimatedPbsCount() {
    return root.estimatedPbsCount();
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Arithmetic Operations
  // ──────────────────────────────────────────────────────────────────────────

  public FheExpression<V, T> plus(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.ADD, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> plus(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.ADD, this.root, other.root));
  }

  public FheExpression<V, T> plusScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.ADD, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> minus(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SUB, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> minus(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SUB, this.root, other.root));
  }

  public FheExpression<V, T> minusScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SUB, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> times(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MUL, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> times(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MUL, this.root, other.root));
  }

  public FheExpression<V, T> timesScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MUL, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> dividedBy(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.DIV, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> dividedBy(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.DIV, this.root, other.root));
  }

  public FheExpression<V, T> dividedByScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.DIV, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> remainder(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.REM, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> remainder(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.REM, this.root, other.root));
  }

  public FheExpression<V, T> remainderScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.REM, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> negate() {
    return new FheExpression<>(new FheNode.UnaryNode<>(OpType.NEGATE, this.root));
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Bitwise / Logic Operations
  // ──────────────────────────────────────────────────────────────────────────

  public FheExpression<V, T> bitAnd(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_AND, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> bitAnd(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_AND, this.root, other.root));
  }

  public FheExpression<V, T> bitAndScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_AND, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> bitOr(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_OR, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> bitOr(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_OR, this.root, other.root));
  }

  public FheExpression<V, T> bitOrScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_OR, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> bitXor(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_XOR, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> bitXor(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_XOR, this.root, other.root));
  }

  public FheExpression<V, T> bitXorScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.BIT_XOR, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> bitNot() {
    return new FheExpression<>(new FheNode.UnaryNode<>(OpType.BIT_NOT, this.root));
  }

  public FheExpression<V, T> shiftLeft(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SHL, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> shiftLeftScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SHL, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> shiftRight(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SHR, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> shiftRightScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.SHR, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Comparison (Min / Max)
  // ──────────────────────────────────────────────────────────────────────────

  public FheExpression<V, T> min(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MIN, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> min(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MIN, this.root, other.root));
  }

  public FheExpression<V, T> minScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MIN, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  public FheExpression<V, T> max(T other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MAX, this.root, new FheNode.LeafNode<>(other)));
  }

  public FheExpression<V, T> max(FheExpression<V, T> other) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MAX, this.root, other.root));
  }

  public FheExpression<V, T> maxScalar(V scalar) {
    return new FheExpression<>(new FheNode.BinaryNode<>(OpType.MAX, this.root, new FheNode.ScalarNode<>(scalar)));
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Conditional Selection (CMUX)
  // ──────────────────────────────────────────────────────────────────────────

  public FheExpression<V, T> ifThenElse(FheBool condition, FheExpression<V, T> elseBranch) {
    return new FheExpression<>(new FheNode.TernaryNode<>(condition, this.root, elseBranch.root));
  }

  public FheExpression<V, T> ifThenElse(FheBool condition, T elseValue) {
    return ifThenElse(condition, FheExpression.of(elseValue));
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Optimization & Evaluation
  // ──────────────────────────────────────────────────────────────────────────

  /// Optimizes the expression tree by constant folding and associative tree rebalancing.
  public FheExpression<V, T> optimize() {
    return new FheExpression<>(AstOptimizer.optimize(this.root));
  }

  /// Evaluates the expression and returns the resulting ciphertext.
  /// Intermediate off-heap ciphertext allocations are eagerly recycled during traversal.
  public T eval() {
    return AstEvaluator.evaluate(this.root);
  }
}
