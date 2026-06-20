package io.github.rdlopes.tfhe.utils;

import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import io.github.rdlopes.tfhe.ffm.FheTypeHandles;

import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/// Runtime registry for dynamically-resolved native operations.
///
/// ## Design rationale
///
/// Operations that are 1:1 per type (lifecycle, arithmetic, bitwise, …) are
/// stored in per-type [io.github.rdlopes.tfhe.ffm.FheTypeHandles] records
/// as compile-time method references. This registry handles the remaining
/// operations whose resolution must be deferred:
///
/// * **Cast ops** — there are \(O(n^2)\) possible type pairs (~7 000+); enumerating
///   them statically in every concrete type would be impractical. Resolved once
///   per pair via [MethodHandle] lookup on first use and cached permanently.
/// * **List-get ops** (compressed and compact) — same cardinality issue.
/// * **Try-decrypt-trivial ops** — sparse: only some types expose a native
///   `try_decrypt_trivial` symbol; others return [Optional#empty()].
///
/// ## Null-caching fix
///
/// [ConcurrentHashMap] does not store `null` values; calling
/// `computeIfAbsent` when the compute function returns `null` leaves
/// no entry in the map, so the next call re-triggers the (expensive) lookup.
/// This implementation uses a typed `ABSENT` sentinel to cache negative
/// results in \(O(1)\).
public final class FheRegistry {
  
  // ── Sentinels for "no native method found" ────────────────────────────────
  
  /// Sentinel stored when a cast op does not exist for a given type pair.
  private static final FheOps.UnaryOp CAST_ABSENT =
    (a, b) -> {
      throw new AssertionError("CAST_ABSENT sentinel invoked");
    };
  
  /// Sentinel stored when a list-get op does not exist for a given type.
  private static final FheOps.ListGetOp LIST_GET_ABSENT =
    (list, idx, result) -> {
      throw new AssertionError("LIST_GET_ABSENT sentinel invoked");
    };
  
  /// Sentinel stored when a try-decrypt op does not exist for a given type.
  private static final FheOps.TryDecryptPrimitiveOp TRY_DECRYPT_PRIM_ABSENT =
    (enc, out) -> {
      throw new AssertionError("TRY_DECRYPT_PRIM_ABSENT sentinel invoked");
    };
  
  /// Sentinel stored when a wide try-decrypt op does not exist for a given type.
  private static final FheOps.TryDecryptWideOp TRY_DECRYPT_WIDE_ABSENT =
    (enc, out) -> {
      throw new AssertionError("TRY_DECRYPT_WIDE_ABSENT sentinel invoked");
    };
  
  // ── Caches ────────────────────────────────────────────────────────────────
  
  private static final Map<ClassPair, FheOps.UnaryOp> CAST_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, Supplier<?>> FACTORIES = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheTypeHandles<?>> HANDLES = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.ListGetOp> COMPRESSED_GET_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.ListGetOp> COMPACT_GET_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.TryDecryptPrimitiveOp> TRY_DECRYPT_PRIM = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.TryDecryptWideOp> TRY_DECRYPT_WIDE = new ConcurrentHashMap<>();
  
  private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
  
  private FheRegistry() {
  }
  
  // ── Factory registration ──────────────────────────────────────────────────
  
  @SuppressWarnings("PMD.EmptyCatchBlock")
  public static void registerFactory(Class<?> clazz, Supplier<?> factory) {
    FACTORIES.put(clazz, factory);
  }
  
  @SuppressWarnings({"unchecked", "PMD.EmptyCatchBlock"})
  public static <T> Supplier<T> getFactory(Class<T> clazz) {
    try {
      // Ensure the class's static initializer has run (registers the factory).
      Class.forName(clazz.getName(), true, clazz.getClassLoader());
    } catch (ClassNotFoundException _) {
      // Class might not exist or be generated yet, ignore
    }
    return (Supplier<T>) FACTORIES.get(clazz);
  }

  // ── Handles registration ──────────────────────────────────────────────────

  @SuppressWarnings("PMD.EmptyCatchBlock")
  public static <V> void registerHandles(Class<?> clazz, FheTypeHandles<V> handles) {
    HANDLES.put(clazz, handles);
  }

  @SuppressWarnings({"unchecked", "PMD.EmptyCatchBlock"})
  public static <V> FheTypeHandles<V> getHandles(Class<?> clazz) {
    try {
      // Ensure the class's static initializer has run (registers the handles).
      Class.forName(clazz.getName(), true, clazz.getClassLoader());
    } catch (ClassNotFoundException _) {
      // Class might not exist or be generated yet, ignore
    }
    return (FheTypeHandles<V>) HANDLES.get(clazz);
  }
  
  // ── Cast ops ──────────────────────────────────────────────────────────────
  
  /// Returns the native cast operation from `sourceClass` to `targetClass`,
  /// or `null` if no such operation exists in the native library.
  ///
  /// Negative results are cached to avoid repeated MethodHandle lookups.
  public static FheOps.UnaryOp getCastOp(Class<?> sourceClass, Class<?> targetClass) {
    FheOps.UnaryOp op = CAST_OPS.computeIfAbsent(
      new ClassPair(sourceClass, targetClass),
      pair -> resolveUnaryOp(
        FheUtils.nativeType(pair.first().getSimpleName())
          + "_cast_into_"
          + FheUtils.nativeType(pair.second().getSimpleName()))
        .orElse(CAST_ABSENT));
    return op == CAST_ABSENT ? null : op;
  }
  
