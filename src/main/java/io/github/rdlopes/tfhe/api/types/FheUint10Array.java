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
public class FheUint10Array extends NativeArray implements FheArray<Short, FheUint10, CompressedFheUint10, FheUint10Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint10Array.class);
// @formatter:on

  public FheUint10Array(List<Short> values, ClientKey clientKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint10 element = new FheUint10();
               execute(() -> fhe_uint10_try_encrypt_with_client_key_u16(values.get(index), clientKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint10Array(List<Short> values, PublicKey publicKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint10 element = new FheUint10();
               execute(() -> fhe_uint10_try_encrypt_with_public_key_u16(values.get(index), publicKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint10Array(List<Short> values) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint10 element = new FheUint10();
               execute(() -> fhe_uint10_try_encrypt_trivial_u16(values.get(index), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  /// ```c
  /// int fhe_uint10_array_contains_sub_slice(struct FheUint10 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint10 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint10Array other) {
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
  public FheBool equalsArray(FheUint10Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

// @formatter:off
}
// @formatter:on
