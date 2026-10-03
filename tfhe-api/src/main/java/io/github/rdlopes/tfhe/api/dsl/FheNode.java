package io.github.rdlopes.tfhe.api.dsl;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.types.FheBool;

import java.util.Objects;

/// Sealed node hierarchy representing an abstract syntax tree (AST) for homomorphic expressions.
///
/// Enables algebraic optimizations such as constant folding, scalar hoisting, and associative
/// tree rebalancing before physical native dispatch.
public sealed interface FheNode<V, T extends AbstractFheType<V, T, ?>>
    permits FheNode.LeafNode, FheNode.ScalarNode, FheNode.UnaryNode, FheNode.BinaryNode, FheNode.TernaryNode {

  /// Returns the maximum depth of the AST rooted at this node.
  int depth();

  /// Estimates the number of programmable bootstrappings (PBS) required to evaluate this subtree.
  int estimatedPbsCount();

  /// Leaf node representing an encrypted ciphertext operand.
  record LeafNode<V, T extends AbstractFheType<V, T, ?>>(T ciphertext) implements FheNode<V, T> {
    public LeafNode {
      Objects.requireNonNull(ciphertext, "ciphertext cannot be null");
    }

    @Override
    public int depth() {
      return 1;
    }

    @Override
    public int estimatedPbsCount() {
      return 0;
    }
  }

  /// Leaf node representing an unencrypted cleartext scalar operand.
  record ScalarNode<V, T extends AbstractFheType<V, T, ?>>(V scalar) implements FheNode<V, T> {
    public ScalarNode {
      Objects.requireNonNull(scalar, "scalar cannot be null");
    }

    @Override
    public int depth() {
      return 0;
    }

    @Override
    public int estimatedPbsCount() {
      return 0;
    }
  }

  /// Unary operator node (e.g. negation, bitwise NOT).
  record UnaryNode<V, T extends AbstractFheType<V, T, ?>>(OpType op, FheNode<V, T> operand) implements FheNode<V, T> {
    public UnaryNode {
      Objects.requireNonNull(op, "op cannot be null");
      Objects.requireNonNull(operand, "operand cannot be null");
    }

    @Override
    public int depth() {
      return 1 + operand.depth();
    }

    @Override
    public int estimatedPbsCount() {
      return op.basePbsCost() + operand.estimatedPbsCount();
    }
  }

  /// Binary operator node (e.g. addition, multiplication, bitwise AND/OR/XOR).
  record BinaryNode<V, T extends AbstractFheType<V, T, ?>>(
      OpType op,
      FheNode<V, T> left,
      FheNode<V, T> right
  ) implements FheNode<V, T> {
    public BinaryNode {
      Objects.requireNonNull(op, "op cannot be null");
      Objects.requireNonNull(left, "left cannot be null");
      Objects.requireNonNull(right, "right cannot be null");
    }

    @Override
    public int depth() {
      return 1 + Math.max(left.depth(), right.depth());
    }

    @Override
    public int estimatedPbsCount() {
      int selfCost = (left instanceof ScalarNode || right instanceof ScalarNode)
          ? 0 // Scalar operations bypass ciphertext-ciphertext PBS
          : op.basePbsCost();
      return selfCost + left.estimatedPbsCount() + right.estimatedPbsCount();
    }
  }

  /// Ternary operator node representing homomorphic conditional selection (CMUX / if-then-else).
  record TernaryNode<V, T extends AbstractFheType<V, T, ?>>(
      FheBool condition,
      FheNode<V, T> thenBranch,
      FheNode<V, T> elseBranch
  ) implements FheNode<V, T> {
    public TernaryNode {
      Objects.requireNonNull(condition, "condition cannot be null");
      Objects.requireNonNull(thenBranch, "thenBranch cannot be null");
      Objects.requireNonNull(elseBranch, "elseBranch cannot be null");
    }

    @Override
    public int depth() {
      return 1 + Math.max(thenBranch.depth(), elseBranch.depth());
    }

    @Override
    public int estimatedPbsCount() {
      return 1 + thenBranch.estimatedPbsCount() + elseBranch.estimatedPbsCount();
    }
  }
}