  // ── List-get ops ──────────────────────────────────────────────────────────
  
  /// Returns the native op to extract a `targetClass` value from a
  /// `CompressedCiphertextList`, or `null` if not supported.
  public static FheOps.ListGetOp getCompressedGetOp(Class<?> targetClass) {
    FheOps.ListGetOp op = COMPRESSED_GET_OPS.computeIfAbsent(
      targetClass,
      c -> resolveListGetOp(
        "compressed_ciphertext_list_get_" + FheUtils.nativeType(c.getSimpleName()))
        .orElse(LIST_GET_ABSENT));
    return op == LIST_GET_ABSENT ? null : op;
  }
  
  /// Returns the native op to extract a `targetClass` value from a
  /// `CompactCiphertextListExpander`, or `null` if not supported.
  public static FheOps.ListGetOp getCompactGetOp(Class<?> targetClass) {
    FheOps.ListGetOp op = COMPACT_GET_OPS.computeIfAbsent(
      targetClass,
      c -> resolveListGetOp(
        "compact_ciphertext_list_expander_get_" + FheUtils.nativeType(c.getSimpleName()))
        .orElse(LIST_GET_ABSENT));
    return op == LIST_GET_ABSENT ? null : op;
  }
  
  // ── Try-decrypt-trivial ops ───────────────────────────────────────────────
  
  /// Returns the primitive try-decrypt-trivial op for the given type,
  /// or `null` if the native library does not expose one.
  public static FheOps.TryDecryptPrimitiveOp getTryDecryptPrimitiveOp(Class<?> clazz) {
    FheOps.TryDecryptPrimitiveOp op = TRY_DECRYPT_PRIM.computeIfAbsent(
      clazz,
      c -> resolveUnaryOp(
        FheUtils.nativeType(c.getSimpleName()) + "_try_decrypt_trivial")
        .map(FheRegistry::toTryDecryptPrimitive)
        .orElse(TRY_DECRYPT_PRIM_ABSENT));
    return op == TRY_DECRYPT_PRIM_ABSENT ? null : op;
  }
  
  /// Returns the wide try-decrypt-trivial op for the given type,
  /// or `null` if the native library does not expose one.
  public static FheOps.TryDecryptWideOp getTryDecryptWideOp(Class<?> clazz) {
    FheOps.TryDecryptWideOp op = TRY_DECRYPT_WIDE.computeIfAbsent(
      clazz,
      c -> resolveUnaryOp(
        FheUtils.nativeType(c.getSimpleName()) + "_try_decrypt_trivial")
        .map(FheRegistry::toTryDecryptWide)
        .orElse(TRY_DECRYPT_WIDE_ABSENT));
    return op == TRY_DECRYPT_WIDE_ABSENT ? null : op;
  }
  
  // ── Resolution helpers ────────────────────────────────────────────────────
  
  /// Looks up a static `(MemorySegment, MemorySegment) -> int` method on
  /// [TfheHeader] via [MethodHandle]. Returns [Optional#empty()]
  /// when no such method exists — this is cached by callers using the sentinels above.
  private static Optional<FheOps.UnaryOp> resolveUnaryOp(String methodName) {
    try {
      MethodHandle mh = LOOKUP.findStatic(
        TfheHeader.class,
        methodName,
        MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class));
      return Optional.of((self, result) -> {
        try {
          return (int) mh.invokeExact(self, result);
        } catch (Throwable t) {
          throw new IllegalStateException("Native call failed: " + methodName, t);
        }
      });
    } catch (NoSuchMethodException | IllegalAccessException _) {
      // Method doesn't exist in native, ignore
      return Optional.empty();
    }
  }
  
  /// Looks up a static `(MemorySegment, long, MemorySegment) -> int` method
  /// on [TfheHeader]. Returns [Optional#empty()] when no such method exists.
  private static Optional<FheOps.ListGetOp> resolveListGetOp(String methodName) {
    try {
      MethodHandle mh = LOOKUP.findStatic(
        TfheHeader.class,
        methodName,
        MethodType.methodType(int.class, MemorySegment.class, long.class, MemorySegment.class));
      return Optional.of((list, index, result) -> {
        try {
          return (int) mh.invokeExact(list, index, result);
        } catch (Throwable t) {
          throw new IllegalStateException("Native call failed: " + methodName, t);
        }
      });
    } catch (NoSuchMethodException | IllegalAccessException _) {
      // Method doesn't exist in native, ignore
      return Optional.empty();
    }
  }
  
  private static FheOps.TryDecryptPrimitiveOp toTryDecryptPrimitive(FheOps.UnaryOp op) {
    return op::apply;
  }
  
  private static FheOps.TryDecryptWideOp toTryDecryptWide(FheOps.UnaryOp op) {
    return op::apply;
  }
  
  // ── Key types ─────────────────────────────────────────────────────────────
  
  private record ClassPair(Class<?> first, Class<?> second) {
  }
}
