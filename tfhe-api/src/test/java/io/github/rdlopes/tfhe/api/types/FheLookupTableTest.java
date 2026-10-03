package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.extended.FheUint2;
import io.github.rdlopes.tfhe.api.types.extended.FheUint16;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FheLookupTableTest {

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
  void testFheUint2UnivariateLut() {
    // f(x) = (x + 1) % 4
    try (FheUint2 c0 = FheUint2.encrypt((byte) 0, clientKey);
         FheUint2 c1 = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 c2 = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 c3 = FheUint2.encrypt((byte) 3, clientKey)) {

      try (FheUint2 r0 = c0.applyLookupTable(x -> (x + 1) % 4);
           FheUint2 r1 = c1.applyLookupTable(x -> (x + 1) % 4);
           FheUint2 r2 = c2.applyLookupTable(x -> (x + 1) % 4);
           FheUint2 r3 = c3.applyLookupTable(x -> (x + 1) % 4)) {

        assertThat(r0.decrypt(clientKey)).isEqualTo((byte) 1);
        assertThat(r1.decrypt(clientKey)).isEqualTo((byte) 2);
        assertThat(r2.decrypt(clientKey)).isEqualTo((byte) 3);
        assertThat(r3.decrypt(clientKey)).isEqualTo((byte) 0);
      }
    }
  }

  @Test
  void testFheInt8BoundedLut() {
    // Non-linear ReLU activation: f(x) = x > 0 ? x : 0
    try (FheInt8 neg = FheInt8.encrypt((byte) -3, clientKey);
         FheInt8 zero = FheInt8.encrypt((byte) 0, clientKey);
         FheInt8 pos = FheInt8.encrypt((byte) 4, clientKey)) {

      try (FheInt8 rNeg = neg.applyLookupTable(x -> Math.max(0, x), -5, 5);
           FheInt8 rZero = zero.applyLookupTable(x -> Math.max(0, x), -5, 5);
           FheInt8 rPos = pos.applyLookupTable(x -> Math.max(0, x), -5, 5)) {

        assertThat(rNeg.decrypt(clientKey)).isEqualTo((byte) 0);
        assertThat(rZero.decrypt(clientKey)).isEqualTo((byte) 0);
        assertThat(rPos.decrypt(clientKey)).isEqualTo((byte) 4);
      }
    }
  }

  @Test
  void testFheUint2BivariateLut() {
    // g(x, y) = max(x, y)
    try (FheUint2 x = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 y = FheUint2.encrypt((byte) 3, clientKey)) {

      try (FheUint2 max = x.applyBivariateLookupTable(y, Math::max)) {
        assertThat(max.decrypt(clientKey)).isEqualTo((byte) 3);
      }
    }
  }

  @Test
  void testLargeBitwidthRequiresExplicitBounds() {
    try (FheUint16 val = FheUint16.encrypt((short) 10, clientKey)) {
      assertThatThrownBy(() -> val.applyLookupTable(x -> x * 2))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Please specify explicit [minDomain, maxDomain] bounds");
    }
  }
}
