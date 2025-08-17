package io.github.rdlopes.tfhe.ffm;

import org.junit.jupiter.api.*;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheNative.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("native")
class HighLevelArrayTest {

  private final MemorySegment configBuilderPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment configPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment clientKeyPtr = TfheWrapper.createPointer(C_POINTER);
  private final MemorySegment serverKeyPtr = TfheWrapper.createPointer(C_POINTER);

  @BeforeAll
  static void beforeAll() {
    TfheWrapper.loadNativeLibrary();
  }

  @BeforeEach
  void setUp() {
    int rcBuilder = config_builder_default(configBuilderPtr);
    assertThat(rcBuilder).isZero();

    int rcConfig = config_builder_build(configBuilderPtr.get(C_POINTER, 0), configPtr);
    assertThat(rcConfig).isZero();

    int rcKeys = generate_keys(configPtr.get(C_POINTER, 0), clientKeyPtr, serverKeyPtr);
    assertThat(rcKeys).isZero();

    int rcSetServerKey = set_server_key(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcSetServerKey).isZero();
  }

  @AfterEach
  void tearDown() {
    int rcDestroyClientKey = client_key_destroy(clientKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyClientKey).isZero();
    int rcDestroyServerKey = server_key_destroy(serverKeyPtr.get(C_POINTER, 0));
    assertThat(rcDestroyServerKey).isZero();
  }

  /**
   * Encrypts a string in a FheUint8 array
   * Ports the encrypt_str function from the C test
   */
  private MemorySegment[] encryptString(String str, MemorySegment clientKey) {
    byte[] bytes = str.getBytes();
    MemorySegment[] result = new MemorySegment[bytes.length];

    for (int i = 0; i < bytes.length; i++) {
      MemorySegment fheUint8Ptr = TfheWrapper.createPointer(C_POINTER);
      int rc = fhe_uint8_try_encrypt_with_client_key_u8(bytes[i], clientKey, fheUint8Ptr);
      assertThat(rc).isZero();
      result[i] = fheUint8Ptr.get(C_POINTER, 0);
    }

    return result;
  }

  /**
   * Destroys an array of FheUint8 values
   * Ports the destroy_fhe_uint8_array function from the C test
   */
  private void destroyFheUint8Array(MemorySegment[] array) {
    for (MemorySegment segment : array) {
      int rc = fhe_uint8_destroy(segment);
      assertThat(rc).isZero();
    }
  }

