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
public class FheUint32Array extends NativeArray implements FheArray<Integer, FheUint32, CompressedFheUint32, FheUint32Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint32Array.class);
// @formatter:on

  public FheUint32Array(List<Integer> values, ClientKey clientKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint32 element = new FheUint32();
               execute(() -> fhe_uint32_try_encrypt_with_client_key_u32(values.get(index), clientKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint32Array(List<Integer> values, PublicKey publicKey) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint32 element = new FheUint32();
               execute(() -> fhe_uint32_try_encrypt_with_public_key_u32(values.get(index), publicKey.getValue(), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  public FheUint32Array(List<Integer> values) {
    logger.trace("init");
    super(values.size());
    IntStream.range(0, values.size())
             .forEach(index -> {
               FheUint32 element = new FheUint32();
               execute(() -> fhe_uint32_try_encrypt_trivial_u32(values.get(index), element.getAddress()));
               getAddress().set(C_POINTER, index * C_POINTER.byteSize(), element.getValue());
             });
  }

  /// ```c
  /// int fhe_uint32_array_contains_sub_slice(struct FheUint32 *const *lhs,
  ///                                         size_t lhs_len,
  ///                                         struct FheUint32 *const *rhs,
  ///                                         size_t rhs_len,
  ///                                         struct FheBool **result);
  ///```
  @Override
  public FheBool containsArray(FheUint32Array other) {
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
  public FheBool equalsArray(FheUint32Array other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_array_eq(getAddress(), getSize(), other.getAddress(), other.getSize(), result.getAddress()));
    return result;

  }

// @formatter:off
}
// @formatter:on
