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
public class FheUint32Array extends NativeArray implements FheArray<Integer, FheUint32, CompressedFheUint32, FheUint32Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint32Array.class);
// @formatter:on

  public FheUint32Array(Collection<FheUint32> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint32Array encrypt(Collection<Integer> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint32> elements = values.stream()
                                           .map(value -> FheUint32.encrypt(value, clientKey))
                                           .toList();
    return new FheUint32Array(elements);
  }

  public static FheUint32Array encrypt(Collection<Integer> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint32> elements = values.stream()
                                           .map(value -> FheUint32.encrypt(value, publicKey))
                                           .toList();
    return new FheUint32Array(elements);
  }

  public static FheUint32Array encrypt(Collection<Integer> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint32> elements = values.stream()
                                           .map(FheUint32::encrypt)
                                           .toList();
    return new FheUint32Array(elements);
  }

  /// ```c
  /// int fhe_uint32_array_contains_sub_slice(struct FheUint32 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint32 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint32Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_array_eq(struct FheUint32 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint32 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint32Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_sum(const struct FheUint32 *const *lhs, size_t len, struct FheUint32 **out_result);
  ///```
  @Override
public FheUint32 sum(){
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_sum(getAddress(), getSize(), result.getAddress()));
    return result;

}
  

  // @formatter:off
}
// @formatter:on
