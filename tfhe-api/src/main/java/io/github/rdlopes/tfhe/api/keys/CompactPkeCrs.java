package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.FheObject;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public class CompactPkeCrs extends NativePointer implements FheObject, AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompactPkeCrs.class);

  public CompactPkeCrs() {
    super(TfheHeader::compact_pke_crs_destroy);
  }

  public CompactPkeCrs(Config config, long maxNumBits) {
    this();
    logger.trace("init from config - maxNumBits: {}", maxNumBits);
    execute(() -> compact_pke_crs_from_config(config.getValue(), maxNumBits, getAddress()));
  }

  public static CompactPkeCrs deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize");
    CompactPkeCrs deserialized = new CompactPkeCrs();
    execute(() -> compact_pke_crs_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  public static CompactPkeCrs deserializeUnsafe(DynamicBuffer dynamicBuffer) {
    logger.trace("deserializeUnsafe");
    CompactPkeCrs deserialized = new CompactPkeCrs();
    execute(() -> compact_pke_crs_deserialize(dynamicBuffer.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  public static CompactPkeCrs deserializeFromParams(DynamicBuffer dynamicBuffer) {
    logger.trace("deserializeFromParams");
    CompactPkeCrs deserialized = new CompactPkeCrs();
    execute(() -> compact_pke_crs_deserialize_from_params(dynamicBuffer.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  public static CompactPkeCrs safeDeserializeFromParams(DynamicBuffer dynamicBuffer) {
    logger.trace("safeDeserializeFromParams");
    CompactPkeCrs deserialized = new CompactPkeCrs();
    execute(() -> compact_pke_crs_safe_deserialize_from_params(
        dynamicBuffer.getAddress(),
        dynamicBuffer.getLength(),
        deserialized.getAddress()
    ));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    return serialize(true);
  }

  public DynamicBuffer serialize(boolean compress) {
    logger.trace("serialize - compress: {}", compress);
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compact_pke_crs_safe_serialize(getValue(), compress, MAX_SERIALIZATION_SIZE, dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public DynamicBuffer serializeUnsafe() {
    return serializeUnsafe(true);
  }

  public DynamicBuffer serializeUnsafe(boolean compress) {
    logger.trace("serializeUnsafe - compress: {}", compress);
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compact_pke_crs_serialize(getValue(), compress, dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  @Override
  public void close() {
    destroy();
  }
}
