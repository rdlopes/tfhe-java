package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheOps.*;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.NativeAddress;
import io.github.rdlopes.tfhe.ffm.NativePointer;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;

/// Abstract base for all compressed FHE types (e.g. `CompressedFheInt8`).
///
/// Compressed types have only five operations: decompress, clone, serialize,
/// deserialize, and encrypt (client key only). All are implemented as
/// `final` methods here. Concrete subclasses supply only a minimal
/// [Handles] record and factory methods.
///
/// @param <V> the Java clear-text type
/// @param <D> the decompressed type (e.g. `FheInt8`)
/// @param <T> the compressed type itself (self-referential)
public abstract class AbstractCompressedFheType<
    V,
    D extends AbstractFheType<V, D, T>,
    T extends AbstractCompressedFheType<V, D, T>>
    extends NativePointer
    implements CompressedFheType<V, D, T> {

  // ──────────────────────────────────────────────────────────────────────────
  // Handles — lightweight record, only the 6 operations needed by compressed types
  // ──────────────────────────────────────────────────────────────────────────

  /// Lightweight handle holder for a compressed type.
  /// Much smaller than [io.github.rdlopes.tfhe.ffm.FheTypeHandles] since
  /// compressed types support only lifecycle + encrypt operations.
  ///
  /// @param <V> the clear-text type
  public record Handles<V>(
      FheValueKind<V>       valueKind,
      UnaryOp               decompress,
      UnaryOp               clone_,
      SerializeOp           serialize,
      DeserializeOp         deserialize,
      EncryptPrimitiveOp<V> encryptClientKey,
      EncryptWideOp         encryptWideClientKey
  ) {}

  // ── Subclass contract ─────────────────────────────────────────────────────

  protected abstract Handles<V> handles();
  protected abstract D newDecompressed();
  protected abstract T newInstance();

  // ── Constructor ───────────────────────────────────────────────────────────

  protected AbstractCompressedFheType(Function<MemorySegment, Integer> destroyRef) {
    super(destroyRef);
  }

  // ══════════════════════════════════════════════════════════════════════════
  // Interface implementation — all final
  // ══════════════════════════════════════════════════════════════════════════

  @Override
  public final D decompress() {
    D r = newDecompressed();
    execute(() -> handles().decompress().apply(getValue(), r.getAddress()));
    return r;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public final T clone() {
    T r = newInstance();
    execute(() -> handles().clone_().apply(getValue(), r.getAddress()));
    return r;
  }

  @Override
  public final DynamicBuffer serialize() {
    DynamicBuffer buf = new DynamicBuffer();
    execute(() -> handles().serialize().apply(getValue(), buf.getAddress(), MAX_SERIALIZATION_SIZE));
    return buf;
  }

  // ══════════════════════════════════════════════════════════════════════════
  // Static factory helpers — called from concrete static methods
  // ══════════════════════════════════════════════════════════════════════════

  /// Encrypt a clear-text value with a client key into a compressed ciphertext.
  protected static <V, T extends AbstractCompressedFheType<V, ?, T>> T encryptClientKey(
      Handles<V> h, V clear, ClientKey key, Supplier<T> factory) {
    T r = factory.get();
    execute(() -> switch (h.valueKind()) {
      case FheValueKind.Primitive<V> ignored ->
          h.encryptClientKey().apply(clear, key.getValue(), r.getAddress());
      case FheValueKind.Wide<V> ignored ->
          h.encryptWideClientKey().apply(
              ((NativeAddress) clear).getAddress(), key.getValue(), r.getAddress());
    });
    return r;
  }

  /// Deserialize a compressed ciphertext from a [DynamicBuffer].
  protected static <V, T extends AbstractCompressedFheType<V, ?, T>> T deserialize(
      Handles<V> h, DynamicBuffer buf, ServerKey key, Supplier<T> factory) {
    T r = factory.get();
    execute(() -> h.deserialize().apply(buf.getAddress(), MAX_SERIALIZATION_SIZE, key.getValue(), r.getAddress()));
    return r;
  }
}
