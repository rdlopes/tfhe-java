package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheObject;
import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ProvenCompactCiphertextList extends NativePointer implements FheObject, AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(ProvenCompactCiphertextList.class);

  public ProvenCompactCiphertextList() {
    super(TfheHeader::proven_compact_ciphertext_list_destroy);
  }

  public static ProvenCompactCiphertextList deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize");
    ProvenCompactCiphertextList deserialized = new ProvenCompactCiphertextList();
    execute(() -> proven_compact_ciphertext_list_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  public static ProvenCompactCiphertextList deserializeConformant(DynamicBuffer dynamicBuffer, CompactPublicKey publicKey, CompactPkeCrs crs) {
    logger.trace("deserializeConformant");
    ProvenCompactCiphertextList deserialized = new ProvenCompactCiphertextList();
    execute(() -> proven_compact_ciphertext_list_safe_deserialize_conformant(
        dynamicBuffer.getAddress(),
        BUFFER_MAX_SIZE,
        publicKey.getValue(),
        crs.getValue(),
        deserialized.getAddress()
    ));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> proven_compact_ciphertext_list_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer;
  }

  public CompactCiphertextListExpander verifyAndExpand(CompactPkeCrs crs, CompactPublicKey publicKey, byte[] metadata) {
    logger.trace("verifyAndExpand");
    CompactCiphertextListExpander expander = new CompactCiphertextListExpander();
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment metadataSegment;
      long metadataLen;
      if (metadata == null || metadata.length == 0) {
        metadataSegment = MemorySegment.NULL;
        metadataLen = 0;
      } else {
        metadataSegment = arena.allocate(metadata.length);
        metadataSegment.copyFrom(MemorySegment.ofArray(metadata));
        metadataLen = metadata.length;
      }
      execute(() -> proven_compact_ciphertext_list_verify_and_expand(
          getValue(),
          crs.getValue(),
          publicKey.getValue(),
          metadataSegment,
          metadataLen,
          expander.getAddress()
      ));
    }
    return expander;
  }

  @Override
  public void close() {
    destroy();
  }
}
