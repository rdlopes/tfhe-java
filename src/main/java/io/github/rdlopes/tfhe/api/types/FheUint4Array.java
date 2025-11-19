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
public class FheUint4Array extends NativeArray implements FheArray<Byte, FheUint4, CompressedFheUint4, FheUint4Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint4Array.class);
// @formatter:on

  public FheUint4Array(Collection<FheUint4> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheUint4Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheUint4> elements = values.stream()
                                          .map(value -> FheUint4.encrypt(value, clientKey))
                                          .toList();
    return new FheUint4Array(elements);
  }

  public static FheUint4Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheUint4> elements = values.stream()
                                          .map(value -> FheUint4.encrypt(value, publicKey))
                                          .toList();
    return new FheUint4Array(elements);
  }

  public static FheUint4Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheUint4> elements = values.stream()
                                          .map(FheUint4::encrypt)
                                          .toList();
    return new FheUint4Array(elements);
  }

  /// ```c
  /// int fhe_uint4_array_contains_sub_slice(struct FheUint4 *const *lhs,
  ///                                        size_t lhs_len,
  ///                                        struct FheUint4 *const *rhs,
  ///                                        size_t rhs_len,
  ///                                        struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint4Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint4_array_contains_sub_slice(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint4_array_eq(struct FheUint4 *const *lhs,
  ///                        size_t lhs_len,
  ///                        struct FheUint4 *const *rhs,
  ///                        size_t rhs_len,
  ///                        struct FheBool **result);
  ///```
  @Override
  public FheBool equalsArray(FheUint4Array other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint4_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint4_sum(const struct FheUint4 *const *lhs, size_t len, struct FheUint4 **out_result);
  ///```
  @Override
public FheUint4 sum(){
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint4_sum(getAddress(), getSize(), result.getAddress()));
    return result;

}
  

  // @formatter:off
}
// @formatter:on
