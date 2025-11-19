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
public class FheInt12 extends NativePointer implements FheInteger<Short, FheInt12, CompressedFheInt12> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt12.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_int12_destroy(struct FheInt12 *ptr);
  ///```
  FheInt12() {
    logger.trace("init");
    super(TfheHeader::fhe_int12_destroy);
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
  /// int fhe_int12_safe_serialize(const struct FheInt12 *sself,
  ///                              struct DynamicBuffer *result,
  ///                              uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_int12_bitand(const struct FheInt12 *lhs,
  ///                      const struct FheInt12 *rhs,
  ///                      struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 bitAnd(FheInt12 other){
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_bitand(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 bitAndScalar(Short other){
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_bitand_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
///```
@Override
public void bitAndAssign(FheInt12 other){
    execute(() -> fhe_int12_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int12_scalar_bitand_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_bitor(const struct FheInt12 *lhs,
  ///                     const struct FheInt12 *rhs,
  ///                     struct FheInt12 **result);
///```
@Override
public FheInt12 bitOr(FheInt12 other){
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int12_scalar_bitor(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 bitOrScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_bitor_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
///```
@Override
public void bitOrAssign(FheInt12 other){
    execute(() -> fhe_int12_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int12_scalar_bitor_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_bitxor(const struct FheInt12 *lhs,
  ///                      const struct FheInt12 *rhs,
///                      struct FheInt12 **result);
///```
@Override
public FheInt12 bitXor(FheInt12 other){
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int12_scalar_bitxor(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 bitXorScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_bitxor_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheInt12 other) {
    execute(() -> fhe_int12_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_bitxor_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_not(const struct FheInt12 *input, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 bitNot() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_if_then_else(const struct FheBool *condition_ct,
  ///                            const struct FheInt12 *then_ct,
  ///                            const struct FheInt12 *else_ct,
  ///                            struct FheInt12 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheInt12 ifThenElse(FheBool condition, FheInt12 thenValue, FheInt12 elseValue) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_eq(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_eq(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_ne(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_ne(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_int12_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                           uint64_t serialized_size_limit,
  ///                                           const struct ServerKey *server_key,
///                                           struct FheInt12 **result);
///```
public static FheInt12 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheInt12 deserialized = new FheInt12();
  execute(() -> fhe_int12_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_int12_try_encrypt_with_client_key_i16(int16_t value,
///                                               const struct ClientKey *client_key,
///                                               struct FheInt12 **result);
  ///```
  public static FheInt12 encrypt(Short clearValue, ClientKey clientKey) {
    FheInt12 encrypted = new FheInt12();
    execute(() -> fhe_int12_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
/// int fhe_int12_try_encrypt_with_public_key_i16(int16_t value,
  ///                                               const struct PublicKey *public_key,
///                                               struct FheInt12 **result);
  ///```
  public static FheInt12 encrypt(Short clearValue, PublicKey publicKey){
    FheInt12 encrypted = new FheInt12();
      execute(() -> fhe_int12_try_encrypt_with_public_key_i16(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int12_try_encrypt_trivial_i16(int16_t value, struct FheInt12 **result);
  ///```
  public static FheInt12 encrypt(Short clearValue){
    FheInt12 encrypted = new FheInt12();
      execute(() -> fhe_int12_try_encrypt_trivial_i16(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int12_clone(const struct FheInt12 *sself, struct FheInt12 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt12 clone() {
    FheInt12 cloned = new FheInt12();
    execute(() -> fhe_int12_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_int12_decrypt(const struct FheInt12 *encrypted_value,
  ///                       const struct ClientKey *client_key,
  ///                       int16_t *result);
  ///```
  @Override
  public Short decrypt(ClientKey clientKey) {
    return executeAndReturn(Short.class, address -> fhe_int12_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_int12_compress(const struct FheInt12 *sself, struct CompressedFheInt12 **result);
///```
@Override
public CompressedFheInt12 compress() {
  CompressedFheInt12 compressed = new CompressedFheInt12();
  execute(() -> fhe_int12_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_int12_add(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 add(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_overflowing_add(const struct FheInt12 *lhs,
  ///                               const struct FheInt12 *rhs,
  ///                               struct FheInt12 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheInt12, CompressedFheInt12> addWithOverflow(FheInt12 other) {
    FheInt12 result = new FheInt12();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int12_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int12_scalar_add(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 addScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_add_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void addAssign(FheInt12 other) {
    execute(() -> fhe_int12_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_add_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void addScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_sub(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 subtract(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_overflowing_sub(const struct FheInt12 *lhs,
  ///                               const struct FheInt12 *rhs,
  ///                               struct FheInt12 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheInt12, CompressedFheInt12> subtractWithOverflow(FheInt12 other) {
    FheInt12 result = new FheInt12();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int12_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int12_scalar_sub(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 subtractScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_sub_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void subtractAssign(FheInt12 other) {
    execute(() -> fhe_int12_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_sub_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_mul(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 multiply(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_overflowing_mul(const struct FheInt12 *lhs,
  ///                               const struct FheInt12 *rhs,
  ///                               struct FheInt12 **out_result,
  ///                               struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheInt12, CompressedFheInt12> multiplyWithOverflow(FheInt12 other) {
    FheInt12 result = new FheInt12();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int12_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int12_scalar_mul(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 multiplyScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_mul_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheInt12 other) {
    execute(() -> fhe_int12_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_mul_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_div(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 divide(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_div(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 divideScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_div_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void divideAssign(FheInt12 other) {
    execute(() -> fhe_int12_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_div_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_rem(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 remainder(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_rem(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 remainderScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_rem_assign(struct FheInt12 *lhs, const struct FheInt12 *rhs);
  ///```
  @Override
  public void remainderAssign(FheInt12 other) {
    execute(() -> fhe_int12_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_rem_assign(struct FheInt12 *lhs, int16_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_div_rem(const struct FheInt12 *lhs,
  ///                       const struct FheInt12 *rhs,
  ///                       struct FheInt12 **q_result,
  ///                       struct FheInt12 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheInt12, CompressedFheInt12> divideWithRemainder(FheInt12 other) {
    FheInt12 divider = new FheInt12();
    FheInt12 remainder = new FheInt12();
    execute(() -> fhe_int12_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int12_scalar_div_rem(const struct FheInt12 *lhs,
  ///                              int16_t rhs,
  ///                              struct FheInt12 **q_result,
  ///                              struct FheInt12 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheInt12, CompressedFheInt12> divideWithRemainderScalar(Short other) {
    FheInt12 divider = new FheInt12();
    FheInt12 remainder = new FheInt12();
    execute(() -> fhe_int12_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int12_neg(const struct FheInt12 *input, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 negate() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_int12_ilog2(const struct FheInt12 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt12 ilog2() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_int12_checked_ilog2(const struct FheInt12 *input,
  ///                             struct FheUint32 **result_1,
  ///                             struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Short, FheInt12, CompressedFheInt12> ilog2WithCheck() {
    FheInt12 result = new FheInt12();
    FheBool check = new FheBool();
    execute(() -> fhe_int12_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_int12_lt(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_lt(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_le(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_le(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_gt(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_gt(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_ge(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheInt12 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_ge(const struct FheInt12 *lhs, int16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int12_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_min(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 min(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_min(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 minScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_max(const struct FheInt12 *lhs, const struct FheInt12 *rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 max(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_max(const struct FheInt12 *lhs, int16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 maxScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_shl(const struct FheInt12 *lhs,
  ///                   const struct FheUint12 *rhs,
  ///                   struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 shiftLeft(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_shl(const struct FheInt12 *lhs, uint16_t rhs, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 shiftLeftScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_shl_assign(struct FheInt12 *lhs, const struct FheUint12 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheInt12 other) {
    execute(() -> fhe_int12_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_shl_assign(struct FheInt12 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_shr(const struct FheInt12 *lhs,
  ///                   const struct FheUint12 *rhs,
  ///                   struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 shiftRight(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_shr(const struct FheInt12 *lhs, uint16_t rhs, struct FheInt12 **result);
  ///```
@Override
public FheInt12 shiftRightScalar(Short other) {
  FheInt12 result = new FheInt12();
  execute(() -> fhe_int12_scalar_shr(getValue(), other, result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int12_shr_assign(struct FheInt12 *lhs, const struct FheUint12 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheInt12 other) {
    execute(() -> fhe_int12_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_shr_assign(struct FheInt12 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_rotate_left(const struct FheInt12 *lhs,
  ///                           const struct FheUint12 *rhs,
  ///                           struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 rotateLeft(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_rotate_left(const struct FheInt12 *lhs,
  ///                                  uint16_t rhs,
  ///                                  struct FheInt12 **result);
  ///```
@Override
public FheInt12 rotateLeftScalar(Short other) {
  FheInt12 result = new FheInt12();
  execute(() -> fhe_int12_scalar_rotate_left(getValue(), other, result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int12_rotate_left_assign(struct FheInt12 *lhs, const struct FheUint12 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheInt12 other) {
    execute(() -> fhe_int12_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_rotate_left_assign(struct FheInt12 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int12_rotate_right(const struct FheInt12 *lhs,
  ///                            const struct FheUint12 *rhs,
  ///                            struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 rotateRight(FheInt12 other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_scalar_rotate_right(const struct FheInt12 *lhs,
  ///                                   uint16_t rhs,
  ///                                   struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 rotateRightScalar(Short other) {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_rotate_right_assign(struct FheInt12 *lhs, const struct FheUint12 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheInt12 other) {
    execute(() -> fhe_int12_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int12_scalar_rotate_right_assign(struct FheInt12 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Short other) {
    execute(() -> fhe_int12_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_int12_leading_ones(const struct FheInt12 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt12 leadingOnes() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_int12_leading_zeros(const struct FheInt12 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt12 leadingZeros() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_int12_trailing_ones(const struct FheInt12 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt12 trailingOnes() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_int12_trailing_zeros(const struct FheInt12 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt12 trailingZeros() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_int12(struct FheInt12 **out_result,
  ///                                                uint64_t seed_low_bytes,
  ///                                                uint64_t seed_high_bytes);
  ///```
  @Override
  public FheInt12 random(long seedLow, long seedHigh) {
    FheInt12 result = new FheInt12();
    execute(() -> generate_oblivious_pseudo_random_fhe_int12(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_int12(struct FheInt12 **out_result,
  ///                                                        uint64_t seed_low_bytes,
  ///                                                        uint64_t seed_high_bytes,
  ///                                                        uint64_t random_bits_count);
  ///```
  @Override
  public FheInt12 random(long seedLow, long seedHigh, long bitsCount){
      FheInt12 result = new FheInt12();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_int12(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the absolute value.
  ///  *
  ///  * (if x < 0 { -x } else { x })
  ///  */
  /// int fhe_int12_abs(const struct FheInt12 *input, struct FheInt12 **result);
  ///```
  @Override
  public FheInt12 abs() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_abs(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int10(const struct FheInt12 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int12_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int1024(const struct FheInt12 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int12_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int12(const struct FheInt12 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int128(const struct FheInt12 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int12_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int14(const struct FheInt12 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int12_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int16(const struct FheInt12 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int12_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int160(const struct FheInt12 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int12_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int2(const struct FheInt12 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int12_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int2048(const struct FheInt12 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int12_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int256(const struct FheInt12 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int12_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int32(const struct FheInt12 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int12_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int4(const struct FheInt12 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int12_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int512(const struct FheInt12 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int12_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int6(const struct FheInt12 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int12_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int64(const struct FheInt12 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int12_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_int8(const struct FheInt12 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int12_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint10(const struct FheInt12 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int12_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint1024(const struct FheInt12 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int12_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint12(const struct FheInt12 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int12_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint128(const struct FheInt12 *sself, struct FheUint128 **result);
///```
public FheUint128 castIntoFheUint128() {
  FheUint128 result = new FheUint128();
  execute(() -> fhe_int12_cast_into_fhe_uint128(getValue(), result.getAddress()));
  return result;
}

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint14(const struct FheInt12 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int12_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint16(const struct FheInt12 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int12_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint160(const struct FheInt12 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int12_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint2(const struct FheInt12 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int12_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint2048(const struct FheInt12 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int12_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint256(const struct FheInt12 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int12_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint32(const struct FheInt12 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int12_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint4(const struct FheInt12 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int12_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint512(const struct FheInt12 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int12_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int12_cast_into_fhe_uint6(const struct FheInt12 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
  execute(() -> fhe_int12_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int12_cast_into_fhe_uint64(const struct FheInt12 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_int12_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int12_cast_into_fhe_uint8(const struct FheInt12 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_int12_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
