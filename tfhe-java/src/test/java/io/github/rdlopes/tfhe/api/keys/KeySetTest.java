package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.types.extended.CompressedFheUint256;
import io.github.rdlopes.tfhe.api.types.extended.CompressedFheUint32;
import io.github.rdlopes.tfhe.api.types.extended.FheUint256;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;
import io.github.rdlopes.tfhe.api.values.extended.U256;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class KeySetTest {
  private static final Logger logger = LoggerFactory.getLogger(KeySetTest.class);
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }
  
  @AfterEach
  void tearDown() {
    if (keySet != null) keySet.close();
  }
  
  // tag::keyset_builder[]
  @Test
  void generatesKeys() {
    assertThat(keySet.getClientKey()).isNotNull();
    assertThat(keySet.getServerKey()).isNotNull();
  }
// end::keyset_builder[]

// tag::keyset_advanced_builders[]
  @Test
  void enablesCompression() {
    try (KeySet compressionKeySet = KeySet.builder()
                                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                          .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                          .build()) {
      assertThat(compressionKeySet.getClientKey()).isNotNull();
      assertThat(compressionKeySet.getServerKey()).isNotNull();
    }
  }

  @Test
  void enablesCompactLists() {
    try (KeySet compressionKeySet = KeySet.builder()
                                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                          .useCompactKeyEncryptionParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                          .build()) {
      assertThat(compressionKeySet.getClientKey()).isNotNull();
      assertThat(compressionKeySet.getServerKey()).isNotNull();
    }
  }
// end::keyset_advanced_builders[]

  @Test
  void testCompressedFheUint32() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      
      // In-memory compression & direct decompression
      FheUint32 clientCiphertext = FheUint32.encrypt(100, ks.getClientKey());
      CompressedFheUint32 compressedInMemory = clientCiphertext.compress();
      FheUint32 decompressedDirect = compressedInMemory.decompress();
      assertThat(decompressedDirect.decrypt(ks.getClientKey())).isEqualTo(100);
      
      // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
      CompressedFheUint32 compressed = CompressedFheUint32.encrypt(100, ks.getClientKey());
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
        CompressedFheUint32 deserialized = CompressedFheUint32.deserialize(serialized, ks.getServerKey());
        FheUint32 decompressed = deserialized.decompress();
        int result = decompressed.decrypt(ks.getClientKey());
        assertThat(result).isEqualTo(100);
      }
    }
  }

  @Test
  void testCompressedFheBool() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      
      // In-memory compression & direct decompression
      io.github.rdlopes.tfhe.api.types.FheBool clientCiphertext = io.github.rdlopes.tfhe.api.types.FheBool.encrypt(true, ks.getClientKey());
      io.github.rdlopes.tfhe.api.types.CompressedFheBool compressedInMemory = clientCiphertext.compress();
      io.github.rdlopes.tfhe.api.types.FheBool decompressedDirect = compressedInMemory.decompress();
      assertThat(decompressedDirect.decrypt(ks.getClientKey())).isTrue();
      
      // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
      io.github.rdlopes.tfhe.api.types.CompressedFheBool compressed = io.github.rdlopes.tfhe.api.types.CompressedFheBool.encrypt(true, ks.getClientKey());
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
        io.github.rdlopes.tfhe.api.types.CompressedFheBool deserialized = io.github.rdlopes.tfhe.api.types.CompressedFheBool.deserialize(serialized, ks.getServerKey());
        io.github.rdlopes.tfhe.api.types.FheBool decompressed = deserialized.decompress();
        boolean result = decompressed.decrypt(ks.getClientKey());
        assertThat(result).isTrue();
      }
    }
  }

  @Test
  void encryptsAndDecryptsFheUint32() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      FheUint32 clientCiphertext = FheUint32.encrypt(100, ks.getClientKey());
      
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = clientCiphertext.serialize()) {
        logger.info("Serialized uncompressed length: {}", serialized.getLength());
        FheUint32 deserialized = FheUint32.deserialize(serialized, ks.getServerKey());
        logger.info("Deserialization uncompressed succeeded!");
        int result = deserialized.decrypt(ks.getClientKey());
        assertThat(result).isEqualTo(100);
      }
    }
  }

  @Test
  void encryptsAndDecryptsFheUint32WithCompressedCiphertext() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      FheUint32 clientCiphertext = FheUint32.encrypt(100, ks.getClientKey());
      
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = clientCiphertext.serialize()) {
        logger.info("Serialized uncompressed (with comp key) length: {}", serialized.getLength());
        FheUint32 deserialized = FheUint32.deserialize(serialized, ks.getServerKey());
        logger.info("Deserialization uncompressed (with comp key) succeeded!");
        int result = deserialized.decrypt(ks.getClientKey());
        assertThat(result).isEqualTo(100);
      }
    }
  }

  @Test
  void testCompressedFheUint8() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      
      // In-memory compression & direct decompression
      io.github.rdlopes.tfhe.api.types.FheUint8 clientCiphertext = io.github.rdlopes.tfhe.api.types.FheUint8.encrypt((byte) 100, ks.getClientKey());
      io.github.rdlopes.tfhe.api.types.CompressedFheUint8 compressedInMemory = clientCiphertext.compress();
      io.github.rdlopes.tfhe.api.types.FheUint8 decompressedDirect = compressedInMemory.decompress();
      assertThat(decompressedDirect.decrypt(ks.getClientKey())).isEqualTo((byte) 100);
      
      // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
      io.github.rdlopes.tfhe.api.types.CompressedFheUint8 compressed = io.github.rdlopes.tfhe.api.types.CompressedFheUint8.encrypt((byte) 100, ks.getClientKey());
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
        io.github.rdlopes.tfhe.api.types.CompressedFheUint8 deserialized = io.github.rdlopes.tfhe.api.types.CompressedFheUint8.deserialize(serialized, ks.getServerKey());
        io.github.rdlopes.tfhe.api.types.FheUint8 decompressed = deserialized.decompress();
        byte result = decompressed.decrypt(ks.getClientKey());
        assertThat(result).isEqualTo((byte) 100);
      }
    }
  }

  @Test
  void testCompressedFheUint256() {
    try (KeySet ks = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build()) {
      ks.getCompressedServerKey().use();
      
      U256 value = U256.of(BigInteger.valueOf(100));
      
      // In-memory compression & direct decompression
      FheUint256 clientCiphertext = FheUint256.encrypt(value, ks.getClientKey());
      CompressedFheUint256 compressedInMemory = clientCiphertext.compress();
      FheUint256 decompressedDirect = compressedInMemory.decompress();
      assertThat(decompressedDirect.decrypt(ks.getClientKey()).asBigInteger()).isEqualTo(value.asBigInteger());
      
      // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
      CompressedFheUint256 compressed = CompressedFheUint256.encrypt(value, ks.getClientKey());
      try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
        CompressedFheUint256 deserialized = CompressedFheUint256.deserialize(serialized, ks.getServerKey());
        FheUint256 decompressed = deserialized.decompress();
        U256 result = decompressed.decrypt(ks.getClientKey());
        assertThat(result.asBigInteger()).isEqualTo(value.asBigInteger());
      }
    }
  }
}
