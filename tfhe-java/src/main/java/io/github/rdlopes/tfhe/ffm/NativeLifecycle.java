package io.github.rdlopes.tfhe.ffm;

import java.util.concurrent.atomic.AtomicBoolean;

/// Shared lifecycle token between a [NativeAddress] and its associated
/// [NativeHandle] (held by a [java.lang.ref.Cleaner]).
///
/// When [NativeAddress#release()] or [NativeAddress#destroy()] is
/// called, it flags this object so that the Cleaner callback in
/// [NativeHandle#run()] skips the native destructor call. This avoids a
/// double-free when ownership is deliberately transferred to native code.
///
/// Using a named class (rather than a raw [AtomicBoolean] shared by
/// reference) makes the coupling between the two classes explicit and
/// self-documenting.
final class NativeLifecycle {

  private final AtomicBoolean released = new AtomicBoolean(false);

  /// Marks the native resource as already released (transferred or explicitly freed).
  void release() {
    released.set(true);
  }

  /// Returns `true` if the native resource has already been freed or transferred.
  boolean isReleased() {
    return released.get();
  }
}
