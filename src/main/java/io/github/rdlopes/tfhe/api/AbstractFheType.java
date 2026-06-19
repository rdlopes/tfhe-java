package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;
import io.github.rdlopes.tfhe.ffm.*;
import io.github.rdlopes.tfhe.utils.FheRegistry;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.*;

/// Abstract base class for all FHE encrypted integer types (signed and unsigned).
///
/// Every one of the 13 native call patterns is implemented exactly once here
/// as a `final` method. Concrete subclasses supply only three pieces of
/// metadata via abstract methods — no logic whatsoever:
/// 1. [handles()] — the per-type [FheTypeHandles] instance.
/// 2. [newInstance()] — allocates a new empty `T` (output slot).
/// 3. [newCompressed()] — allocates a new empty `C`.
///
/// This class never references `TfheHeader` directly. All native dispatch
/// goes through method references stored in [FheTypeHandles].
///
/// @param <V> the Java clear-text type (e.g. `Byte`)
/// @param <T> the concrete encrypted type (e.g. `FheInt8`)
/// @param <C> the corresponding compressed type (e.g. `CompressedFheInt8`)
@SuppressWarnings({"java:S2975", "java:S1182"})
public abstract class AbstractFheType<
    V,
    T extends AbstractFheType<V, T, C>,
    C extends AbstractCompressedFheType<V, T, C>>
    extends NativePointer
    implements FheInteger<V, T, C> {

  // ── Subclass contract — pure metadata, zero logic ───────────────────────────

  /// Returns the per-type handles instance (stored as a `static final` field).
  protected abstract FheTypeHandles<V> handles();

  /// Allocates a new empty output slot of type `T`.
  protected abstract T newInstance();

  /// Allocates a new empty output slot of type `C`.
  protected abstract C newCompressed();

  // ── Constructor ─────────────────────────────────────────────────────────────

  /// @param destroyRef method reference to the type-specific destroy function,
/// passed explicitly so no virtual method is called during
/// `super()` construction.
/// Example: `TfheHeader::fhe_int8_destroy`
  protected AbstractFheType(Function<MemorySegment, Integer> destroyRef) {
    super(destroyRef);
  }

  // ══════════════════════════════════════════════════════════════════════════
  // Private dispatch helpers — one per call shape
  // ══════════════════════════════════════════════════════════════════════════

  /// P7 — unary → T result
  private T unary(FheOps.UnaryOp op) {
    T r = newInstance();
    execute(() -> op.apply(getValue(), r.getAddress()));
    return r;
  }

  /// P1 — binary → T result
  private T binary(FheOps.BinaryOp op, T other) {
    T r = newInstance();
    execute(() -> op.apply(getValue(), other.getValue(), r.getAddress()));
    return r;
  }

  /// P2/P3 — scalar → T result
  private T scalar(FheOps.ScalarOp<V> op, V v) {
    T r = newInstance();
    execute(() -> op.apply(getValue(), v, r.getAddress()));
    return r;
  }

  /// P1 → FheBool result
  private FheBool binaryBool(FheOps.BinaryOp op, T other) {
    FheBool r = FheBool.newEmpty();
    execute(() -> op.apply(getValue(), other.getValue(), r.getAddress()));
    return r;
  }

  /// P2/P3 → FheBool result
  private FheBool scalarBool(FheOps.ScalarOp<V> op, V v) {
    FheBool r = FheBool.newEmpty();
    execute(() -> op.apply(getValue(), v, r.getAddress()));
    return r;
  }

  /// P4 — assign binary
  private void assignBinary(FheOps.AssignOp op, T other) {
    execute(() -> op.apply(getValue(), other.getValue()));
  }

  /// P5/P6 — assign scalar
  private void assignScalar(FheOps.ScalarAssignOp<V> op, V v) {
    execute(() -> op.apply(getValue(), v));
  }

  /// P8 — checked binary → [CheckedResult]
  private CheckedResult<T> checked(FheOps.CheckedOp op, T other) {
    T r = newInstance();
    FheBool flag = FheBool.newEmpty();
    execute(() -> op.apply(getValue(), other.getValue(), r.getAddress(), flag.getAddress()));
    return new CheckedResult<>(r, flag);
  }

  /// P8 variant — div_rem → [QuotientAndRemainder]
  private QuotientAndRemainder<T> divRem(FheOps.CheckedOp op, T other) {
    T q = newInstance();
    T r = newInstance();
    execute(() -> op.apply(getValue(), other.getValue(), q.getAddress(), r.getAddress()));
    return new QuotientAndRemainder<>(q, r);
  }

  /// P9 — scalar checked → [QuotientAndRemainder]
  private QuotientAndRemainder<T> scalarDivRem(FheOps.ScalarCheckedOp<V> op, V v) {
    T q = newInstance();
    T r = newInstance();
    execute(() -> op.apply(getValue(), v, q.getAddress(), r.getAddress()));
    return new QuotientAndRemainder<>(q, r);
  }

  // ══════════════════════════════════════════════════════════════════════════
  // FheLogic
  // ══════════════════════════════════════════════════════════════════════════

  @Override public final T    bitAnd(T o)             { return binary(handles().logic().bitAnd(), o); }
  @Override public final T    bitAndScalar(V o)       { return scalar(handles().logic().scalarBitAnd(), o); }
  @Override public final void bitAndAssign(T o)       { assignBinary(handles().logic().bitAndAssign(), o); }
  @Override public final void bitAndScalarAssign(V o) { assignScalar(handles().logic().scalarBitAndAssign(), o); }
  @Override public final T    bitOr(T o)              { return binary(handles().logic().bitOr(), o); }
  @Override public final T    bitOrScalar(V o)        { return scalar(handles().logic().scalarBitOr(), o); }
  @Override public final void bitOrAssign(T o)        { assignBinary(handles().logic().bitOrAssign(), o); }
  @Override public final void bitOrScalarAssign(V o)  { assignScalar(handles().logic().scalarBitOrAssign(), o); }
  @Override public final T    bitXor(T o)             { return binary(handles().logic().bitXor(), o); }
  @Override public final T    bitXorScalar(V o)       { return scalar(handles().logic().scalarBitXor(), o); }
  @Override public final void bitXorAssign(T o)       { assignBinary(handles().logic().bitXorAssign(), o); }
  @Override public final void bitXorScalarAssign(V o) { assignScalar(handles().logic().scalarBitXorAssign(), o); }
  @Override public final T    bitNot()                { return unary(handles().logic().bitNot()); }

  // ══════════════════════════════════════════════════════════════════════════
  // FheEquality
  // ══════════════════════════════════════════════════════════════════════════

  @Override public final FheBool equalTo(T o)          { return binaryBool(handles().equality().eq(), o); }
  @Override public final FheBool equalToScalar(V o)    { return scalarBool(handles().equality().scalarEq(), o); }
  @Override public final FheBool notEqualTo(T o)       { return binaryBool(handles().equality().ne(), o); }
  @Override public final FheBool notEqualToScalar(V o) { return scalarBool(handles().equality().scalarNe(), o); }

  // ══════════════════════════════════════════════════════════════════════════
  // FheComparison
  // ══════════════════════════════════════════════════════════════════════════

  @Override public final FheBool lessThan(T o)                   { return binaryBool(handles().comparison().lt(), o); }
  @Override public final FheBool lessThanScalar(V o)             { return scalarBool(handles().comparison().scalarLt(), o); }
  @Override public final FheBool lessThanOrEqualTo(T o)          { return binaryBool(handles().comparison().le(), o); }
  @Override public final FheBool lessThanOrEqualToScalar(V o)    { return scalarBool(handles().comparison().scalarLe(), o); }
  @Override public final FheBool greaterThan(T o)                { return binaryBool(handles().comparison().gt(), o); }
  @Override public final FheBool greaterThanScalar(V o)          { return scalarBool(handles().comparison().scalarGt(), o); }
  @Override public final FheBool greaterThanOrEqualTo(T o)       { return binaryBool(handles().comparison().ge(), o); }
  @Override public final FheBool greaterThanOrEqualToScalar(V o) { return scalarBool(handles().comparison().scalarGe(), o); }
  @Override public final T       min(T o)                        { return binary(handles().comparison().min(), o); }
  @Override public final T       minScalar(V o)                  { return scalar(handles().comparison().scalarMin(), o); }
  @Override public final T       max(T o)                        { return binary(handles().comparison().max(), o); }
  @Override public final T       maxScalar(V o)                  { return scalar(handles().comparison().scalarMax(), o); }

  // ══════════════════════════════════════════════════════════════════════════
  // FheArithmetics
  // ══════════════════════════════════════════════════════════════════════════

  @Override public final T                    add(T o)                   { return binary(handles().arithmetic().add(), o); }
  @Override public final CheckedResult<T>     addWithOverflow(T o)       { return checked(handles().arithmetic().overflowingAdd(), o); }
  @Override public final T                    addScalar(V o)             { return scalar(handles().arithmetic().scalarAdd(), o); }
  @Override public final void                 addAssign(T o)             { assignBinary(handles().arithmetic().addAssign(), o); }
  @Override public final void                 addScalarAssign(V o)       { assignScalar(handles().arithmetic().scalarAddAssign(), o); }
  @Override public final T                    subtract(T o)              { return binary(handles().arithmetic().sub(), o); }
  @Override public final CheckedResult<T>     subtractWithOverflow(T o)  { return checked(handles().arithmetic().overflowingSub(), o); }
  @Override public final T                    subtractScalar(V o)        { return scalar(handles().arithmetic().scalarSub(), o); }
  @Override public final void                 subtractAssign(T o)        { assignBinary(handles().arithmetic().subAssign(), o); }
  @Override public final void                 subtractScalarAssign(V o)  { assignScalar(handles().arithmetic().scalarSubAssign(), o); }
  @Override public final T                    multiply(T o)              { return binary(handles().arithmetic().mul(), o); }
  @Override public final CheckedResult<T>     multiplyWithOverflow(T o)  { return checked(handles().arithmetic().overflowingMul(), o); }
  @Override public final T                    multiplyScalar(V o)        { return scalar(handles().arithmetic().scalarMul(), o); }
  @Override public final void                 multiplyAssign(T o)        { assignBinary(handles().arithmetic().mulAssign(), o); }
  @Override public final void                 multiplyScalarAssign(V o)  { assignScalar(handles().arithmetic().scalarMulAssign(), o); }
  @Override public final T                    divide(T o)                { return binary(handles().arithmetic().div(), o); }
  @Override public final T                    divideScalar(V o)          { return scalar(handles().arithmetic().scalarDiv(), o); }
  @Override public final void                 divideAssign(T o)          { assignBinary(handles().arithmetic().divAssign(), o); }
  @Override public final void                 divideScalarAssign(V o)    { assignScalar(handles().arithmetic().scalarDivAssign(), o); }
  @Override public final QuotientAndRemainder<T> divideWithRemainder(T o)       { return divRem(handles().arithmetic().divRem(), o); }
  @Override public final QuotientAndRemainder<T> divideWithRemainderScalar(V o) { return scalarDivRem(handles().arithmetic().scalarDivRem(), o); }
  @Override public final T                    remainder(T o)             { return binary(handles().arithmetic().rem(), o); }
  @Override public final T                    remainderScalar(V o)       { return scalar(handles().arithmetic().scalarRem(), o); }
  @Override public final void                 remainderAssign(T o)       { assignBinary(handles().arithmetic().remAssign(), o); }
  @Override public final void                 remainderScalarAssign(V o) { assignScalar(handles().arithmetic().scalarRemAssign(), o); }
  @Override public final T                    negate()                   { return unary(handles().arithmetic().neg()); }

  /// `ilog2()` — native returns `FheUint32` but the interface declares `T`.
  /// The native pointer is stored in a `T` wrapper following the same
  /// convention as the existing hand-written boilerplate.
  @Override
  @SuppressWarnings("unchecked")
  public T ilog2() {
    if (this instanceof FheUint32) {
      T r = newInstance();
      execute(() -> handles().arithmetic().ilog2().apply(getValue(), r.getAddress()));
      return r;
    }

    try (FheUint32 u32 = FheRegistry.getFactory(FheUint32.class).get()) {
      execute(() -> handles().arithmetic().ilog2().apply(getValue(), u32.getAddress()));
      return u32.castInto((Class<T>) this.getClass());
    }
  }

  /// `checked_ilog2` — same convention as [ilog2()].
  @Override
  @SuppressWarnings("unchecked")
  public CheckedResult<T> ilog2WithCheck() {
    if (this instanceof FheUint32) {
      T r = newInstance();
      FheBool flag = FheBool.newEmpty();
      execute(() -> handles().arithmetic().checkedIlog2().apply(getValue(), r.getAddress(), flag.getAddress()));
      return new CheckedResult<>(r, flag);
    }

    FheBool flag = FheBool.newEmpty();
    try (FheUint32 u32 = FheRegistry.getFactory(FheUint32.class).get()) {
      execute(() -> handles().arithmetic().checkedIlog2().apply(getValue(), u32.getAddress(), flag.getAddress()));
      T r = u32.castInto((Class<T>) this.getClass());
      return new CheckedResult<>(r, flag);
    }
  }
  
  /// `abs()` — only valid for signed types; exposed via [FheSignedInteger].
  /// Overridden in [AbstractFheUnsignedInteger] to throw [UnsupportedOperationException].
  public T abs() {
    return unary(handles().arithmetic().abs());
  }

  // ══════════════════════════════════════════════════════════════════════════
  // FheBitwise
  // ══════════════════════════════════════════════════════════════════════════

  @Override public final T    shiftLeft(T o)                { return binary(handles().bitwise().shl(), o); }
  @Override public final T    shiftLeftScalar(V o)          { return scalar(handles().bitwise().scalarShl(), o); }
  @Override public final void shiftLeftAssign(T o)          { assignBinary(handles().bitwise().shlAssign(), o); }
  @Override public final void shiftLeftScalarAssign(V o)    { assignScalar(handles().bitwise().scalarShlAssign(), o); }
  @Override public final T    shiftRight(T o)               { return binary(handles().bitwise().shr(), o); }
  @Override public final T    shiftRightScalar(V o)         { return scalar(handles().bitwise().scalarShr(), o); }
  @Override public final void shiftRightAssign(T o)         { assignBinary(handles().bitwise().shrAssign(), o); }
  @Override public final void shiftRightScalarAssign(V o)   { assignScalar(handles().bitwise().scalarShrAssign(), o); }
  @Override public final T    rotateLeft(T o)               { return binary(handles().bitwise().rotateLeft(), o); }
  @Override public final T    rotateLeftScalar(V o)         { return scalar(handles().bitwise().scalarRotateLeft(), o); }
  @Override public final void rotateLeftAssign(T o)         { assignBinary(handles().bitwise().rotateLeftAssign(), o); }
  @Override public final void rotateLeftScalarAssign(V o)   { assignScalar(handles().bitwise().scalarRotateLeftAssign(), o); }
  @Override public final T    rotateRight(T o)              { return binary(handles().bitwise().rotateRight(), o); }
  @Override public final T    rotateRightScalar(V o)        { return scalar(handles().bitwise().scalarRotateRight(), o); }
  @Override public final void rotateRightAssign(T o)        { assignBinary(handles().bitwise().rotateRightAssign(), o); }
  @Override public final void rotateRightScalarAssign(V o)  { assignScalar(handles().bitwise().scalarRotateRightAssign(), o); }
  @Override public final T    leadingOnes()                 { return unary(handles().bitwise().leadingOnes()); }
  @Override public final T    leadingZeros()                { return unary(handles().bitwise().leadingZeros()); }
  @Override public final T    trailingOnes()                { return unary(handles().bitwise().trailingOnes()); }
  @Override public final T    trailingZeros()               { return unary(handles().bitwise().trailingZeros()); }

  // ══════════════════════════════════════════════════════════════════════════
  // FheType (compress, serialize, clone)
  // ══════════════════════════════════════════════════════════════════════════

  @Override
  public final C compress() {
    C r = newCompressed();
    execute(() -> handles().lifecycle().compress().apply(getValue(), r.getAddress()));
    return r;
  }

  @Override
  public final DynamicBuffer serialize() {
    DynamicBuffer buf = new DynamicBuffer();
    execute(() -> handles().lifecycle().serialize().apply(getValue(), buf.getAddress(), MAX_SERIALIZATION_SIZE));
    return buf;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public final T clone() {
    T r = newInstance();
    execute(() -> handles().lifecycle().clone_().apply(getValue(), r.getAddress()));
    return r;
  }

  // ══════════════════════════════════════════════════════════════════════════
  // FheDecryption — dispatches on valueKind
  // ══════════════════════════════════════════════════════════════════════════

  @Override
  public final V decrypt(ClientKey clientKey) {
    return switch (handles().valueKind()) {
      case FheValueKind.Primitive<V>(var javaType) -> executeAndReturn(
          javaType,
              addr -> handles().encryption().decryptPrimitive().apply(getValue(), clientKey.getValue(), addr));
      case FheValueKind.Wide<V>(var factory) -> {
        V out = factory.get();
        execute(() -> handles().encryption().decryptWide().apply(
            getValue(), clientKey.getValue(), ((NativeAddress) out).getAddress()));
        yield out;
      }
    };
  }

  @Override
  public final java.util.Optional<V> tryDecryptTrivial() {
    return switch (handles().valueKind()) {
      case FheValueKind.Primitive<V>(var javaType) -> {
        var op = FheRegistry.getTryDecryptPrimitiveOp(this.getClass());
        if (op == null) yield java.util.Optional.empty();
        try (java.lang.foreign.Arena arena = java.lang.foreign.Arena.ofConfined()) {
          MemorySegment memorySegment = arena.allocate(
              switch (javaType) {
                case Class<?> type when type == Boolean.class -> TfheHeader$shared.C_BOOL;
                case Class<?> type when type == Byte.class -> TfheHeader$shared.C_CHAR;
                case Class<?> type when type == Short.class -> TfheHeader$shared.C_SHORT;
                case Class<?> type when type == Integer.class -> TfheHeader$shared.C_INT;
                case Class<?> type when type == Long.class -> TfheHeader$shared.C_LONG_LONG;
                default -> TfheHeader$shared.C_POINTER;
              }
          );
          int status = op.apply(getValue(), memorySegment);
          if (status != 0) {
            MemorySegment errorMessageAddress = TfheHeader.tfhe_error_get_last();
            String errorMessage = errorMessageAddress.getString(0);
            if (!NO_ERROR_MESSAGE.equals(errorMessage)) {
              throw new NativeCallException(status, errorMessage);
            }
            yield java.util.Optional.empty();
          }
          var result = switch (javaType) {
            case Class<?> type when type == Boolean.class -> memorySegment.get(TfheHeader$shared.C_BOOL, 0);
            case Class<?> type when type == Byte.class -> memorySegment.get(TfheHeader$shared.C_CHAR, 0);
            case Class<?> type when type == Short.class -> memorySegment.get(TfheHeader$shared.C_SHORT, 0);
            case Class<?> type when type == Integer.class -> memorySegment.get(TfheHeader$shared.C_INT, 0);
            case Class<?> type when type == Long.class -> memorySegment.get(TfheHeader$shared.C_LONG_LONG, 0);
            default -> memorySegment.get(TfheHeader$shared.C_POINTER, 0);
          };
          yield java.util.Optional.of(javaType.cast(result));
        }
      }
      case FheValueKind.Wide<V>(var factory) -> {
        var op = FheRegistry.getTryDecryptWideOp(this.getClass());
        if (op == null) yield java.util.Optional.empty();
        V out = factory.get();
        int status = op.apply(getValue(), ((NativeAddress) out).getAddress());
        if (status != 0) {
          MemorySegment errorMessageAddress = TfheHeader.tfhe_error_get_last();
          String errorMessage = errorMessageAddress.getString(0);
          if (!NO_ERROR_MESSAGE.equals(errorMessage)) {
            throw new NativeCallException(status, errorMessage);
          }
          ((NativeAddress) out).destroy();
          yield java.util.Optional.empty();
        }
        yield java.util.Optional.of(out);
      }
    };
  }

  // ══════════════════════════════════════════════════════════════════════════
  // FheRandom
  // ══════════════════════════════════════════════════════════════════════════
  
  /// Returns a new random encrypted value using the given 128-bit seed.
  public final T random(long seedLow, long seedHigh) {
    T r = newInstance();
    execute(() -> handles().misc().random().apply(r.getAddress(), seedLow, seedHigh));
    return r;
  }
  
  /// Returns a new random encrypted value bounded to `bitsCount` significant bits.
  public final T random(long seedLow, long seedHigh, long bitsCount) {
    T r = newInstance();
    execute(() -> handles().misc().randomBounded().apply(r.getAddress(), seedLow, seedHigh, bitsCount));
    return r;
  }

  // ══════════════════════════════════════════════════════════════════════════
  // Protected helpers for concrete-class static factory methods
  // ══════════════════════════════════════════════════════════════════════════

  /// Encrypt a value with a client key.
/// Dispatches on [FheValueKind] to handle primitive vs. wide types.
  protected static <V, T extends AbstractFheType<V, T, ?>> T encryptClientKey(
      FheTypeHandles<V> h, V clear, ClientKey key, java.util.function.Supplier<T> factory) {
    T r = factory.get();
    execute(() -> switch (h.valueKind()) {
      case FheValueKind.Primitive<V> _ ->
          h.encryption().encryptClientKey().apply(clear, key.getValue(), r.getAddress());
      case FheValueKind.Wide<V> _ ->
          h.encryption().encryptWideClientKey().apply(
              ((NativeAddress) clear).getAddress(), key.getValue(), r.getAddress());
    });
    return r;
  }

  /// Encrypt a value with a public key.
  protected static <V, T extends AbstractFheType<V, T, ?>> T encryptPublicKey(
      FheTypeHandles<V> h, V clear, PublicKey key, java.util.function.Supplier<T> factory) {
    T r = factory.get();
    execute(() -> switch (h.valueKind()) {
      case FheValueKind.Primitive<V> _ ->
          h.encryption().encryptPublicKey().apply(clear, key.getValue(), r.getAddress());
      case FheValueKind.Wide<V> _ ->
          h.encryption().encryptWidePublicKey().apply(
              ((NativeAddress) clear).getAddress(), key.getValue(), r.getAddress());
    });
    return r;
  }

  /// Encrypt a value trivially (no key).
  protected static <V, T extends AbstractFheType<V, T, ?>> T encryptTrivial(
      FheTypeHandles<V> h, V clear, java.util.function.Supplier<T> factory) {
    T r = factory.get();
    execute(() -> switch (h.valueKind()) {
      case FheValueKind.Primitive<V> _ ->
          h.encryption().encryptTrivial().apply(clear, r.getAddress());
      case FheValueKind.Wide<V> _ ->
          h.encryption().encryptWideTrivial().apply(
              ((NativeAddress) clear).getAddress(), r.getAddress());
    });
    return r;
  }

  /// Deserialize from a [DynamicBuffer].
  protected static <V, T extends AbstractFheType<V, T, ?>> T deserialize(
      FheTypeHandles<V> h, DynamicBuffer buf, ServerKey key, java.util.function.Supplier<T> factory) {
    T r = factory.get();
    execute(() -> h.lifecycle()
                   .deserialize()
                   .apply(buf.getAddress(), MAX_SERIALIZATION_SIZE, key.getValue(), r.getAddress()));
    return r;
  }

  /// Evaluate a homomorphic if-then-else.
  protected static <V, T extends AbstractFheType<V, T, ?>> T ifThenElse(
      FheTypeHandles<V> h, FheBool condition, T thenVal, T elseVal, java.util.function.Supplier<T> factory) {
    T r = factory.get();
    execute(() -> h.misc().ifThenElse().apply(
        condition.getValue(), thenVal.getValue(), elseVal.getValue(), r.getAddress()));
    return r;
  }

  /// Cast this value to a different FHE type.
  /// Used by concrete type `castInto*()` one-liners.
  /// The destroy function is provided by the target type's own constructor;
  /// we only need a factory to allocate the result slot.
  @SuppressWarnings("unchecked")
  public final <R extends AbstractFheType<?, R, ?>> R castInto(Class<R> targetClass) {
    FheOps.UnaryOp castOp = FheRegistry.getCastOp(this.getClass(), targetClass);
    if (castOp == null) {
      throw new UnsupportedOperationException("Casting from " + this.getClass().getSimpleName() + " to " + targetClass.getSimpleName() + " is not supported.");
    }
    java.util.function.Supplier<R> factory = FheRegistry.getFactory(targetClass);
    return castInto(factory, castOp);
  }

  @SuppressWarnings("unchecked")
  protected final <R extends NativePointer> R castInto(
      java.util.function.Supplier<R> factory,
      FheOps.UnaryOp castOp) {
    R r = factory.get();
    execute(() -> castOp.apply(getValue(), r.getAddress()));
    return r;
  }
}