  @Test
  void testArrayEquality() {
    String sentence = "The quick brown fox jumps over the lazy dog";
    String pattern1 = "wn fox ";
    String pattern2 = "tfhe-rs";

    assertThat(pattern1.length()).isEqualTo(pattern2.length()); // We use this later in the tests

    MemorySegment[] encryptedSentence = encryptString(sentence, clientKeyPtr.get(C_POINTER, 0));
    MemorySegment[] encryptedPattern1 = encryptString(pattern1, clientKeyPtr.get(C_POINTER, 0));
    MemorySegment[] encryptedPattern2 = encryptString(pattern2, clientKeyPtr.get(C_POINTER, 0));

    // Test equality with different lengths (sentence vs pattern1)
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      // Create arrays for the native call
      MemorySegment sentenceArray = TfheWrapper.createPointer(C_POINTER, encryptedSentence.length);
      MemorySegment pattern1Array = TfheWrapper.createPointer(C_POINTER, encryptedPattern1.length);

      for (int i = 0; i < encryptedSentence.length; i++) {
        sentenceArray.setAtIndex(C_POINTER, i, encryptedSentence[i]);
      }
      for (int i = 0; i < encryptedPattern1.length; i++) {
        pattern1Array.setAtIndex(C_POINTER, i, encryptedPattern1[i]);
      }

      int rc = fhe_uint8_array_eq(sentenceArray, encryptedSentence.length,
        pattern1Array, encryptedPattern1.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isFalse(); // Different lengths should be false

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    // Test equality with same lengths but different content (pattern2 vs pattern1)
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      MemorySegment pattern2Array = TfheWrapper.createPointer(C_POINTER, encryptedPattern2.length);
      MemorySegment pattern1Array = TfheWrapper.createPointer(C_POINTER, encryptedPattern1.length);

      for (int i = 0; i < encryptedPattern2.length; i++) {
        pattern2Array.setAtIndex(C_POINTER, i, encryptedPattern2[i]);
      }
      for (int i = 0; i < encryptedPattern1.length; i++) {
        pattern1Array.setAtIndex(C_POINTER, i, encryptedPattern1[i]);
      }

      int rc = fhe_uint8_array_eq(pattern2Array, encryptedPattern2.length,
        pattern1Array, encryptedPattern1.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isFalse(); // Different content should be false

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    // Test equality with same array (sentence vs sentence)
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      MemorySegment sentenceArray = TfheWrapper.createPointer(C_POINTER, encryptedSentence.length);

      for (int i = 0; i < encryptedSentence.length; i++) {
        sentenceArray.setAtIndex(C_POINTER, i, encryptedSentence[i]);
      }

      int rc = fhe_uint8_array_eq(sentenceArray, encryptedSentence.length,
        sentenceArray, encryptedSentence.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isTrue(); // Same array should be true

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    destroyFheUint8Array(encryptedSentence);
    destroyFheUint8Array(encryptedPattern1);
    destroyFheUint8Array(encryptedPattern2);
  }

  @Test
  void testArrayContainsSubSlice() {
    String sentence = "The quick brown fox jumps over the lazy dog";
    String pattern1 = "wn fox ";  // This should be found in the sentence
    String pattern2 = "tfhe-rs";  // This should not be found in the sentence

    assertThat(pattern1.length()).isEqualTo(pattern2.length()); // We use this later in the tests

    MemorySegment[] encryptedSentence = encryptString(sentence, clientKeyPtr.get(C_POINTER, 0));
    MemorySegment[] encryptedPattern1 = encryptString(pattern1, clientKeyPtr.get(C_POINTER, 0));
    MemorySegment[] encryptedPattern2 = encryptString(pattern2, clientKeyPtr.get(C_POINTER, 0));

    // Test contains sub slice - pattern1 should be found in sentence
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      MemorySegment sentenceArray = TfheWrapper.createPointer(C_POINTER, encryptedSentence.length);
      MemorySegment pattern1Array = TfheWrapper.createPointer(C_POINTER, encryptedPattern1.length);

      for (int i = 0; i < encryptedSentence.length; i++) {
        sentenceArray.setAtIndex(C_POINTER, i, encryptedSentence[i]);
      }
      for (int i = 0; i < encryptedPattern1.length; i++) {
        pattern1Array.setAtIndex(C_POINTER, i, encryptedPattern1[i]);
      }

      int rc = fhe_uint8_array_contains_sub_slice(sentenceArray, encryptedSentence.length,
        pattern1Array, encryptedPattern1.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isTrue(); // pattern1 should be found

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    // Test contains sub slice - pattern2 should not be found in sentence
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      MemorySegment sentenceArray = TfheWrapper.createPointer(C_POINTER, encryptedSentence.length);
      MemorySegment pattern2Array = TfheWrapper.createPointer(C_POINTER, encryptedPattern2.length);

      for (int i = 0; i < encryptedSentence.length; i++) {
        sentenceArray.setAtIndex(C_POINTER, i, encryptedSentence[i]);
      }
      for (int i = 0; i < encryptedPattern2.length; i++) {
        pattern2Array.setAtIndex(C_POINTER, i, encryptedPattern2[i]);
      }

      int rc = fhe_uint8_array_contains_sub_slice(sentenceArray, encryptedSentence.length,
        pattern2Array, encryptedPattern2.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isFalse(); // pattern2 should not be found

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    // Test contains sub slice - sentence should contain itself
    {
      MemorySegment resultPtr = TfheWrapper.createPointer(C_POINTER);

      MemorySegment sentenceArray = TfheWrapper.createPointer(C_POINTER, encryptedSentence.length);

      for (int i = 0; i < encryptedSentence.length; i++) {
        sentenceArray.setAtIndex(C_POINTER, i, encryptedSentence[i]);
      }

      int rc = fhe_uint8_array_contains_sub_slice(sentenceArray, encryptedSentence.length,
        sentenceArray, encryptedSentence.length, resultPtr);
      assertThat(rc).isZero();

      MemorySegment clearResult = TfheWrapper.createPointer(C_CHAR);
      int rcDecrypt = fhe_bool_decrypt(resultPtr.get(C_POINTER, 0), clientKeyPtr.get(C_POINTER, 0), clearResult);
      assertThat(rcDecrypt).isZero();

      boolean decryptedValue = clearResult.get(C_CHAR, 0) != 0;
      assertThat(decryptedValue).isTrue(); // sentence should contain itself

      int rcDestroy = fhe_bool_destroy(resultPtr.get(C_POINTER, 0));
      assertThat(rcDestroy).isZero();
    }

    destroyFheUint8Array(encryptedSentence);
    destroyFheUint8Array(encryptedPattern1);
    destroyFheUint8Array(encryptedPattern2);
  }
}
