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
public class FheInt4 extends NativePointer implements FheInteger<Byte, FheInt4, CompressedFheInt4> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt4.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_int4_destroy(struct FheInt4 *ptr);
  ///```
  FheInt4() {
    logger.trace("init");
    super(TfheHeader::fhe_int4_destroy);
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
  /// int fhe_int4_safe_serialize(const struct FheInt4 *sself,
  ///                             struct DynamicBuffer *result,
  ///                             uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_int4_bitand(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 bitAnd(FheInt4 other){
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_bitand(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 bitAndScalar(Byte other){
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_bitand_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void bitAndAssign(FheInt4 other){
    execute(() -> fhe_int4_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_bitand_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_bitor(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
///```
@Override
public FheInt4 bitOr(FheInt4 other){
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int4_scalar_bitor(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 bitOrScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_bitor_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
///```
@Override
public void bitOrAssign(FheInt4 other){
    execute(() -> fhe_int4_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int4_scalar_bitor_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_bitxor(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
///```
@Override
public FheInt4 bitXor(FheInt4 other){
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int4_scalar_bitxor(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 bitXorScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_bitxor_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheInt4 other) {
    execute(() -> fhe_int4_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_bitxor_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_not(const struct FheInt4 *input, struct FheInt4 **result);
///```
@Override
public FheInt4 bitNot(){
    FheInt4 result = new FheInt4();
  execute(() -> fhe_int4_not(getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int4_if_then_else(const struct FheBool *condition_ct,
  ///                           const struct FheInt4 *then_ct,
  ///                           const struct FheInt4 *else_ct,
  ///                           struct FheInt4 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheInt4 ifThenElse(FheBool condition, FheInt4 thenValue, FheInt4 elseValue) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_eq(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
///```
@Override
public FheBool equalTo(FheInt4 other){
    FheBool result = new FheBool();
  execute(() -> fhe_int4_eq(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int4_scalar_eq(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_ne(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheInt4 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_ne(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_int4_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                          uint64_t serialized_size_limit,
  ///                                          const struct ServerKey *server_key,
///                                          struct FheInt4 **result);
///```
public static FheInt4 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheInt4 deserialized = new FheInt4();
    execute(() -> fhe_int4_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
  /// int fhe_int4_try_encrypt_with_client_key_i8(int8_t value,
  ///                                             const struct ClientKey *client_key,
  ///                                             struct FheInt4 **result);
  ///```
  public static FheInt4 encrypt(Byte clearValue, ClientKey clientKey) {
    FheInt4 encrypted = new FheInt4();
    execute(() -> fhe_int4_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
/// int fhe_int4_try_encrypt_with_public_key_i8(int8_t value,
///                                             const struct PublicKey *public_key,
///                                             struct FheInt4 **result);
  ///```
  public static FheInt4 encrypt(Byte clearValue, PublicKey publicKey) {
    FheInt4 encrypted = new FheInt4();
      execute(() -> fhe_int4_try_encrypt_with_public_key_i8(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int4_try_encrypt_trivial_i8(int8_t value, struct FheInt4 **result);
  ///```
  public static FheInt4 encrypt(Byte clearValue) {
    FheInt4 encrypted = new FheInt4();
    execute(() -> fhe_int4_try_encrypt_trivial_i8(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int4_clone(const struct FheInt4 *sself, struct FheInt4 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt4 clone() {
    FheInt4 cloned = new FheInt4();
    execute(() -> fhe_int4_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_int4_decrypt(const struct FheInt4 *encrypted_value,
  ///                      const struct ClientKey *client_key,
  ///                      int8_t *result);
  ///```
  @Override
  public Byte decrypt(ClientKey clientKey) {
    return executeAndReturn(Byte.class, address -> fhe_int4_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_int4_compress(const struct FheInt4 *sself, struct CompressedFheInt4 **result);
///```
@Override
public CompressedFheInt4 compress() {
  CompressedFheInt4 compressed = new CompressedFheInt4();
  execute(() -> fhe_int4_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_int4_add(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 add(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_overflowing_add(const struct FheInt4 *lhs,
  ///                              const struct FheInt4 *rhs,
  ///                              struct FheInt4 **out_result,
  ///                              struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Byte, FheInt4, CompressedFheInt4> addWithOverflow(FheInt4 other) {
    FheInt4 result = new FheInt4();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int4_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int4_scalar_add(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 addScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_add_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void addAssign(FheInt4 other) {
    execute(() -> fhe_int4_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_add_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void addScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_sub(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 subtract(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_overflowing_sub(const struct FheInt4 *lhs,
  ///                              const struct FheInt4 *rhs,
  ///                              struct FheInt4 **out_result,
  ///                              struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Byte, FheInt4, CompressedFheInt4> subtractWithOverflow(FheInt4 other) {
    FheInt4 result = new FheInt4();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int4_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int4_scalar_sub(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 subtractScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_sub_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void subtractAssign(FheInt4 other) {
    execute(() -> fhe_int4_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_sub_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_mul(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 multiply(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_overflowing_mul(const struct FheInt4 *lhs,
  ///                              const struct FheInt4 *rhs,
  ///                              struct FheInt4 **out_result,
  ///                              struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Byte, FheInt4, CompressedFheInt4> multiplyWithOverflow(FheInt4 other) {
    FheInt4 result = new FheInt4();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int4_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int4_scalar_mul(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 multiplyScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_mul_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheInt4 other) {
    execute(() -> fhe_int4_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_mul_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_div(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 divide(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_div(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 divideScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_div_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void divideAssign(FheInt4 other) {
    execute(() -> fhe_int4_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_div_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_rem(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 remainder(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_rem(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 remainderScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_rem_assign(struct FheInt4 *lhs, const struct FheInt4 *rhs);
  ///```
  @Override
  public void remainderAssign(FheInt4 other) {
    execute(() -> fhe_int4_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_rem_assign(struct FheInt4 *lhs, int8_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_div_rem(const struct FheInt4 *lhs,
  ///                      const struct FheInt4 *rhs,
  ///                      struct FheInt4 **q_result,
  ///                      struct FheInt4 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Byte, FheInt4, CompressedFheInt4> divideWithRemainder(FheInt4 other) {
    FheInt4 divider = new FheInt4();
    FheInt4 remainder = new FheInt4();
    execute(() -> fhe_int4_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int4_scalar_div_rem(const struct FheInt4 *lhs,
  ///                             int8_t rhs,
  ///                             struct FheInt4 **q_result,
  ///                             struct FheInt4 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Byte, FheInt4, CompressedFheInt4> divideWithRemainderScalar(Byte other) {
    FheInt4 divider = new FheInt4();
    FheInt4 remainder = new FheInt4();
    execute(() -> fhe_int4_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int4_neg(const struct FheInt4 *input, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 negate() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_int4_ilog2(const struct FheInt4 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt4 ilog2() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_int4_checked_ilog2(const struct FheInt4 *input,
  ///                            struct FheUint32 **result_1,
  ///                            struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Byte, FheInt4, CompressedFheInt4> ilog2WithCheck() {
    FheInt4 result = new FheInt4();
    FheBool check = new FheBool();
    execute(() -> fhe_int4_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_int4_lt(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheInt4 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_lt(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_le(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheInt4 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_le(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_gt(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheInt4 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_gt(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_ge(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheInt4 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_ge(const struct FheInt4 *lhs, int8_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Byte other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int4_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_min(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 min(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_min(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 minScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_max(const struct FheInt4 *lhs, const struct FheInt4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 max(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_max(const struct FheInt4 *lhs, int8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 maxScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_shl(const struct FheInt4 *lhs, const struct FheUint4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 shiftLeft(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_shl(const struct FheInt4 *lhs, uint8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 shiftLeftScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_shl_assign(struct FheInt4 *lhs, const struct FheUint4 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheInt4 other) {
    execute(() -> fhe_int4_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_shl_assign(struct FheInt4 *lhs, uint8_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_shr(const struct FheInt4 *lhs, const struct FheUint4 *rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 shiftRight(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_shr(const struct FheInt4 *lhs, uint8_t rhs, struct FheInt4 **result);
  ///```
  @Override
public FheInt4 shiftRightScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_shr_assign(struct FheInt4 *lhs, const struct FheUint4 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheInt4 other) {
    execute(() -> fhe_int4_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_shr_assign(struct FheInt4 *lhs, uint8_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_rotate_left(const struct FheInt4 *lhs,
  ///                          const struct FheUint4 *rhs,
  ///                          struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 rotateLeft(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_rotate_left(const struct FheInt4 *lhs, uint8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 rotateLeftScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_rotate_left_assign(struct FheInt4 *lhs, const struct FheUint4 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheInt4 other) {
    execute(() -> fhe_int4_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_rotate_left_assign(struct FheInt4 *lhs, uint8_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Byte other) {
    execute(() -> fhe_int4_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_int4_rotate_right(const struct FheInt4 *lhs,
  ///                           const struct FheUint4 *rhs,
  ///                           struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 rotateRight(FheInt4 other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_scalar_rotate_right(const struct FheInt4 *lhs, uint8_t rhs, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 rotateRightScalar(Byte other) {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_rotate_right_assign(struct FheInt4 *lhs, const struct FheUint4 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheInt4 other) {
    execute(() -> fhe_int4_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int4_scalar_rotate_right_assign(struct FheInt4 *lhs, uint8_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Byte other){
    execute(() -> fhe_int4_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_int4_leading_ones(const struct FheInt4 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt4 leadingOnes() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_int4_leading_zeros(const struct FheInt4 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt4 leadingZeros() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_int4_trailing_ones(const struct FheInt4 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt4 trailingOnes() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_int4_trailing_zeros(const struct FheInt4 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt4 trailingZeros() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_int4(struct FheInt4 **out_result,
  ///                                               uint64_t seed_low_bytes,
  ///                                               uint64_t seed_high_bytes);
  ///```
  @Override
  public FheInt4 random(long seedLow, long seedHigh) {
    FheInt4 result = new FheInt4();
    execute(() -> generate_oblivious_pseudo_random_fhe_int4(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_int4(struct FheInt4 **out_result,
  ///                                                       uint64_t seed_low_bytes,
  ///                                                       uint64_t seed_high_bytes,
  ///                                                       uint64_t random_bits_count);
  ///```
  @Override
  public FheInt4 random(long seedLow, long seedHigh, long bitsCount) {
    FheInt4 result = new FheInt4();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_int4(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the absolute value.
  ///  *
  ///  * (if x < 0 { -x } else { x })
  ///  */
  /// int fhe_int4_abs(const struct FheInt4 *input, struct FheInt4 **result);
  ///```
  @Override
  public FheInt4 abs() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_abs(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int10(const struct FheInt4 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int4_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int1024(const struct FheInt4 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int4_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int12(const struct FheInt4 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int4_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int128(const struct FheInt4 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int4_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int14(const struct FheInt4 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int4_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int16(const struct FheInt4 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int4_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int160(const struct FheInt4 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int4_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int2(const struct FheInt4 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int4_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int2048(const struct FheInt4 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int4_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int256(const struct FheInt4 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int4_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int32(const struct FheInt4 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int4_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int4(const struct FheInt4 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int512(const struct FheInt4 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int4_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int6(const struct FheInt4 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int4_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int64(const struct FheInt4 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int4_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_int8(const struct FheInt4 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int4_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint10(const struct FheInt4 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int4_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint1024(const struct FheInt4 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int4_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint12(const struct FheInt4 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int4_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint128(const struct FheInt4 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_int4_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint14(const struct FheInt4 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int4_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint16(const struct FheInt4 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int4_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint160(const struct FheInt4 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int4_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint2(const struct FheInt4 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int4_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint2048(const struct FheInt4 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int4_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint256(const struct FheInt4 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int4_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint32(const struct FheInt4 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int4_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint4(const struct FheInt4 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int4_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint512(const struct FheInt4 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int4_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int4_cast_into_fhe_uint6(const struct FheInt4 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
    execute(() -> fhe_int4_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int4_cast_into_fhe_uint64(const struct FheInt4 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_int4_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int4_cast_into_fhe_uint8(const struct FheInt4 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_int4_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
