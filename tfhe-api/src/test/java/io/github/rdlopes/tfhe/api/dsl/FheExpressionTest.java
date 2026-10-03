package io.github.rdlopes.tfhe.api.dsl;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheUint2;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheExpressionTest {

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
  void testBasicArithmeticEvaluation() {
    // (1 + 2) = 3 (mod 4)
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 result = FheExpression.of(a).plus(b).eval()) {

      assertThat(result.decrypt(clientKey)).isEqualTo((byte) 3);
      // Verify inputs remain valid
      assertThat(a.decrypt(clientKey)).isEqualTo((byte) 1);
      assertThat(b.decrypt(clientKey)).isEqualTo((byte) 2);
    }
  }

  @Test
  void testScalarArithmetic() {
    // (1 * 3) + 1 = 0 (mod 4) in 2-bit unsigned
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 result = FheExpression.of(a)
             .timesScalar((byte) 3)
             .plusScalar((byte) 1)
             .eval()) {

      assertThat(result.decrypt(clientKey)).isEqualTo((byte) 0);
    }
  }

  @Test
  void testConstantFoldingAndScalarGrouping() {
    // ((a + 1) + 2) should fold into (a + 3)
    try (FheUint2 a = FheUint2.encrypt((byte) 0, clientKey)) {
      FheExpression<Byte, FheUint2> expr = FheExpression.of(a)
          .plusScalar((byte) 1)
          .plusScalar((byte) 2);

      assertThat(expr.depth()).isEqualTo(3);

      FheExpression<Byte, FheUint2> optimized = expr.optimize();
      // After folding, depth is 2: BinaryNode(ADD, a, ScalarNode(3))
      assertThat(optimized.depth()).isEqualTo(2);

      try (FheUint2 res = optimized.eval()) {
        assertThat(res.decrypt(clientKey)).isEqualTo((byte) 3);
      }
    }
  }

  @Test
  void testAssociativeTreeRebalancing() {
    // a + b + c + d initially depth = 4
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 c = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 d = FheUint2.encrypt((byte) 0, clientKey)) {

      FheExpression<Byte, FheUint2> expr = FheExpression.of(a).plus(b).plus(c).plus(d);
      assertThat(expr.depth()).isEqualTo(4);

      FheExpression<Byte, FheUint2> optimized = expr.optimize();
      // Balanced tree: ((a + b) + (c + d)) has depth 3 (root depth 3, sub-branches depth 2, leaves depth 1)
      assertThat(optimized.depth()).isLessThan(expr.depth());

      try (FheUint2 res = optimized.eval()) {
        assertThat(res.decrypt(clientKey)).isEqualTo((byte) 3);
      }
    }
  }

  @Test
  void testConditionalSelectionCMUX() {
    // if cond true then a else b
    try (FheBool condTrue = FheBool.encrypt(true, clientKey);
         FheBool condFalse = FheBool.encrypt(false, clientKey);
         FheUint2 a = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 1, clientKey)) {

      try (FheUint2 resTrue = FheExpression.of(a).ifThenElse(condTrue, b).eval();
           FheUint2 resFalse = FheExpression.of(a).ifThenElse(condFalse, b).eval()) {

        assertThat(resTrue.decrypt(clientKey)).isEqualTo((byte) 2);
        assertThat(resFalse.decrypt(clientKey)).isEqualTo((byte) 1);
      }
    }
  }

  @Test
  void testBitwiseAndMinMaxExpressions() {
    // min(2, 3) = 2, max(2, 3) = 3
    try (FheUint2 a = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 3, clientKey);
         FheUint2 min = FheExpression.of(a).min(b).eval();
         FheUint2 max = FheExpression.of(a).max(b).eval();
         FheUint2 bitAnd = FheExpression.of(a).bitAnd(b).eval()) {

      assertThat(min.decrypt(clientKey)).isEqualTo((byte) 2);
      assertThat(max.decrypt(clientKey)).isEqualTo((byte) 3);
      assertThat(bitAnd.decrypt(clientKey)).isEqualTo((byte) 2);
    }
  }
}
