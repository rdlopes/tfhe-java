package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeAndReturn;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt64 extends NativePointer implements FheInteger<Long, FheInt64, CompressedFheInt64> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt64.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_int64_destroy(struct FheInt64 *ptr);
  ///```
  FheInt64() {
    logger.trace("init");
    super(TfheHeader::fhe_int64_destroy);
  }

  /// ```c
  ////**
  ///  * Serializes safely.
  ///  *
  ///  * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
  ///  * versions of TFHE-rs.
  ///  *
  ///  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
  ///  *    (to avoid out of memory attacks)
  ///  */
  /// int fhe_int64_safe_serialize(const struct FheInt64 *sself,
  ///                              struct DynamicBuffer *result,
  ///                              uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_int64_bitand(const struct FheInt64 *lhs,
  ///                      const struct FheInt64 *rhs,
  ///                      struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 bitAnd(FheInt64 other){
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_bitand(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 bitAndScalar(Long other){
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_bitand_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
///```
@Override
public void bitAndAssign(FheInt64 other){
    execute(() -> fhe_int64_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int64_scalar_bitand_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_bitor(const struct FheInt64 *lhs,
  ///                     const struct FheInt64 *rhs,
  ///                     struct FheInt64 **result);
///```
@Override
public FheInt64 bitOr(FheInt64 other){
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int64_scalar_bitor(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 bitOrScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_bitor_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
///```
@Override
public void bitOrAssign(FheInt64 other){
    execute(() -> fhe_int64_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int64_scalar_bitor_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_bitxor(const struct FheInt64 *lhs,
  ///                      const struct FheInt64 *rhs,
///                      struct FheInt64 **result);
///```
@Override
public FheInt64 bitXor(FheInt64 other){
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int64_scalar_bitxor(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 bitXorScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_bitxor_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheInt64 other) {
    execute(() -> fhe_int64_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_bitxor_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_not(const struct FheInt64 *input, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 bitNot() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_if_then_else(const struct FheBool *condition_ct,
  ///                            const struct FheInt64 *then_ct,
  ///                            const struct FheInt64 *else_ct,
  ///                            struct FheInt64 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheInt64 ifThenElse(FheBool condition, FheInt64 thenValue, FheInt64 elseValue) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_eq(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_eq(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_ne(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_ne(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_ne(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Deserializes safely, and checks that the resulting ciphertext
  ///  * is in compliance with the shape of ciphertext that the `server_key` expects.
  ///  *
  ///  * This function can only deserialize types which have been serialized
  ///  * by a `safe_serialize` function.
  ///  *
  ///  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
  ///  *    (to avoid out of memory attacks)
  ///  * - `server_key`: ServerKey used in the conformance check
  ///  * - `result`: pointer where resulting deserialized object needs to be stored.
  ///  *    * cannot be NULL
  ///  *    * (*result) will point the deserialized object on success, else NULL
  ///  */
  /// int fhe_int64_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                           uint64_t serialized_size_limit,
  ///                                           const struct ServerKey *server_key,
///                                           struct FheInt64 **result);
///```
public static FheInt64 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheInt64 deserialized = new FheInt64();
  execute(() -> fhe_int64_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_int64_try_encrypt_with_client_key_i64(int64_t value,
///                                               const struct ClientKey *client_key,
///                                               struct FheInt64 **result);
  ///```
  public static FheInt64 encrypt(Long clearValue, ClientKey clientKey) {
    FheInt64 encrypted = new FheInt64();
    execute(() -> fhe_int64_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
/// int fhe_int64_try_encrypt_with_public_key_i64(int64_t value,
  ///                                               const struct PublicKey *public_key,
///                                               struct FheInt64 **result);
  ///```
  public static FheInt64 encrypt(Long clearValue, PublicKey publicKey){
    FheInt64 encrypted = new FheInt64();
      execute(() -> fhe_int64_try_encrypt_with_public_key_i64(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int64_try_encrypt_trivial_i64(int64_t value, struct FheInt64 **result);
  ///```
  public static FheInt64 encrypt(Long clearValue){
    FheInt64 encrypted = new FheInt64();
      execute(() -> fhe_int64_try_encrypt_trivial_i64(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int64_clone(const struct FheInt64 *sself, struct FheInt64 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt64 clone() {
    FheInt64 cloned = new FheInt64();
    execute(() -> fhe_int64_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_int64_decrypt(const struct FheInt64 *encrypted_value,
  ///                       const struct ClientKey *client_key,
  ///                       int64_t *result);
  ///```
  @Override
  public Long decrypt(ClientKey clientKey) {
    return executeAndReturn(Long.class, address -> fhe_int64_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_int64_compress(const struct FheInt64 *sself, struct CompressedFheInt64 **result);
///```
@Override
public CompressedFheInt64 compress() {
  CompressedFheInt64 compressed = new CompressedFheInt64();
  execute(() -> fhe_int64_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_int64_add(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 add(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_overflowing_add(const struct FheInt64 *lhs,
  ///                               const struct FheInt64 *rhs,
  ///                               struct FheInt64 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheInt64, CompressedFheInt64> addWithOverflow(FheInt64 other) {
    FheInt64 result = new FheInt64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int64_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int64_scalar_add(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 addScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_add_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void addAssign(FheInt64 other) {
    execute(() -> fhe_int64_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_add_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void addScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_sub(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 subtract(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_overflowing_sub(const struct FheInt64 *lhs,
  ///                               const struct FheInt64 *rhs,
  ///                               struct FheInt64 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheInt64, CompressedFheInt64> subtractWithOverflow(FheInt64 other) {
    FheInt64 result = new FheInt64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int64_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int64_scalar_sub(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 subtractScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_sub_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void subtractAssign(FheInt64 other) {
    execute(() -> fhe_int64_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_sub_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_mul(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 multiply(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_overflowing_mul(const struct FheInt64 *lhs,
  ///                               const struct FheInt64 *rhs,
  ///                               struct FheInt64 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheInt64, CompressedFheInt64> multiplyWithOverflow(FheInt64 other) {
    FheInt64 result = new FheInt64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int64_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int64_scalar_mul(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 multiplyScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_mul_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheInt64 other) {
    execute(() -> fhe_int64_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_mul_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_div(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 divide(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_div(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 divideScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_div_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void divideAssign(FheInt64 other) {
    execute(() -> fhe_int64_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_div_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_rem(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 remainder(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_rem(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 remainderScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_rem_assign(struct FheInt64 *lhs, const struct FheInt64 *rhs);
  ///```
  @Override
  public void remainderAssign(FheInt64 other) {
    execute(() -> fhe_int64_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_rem_assign(struct FheInt64 *lhs, int64_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_div_rem(const struct FheInt64 *lhs,
  ///                       const struct FheInt64 *rhs,
  ///                       struct FheInt64 **q_result,
  ///                       struct FheInt64 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Long, FheInt64, CompressedFheInt64> divideWithRemainder(FheInt64 other) {
    FheInt64 divider = new FheInt64();
    FheInt64 remainder = new FheInt64();
    execute(() -> fhe_int64_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int64_scalar_div_rem(const struct FheInt64 *lhs,
  ///                              int64_t rhs,
  ///                              struct FheInt64 **q_result,
  ///                              struct FheInt64 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Long, FheInt64, CompressedFheInt64> divideWithRemainderScalar(Long other) {
    FheInt64 divider = new FheInt64();
    FheInt64 remainder = new FheInt64();
    execute(() -> fhe_int64_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int64_neg(const struct FheInt64 *input, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 negate() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_int64_ilog2(const struct FheInt64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt64 ilog2() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_int64_checked_ilog2(const struct FheInt64 *input,
  ///                             struct FheUint32 **result_1,
  ///                             struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Long, FheInt64, CompressedFheInt64> ilog2WithCheck() {
    FheInt64 result = new FheInt64();
    FheBool check = new FheBool();
    execute(() -> fhe_int64_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_int64_lt(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_lt(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_le(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_le(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_gt(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_gt(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_ge(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheInt64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_ge(const struct FheInt64 *lhs, int64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int64_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_min(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 min(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_min(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 minScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_max(const struct FheInt64 *lhs, const struct FheInt64 *rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 max(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_max(const struct FheInt64 *lhs, int64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 maxScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_shl(const struct FheInt64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 shiftLeft(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_shl(const struct FheInt64 *lhs, uint64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 shiftLeftScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_shl_assign(struct FheInt64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheInt64 other) {
    execute(() -> fhe_int64_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_shl_assign(struct FheInt64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_shr(const struct FheInt64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 shiftRight(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_shr(const struct FheInt64 *lhs, uint64_t rhs, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 shiftRightScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_shr_assign(struct FheInt64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheInt64 other) {
    execute(() -> fhe_int64_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_shr_assign(struct FheInt64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_rotate_left(const struct FheInt64 *lhs,
  ///                           const struct FheUint64 *rhs,
  ///                           struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 rotateLeft(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_rotate_left(const struct FheInt64 *lhs,
  ///                                  uint64_t rhs,
  ///                                  struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 rotateLeftScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_rotate_left_assign(struct FheInt64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheInt64 other) {
    execute(() -> fhe_int64_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_rotate_left_assign(struct FheInt64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int64_rotate_right(const struct FheInt64 *lhs,
  ///                            const struct FheUint64 *rhs,
  ///                            struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 rotateRight(FheInt64 other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_scalar_rotate_right(const struct FheInt64 *lhs,
  ///                                   uint64_t rhs,
  ///                                   struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 rotateRightScalar(Long other) {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_rotate_right_assign(struct FheInt64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheInt64 other) {
    execute(() -> fhe_int64_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int64_scalar_rotate_right_assign(struct FheInt64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Long other) {
    execute(() -> fhe_int64_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_int64_leading_ones(const struct FheInt64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt64 leadingOnes() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_int64_leading_zeros(const struct FheInt64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt64 leadingZeros() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_int64_trailing_ones(const struct FheInt64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt64 trailingOnes() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_int64_trailing_zeros(const struct FheInt64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt64 trailingZeros() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_int64(struct FheInt64 **out_result,
  ///                                                uint64_t seed_low_bytes,
  ///                                                uint64_t seed_high_bytes);
  ///```
  @Override
  public FheInt64 random(long seedLow, long seedHigh) {
    FheInt64 result = new FheInt64();
    execute(() -> generate_oblivious_pseudo_random_fhe_int64(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_int64(struct FheInt64 **out_result,
  ///                                                        uint64_t seed_low_bytes,
  ///                                                        uint64_t seed_high_bytes,
  ///                                                        uint64_t random_bits_count);
  ///```
  @Override
  public FheInt64 random(long seedLow, long seedHigh, long bitsCount){
      FheInt64 result = new FheInt64();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_int64(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the absolute value.
  ///  *
  ///  * (if x < 0 { -x } else { x })
  ///  */
  /// int fhe_int64_abs(const struct FheInt64 *input, struct FheInt64 **result);
  ///```
  @Override
  public FheInt64 abs() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_abs(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int10(const struct FheInt64 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int64_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int1024(const struct FheInt64 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int64_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int12(const struct FheInt64 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int64_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int128(const struct FheInt64 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int64_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int14(const struct FheInt64 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int64_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int16(const struct FheInt64 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int64_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int160(const struct FheInt64 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int64_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int2(const struct FheInt64 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int64_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int2048(const struct FheInt64 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int64_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int256(const struct FheInt64 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int64_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int32(const struct FheInt64 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int64_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int4(const struct FheInt64 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int64_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int512(const struct FheInt64 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int64_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int6(const struct FheInt64 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int64_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int64(const struct FheInt64 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_int8(const struct FheInt64 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int64_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint10(const struct FheInt64 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int64_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint1024(const struct FheInt64 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int64_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint12(const struct FheInt64 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int64_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint128(const struct FheInt64 *sself, struct FheUint128 **result);
///```
public FheUint128 castIntoFheUint128() {
  FheUint128 result = new FheUint128();
  execute(() -> fhe_int64_cast_into_fhe_uint128(getValue(), result.getAddress()));
  return result;
}

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint14(const struct FheInt64 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int64_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint16(const struct FheInt64 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int64_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint160(const struct FheInt64 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int64_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint2(const struct FheInt64 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int64_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint2048(const struct FheInt64 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int64_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint256(const struct FheInt64 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int64_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint32(const struct FheInt64 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int64_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint4(const struct FheInt64 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int64_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint512(const struct FheInt64 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int64_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int64_cast_into_fhe_uint6(const struct FheInt64 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
  execute(() -> fhe_int64_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int64_cast_into_fhe_uint64(const struct FheInt64 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_int64_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int64_cast_into_fhe_uint8(const struct FheInt64 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_int64_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
