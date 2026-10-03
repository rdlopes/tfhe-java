package io.github.rdlopes.tfhe.api.dsl;

import io.github.rdlopes.tfhe.api.AbstractFheType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/// Optimization passes for homomorphic abstract syntax trees.
///
/// Implements constant folding, scalar grouping, identity elimination, and
/// associative tree rebalancing to minimize circuit depth and cryptographic noise.
public final class AstOptimizer {

  private AstOptimizer() {}

  /// Optimizes the given AST by running constant folding, associative rebalancing, and identity simplification.
  public static <V, T extends AbstractFheType<V, T, ?>> FheNode<V, T> optimize(FheNode<V, T> root) {
    FheNode<V, T> folded = fold(root);
    FheNode<V, T> rebalanced = rebalance(folded);
    return fold(rebalanced);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Constant Folding & Scalar Grouping
  // ──────────────────────────────────────────────────────────────────────────

  @SuppressWarnings("unchecked")
  private static <V, T extends AbstractFheType<V, T, ?>> FheNode<V, T> fold(FheNode<V, T> node) {
    return switch (node) {
      case FheNode.LeafNode<V, T> leaf -> leaf;
      case FheNode.ScalarNode<V, T> scalar -> scalar;
      case FheNode.UnaryNode<V, T>(OpType op, var operand) -> {
        FheNode<V, T> optOperand = fold(operand);
        if (optOperand instanceof FheNode.ScalarNode<V, T> sc && op == OpType.NEGATE) {
          yield new FheNode.ScalarNode<>(ScalarMath.negate(sc.scalar()));
        }
        yield new FheNode.UnaryNode<>(op, optOperand);
      }
      case FheNode.BinaryNode<V, T>(OpType op, var left, var right) -> {
        FheNode<V, T> l = fold(left);
        FheNode<V, T> r = fold(right);

        // 1. Both scalar: fold completely at compile time
        if (l instanceof FheNode.ScalarNode<V, T> sl && r instanceof FheNode.ScalarNode<V, T> sr) {
          V res = switch (op) {
            case ADD -> ScalarMath.add(sl.scalar(), sr.scalar());
            case SUB -> ScalarMath.subtract(sl.scalar(), sr.scalar());
            case MUL -> ScalarMath.multiply(sl.scalar(), sr.scalar());
            default -> null;
          };
          if (res != null) {
            yield new FheNode.ScalarNode<>(res);
          }
        }

        // 2. Identity simplification
        if (r instanceof FheNode.ScalarNode<V, T> sr) {
          V s = sr.scalar();
          if (op == OpType.ADD && ScalarMath.isZero(s)) yield l;
          if (op == OpType.SUB && ScalarMath.isZero(s)) yield l;
          if (op == OpType.MUL && ScalarMath.isOne(s)) yield l;
          if (op == OpType.MUL && ScalarMath.isZero(s)) yield new FheNode.ScalarNode<>(s);
        }
        if (l instanceof FheNode.ScalarNode<V, T> sl) {
          V s = sl.scalar();
          if (op == OpType.ADD && ScalarMath.isZero(s)) yield r;
          if (op == OpType.MUL && ScalarMath.isOne(s)) yield r;
          if (op == OpType.MUL && ScalarMath.isZero(s)) yield new FheNode.ScalarNode<>(s);
        }

        // 3. Scalar grouping: (X + s1) + s2 -> X + (s1 + s2)
        if (op == OpType.ADD && r instanceof FheNode.ScalarNode<V, T> sr) {
          if (l instanceof FheNode.BinaryNode<V, T> bl && bl.op() == OpType.ADD
              && bl.right() instanceof FheNode.ScalarNode<V, T> sl) {
            yield new FheNode.BinaryNode<>(OpType.ADD, bl.left(),
                new FheNode.ScalarNode<>(ScalarMath.add(sl.scalar(), sr.scalar())));
          }
        }

        // 4. Scalar grouping: (X * s1) * s2 -> X * (s1 * s2)
        if (op == OpType.MUL && r instanceof FheNode.ScalarNode<V, T> sr) {
          if (l instanceof FheNode.BinaryNode<V, T> bl && bl.op() == OpType.MUL
              && bl.right() instanceof FheNode.ScalarNode<V, T> sl) {
            yield new FheNode.BinaryNode<>(OpType.MUL, bl.left(),
                new FheNode.ScalarNode<>(ScalarMath.multiply(sl.scalar(), sr.scalar())));
          }
        }

        yield new FheNode.BinaryNode<>(op, l, r);
      }
      case FheNode.TernaryNode<V, T>(var cond, var thenB, var elseB) ->
          new FheNode.TernaryNode<>(cond, fold(thenB), fold(elseB));
    };
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Associative Tree Rebalancing
  // ──────────────────────────────────────────────────────────────────────────

  private static <V, T extends AbstractFheType<V, T, ?>> FheNode<V, T> rebalance(FheNode<V, T> node) {
    if (node instanceof FheNode.BinaryNode<V, T>(OpType op, var left, var right) && op.isAssociative()) {
      List<FheNode<V, T>> operands = new ArrayList<>();
      collectAssociativeOperands(node, op, operands);
      if (operands.size() > 2) {
        return buildBalancedTree(op, operands, 0, operands.size() - 1);
      }
      return new FheNode.BinaryNode<>(op, rebalance(left), rebalance(right));
    }
    if (node instanceof FheNode.BinaryNode<V, T>(var op, var left, var right)) {
      return new FheNode.BinaryNode<>(op, rebalance(left), rebalance(right));
    }
    if (node instanceof FheNode.UnaryNode<V, T>(var op, var operand)) {
      return new FheNode.UnaryNode<>(op, rebalance(operand));
    }
    if (node instanceof FheNode.TernaryNode<V, T>(var cond, var thenB, var elseB)) {
      return new FheNode.TernaryNode<>(cond, rebalance(thenB), rebalance(elseB));
    }
    return node;
  }

  private static <V, T extends AbstractFheType<V, T, ?>> void collectAssociativeOperands(
      FheNode<V, T> node, OpType targetOp, List<FheNode<V, T>> result) {
    if (node instanceof FheNode.BinaryNode<V, T>(var op, var left, var right) && op == targetOp) {
      collectAssociativeOperands(left, targetOp, result);
      collectAssociativeOperands(right, targetOp, result);
    } else {
      result.add(rebalance(node));
    }
  }

  private static <V, T extends AbstractFheType<V, T, ?>> FheNode<V, T> buildBalancedTree(
      OpType op, List<FheNode<V, T>> operands, int start, int end) {
    if (start == end) {
      return operands.get(start);
    }
    if (start + 1 == end) {
      return new FheNode.BinaryNode<>(op, operands.get(start), operands.get(end));
    }
    int mid = start + (end - start) / 2;
    FheNode<V, T> left = buildBalancedTree(op, operands, start, mid);
    FheNode<V, T> right = buildBalancedTree(op, operands, mid + 1, end);
    return new FheNode.BinaryNode<>(op, left, right);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Helper for scalar math
  // ──────────────────────────────────────────────────────────────────────────

  static final class ScalarMath {
    static long toLong(Object v) {
      if (v instanceof Number n) return n.longValue();
      if (v instanceof BigInteger bi) return bi.longValue();
      return 0L;
    }

    @SuppressWarnings("unchecked")
    static <V> V fromLong(long val, Class<?> targetClass) {
      if (targetClass == Byte.class) return (V) Byte.valueOf((byte) val);
      if (targetClass == Short.class) return (V) Short.valueOf((short) val);
      if (targetClass == Integer.class) return (V) Integer.valueOf((int) val);
      if (targetClass == Long.class) return (V) Long.valueOf(val);
      if (targetClass == BigInteger.class) return (V) BigInteger.valueOf(val);
      throw new UnsupportedOperationException("Unsupported scalar class: " + targetClass);
    }

    static <V> V add(V a, V b) {
      long sum = toLong(a) + toLong(b);
      return fromLong(sum, a.getClass());
    }

    static <V> V subtract(V a, V b) {
      long diff = toLong(a) - toLong(b);
      return fromLong(diff, a.getClass());
    }

    static <V> V multiply(V a, V b) {
      long prod = toLong(a) * toLong(b);
      return fromLong(prod, a.getClass());
    }

    static <V> V negate(V a) {
      long neg = -toLong(a);
      return fromLong(neg, a.getClass());
    }

    static boolean isZero(Object v) {
      return toLong(v) == 0L;
    }

    static boolean isOne(Object v) {
      return toLong(v) == 1L;
    }
  }
}
