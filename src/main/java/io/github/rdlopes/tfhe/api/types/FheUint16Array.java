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
public class FheUint16Array extends NativeArray implements FheArray<Short, FheUint16, CompressedFheUint16, FheUint16Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint16Array.class);
// @formatter:on

  public FheUint16Array(Collection<FheUint16> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint16Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint16> elements = values.stream()
                                           .map(value -> FheUint16.encrypt(value, clientKey))
                                           .toList();
    return new FheUint16Array(elements);
  }

  public static FheUint16Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint16> elements = values.stream()
                                           .map(value -> FheUint16.encrypt(value, publicKey))
                                           .toList();
    return new FheUint16Array(elements);
  }

  public static FheUint16Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint16> elements = values.stream()
                                           .map(FheUint16::encrypt)
                                           .toList();
    return new FheUint16Array(elements);
  }

  /// ```c
  /// int fhe_uint16_array_contains_sub_slice(struct FheUint16 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint16 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint16Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint16_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint16_array_eq(struct FheUint16 *const *lhs,
  ///                         size_t lhs_len,
  ///                         struct FheUint16 *const *rhs,
  ///                         size_t rhs_len,
  ///                         struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint16Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint16_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint16_sum(const struct FheUint16 *const *lhs, size_t len, struct FheUint16 **out_result);
  ///```
  @Override
public FheUint16 sum(){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_sum(getAddress(), getSize(), result.getAddress()));
    return result;

}
  

  // @formatter:off
}
// @formatter:on
