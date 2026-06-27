package io.github.rdlopes.tfhe.core.ffm;

import java.util.function.Supplier;

/// Sealed hierarchy encoding how a Java clear-text value `V` is passed
/// to and from native FFM functions.
///
/// Exactly two cases exist across all 88 FHE types:
/// - [Primitive] — `V` is a Java primitive wrapper
///   (`Boolean, Byte, Short, Integer, Long`).
///   Decrypt uses `NativeCall.executeAndReturn(javaType, …)`.
/// - [Wide] — `V` is a struct-like value type
///   (`I128, I256, U256, …`) backed by a native memory segment.
///   Decrypt writes directly into `V`'s native address.
///
/// @param <V> the Java clear-text type
@SuppressWarnings("java:S2326")
public sealed interface FheValueKind<V> {

  /// V is a Java primitive wrapper.
  /// The `javaType` drives `NativeCall.executeAndReturn` dispatch.
  record Primitive<V>(Class<V> javaType) implements FheValueKind<V> {}

  /// V is a wide value type backed by native memory.
  /// The `factory` creates a new empty instance that receives the decrypted result.
  record Wide<V>(Supplier<V> factory) implements FheValueKind<V> {}

  // ── Pre-built singletons for all primitive types used in the project ─────────

  FheValueKind<Boolean> BOOL  = new Primitive<>(Boolean.class);
  FheValueKind<Byte>    BYTE  = new Primitive<>(Byte.class);
  FheValueKind<Short>   SHORT = new Primitive<>(Short.class);
  FheValueKind<Integer> INT   = new Primitive<>(Integer.class);
  FheValueKind<Long>    LONG  = new Primitive<>(Long.class);
}
