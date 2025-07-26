package ai.zama.tfhe;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ai.zama.tfhe.TfheNative.*;
import static ai.zama.tfhe.TfheNativeTestHelper.doWithKeys;
import static ai.zama.tfhe.TfheNativeTestHelper.unit128;
import static java.nio.file.StandardOpenOption.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheNativeTest {

  @Test
  void addsTwoEncryptedIntegers() {
    doWithKeys((arena, clientKey, _) -> {
      MemorySegment lhs = unit128(arena, 42);
      MemorySegment rhs = unit128(arena, 57);
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
      MemorySegment clientKeyBufferPointer = DynamicBuffer.pointer(clientKeyBuffer);
      long clientKeyLength = DynamicBuffer.length(clientKeyBuffer);
      byte[] clientKeyBytes = new byte[(int) clientKeyLength];
      MemorySegment clientKeyBytesSegment = MemorySegment.ofArray(clientKeyBytes);
      MemorySegment.copy(clientKeyBufferPointer, 0, clientKeyBytesSegment, 0, clientKeyLength);

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
      MemorySegment lhs = unit128(arena, 25);
      MemorySegment rhs = unit128(arena, 17);

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

  @Test
  void serializePublicKeySafelyAndPerformBooleanOperation() {
    doWithKeys((arena, clientKey, _) -> {
      MemorySegment publicKey = arena.allocate(C_POINTER);
      assertThat(public_key_new(clientKey.get(C_POINTER, 0), publicKey)).isZero();

      MemorySegment publicKeyBuffer = DynamicBuffer.allocate(arena);
      assertThat(public_key_safe_serialize(publicKey.get(C_POINTER, 0), publicKeyBuffer, Long.MAX_VALUE)).isZero();

      // Create DynamicBufferView directly from the DynamicBuffer without copying to Java byte array
      MemorySegment publicKeyPointer = DynamicBuffer.pointer(publicKeyBuffer);
      long publicKeyLength = DynamicBuffer.length(publicKeyBuffer);
      assertThat(publicKeyLength).isGreaterThan(0);

      MemorySegment publicKeyView = DynamicBufferView.allocate(arena);
      DynamicBufferView.pointer(publicKeyView, publicKeyPointer);
      DynamicBufferView.length(publicKeyView, publicKeyLength);

      Path outputPath = Paths.get("target/serializePublicKeySafelyAndPerformBooleanOperation/tfhe_public_key.bin");
      Files.deleteIfExists(outputPath);
      Files.createDirectories(outputPath.getParent());
      try (FileChannel fc = FileChannel.open(outputPath, CREATE, READ, WRITE)) {
        MemorySegment fileSegment = fc.map(FileChannel.MapMode.READ_WRITE, 0, publicKeyLength, arena);
        MemorySegment.copy(publicKeyPointer, 0, fileSegment, 0, publicKeyLength);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      MemorySegment deserializedPublicKey = arena.allocate(C_POINTER);
      assertThat(public_key_safe_deserialize(publicKeyView, Long.MAX_VALUE, deserializedPublicKey)).isZero();

      MemorySegment lhsEncrypted = arena.allocate(C_POINTER);
      assertThat(fhe_bool_try_encrypt_with_public_key_bool(true, deserializedPublicKey.get(C_POINTER, 0), lhsEncrypted)).isZero();
      MemorySegment rhsEncrypted = arena.allocate(C_POINTER);
      assertThat(fhe_bool_try_encrypt_with_public_key_bool(false, deserializedPublicKey.get(C_POINTER, 0), rhsEncrypted)).isZero();

      MemorySegment resultEncrypted = arena.allocate(C_POINTER);
      assertThat(fhe_bool_bitand(lhsEncrypted.get(C_POINTER, 0), rhsEncrypted.get(C_POINTER, 0), resultEncrypted)).isZero();

      MemorySegment result = arena.allocate(C_BOOL);
      assertThat(fhe_bool_decrypt(resultEncrypted.get(C_POINTER, 0), clientKey.get(C_POINTER, 0), result)).isZero();

      boolean resultValue = result.get(C_BOOL, 0);
      assertThat(resultValue).isFalse();

      assertThat(fhe_bool_destroy(lhsEncrypted.get(C_POINTER, 0))).isZero();
      assertThat(fhe_bool_destroy(rhsEncrypted.get(C_POINTER, 0))).isZero();
      assertThat(fhe_bool_destroy(resultEncrypted.get(C_POINTER, 0))).isZero();
      assertThat(destroy_dynamic_buffer(publicKeyBuffer)).isZero();
      assertThat(public_key_destroy(publicKey.get(C_POINTER, 0))).isZero();
    });
  }
}
