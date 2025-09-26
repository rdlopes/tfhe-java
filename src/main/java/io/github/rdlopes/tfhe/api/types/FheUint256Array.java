package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U256;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint256Array extends NativeArray implements FheArray<U256, FheUint256, CompressedFheUint256, FheUint256Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint256Array.class);
// @formatter:on

  public FheUint256Array(Collection<FheUint256> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint256Array encrypt(Collection<U256> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint256> elements = values.stream()
                                            .map(value -> FheUint256.encrypt(value, clientKey))
                                            .toList();
    return new FheUint256Array(elements);
  }

  public static FheUint256Array encrypt(Collection<U256> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint256> elements = values.stream()
                                            .map(value -> FheUint256.encrypt(value, publicKey))
                                            .toList();
    return new FheUint256Array(elements);
  }

  public static FheUint256Array encrypt(Collection<U256> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint256> elements = values.stream()
                                            .map(FheUint256::encrypt)
                                            .toList();
    return new FheUint256Array(elements);
  }

  /// ```c
  /// int fhe_uint256_array_contains_sub_slice(struct FheUint256 *const *lhs,
  ///                                          size_t lhs_len,
  ///                                          struct FheUint256 *const *rhs,
  ///                                          size_t rhs_len,
  ///                                          struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint256Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint256_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint256_array_eq(struct FheUint256 *const *lhs,
  ///                          size_t lhs_len,
  ///                          struct FheUint256 *const *rhs,
  ///                          size_t rhs_len,
  ///                          struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint256Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint256_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint256_sum(const struct FheUint256 *const *lhs,
  ///                     size_t len,
  ///                     struct FheUint256 **out_result);
  ///```
  @Override
  public FheUint256 sum() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint256_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
