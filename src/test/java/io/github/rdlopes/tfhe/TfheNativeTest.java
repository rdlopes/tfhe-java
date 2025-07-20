package io.github.rdlopes.tfhe;

import ai.zama.tfhe.DynamicBuffer;
import ai.zama.tfhe.DynamicBufferView;
import ai.zama.tfhe.U128;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static ai.zama.tfhe.TfheNative.*;
import static io.github.rdlopes.tfhe.TfheNativeTestHelper.doWithKeys;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheNativeTest {

  @Test
  void addsTwoEncryptedIntegers() {
    doWithKeys((arena, clientKey, _) -> {
      MemorySegment lhs = U128.allocate(arena);
      U128.w0(lhs, 42);
      U128.w1(lhs, 0);
      MemorySegment rhs = U128.allocate(arena);
      U128.w0(rhs, 57);
      U128.w1(rhs, 0);
      MemorySegment lhsEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(lhs, clientKey.get(C_POINTER, 0), lhsEncrypted)).isZero();
      MemorySegment rhsEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(rhs, clientKey.get(C_POINTER, 0), rhsEncrypted)).isZero();

      MemorySegment resultEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_add(lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

      MemorySegment result = U128.allocate(arena);
      assertThat(fhe_uint128_decrypt(resultEncrypted.get(C_POINTER, 0), clientKey.get(C_POINTER, 0), result)).isZero();

      long w0 = U128.w0(result);
      long w1 = U128.w1(result);
      assertThat(w0).isEqualTo(99);
      assertThat(w1).isEqualTo(0);

      assertThat(fhe_uint128_destroy(resultEncrypted.get(C_POINTER, 0))).isZero();
    });
  }

  @Test
  void xorTwoEncryptedBooleans() {
    doWithKeys(
      (arena, clientKey, _) -> {
        MemorySegment lhsEncrypted = arena.allocate(C_POINTER);
        assertThat(fhe_bool_try_encrypt_with_client_key_bool(true, clientKey.get(C_POINTER, 0), lhsEncrypted)).isZero();
        MemorySegment rhsEncrypted = arena.allocate(C_POINTER);
        assertThat(fhe_bool_try_encrypt_with_client_key_bool(true, clientKey.get(C_POINTER, 0), rhsEncrypted)).isZero();

        MemorySegment resultEncrypted = arena.allocate(C_POINTER);
        assertThat(fhe_bool_bitxor(lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

        MemorySegment result = arena.allocate(C_BOOL);
        assertThat(fhe_bool_decrypt(resultEncrypted.get(C_POINTER, 0), clientKey.get(C_POINTER, 0), result)).isZero();

        boolean resultValue = result.get(C_BOOL, 0);
        assertThat(resultValue).isFalse();

        assertThat(boolean_destroy_ciphertext(resultEncrypted.get(C_POINTER, 0))).isZero();
      });
  }

  @Test
  void serializesKeyPairToDynamicBufferAndExtractsBytes() {
    doWithKeys((arena, clientKey, serverKey) -> {
      // Serialize client key using DynamicBuffer
      MemorySegment clientKeyBuffer = DynamicBuffer.allocate(arena);
      assertThat(client_key_serialize(clientKey.get(C_POINTER, 0), clientKeyBuffer)).isZero();

      // Extract client key bytes to Java byte array
      MemorySegment clientKeyPointer = DynamicBuffer.pointer(clientKeyBuffer);
      long clientKeyLength = DynamicBuffer.length(clientKeyBuffer);
      byte[] clientKeyBytes = new byte[(int) clientKeyLength];
      MemorySegment clientKeyBytesSegment = MemorySegment.ofArray(clientKeyBytes);
      MemorySegment.copy(clientKeyPointer, 0, clientKeyBytesSegment, 0, clientKeyLength);

      // Serialize server key using DynamicBuffer
      MemorySegment serverKeyBuffer = DynamicBuffer.allocate(arena);
      assertThat(server_key_serialize(serverKey.get(C_POINTER, 0), serverKeyBuffer)).isZero();

      // Extract server key bytes to Java byte array
      MemorySegment serverKeyPointer = DynamicBuffer.pointer(serverKeyBuffer);
      long serverKeyLength = DynamicBuffer.length(serverKeyBuffer);
      byte[] serverKeyBytes = new byte[(int) serverKeyLength];
      MemorySegment serverKeyBytesSegment = MemorySegment.ofArray(serverKeyBytes);
      MemorySegment.copy(serverKeyPointer, 0, serverKeyBytesSegment, 0, serverKeyLength);

      // Verify that we have serialized data
      assertThat(clientKeyBytes).isNotEmpty();
      assertThat(serverKeyBytes).isNotEmpty();
      assertThat(clientKeyLength).isGreaterThan(0);
      assertThat(serverKeyLength).isGreaterThan(0);

      assertThat(destroy_dynamic_buffer(clientKeyBuffer)).isZero();
      assertThat(destroy_dynamic_buffer(serverKeyBuffer)).isZero();
    });
  }

  @Test
  void deserializesKeysAndPerformsHomomorphicOperation() {
    doWithKeys((arena, clientKey, serverKey) -> {
      // Serialize client key using DynamicBuffer
      MemorySegment clientKeyBuffer = DynamicBuffer.allocate(arena);
      assertThat(client_key_serialize(clientKey.get(C_POINTER, 0), clientKeyBuffer)).isZero();

      // Extract client key bytes to Java byte array
      MemorySegment clientKeyPointer = DynamicBuffer.pointer(clientKeyBuffer);
      long clientKeyLength = DynamicBuffer.length(clientKeyBuffer);
      byte[] clientKeyBytes = new byte[(int) clientKeyLength];
      MemorySegment clientKeyBytesSegment = MemorySegment.ofArray(clientKeyBytes);
      MemorySegment.copy(clientKeyPointer, 0, clientKeyBytesSegment, 0, clientKeyLength);

      // Serialize server key using DynamicBuffer
      MemorySegment serverKeyBuffer = DynamicBuffer.allocate(arena);
      assertThat(server_key_serialize(serverKey.get(C_POINTER, 0), serverKeyBuffer)).isZero();

      // Extract server key bytes to Java byte array
      MemorySegment serverKeyPointer = DynamicBuffer.pointer(serverKeyBuffer);
      long serverKeyLength = DynamicBuffer.length(serverKeyBuffer);
      byte[] serverKeyBytes = new byte[(int) serverKeyLength];
      MemorySegment serverKeyBytesSegment = MemorySegment.ofArray(serverKeyBytes);
      MemorySegment.copy(serverKeyPointer, 0, serverKeyBytesSegment, 0, serverKeyLength);

      // Verify that we have serialized data
      assertThat(clientKeyBytes).isNotEmpty();
      assertThat(serverKeyBytes).isNotEmpty();
      assertThat(clientKeyLength).isGreaterThan(0);
      assertThat(serverKeyLength).isGreaterThan(0);

      assertThat(destroy_dynamic_buffer(clientKeyBuffer)).isZero();
      assertThat(destroy_dynamic_buffer(serverKeyBuffer)).isZero();

      // Create DynamicBufferView for client key deserialization
      MemorySegment clientKeyView = DynamicBufferView.allocate(arena);
      // Allocate native memory and copy byte array data to it
      MemorySegment clientKeyNativeData = arena.allocate(clientKeyLength);
      MemorySegment clientKeyHeapData = MemorySegment.ofArray(clientKeyBytes);
      MemorySegment.copy(clientKeyHeapData, 0, clientKeyNativeData, 0, clientKeyLength);
      DynamicBufferView.pointer(clientKeyView, clientKeyNativeData);
      DynamicBufferView.length(clientKeyView, clientKeyLength);

      // Deserialize client key
      MemorySegment deserializedClientKey = arena.allocate(C_POINTER);
      assertThat(client_key_deserialize(clientKeyView, deserializedClientKey)).isZero();

      // Create DynamicBufferView for server key deserialization
      MemorySegment serverKeyView = DynamicBufferView.allocate(arena);
      // Allocate native memory and copy byte array data to it
      MemorySegment serverKeyNativeData = arena.allocate(serverKeyLength);
      MemorySegment serverKeyHeapData = MemorySegment.ofArray(serverKeyBytes);
      MemorySegment.copy(serverKeyHeapData, 0, serverKeyNativeData, 0, serverKeyLength);
      DynamicBufferView.pointer(serverKeyView, serverKeyNativeData);
      DynamicBufferView.length(serverKeyView, serverKeyLength);

      // Deserialize server key
      MemorySegment deserializedServerKey = arena.allocate(C_POINTER);
      assertThat(server_key_deserialize(serverKeyView, deserializedServerKey)).isZero();

      // Step 3: Perform homomorphic operation using deserialized keys
      // Set the deserialized server key for operations
      assertThat(set_server_key(deserializedServerKey.get(C_POINTER, 0))).isZero();

      // Create and encrypt two values using the deserialized client key
      MemorySegment lhs = U128.allocate(arena);
      U128.w0(lhs, 25);
      U128.w1(lhs, 0);
      MemorySegment rhs = U128.allocate(arena);
      U128.w0(rhs, 17);
      U128.w1(rhs, 0);

      MemorySegment lhsEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(lhs, deserializedClientKey.get(C_POINTER, 0), lhsEncrypted)).isZero();
      MemorySegment rhsEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_try_encrypt_with_client_key_u128(rhs, deserializedClientKey.get(C_POINTER, 0), rhsEncrypted)).isZero();

      // Perform homomorphic addition
      MemorySegment resultEncrypted = U128.allocate(arena);
      assertThat(fhe_uint128_add(lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

      // Decrypt the result using the deserialized client key
      MemorySegment result = U128.allocate(arena);
      assertThat(fhe_uint128_decrypt(resultEncrypted.get(C_POINTER, 0), deserializedClientKey.get(C_POINTER, 0), result)).isZero();

      // Verify the result is correct (25 + 17 = 42)
      long w0 = U128.w0(result);
      long w1 = U128.w1(result);
      assertThat(w0).isEqualTo(42);
      assertThat(w1).isEqualTo(0);

      assertThat(fhe_uint128_destroy(resultEncrypted.get(C_POINTER, 0))).isZero();
    });
  }
}
