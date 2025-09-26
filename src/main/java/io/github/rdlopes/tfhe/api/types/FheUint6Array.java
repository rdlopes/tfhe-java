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
public class FheUint6Array extends NativeArray implements FheArray<Byte, FheUint6, CompressedFheUint6, FheUint6Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint6Array.class);
// @formatter:on

  public FheUint6Array(Collection<FheUint6> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint6Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint6> elements = values.stream()
                                          .map(value -> FheUint6.encrypt(value, clientKey))
                                          .toList();
    return new FheUint6Array(elements);
  }

  public static FheUint6Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint6> elements = values.stream()
                                          .map(value -> FheUint6.encrypt(value, publicKey))
                                          .toList();
    return new FheUint6Array(elements);
  }

  public static FheUint6Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint6> elements = values.stream()
                                          .map(FheUint6::encrypt)
                                          .toList();
    return new FheUint6Array(elements);
  }

  /// ```c
  /// int fhe_uint6_array_contains_sub_slice(struct FheUint6 *const *lhs,
  ///                                        size_t lhs_len,
  ///                                        struct FheUint6 *const *rhs,
  ///                                        size_t rhs_len,
  ///                                        struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint6Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint6_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint6_array_eq(struct FheUint6 *const *lhs,
  ///                        size_t lhs_len,
  ///                        struct FheUint6 *const *rhs,
  ///                        size_t rhs_len,
  ///                        struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint6Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint6_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint6_sum(const struct FheUint6 *const *lhs, size_t len, struct FheUint6 **out_result);
  ///```
  @Override
  public FheUint6 sum() {
    FheUint6 result = new FheUint6();
    execute(() -> fhe_uint6_sum(getAddress(), getSize(), result.getAddress()));
    return result;

  }

  // @formatter:off
}
// @formatter:on
