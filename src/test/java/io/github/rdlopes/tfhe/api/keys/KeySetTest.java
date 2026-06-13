package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static org.assertj.core.api.Assertions.assertThat;

class KeySetTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
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
    KeySet compressionKeySet = KeySet.builder()
                                     .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                     .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                     .build();

    assertThat(compressionKeySet.getClientKey()).isNotNull();
    assertThat(compressionKeySet.getServerKey()).isNotNull();
  }

  @Test
  void enablesCompactLists() {
    KeySet compressionKeySet = KeySet.builder()
                                     .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                     .useCompactKeyEncryptionParameters(SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                                     .build();

    assertThat(compressionKeySet.getClientKey()).isNotNull();
    assertThat(compressionKeySet.getServerKey()).isNotNull();
  }
// end::keyset_advanced_builders[]
  @Test
  void testCompressedFheUint32() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    keySet.getServerKey().use();
    
    // In-memory compression & direct decompression
    io.github.rdlopes.tfhe.api.types.FheUint32 clientCiphertext = io.github.rdlopes.tfhe.api.types.FheUint32.encrypt(100, keySet.getClientKey());
    io.github.rdlopes.tfhe.api.types.CompressedFheUint32 compressedInMemory = clientCiphertext.compress();
    io.github.rdlopes.tfhe.api.types.FheUint32 decompressedDirect = compressedInMemory.decompress();
    assertThat(decompressedDirect.decrypt(keySet.getClientKey())).isEqualTo(100);

    // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
    io.github.rdlopes.tfhe.api.types.CompressedFheUint32 compressed = io.github.rdlopes.tfhe.api.types.CompressedFheUint32.encrypt(100, keySet.getClientKey());
    try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
      io.github.rdlopes.tfhe.api.types.CompressedFheUint32 deserialized = io.github.rdlopes.tfhe.api.types.CompressedFheUint32.deserialize(serialized, keySet.getServerKey());
      io.github.rdlopes.tfhe.api.types.FheUint32 decompressed = deserialized.decompress();
      int result = decompressed.decrypt(keySet.getClientKey());
      assertThat(result).isEqualTo(100);
    }
  }

  @Test
  void testCompressedFheBool() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    keySet.getServerKey().use();

    // In-memory compression & direct decompression
    io.github.rdlopes.tfhe.api.types.FheBool clientCiphertext = io.github.rdlopes.tfhe.api.types.FheBool.encrypt(true, keySet.getClientKey());
    io.github.rdlopes.tfhe.api.types.CompressedFheBool compressedInMemory = clientCiphertext.compress();
    io.github.rdlopes.tfhe.api.types.FheBool decompressedDirect = compressedInMemory.decompress();
    assertThat(decompressedDirect.decrypt(keySet.getClientKey())).isTrue();

    // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
    io.github.rdlopes.tfhe.api.types.CompressedFheBool compressed = io.github.rdlopes.tfhe.api.types.CompressedFheBool.encrypt(true, keySet.getClientKey());
    try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
      io.github.rdlopes.tfhe.api.types.CompressedFheBool deserialized = io.github.rdlopes.tfhe.api.types.CompressedFheBool.deserialize(serialized, keySet.getServerKey());
      io.github.rdlopes.tfhe.api.types.FheBool decompressed = deserialized.decompress();
      boolean result = decompressed.decrypt(keySet.getClientKey());
      assertThat(result).isTrue();
    }
  }

  @Test
  void testFheUint32() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    keySet.getServerKey().use();
    io.github.rdlopes.tfhe.api.types.FheUint32 clientCiphertext = io.github.rdlopes.tfhe.api.types.FheUint32.encrypt(100, keySet.getClientKey());
    
    try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = clientCiphertext.serialize()) {
      System.out.println("Serialized uncompressed length: " + serialized.getLength());
      io.github.rdlopes.tfhe.api.types.FheUint32 deserialized = io.github.rdlopes.tfhe.api.types.FheUint32.deserialize(serialized, keySet.getServerKey());
      System.out.println("Deserialization uncompressed succeeded!");
      int result = deserialized.decrypt(keySet.getClientKey());
      assertThat(result).isEqualTo(100);
    }
  }

  @Test
  void testFheUint32WithCompression() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    keySet.getServerKey().use();
    io.github.rdlopes.tfhe.api.types.FheUint32 clientCiphertext = io.github.rdlopes.tfhe.api.types.FheUint32.encrypt(100, keySet.getClientKey());
    
    try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = clientCiphertext.serialize()) {
      System.out.println("Serialized uncompressed (with comp key) length: " + serialized.getLength());
      io.github.rdlopes.tfhe.api.types.FheUint32 deserialized = io.github.rdlopes.tfhe.api.types.FheUint32.deserialize(serialized, keySet.getServerKey());
      System.out.println("Deserialization uncompressed (with comp key) succeeded!");
      int result = deserialized.decrypt(keySet.getClientKey());
      assertThat(result).isEqualTo(100);
    }
  }

  @Test
  void testCompressedFheUint8() {
    KeySet keySet = KeySet.builder()
                          .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                          .build();
    keySet.getServerKey().use();

    // In-memory compression & direct decompression
    io.github.rdlopes.tfhe.api.types.FheUint8 clientCiphertext = io.github.rdlopes.tfhe.api.types.FheUint8.encrypt((byte) 100, keySet.getClientKey());
    io.github.rdlopes.tfhe.api.types.CompressedFheUint8 compressedInMemory = clientCiphertext.compress();
    io.github.rdlopes.tfhe.api.types.FheUint8 decompressedDirect = compressedInMemory.decompress();
    assertThat(decompressedDirect.decrypt(keySet.getClientKey())).isEqualTo((byte) 100);

    // Direct encryption (seeded compressed ciphertext) supports safe serialization/deserialization
    io.github.rdlopes.tfhe.api.types.CompressedFheUint8 compressed = io.github.rdlopes.tfhe.api.types.CompressedFheUint8.encrypt((byte) 100, keySet.getClientKey());
    try (io.github.rdlopes.tfhe.api.serde.DynamicBuffer serialized = compressed.serialize()) {
      io.github.rdlopes.tfhe.api.types.CompressedFheUint8 deserialized = io.github.rdlopes.tfhe.api.types.CompressedFheUint8.deserialize(serialized, keySet.getServerKey());
      io.github.rdlopes.tfhe.api.types.FheUint8 decompressed = deserialized.decompress();
      byte result = decompressed.decrypt(keySet.getClientKey());
      assertThat(result).isEqualTo((byte) 100);
    }
  }
}
