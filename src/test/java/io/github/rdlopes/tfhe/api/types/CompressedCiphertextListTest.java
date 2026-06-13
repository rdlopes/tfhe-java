package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class CompressedCiphertextListTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .build();
    keySet.getServerKey()
          .use();
  }

  @Test
  void testCompressedListBuildAndUnpack() {
    // Encrypt some inputs
    FheBool boolVal = FheBool.encrypt(true, keySet.getClientKey());
    FheUint8 uint8Val = FheUint8.encrypt((byte) 42, keySet.getClientKey());
    FheInt32 int32Val = FheInt32.encrypt(123456, keySet.getClientKey());
    FheUint256 uint256Val = FheUint256.encrypt(U256.valueOf(BigInteger.valueOf(1000000000000L)), keySet.getClientKey());

    // Create the compressed ciphertext list
    try (CompressedCiphertextListBuilder builder = new CompressedCiphertextListBuilder()) {
      builder.push(boolVal);
      builder.push(uint8Val);
      builder.push(int32Val);
      builder.push(uint256Val);

      try (CompressedCiphertextList list = builder.build()) {
        assertThat(list).isNotNull();

        // Serialize the list
        try (DynamicBuffer serialized = list.serialize()) {
          byte[] bytes = serialized.toByteArray();
          assertThat(bytes).isNotEmpty();

          // Deserialize the list
          try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes);
               CompressedCiphertextList deserialized = CompressedCiphertextList.deserialize(buffer)) {
            assertThat(deserialized).isNotNull();

            // Verify size and kinds
            assertThat(deserialized.size()).isEqualTo(4);
            assertThat(deserialized.getKindOf(0)).isEqualTo(FheTypes.FheBool);
            assertThat(deserialized.getKindOf(1)).isEqualTo(FheTypes.FheUint8);
            assertThat(deserialized.getKindOf(2)).isEqualTo(FheTypes.FheInt32);
            assertThat(deserialized.getKindOf(3)).isEqualTo(FheTypes.FheUint256);

            // Extract and decrypt
            FheBool decryptedBool = deserialized.getFheBool(0);
            FheUint8 decryptedUint8 = deserialized.getFheUint8(1);
            FheInt32 decryptedInt32 = deserialized.getFheInt32(2);
            FheUint256 decryptedUint256 = deserialized.getFheUint256(3);

            assertThat(decryptedBool.decrypt(keySet.getClientKey())).isTrue();
            assertThat(decryptedUint8.decrypt(keySet.getClientKey())).isEqualTo((byte) 42);
            assertThat(decryptedInt32.decrypt(keySet.getClientKey())).isEqualTo(123456);
            assertThat(decryptedUint256.decrypt(keySet.getClientKey()).getValue()).isEqualTo(BigInteger.valueOf(1000000000000L));
          }
        }
      }
    }
  }
}
