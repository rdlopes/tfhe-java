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
public class FheUint256Array extends NativeArray implements FheArray<U256, FheUint256, CompressedFheUint256, FheUint256Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint256Array.class);
// @formatter:on

  public FheUint256Array(List<U256> values, ClientKey clientKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint256 element = new FheUint256();
               execute(() -> fhe_uint256_try_encrypt_with_client_key_u256(values.get(index)
                                                                                .getAddress(), clientKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint256Array(List<U256> values, PublicKey publicKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint256 element = new FheUint256();
               execute(() -> fhe_uint256_try_encrypt_with_public_key_u256(values.get(index)
                                                                                .getAddress(), publicKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint256Array(List<U256> values) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint256 element = new FheUint256();
               execute(() -> fhe_uint256_try_encrypt_trivial_u256(values.get(index)
                                                                        .getAddress(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
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

// @formatter:off
}
// @formatter:on
