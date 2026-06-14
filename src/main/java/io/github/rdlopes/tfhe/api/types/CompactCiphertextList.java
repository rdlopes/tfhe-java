package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheObject;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompactCiphertextList extends NativePointer implements FheObject, AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompactCiphertextList.class);

  public CompactCiphertextList() {
    super(TfheHeader::compact_ciphertext_list_destroy);
  }

  public static CompactCiphertextList deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);
    CompactCiphertextList deserialized = new CompactCiphertextList();
    execute(() -> compact_ciphertext_list_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  public static CompactCiphertextList safeDeserializeConformant(
      DynamicBuffer dynamicBuffer, io.github.rdlopes.tfhe.api.keys.CompactPublicKey publicKey, long expectedNumElements) {
    logger.trace("safeDeserializeConformant");
    CompactCiphertextList deserialized = new CompactCiphertextList();
    execute(() -> compact_ciphertext_list_safe_deserialize_conformant(
        dynamicBuffer.getAddress(),
        BUFFER_MAX_SIZE,
        publicKey.getValue(),
        expectedNumElements,
        deserialized.getAddress()
    ));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compact_ciphertext_list_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer;
  }

  public CompactCiphertextListExpander expand() {
    logger.trace("expand");
    CompactCiphertextListExpander expander = new CompactCiphertextListExpander();
    execute(() -> compact_ciphertext_list_expand(getValue(), expander.getAddress()));
    return expander;
  }

  @Override
  public void close() {
    destroy();
  }
}
