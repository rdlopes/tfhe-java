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
public class FheUint2Array extends NativeArray implements FheArray<Byte, FheUint2, CompressedFheUint2, FheUint2Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint2Array.class);
// @formatter:on

  public FheUint2Array(Collection<FheUint2> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint2Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint2> elements = values.stream()
                                          .map(value -> FheUint2.encrypt(value, clientKey))
                                          .toList();
    return new FheUint2Array(elements);
  }

  public static FheUint2Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint2> elements = values.stream()
                                          .map(value -> FheUint2.encrypt(value, publicKey))
                                          .toList();
    return new FheUint2Array(elements);
  }

  public static FheUint2Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint2> elements = values.stream()
                                          .map(FheUint2::encrypt)
                                          .toList();
    return new FheUint2Array(elements);
  }

  /// ```c
  /// int fhe_uint2_array_contains_sub_slice(struct FheUint2 *const *lhs,
  ///                                        size_t lhs_len,
  ///                                        struct FheUint2 *const *rhs,
  ///                                        size_t rhs_len,
  ///                                        struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint2Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2_array_eq(struct FheUint2 *const *lhs,
  ///                        size_t lhs_len,
  ///                        struct FheUint2 *const *rhs,
  ///                        size_t rhs_len,
  ///                        struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint2Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2_sum(const struct FheUint2 *const *lhs, size_t len, struct FheUint2 **out_result);
  ///```
  @Override
  public FheUint2 sum() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint2_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
