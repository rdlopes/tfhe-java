package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.extended.FheInt32Array;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FheArraySignedTest {

  private static ClientKey clientKey;

  @BeforeAll
  static void setUp() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    clientKey = keySet.getClientKey();
    ServerKey serverKey = keySet.getServerKey();
    serverKey.use();
  }

  @Test
  void testSignedInt8ArrayEqualsAndContains() {
    try (FheInt8Array arr1 = FheInt8Array.encrypt(List.of((byte) 1, (byte) -2, (byte) 3), clientKey);
         FheInt8Array arr2 = FheInt8Array.encrypt(List.of((byte) 1, (byte) -2, (byte) 3), clientKey);
         FheInt8Array arr3 = FheInt8Array.encrypt(List.of((byte) 1, (byte) 2, (byte) 3), clientKey);
         FheInt8Array sub = FheInt8Array.encrypt(List.of((byte) -2, (byte) 3), clientKey);
         FheInt8Array notSub = FheInt8Array.encrypt(List.of((byte) -2, (byte) 4), clientKey)) {

      try (FheBool eq = arr1.equalsArray(arr2);
           FheBool neq = arr1.equalsArray(arr3);
           FheBool contains = arr1.containsArray(sub);
           FheBool notContains = arr1.containsArray(notSub)) {

        assertThat(eq.decrypt(clientKey)).isTrue();
        assertThat(neq.decrypt(clientKey)).isFalse();
        assertThat(contains.decrypt(clientKey)).isTrue();
        assertThat(notContains.decrypt(clientKey)).isFalse();
      }
    }
  }

  @Test
  void testSignedInt32ArrayEqualsAndContains() {
    try (FheInt32Array arr1 = FheInt32Array.encrypt(List.of(100, -200, 300), clientKey);
         FheInt32Array arr2 = FheInt32Array.encrypt(List.of(100, -200, 300), clientKey);
         FheInt32Array arr3 = FheInt32Array.encrypt(List.of(100, 200, 300), clientKey);
         FheInt32Array sub = FheInt32Array.encrypt(List.of(-200, 300), clientKey)) {

      try (FheBool eq = arr1.equalsArray(arr2);
           FheBool neq = arr1.equalsArray(arr3);
           FheBool contains = arr1.containsArray(sub)) {

        assertThat(eq.decrypt(clientKey)).isTrue();
        assertThat(neq.decrypt(clientKey)).isFalse();
        assertThat(contains.decrypt(clientKey)).isTrue();
      }
    }
  }
}
