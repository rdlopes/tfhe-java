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
public class FheUint2Array extends NativeArray implements FheArray<Byte, FheUint2, CompressedFheUint2, FheUint2Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint2Array.class);
// @formatter:on

  public FheUint2Array(List<Byte> values, ClientKey clientKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint2 element = new FheUint2();
               execute(() -> fhe_uint2_try_encrypt_with_client_key_u8(values.get(index), clientKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint2Array(List<Byte> values, PublicKey publicKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint2 element = new FheUint2();
               execute(() -> fhe_uint2_try_encrypt_with_public_key_u8(values.get(index), publicKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint2Array(List<Byte> values) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint2 element = new FheUint2();
               execute(() -> fhe_uint2_try_encrypt_trivial_u8(values.get(index), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
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

// @formatter:off
}
// @formatter:on
