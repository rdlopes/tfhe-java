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
public class FheUint64Array extends NativeArray implements FheArray<Long, FheUint64, CompressedFheUint64, FheUint64Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint64Array.class);
// @formatter:on

  public FheUint64Array(Collection<FheUint64> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint64Array encrypt(Collection<Long> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint64> elements = values.stream()
                                           .map(value -> FheUint64.encrypt(value, clientKey))
                                           .toList();
    return new FheUint64Array(elements);
  }

  public static FheUint64Array encrypt(Collection<Long> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint64> elements = values.stream()
                                           .map(value -> FheUint64.encrypt(value, publicKey))
                                           .toList();
    return new FheUint64Array(elements);
  }

  public static FheUint64Array encrypt(Collection<Long> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint64> elements = values.stream()
                                           .map(FheUint64::encrypt)
                                           .toList();
    return new FheUint64Array(elements);
  }

  /// ```c
  /// int fhe_uint64_array_contains_sub_slice(struct FheUint64 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint64 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint64Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_array_eq(struct FheUint64 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint64 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint64Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_sum(const struct FheUint64 *const *lhs, size_t len, struct FheUint64 **out_result);
  ///```
  @Override
  public FheUint64 sum() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
