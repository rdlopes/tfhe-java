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
public class FheUint12Array extends NativeArray implements FheArray<Short, FheUint12, CompressedFheUint12, FheUint12Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint12Array.class);
// @formatter:on

  public FheUint12Array(Collection<FheUint12> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint12Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint12> elements = values.stream()
                                           .map(value -> FheUint12.encrypt(value, clientKey))
                                           .toList();
    return new FheUint12Array(elements);
  }

  public static FheUint12Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint12> elements = values.stream()
                                           .map(value -> FheUint12.encrypt(value, publicKey))
                                           .toList();
    return new FheUint12Array(elements);
  }

  public static FheUint12Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint12> elements = values.stream()
                                           .map(FheUint12::encrypt)
                                           .toList();
    return new FheUint12Array(elements);
  }

  /// ```c
  /// int fhe_uint12_array_contains_sub_slice(struct FheUint12 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint12 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint12Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint12_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint12_array_eq(struct FheUint12 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint12 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint12Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint12_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint12_sum(const struct FheUint12 *const *lhs, size_t len, struct FheUint12 **out_result);
  ///```
  @Override
  public FheUint12 sum() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint12_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
