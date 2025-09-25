package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class FheKeySetTest {
  private FheKeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = FheKeySet.builder()
                      .build();
  }

  @Test
  void generatesKeys() {
    assertThat(keySet.getClientKey()).isNotNull();
    assertThat(keySet.getServerKey()).isNotNull();
  }

  @Test
  void enablesCompression() {
    FheKeySet compressionKeySet = FheKeySet.builder()
                                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                           .build();

    assertThat(compressionKeySet.getClientKey()).isNotNull();
    assertThat(compressionKeySet.getServerKey()).isNotNull();
  }

  @Test
  void enablesCompactLists() {
    FheKeySet compressionKeySet = FheKeySet.builder()
                                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                           .useCompactKeyEncryptionParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                           .build();

    assertThat(compressionKeySet.getClientKey()).isNotNull();
    assertThat(compressionKeySet.getServerKey()).isNotNull();
  }
}
