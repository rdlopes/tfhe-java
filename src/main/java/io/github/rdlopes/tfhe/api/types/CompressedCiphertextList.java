package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheObject;
import io.github.rdlopes.tfhe.api.FheType;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedCiphertextList extends NativePointer implements FheObject, AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompressedCiphertextList.class);

  public CompressedCiphertextList() {
    super(TfheHeader::compressed_ciphertext_list_destroy);
  }

  public static CompressedCiphertextList deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize");
    CompressedCiphertextList deserialized = new CompressedCiphertextList();
    execute(() -> compressed_ciphertext_list_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_ciphertext_list_safe_serialize(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));
    return dynamicBuffer;
  }

  public long size() {
    logger.trace("size");
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment sizePtr = arena.allocate(ValueLayout.JAVA_LONG);
      execute(() -> compressed_ciphertext_list_len(getValue(), sizePtr));
      return sizePtr.get(ValueLayout.JAVA_LONG, 0);
    }
  }

  public FheTypes getKindOf(long index) {
    logger.trace("getKindOf - index: {}", index);
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment kindPtr = arena.allocate(ValueLayout.JAVA_INT);
      execute(() -> compressed_ciphertext_list_get_kind_of(getValue(), index, kindPtr));
      int kindVal = kindPtr.get(ValueLayout.JAVA_INT, 0);
      return FheTypes.valueOrdered(kindVal);
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends FheType<?, T, ?>> T get(long index, Class<T> type) {
    logger.trace("get - index: {}, type: {}", index, type.getSimpleName());
    FheOps.ListGetOp getOp = FheRegistry.getCompressedGetOp(type);
    if (getOp == null) {
      throw new UnsupportedOperationException("Getting type " + type.getSimpleName() + " from CompressedCiphertextList is not supported.");
    }
    java.util.function.Supplier<T> factory = FheRegistry.getFactory(type);
    T result = factory.get();
    execute(() -> getOp.apply(getValue(), index, ((NativePointer) result).getAddress()));
    return result;
  }

  @Override
  public void close() {
    destroy();
  }
}
