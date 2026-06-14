package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.MemorySegment;

/// Typed functional interfaces for each distinct native FFM call shape.
///
/// All implementations are method references to TfheHeader static wrapper
/// methods, which internally call `invokeExact` on a
/// `static final MethodHandle` (the actual downcall handle generated
/// by jextract) and already handle `Throwable` by re-throwing as
/// `RuntimeException | Error`. No checked exceptions escape.
public final class FheOps {

  private FheOps() {}

  // ──────────────────────────────────────────────────────────────────────────
  // Binary shapes
  // ──────────────────────────────────────────────────────────────────────────

  /// P1 — binary → result: `(lhs, rhs, result**) → int`
  @FunctionalInterface
  public interface BinaryOp {
    int apply(MemorySegment lhs, MemorySegment rhs, MemorySegment result);
  }

  /// P4 — assign binary (in-place): `(lhs, rhs) → int`
  @FunctionalInterface
  public interface AssignOp {
    int apply(MemorySegment lhs, MemorySegment rhs);
  }

  /// P7 — unary → result: `(input, result**) → int`
  @FunctionalInterface
  public interface UnaryOp {
    int apply(MemorySegment input, MemorySegment result);
  }

  /// P8 — checked binary → two outputs: `(lhs, rhs, result1**, result2**) → int`
  @FunctionalInterface
  public interface CheckedOp {
    int apply(MemorySegment lhs, MemorySegment rhs, MemorySegment result1, MemorySegment result2);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Scalar shapes — V is the Java clear-text type (Byte, Short, Integer, Long …)
  // ──────────────────────────────────────────────────────────────────────────

  /// P2/P3 — scalar → result: `(lhs, scalar, result**) → int`.
  ///
  /// The lambda captures the type-specific argument adaptation:
  /// primitive types are auto-unboxed by Java at the call site;
  /// wide types delegate via their native address.
  @FunctionalInterface
  public interface ScalarOp<V> {
    int apply(MemorySegment lhs, V scalar, MemorySegment result);
  }

  /// P5/P6 — scalar assign: `(lhs, scalar) → int`
  @FunctionalInterface
  public interface ScalarAssignOp<V> {
    int apply(MemorySegment lhs, V scalar);
  }

  /// P9 — scalar checked → two outputs: `(lhs, scalar, result1**, result2**) → int`
  @FunctionalInterface
  public interface ScalarCheckedOp<V> {
    int apply(MemorySegment lhs, V scalar, MemorySegment result1, MemorySegment result2);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Encryption / decryption
  // ──────────────────────────────────────────────────────────────────────────

  /// P12 — encrypt primitive (⚠ scalar is FIRST arg):
  /// `(V clear, key*, result**) → int`
  @FunctionalInterface
  public interface EncryptPrimitiveOp<V> {
    int apply(V clear, MemorySegment key, MemorySegment result);
  }

  /// P13 — encrypt wide: `(clear*, key*, result**) → int`
  @FunctionalInterface
  public interface EncryptWideOp {
    int apply(MemorySegment clear, MemorySegment key, MemorySegment result);
  }

  /// Trivial encrypt primitive (no key): `(V clear, result**) → int`
  @FunctionalInterface
  public interface TrivialEncryptOp<V> {
    int apply(V clear, MemorySegment result);
  }

  /// Trivial encrypt wide (no key): `(clear*, result**) → int`
  @FunctionalInterface
  public interface TrivialEncryptWideOp {
    int apply(MemorySegment clear, MemorySegment result);
  }

  /// P10 — decrypt primitive: `(enc*, key*, outPtr) → int`
  @FunctionalInterface
  public interface DecryptPrimitiveOp {
    int apply(MemorySegment encrypted, MemorySegment key, MemorySegment outPtr);
  }

  /// P11 — decrypt wide: `(enc*, key*, wideAddr) → int`
  @FunctionalInterface
  public interface DecryptWideOp {
    int apply(MemorySegment encrypted, MemorySegment key, MemorySegment out);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Serialization
  // ──────────────────────────────────────────────────────────────────────────

  /// `(self*, buffer*, maxSize) → int`
  @FunctionalInterface
  public interface SerializeOp {
    int apply(MemorySegment self, MemorySegment buffer, long maxSize);
  }

  /// `(buffer*, maxSize, serverKey*, result**) → int`
  @FunctionalInterface
  public interface DeserializeOp {
    int apply(MemorySegment buffer, long maxSize, MemorySegment serverKey, MemorySegment result);
  }

  // ──────────────────────────────────────────────────────────────────────────
  // Specialised return shapes
  // ──────────────────────────────────────────────────────────────────────────

  /// ilog2: `(input*, FheUint32** result) → int`
  @FunctionalInterface
  public interface ILog2Op {
    int apply(MemorySegment input, MemorySegment result);
  }

  /// checked_ilog2: `(input*, FheUint32** result, FheBool** flag) → int`
  @FunctionalInterface
  public interface CheckedILog2Op {
    int apply(MemorySegment input, MemorySegment result, MemorySegment flag);
  }

  /// random: `(result**, seedLow, seedHigh) → int`
  @FunctionalInterface
  public interface RandomOp {
    int apply(MemorySegment result, long seedLow, long seedHigh);
  }

  /// random bounded: `(result**, seedLow, seedHigh, bitsCount) → int`
  @FunctionalInterface
  public interface RandomBoundedOp {
    int apply(MemorySegment result, long seedLow, long seedHigh, long bitsCount);
  }

  /// if_then_else: `(FheBool* cond, T* then, T* else, T** result) → int`
  @FunctionalInterface
  public interface IfThenElseOp {
    int apply(MemorySegment cond, MemorySegment then_, MemorySegment else_, MemorySegment result);
  }

  /// Array contains / equals: `(lhs*, lhsLen, rhs*, rhsLen, FheBool** result) → int`
  @FunctionalInterface
  public interface ArrayBinaryOp {
    int apply(MemorySegment lhs, long lhsLen, MemorySegment rhs, long rhsLen, MemorySegment result);
  }

  /// Array sum: `(arr*, len, result**) → int`
  @FunctionalInterface
  public interface ArraySumOp {
    int apply(MemorySegment arr, long len, MemorySegment result);
  }

  /// List getter: `(list*, index, result**) → int`
  @FunctionalInterface
  public interface ListGetOp {
    int apply(MemorySegment list, long index, MemorySegment result);
  }

  /// Try decrypt primitive: `(enc*, outPtr) → int`
  @FunctionalInterface
  public interface TryDecryptPrimitiveOp {
    int apply(MemorySegment encrypted, MemorySegment outPtr);
  }

  /// Try decrypt wide: `(enc*, wideAddr) → int`
  @FunctionalInterface
  public interface TryDecryptWideOp {
    int apply(MemorySegment encrypted, MemorySegment out);
  }
}
