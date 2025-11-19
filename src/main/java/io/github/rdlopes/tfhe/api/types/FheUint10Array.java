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
public class FheUint10Array extends NativeArray implements FheArray<Short, FheUint10, CompressedFheUint10, FheUint10Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint10Array.class);
// @formatter:on

  public FheUint10Array(Collection<FheUint10> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint10Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint10> elements = values.stream()
                                           .map(value -> FheUint10.encrypt(value, clientKey))
                                           .toList();
    return new FheUint10Array(elements);
  }

  public static FheUint10Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint10> elements = values.stream()
                                           .map(value -> FheUint10.encrypt(value, publicKey))
                                           .toList();
    return new FheUint10Array(elements);
  }

  public static FheUint10Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint10> elements = values.stream()
                                           .map(FheUint10::encrypt)
                                           .toList();
    return new FheUint10Array(elements);
  }

  /// ```c
  /// int fhe_uint10_array_contains_sub_slice(struct FheUint10 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint10 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint10Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_array_eq(struct FheUint10 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint10 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint10Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_sum(const struct FheUint10 *const *lhs, size_t len, struct FheUint10 **out_result);
  ///```
  @Override
public FheUint10 sum(){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_sum(getAddress(), getSize(), result.getAddress()));
    return result;

}
  

  // @formatter:off
}
// @formatter:on
