package io.github.rdlopes.tfhe.api.dsl;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.keys.ServerKey;

/// Evaluator engine for homomorphic abstract syntax trees.
///
/// Dispatches physical FHE operations through [AbstractFheType] and [ServerKey],
/// eagerly reclaiming native off-heap intermediate ciphertext allocations
/// to prevent memory exhaustion during complex expression evaluation.
public final class AstEvaluator {

  private AstEvaluator() {}

  /// Evaluates the given AST root node and returns the resulting ciphertext.
  /// All intermediate native allocations produced along the way are eagerly destroyed.
  public static <V, T extends AbstractFheType<V, T, ?>> T evaluate(FheNode<V, T> root) {
    ServerKey current = ServerKey.current();
    if (current != null) {
      current.use();
    }
    EvalResult<V, T> res = evalNode(root);
    if (res.ciphertext != null) {
      return res.ciphertext;
    }
    throw new IllegalStateException("AST evaluated to pure scalar without ciphertext context. " +
        "Wrap at least one operand in an encrypted ciphertext.");
  }

  record EvalResult<V, T extends AbstractFheType<V, T, ?>>(T ciphertext, V scalar, boolean isIntermediate) {
    void closeIfIntermediate() {
      if (isIntermediate && ciphertext != null) {
        ciphertext.destroy();
      }
    }
  }

  private static <V, T extends AbstractFheType<V, T, ?>> EvalResult<V, T> evalNode(FheNode<V, T> node) {
    return switch (node) {
      case FheNode.LeafNode<V, T>(T ct) -> new EvalResult<>(ct, null, false);
      case FheNode.ScalarNode<V, T>(V s) -> new EvalResult<>(null, s, false);
      case FheNode.UnaryNode<V, T>(OpType op, var operand) -> {
        EvalResult<V, T> res = evalNode(operand);
        try {
          if (res.ciphertext != null) {
            T computed = switch (op) {
              case NEGATE -> res.ciphertext.negate();
              case BIT_NOT -> res.ciphertext.bitNot();
              default -> throw new UnsupportedOperationException("Unsupported unary op: " + op);
            };
            yield new EvalResult<>(computed, null, true);
          } else {
            V computedScalar = switch (op) {
              case NEGATE -> AstOptimizer.ScalarMath.negate(res.scalar);
              default -> throw new UnsupportedOperationException("Unsupported unary scalar op: " + op);
            };
            yield new EvalResult<>(null, computedScalar, false);
          }
        } finally {
          res.closeIfIntermediate();
        }
      }
      case FheNode.BinaryNode<V, T>(OpType op, var left, var right) -> {
        EvalResult<V, T> l = evalNode(left);
        EvalResult<V, T> r = evalNode(right);
        try {
          // Both ciphertexts
          if (l.ciphertext != null && r.ciphertext != null) {
            T combined = switch (op) {
              case ADD -> l.ciphertext.add(r.ciphertext);
              case SUB -> l.ciphertext.subtract(r.ciphertext);
              case MUL -> l.ciphertext.multiply(r.ciphertext);
              case DIV -> l.ciphertext.divide(r.ciphertext);
              case REM -> l.ciphertext.remainder(r.ciphertext);
              case BIT_AND -> l.ciphertext.bitAnd(r.ciphertext);
              case BIT_OR -> l.ciphertext.bitOr(r.ciphertext);
              case BIT_XOR -> l.ciphertext.bitXor(r.ciphertext);
              case SHL -> l.ciphertext.shiftLeft(r.ciphertext);
              case SHR -> l.ciphertext.shiftRight(r.ciphertext);
              case ROTL -> l.ciphertext.rotateLeft(r.ciphertext);
              case ROTR -> l.ciphertext.rotateRight(r.ciphertext);
              case MIN -> l.ciphertext.min(r.ciphertext);
              case MAX -> l.ciphertext.max(r.ciphertext);
              default -> throw new UnsupportedOperationException("Unsupported binary ciphertext op: " + op);
            };
            yield new EvalResult<>(combined, null, true);
          }

          // Left ciphertext, Right scalar
          if (l.ciphertext != null && r.scalar != null) {
            T combined = switch (op) {
              case ADD -> l.ciphertext.addScalar(r.scalar);
              case SUB -> l.ciphertext.subtractScalar(r.scalar);
              case MUL -> l.ciphertext.multiplyScalar(r.scalar);
              case DIV -> l.ciphertext.divideScalar(r.scalar);
              case REM -> l.ciphertext.remainderScalar(r.scalar);
              case BIT_AND -> l.ciphertext.bitAndScalar(r.scalar);
              case BIT_OR -> l.ciphertext.bitOrScalar(r.scalar);
              case BIT_XOR -> l.ciphertext.bitXorScalar(r.scalar);
              case SHL -> l.ciphertext.shiftLeftScalar(r.scalar);
              case SHR -> l.ciphertext.shiftRightScalar(r.scalar);
              case ROTL -> l.ciphertext.rotateLeftScalar(r.scalar);
              case ROTR -> l.ciphertext.rotateRightScalar(r.scalar);
              case MIN -> l.ciphertext.minScalar(r.scalar);
              case MAX -> l.ciphertext.maxScalar(r.scalar);
              default -> throw new UnsupportedOperationException("Unsupported binary scalar op: " + op);
            };
            yield new EvalResult<>(combined, null, true);
          }

          // Left scalar, Right ciphertext
          if (l.scalar != null && r.ciphertext != null) {
            T combined = switch (op) {
              case ADD -> r.ciphertext.addScalar(l.scalar); // Commutative
              case MUL -> r.ciphertext.multiplyScalar(l.scalar); // Commutative
              case BIT_AND -> r.ciphertext.bitAndScalar(l.scalar); // Commutative
              case BIT_OR -> r.ciphertext.bitOrScalar(l.scalar); // Commutative
              case BIT_XOR -> r.ciphertext.bitXorScalar(l.scalar); // Commutative
              case MIN -> r.ciphertext.minScalar(l.scalar); // Commutative
              case MAX -> r.ciphertext.maxScalar(l.scalar); // Commutative
              case SUB -> {
                try (T trivial = r.ciphertext.trivialEncrypt(l.scalar)) {
                  yield trivial.subtract(r.ciphertext);
                }
              }
              default -> throw new UnsupportedOperationException("Unsupported scalar-first op: " + op);
            };
            yield new EvalResult<>(combined, null, true);
          }

          // Both scalar
          V combinedScalar = switch (op) {
            case ADD -> AstOptimizer.ScalarMath.add(l.scalar, r.scalar);
            case SUB -> AstOptimizer.ScalarMath.subtract(l.scalar, r.scalar);
            case MUL -> AstOptimizer.ScalarMath.multiply(l.scalar, r.scalar);
            default -> throw new UnsupportedOperationException("Unsupported pure scalar op: " + op);
          };
          yield new EvalResult<>(null, combinedScalar, false);

        } finally {
          l.closeIfIntermediate();
          r.closeIfIntermediate();
        }
      }
      case FheNode.TernaryNode<V, T>(var cond, var thenB, var elseB) -> {
        EvalResult<V, T> t = evalNode(thenB);
        EvalResult<V, T> e = evalNode(elseB);
        try {
          if (t.ciphertext != null && e.ciphertext != null) {
            T combined = t.ciphertext.ifThenElse(cond, e.ciphertext);
            yield new EvalResult<>(combined, null, true);
          }
          throw new UnsupportedOperationException("CMUX on pure scalars is not supported in AST");
        } finally {
          t.closeIfIntermediate();
          e.closeIfIntermediate();
        }
      }
    };
  }
}
