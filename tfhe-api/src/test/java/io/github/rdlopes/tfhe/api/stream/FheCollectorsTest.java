package io.github.rdlopes.tfhe.api.stream;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.extended.FheUint2;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FheCollectorsTest {

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
  void testSumMultipleElements() {
    // 1 + 2 = 3 (mod 4 in 2-bit unsigned)
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 sum = Stream.of(a, b).collect(FheCollectors.sum())) {

      assertThat(sum).isNotNull();
      assertThat(sum.decrypt(clientKey)).isEqualTo((byte) 3);
      // Verify inputs were preserved
      assertThat(a.decrypt(clientKey)).isEqualTo((byte) 1);
      assertThat(b.decrypt(clientKey)).isEqualTo((byte) 2);
    }
  }

  @Test
  void testSumConsumingInputs() {
    // 1 + 1 + 1 = 3 (mod 4)
    FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
    FheUint2 b = FheUint2.encrypt((byte) 1, clientKey);
    FheUint2 c = FheUint2.encrypt((byte) 1, clientKey);

    try (FheUint2 sum = Stream.of(a, b, c).collect(FheCollectors.sum(true))) {
      assertThat(sum).isNotNull();
      assertThat(sum.decrypt(clientKey)).isEqualTo((byte) 3);
    }
  }

  @Test
  void testSumEmptyStream() {
    Stream<FheUint2> empty = Stream.empty();
    FheUint2 sum = empty.collect(FheCollectors.sum());
    assertThat(sum).isNull();

    Optional<FheUint2> sumOpt = Stream.<FheUint2>empty().collect(FheCollectors.sumOptional());
    assertThat(sumOpt).isEmpty();
  }

  @Test
  void testSumWithIdentity() {
    try (FheUint2 identity = FheUint2.encrypt((byte) 0, clientKey)) {
      // Empty stream returns identity
      try (FheUint2 resEmpty = Stream.<FheUint2>empty().collect(FheCollectors.sum(identity))) {
        assertThat(resEmpty.decrypt(clientKey)).isEqualTo((byte) 0);
      }

      // Non-empty stream adds to identity
      try (FheUint2 a = FheUint2.encrypt((byte) 2, clientKey);
           FheUint2 b = FheUint2.encrypt((byte) 1, clientKey);
           FheUint2 res = Stream.of(a, b).collect(FheCollectors.sum(identity))) {
        assertThat(res.decrypt(clientKey)).isEqualTo((byte) 3);
      }
    }
  }

  @Test
  void testProduct() {
    // 1 * 3 = 3 (mod 4)
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 3, clientKey);
         FheUint2 prod = Stream.of(a, b).collect(FheCollectors.product())) {

      assertThat(prod).isNotNull();
      assertThat(prod.decrypt(clientKey)).isEqualTo((byte) 3);
    }
  }

  @Test
  void testMinAndMax() {
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 3, clientKey);
         FheUint2 c = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 min = Stream.of(a, b, c).collect(FheCollectors.min());
         FheUint2 max = Stream.of(a, b, c).collect(FheCollectors.max())) {

      assertThat(min).isNotNull();
      assertThat(min.decrypt(clientKey)).isEqualTo((byte) 1);

      assertThat(max).isNotNull();
      assertThat(max.decrypt(clientKey)).isEqualTo((byte) 3);
    }
  }

  @Test
  void testLogicalOperationsOnFheBool() {
    try (FheBool t = FheBool.encrypt(true, clientKey);
         FheBool f = FheBool.encrypt(false, clientKey);
         FheBool and = Stream.of(t, f).collect(FheCollectors.and());
         FheBool or = Stream.of(t, f).collect(FheCollectors.or());
         FheBool xor = Stream.of(t, f).collect(FheCollectors.xor())) {

      assertThat(and.decrypt(clientKey)).isFalse();
      assertThat(or.decrypt(clientKey)).isTrue();
      assertThat(xor.decrypt(clientKey)).isTrue();
    }
  }

  @Test
  void testCountTrue() {
    try (FheBool t1 = FheBool.encrypt(true, clientKey);
         FheBool t2 = FheBool.encrypt(true, clientKey);
         FheBool f = FheBool.encrypt(false, clientKey);
         FheUint32 count = Stream.of(t1, f, t2).collect(FheCollectors.countTrue())) {

      assertThat(count.decrypt(clientKey)).isEqualTo(2);
    }
  }

  @Test
  void testCountingPredicate() {
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 2, clientKey);
         FheUint2 c = FheUint2.encrypt((byte) 3, clientKey);
         // Count elements > 1 (i.e. b and c -> count = 2)
         FheUint32 count = Stream.of(a, b, c).collect(
             FheCollectors.counting(x -> x.greaterThanScalar((byte) 1)))) {

      assertThat(count.decrypt(clientKey)).isEqualTo(2);
    }
  }

  @Test
  void testParallelStreamReduction() {
    try (FheUint2 a = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 b = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 c = FheUint2.encrypt((byte) 1, clientKey);
         FheUint2 sum = List.of(a, b, c).parallelStream().collect(FheCollectors.sum())) {

      assertThat(sum).isNotNull();
      assertThat(sum.decrypt(clientKey)).isEqualTo((byte) 3);
    }
  }
}
