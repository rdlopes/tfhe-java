package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.NativeCall;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Centralized entry-point and factory for FHE types.
///
/// This class provides type-safe factory methods to encrypt values and
/// deserialize buffers. It delegates the operations to specific types
/// using handles registered in [FheRegistry].
public final class Fhe {

  private Fhe() {
    // Utility class
  }

  /// Encrypts a clear-text value using a client key.
  @SuppressWarnings("unchecked")
  public static <V, T extends FheType<V, T, ?>> T encrypt(V clearValue, ClientKey key, Class<T> targetClass) {
    if (targetClass == FheBool.class) {
      return (T) encryptBool((Boolean) clearValue, key);
    }
    return (T) delegateEncrypt(clearValue, key, (Class<? extends AbstractFheType<V, ?, ?>>) targetClass);
  }

  /// Encrypts a clear-text value using a public key.
  @SuppressWarnings("unchecked")
  public static <V, T extends FheType<V, T, ?>> T encrypt(V clearValue, PublicKey key, Class<T> targetClass) {
    if (targetClass == FheBool.class) {
      return (T) encryptBool((Boolean) clearValue, key);
    }
    return (T) delegateEncrypt(clearValue, key, (Class<? extends AbstractFheType<V, ?, ?>>) targetClass);
  }

  /// Encrypts a clear-text value trivially (without a key).
  @SuppressWarnings("unchecked")
  public static <V, T extends FheType<V, T, ?>> T encrypt(V clearValue, Class<T> targetClass) {
    if (targetClass == FheBool.class) {
      return (T) encryptBool((Boolean) clearValue);
    }
    return (T) delegateEncrypt(clearValue, (Class<? extends AbstractFheType<V, ?, ?>>) targetClass);
  }

  /// Deserializes an FHE type from a buffer using a server key.
  @SuppressWarnings("unchecked")
  public static <V, T extends FheType<V, T, ?>> T deserialize(DynamicBuffer buffer, ServerKey key, Class<T> targetClass) {
    if (targetClass == FheBool.class) {
      return (T) deserializeBool(buffer, key);
    }
    return (T) delegateDeserialize(buffer, key, (Class<? extends AbstractFheType<V, ?, ?>>) targetClass);
  }

  @SuppressWarnings("unchecked")
  private static <V, T extends AbstractFheType<V, T, ?>> T delegateEncrypt(V clearValue, ClientKey key, Class<? extends AbstractFheType<V, ?, ?>> targetClass) {
    FheTypeHandles<V> h = FheRegistry.getHandles(targetClass);
    return AbstractFheType.encryptClientKey(h, clearValue, key, (java.util.function.Supplier<T>) FheRegistry.getFactory(targetClass));
  }

  @SuppressWarnings("unchecked")
  private static <V, T extends AbstractFheType<V, T, ?>> T delegateEncrypt(V clearValue, PublicKey key, Class<? extends AbstractFheType<V, ?, ?>> targetClass) {
    FheTypeHandles<V> h = FheRegistry.getHandles(targetClass);
    return AbstractFheType.encryptPublicKey(h, clearValue, key, (java.util.function.Supplier<T>) FheRegistry.getFactory(targetClass));
  }

  @SuppressWarnings("unchecked")
  private static <V, T extends AbstractFheType<V, T, ?>> T delegateEncrypt(V clearValue, Class<? extends AbstractFheType<V, ?, ?>> targetClass) {
    FheTypeHandles<V> h = FheRegistry.getHandles(targetClass);
    return AbstractFheType.encryptTrivial(h, clearValue, (java.util.function.Supplier<T>) FheRegistry.getFactory(targetClass));
  }

  @SuppressWarnings("unchecked")
  private static <V, T extends AbstractFheType<V, T, ?>> T delegateDeserialize(DynamicBuffer buffer, ServerKey key, Class<? extends AbstractFheType<V, ?, ?>> targetClass) {
    FheTypeHandles<V> h = FheRegistry.getHandles(targetClass);
    return AbstractFheType.deserialize(h, buffer, key, (java.util.function.Supplier<T>) FheRegistry.getFactory(targetClass));
  }

  private static FheBool encryptBool(Boolean clearValue, ClientKey key) {
    FheBool encrypted = FheBool.newEmpty();
    NativeCall.execute(() -> io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_bool_try_encrypt_with_client_key_bool(
        clearValue, key.getValue(), encrypted.getAddress()));
    return encrypted;
  }

  private static FheBool encryptBool(Boolean clearValue, PublicKey key) {
    FheBool encrypted = FheBool.newEmpty();
    NativeCall.execute(() -> io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_bool_try_encrypt_with_public_key_bool(
        clearValue, key.getValue(), encrypted.getAddress()));
    return encrypted;
  }

  private static FheBool encryptBool(Boolean clearValue) {
    FheBool encrypted = FheBool.newEmpty();
    NativeCall.execute(() -> io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_bool_try_encrypt_trivial_bool(
        clearValue, encrypted.getAddress()));
    return encrypted;
  }

  private static FheBool deserializeBool(DynamicBuffer buffer, ServerKey key) {
    FheBool deserialized = FheBool.newEmpty();
    NativeCall.execute(() -> io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_bool_safe_deserialize_conformant(
        buffer.getAddress(), DynamicBuffer.MAX_SERIALIZATION_SIZE, key.getValue(), deserialized.getAddress()));
    return deserialized;
  }
}
