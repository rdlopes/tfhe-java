package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CompactCiphertextListTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
    keySet.getServerKey()
          .use();
  }

  @Test
  void testCompactListBuildAndExpand() {
    CompactPublicKey compactPublicKey = new CompactPublicKey(keySet.getClientKey());
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);

    builder.push(true);
    builder.push((byte) 42);
    builder.push(123456);
    builder.push(9876543210L);
    builder.push(U256.valueOf(BigInteger.valueOf(1000000000000L)));

    CompactCiphertextList list = builder.buildPacked();
    assertThat(list).isNotNull();

    // Test serialization/deserialization
    try (DynamicBuffer serialized = list.serialize()) {
      byte[] bytes = serialized.toByteArray();
      assertThat(bytes).isNotEmpty();

      try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes)) {
        CompactCiphertextList deserialized = CompactCiphertextList.deserialize(buffer);
        assertThat(deserialized).isNotNull();

        try (CompactCiphertextListExpander expander = deserialized.expand()) {
          assertThat(expander.size()).isEqualTo(5);

          assertThat(expander.getKindOf(0)).isEqualTo(FheTypes.FheBool);
          assertThat(expander.getKindOf(1)).isEqualTo(FheTypes.FheInt8);
          assertThat(expander.getKindOf(2)).isEqualTo(FheTypes.FheInt32);
          assertThat(expander.getKindOf(3)).isEqualTo(FheTypes.FheInt64);
          assertThat(expander.getKindOf(4)).isEqualTo(FheTypes.FheUint256);

          FheBool decryptedBool = expander.getFheBool(0);
          FheInt8 decryptedInt8 = expander.getFheInt8(1);
          FheInt32 decryptedInt32 = expander.getFheInt32(2);
          FheInt64 decryptedInt64 = expander.getFheInt64(3);
          FheUint256 decryptedUint256 = expander.getFheUint256(4);

          assertThat(decryptedBool.decrypt(keySet.getClientKey())).isTrue();
          assertThat(decryptedInt8.decrypt(keySet.getClientKey())).isEqualTo((byte) 42);
          assertThat(decryptedInt32.decrypt(keySet.getClientKey())).isEqualTo(123456);
          assertThat(decryptedInt64.decrypt(keySet.getClientKey())).isEqualTo(9876543210L);
          assertThat(decryptedUint256.decrypt(keySet.getClientKey()).getValue()).isEqualTo(BigInteger.valueOf(1000000000000L));
        }
      }
    }
  }
}
