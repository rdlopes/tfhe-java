package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompactPkeCrsTest {

  private Config config;

  @BeforeEach
  void setUp() {
    try (ConfigBuilder builder = new ConfigBuilder()) {
      execute(() -> use_dedicated_compact_public_key_parameters(
          builder.getAddress(),
          SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
      ));
      config = builder.build();
    }
  }

  @Test
  void testZ_ZeroAndSimpleStart() {
    // Zero/Simple: default empty construction does not call native initialization, safe to close
    try (CompactPkeCrs crs = new CompactPkeCrs()) {
      assertThat(crs).isNotNull();
      assertThat(crs.getAddress()).isNotNull();
    }
  }

  @Test
  void testO_OneAndBoundary() {
    // One/Boundary: Initialize from config with boundary bit size
    try (CompactPkeCrs crs = new CompactPkeCrs(config, 8)) {
      assertThat(crs).isNotNull();
      assertThat(crs.getValue()).isNotNull();
    }
  }

  @Test
  void testI_InterfaceSerializationRoundtrip() {
    // Interface: test serialize/deserialize methods
    try (CompactPkeCrs crs = new CompactPkeCrs(config, 8)) {
      // 1. Safe Serialization & Deserialization
      try (DynamicBuffer buffer = crs.serialize()) {
        assertThat(buffer).isNotNull();
        try (CompactPkeCrs deserialized = CompactPkeCrs.deserialize(buffer)) {
          assertThat(deserialized).isNotNull();
          assertThat(deserialized.getValue()).isNotNull();
        }
      }

      // 2. Unsafe Serialization & Deserialization
      try (DynamicBuffer unsafeBuffer = crs.serializeUnsafe()) {
        assertThat(unsafeBuffer).isNotNull();
        try (CompactPkeCrs deserializedUnsafe = CompactPkeCrs.deserializeUnsafe(unsafeBuffer)) {
          assertThat(deserializedUnsafe).isNotNull();
          assertThat(deserializedUnsafe.getValue()).isNotNull();
        }
      }

      // 3. Deserialize from params (should fail/panic with invalid parameters buffer)
      try (DynamicBuffer paramsBuffer = crs.serialize(false)) {
        assertThat(paramsBuffer).isNotNull();
        assertThatThrownBy(() -> CompactPkeCrs.deserializeFromParams(paramsBuffer))
            .isInstanceOf(RuntimeException.class);
      }

      // 4. Safe deserialize from params (should fail/panic with mismatched parameters buffer size)
      try (DynamicBuffer paramsBuffer = crs.serialize(false)) {
        assertThatThrownBy(() -> CompactPkeCrs.safeDeserializeFromParams(paramsBuffer))
            .isInstanceOf(RuntimeException.class);
      }
    }
  }

  @Test
  void testE_ExceptionalBehavior() {
    // Exceptional: deserializing from empty buffer should trigger an exception
    try (DynamicBuffer emptyBuffer = new DynamicBuffer()) {
      assertThatThrownBy(() -> CompactPkeCrs.deserialize(emptyBuffer))
          .isInstanceOf(RuntimeException.class);
    }
  }
}
