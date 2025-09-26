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
public class FheUint14Array extends NativeArray implements FheArray<Short, FheUint14, CompressedFheUint14, FheUint14Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint14Array.class);
// @formatter:on

  public FheUint14Array(Collection<FheUint14> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint14Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint14> elements = values.stream()
                                           .map(value -> FheUint14.encrypt(value, clientKey))
                                           .toList();
    return new FheUint14Array(elements);
  }

  public static FheUint14Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint14> elements = values.stream()
                                           .map(value -> FheUint14.encrypt(value, publicKey))
                                           .toList();
    return new FheUint14Array(elements);
  }

  public static FheUint14Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint14> elements = values.stream()
                                           .map(FheUint14::encrypt)
                                           .toList();
    return new FheUint14Array(elements);
  }

  /// ```c
  /// int fhe_uint14_array_contains_sub_slice(struct FheUint14 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint14 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint14Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_array_eq(struct FheUint14 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint14 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint14Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_sum(const struct FheUint14 *const *lhs, size_t len, struct FheUint14 **out_result);
  ///```
  @Override
  public FheUint14 sum() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
