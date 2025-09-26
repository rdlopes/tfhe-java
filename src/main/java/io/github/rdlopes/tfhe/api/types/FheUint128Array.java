package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.IntStream;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint128Array extends NativeArray implements FheArray<U128, FheUint128, CompressedFheUint128, FheUint128Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint128Array.class);
// @formatter:on

  public FheUint128Array(List<U128> values, ClientKey clientKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint128 element = new FheUint128();
               execute(() -> fhe_uint128_try_encrypt_with_client_key_u128(values.get(index)
                                                                                .getAddress(), clientKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint128Array(List<U128> values, PublicKey publicKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint128 element = new FheUint128();
               execute(() -> fhe_uint128_try_encrypt_with_public_key_u128(values.get(index)
                                                                                .getAddress(), publicKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint128Array(List<U128> values) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint128 element = new FheUint128();
               execute(() -> fhe_uint128_try_encrypt_trivial_u128(values.get(index)
                                                                        .getAddress(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
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

// @formatter:off
}
// @formatter:on
