package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint8Array extends NativeArray implements FheArray<Byte, FheUint8, CompressedFheUint8, FheUint8Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint8Array.class);
// @formatter:on

  public FheUint8Array(Collection<FheUint8> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint8Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint8> elements = values.stream()
                                          .map(value -> FheUint8.encrypt(value, clientKey))
                                          .toList();
    return new FheUint8Array(elements);
  }

  public static FheUint8Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint8> elements = values.stream()
                                          .map(value -> FheUint8.encrypt(value, publicKey))
                                          .toList();
    return new FheUint8Array(elements);
  }

  public static FheUint8Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint8> elements = values.stream()
                                          .map(FheUint8::encrypt)
                                          .toList();
    return new FheUint8Array(elements);
  }

  /// ```c
  /// int fhe_uint8_array_contains_sub_slice(struct FheUint8 *const *lhs,
  ///                                        size_t lhs_len,
  ///                                        struct FheUint8 *const *rhs,
  ///                                        size_t rhs_len,
  ///                                        struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint8Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint8_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint8_array_eq(struct FheUint8 *const *lhs,
  ///                        size_t lhs_len,
  ///                        struct FheUint8 *const *rhs,
  ///                        size_t rhs_len,
  ///                        struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint8Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint8_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint8_sum(const struct FheUint8 *const *lhs, size_t len, struct FheUint8 **out_result);
  ///```
  @Override
  public FheUint8 sum() {
    FheUint8 result = new FheUint8();
    execute(() -> fhe_uint8_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
