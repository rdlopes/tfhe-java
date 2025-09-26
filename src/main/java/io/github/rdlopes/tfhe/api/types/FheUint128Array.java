package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U128;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint128Array extends NativeArray implements FheArray<U128, FheUint128, CompressedFheUint128, FheUint128Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint128Array.class);
// @formatter:on

  public FheUint128Array(Collection<FheUint128> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint128Array encrypt(Collection<U128> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint128> elements = values.stream()
                                            .map(value -> FheUint128.encrypt(value, clientKey))
                                            .toList();
    return new FheUint128Array(elements);
  }

  public static FheUint128Array encrypt(Collection<U128> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint128> elements = values.stream()
                                            .map(value -> FheUint128.encrypt(value, publicKey))
                                            .toList();
    return new FheUint128Array(elements);
  }

  public static FheUint128Array encrypt(Collection<U128> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint128> elements = values.stream()
                                            .map(FheUint128::encrypt)
                                            .toList();
    return new FheUint128Array(elements);
  }

  /// ```c
  /// int fhe_uint128_array_contains_sub_slice(struct FheUint128 *const *lhs,
  ///                                          size_t lhs_len,
  ///                                          struct FheUint128 *const *rhs,
  ///                                          size_t rhs_len,
  ///                                          struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint128Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint128_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint128_array_eq(struct FheUint128 *const *lhs,
  ///                          size_t lhs_len,
  ///                          struct FheUint128 *const *rhs,
  ///                          size_t rhs_len,
  ///                          struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint128Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint128_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint128_sum(const struct FheUint128 *const *lhs,
  ///                     size_t len,
  ///                     struct FheUint128 **out_result);
  ///```
  @Override
  public FheUint128 sum() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint128_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
