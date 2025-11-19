package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
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
public class FheUint14 extends NativePointer implements FheUnsignedInteger<Short, FheUint14, CompressedFheUint14> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint14.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint14_destroy(struct FheUint14 *ptr);
  ///```
  FheUint14() {
    logger.trace("init");
    super(TfheHeader::fhe_uint14_destroy);
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
  /// int fhe_uint14_safe_serialize(const struct FheUint14 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint14_bitand(const struct FheUint14 *lhs,
  ///                       const struct FheUint14 *rhs,
  ///                       struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 bitAnd(FheUint14 other){
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_bitand(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 bitAndScalar(Short other){
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_bitand_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void bitAndAssign(FheUint14 other){
    execute(() -> fhe_uint14_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_bitand_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_bitor(const struct FheUint14 *lhs,
  ///                      const struct FheUint14 *rhs,
  ///                      struct FheUint14 **result);
///```
@Override
public FheUint14 bitOr(FheUint14 other){
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint14_scalar_bitor(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 bitOrScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_bitor_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
///```
@Override
public void bitOrAssign(FheUint14 other){
    execute(() -> fhe_uint14_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint14_scalar_bitor_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_bitxor(const struct FheUint14 *lhs,
  ///                       const struct FheUint14 *rhs,
///                       struct FheUint14 **result);
///```
@Override
public FheUint14 bitXor(FheUint14 other){
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint14_scalar_bitxor(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 bitXorScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_bitxor_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint14 other) {
    execute(() -> fhe_uint14_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_bitxor_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_not(const struct FheUint14 *input, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 bitNot() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheUint14 *then_ct,
  ///                             const struct FheUint14 *else_ct,
  ///                             struct FheUint14 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint14 ifThenElse(FheBool condition, FheUint14 thenValue, FheUint14 elseValue) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_eq(const struct FheUint14 *lhs,
  ///                   const struct FheUint14 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_eq(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint14_ne(const struct FheUint14 *lhs,
///                   const struct FheUint14 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_ne(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Short other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_uint14_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                            uint64_t serialized_size_limit,
  ///                                            const struct ServerKey *server_key,
///                                            struct FheUint14 **result);
///```
public static FheUint14 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheUint14 deserialized = new FheUint14();
  execute(() -> fhe_uint14_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_uint14_try_encrypt_with_client_key_u16(uint16_t value,
  ///                                                const struct ClientKey *client_key,
///                                                struct FheUint14 **result);
  ///```
  public static FheUint14 encrypt(Short clearValue, ClientKey clientKey){
    FheUint14 encrypted = new FheUint14();
    execute(() -> fhe_uint14_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

}
  
/// ```c
/// int fhe_uint14_try_encrypt_with_public_key_u16(uint16_t value,
///                                                const struct PublicKey *public_key,
///                                                struct FheUint14 **result);
///```
public static FheUint14 encrypt(Short clearValue, PublicKey publicKey){
    FheUint14 encrypted = new FheUint14();
      execute(() -> fhe_uint14_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint14_try_encrypt_trivial_u16(uint16_t value, struct FheUint14 **result);
  ///```
  public static FheUint14 encrypt(Short clearValue){
    FheUint14 encrypted = new FheUint14();
      execute(() -> fhe_uint14_try_encrypt_trivial_u16(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint14_clone(const struct FheUint14 *sself, struct FheUint14 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint14 clone() {
    FheUint14 cloned = new FheUint14();
    execute(() -> fhe_uint14_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint14_decrypt(const struct FheUint14 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        uint16_t *result);
  ///```
  @Override
  public Short decrypt(ClientKey clientKey) {
    return executeAndReturn(Short.class, address -> fhe_uint14_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_uint14_compress(const struct FheUint14 *sself, struct CompressedFheUint14 **result);
///```
@Override
public CompressedFheUint14 compress() {
  CompressedFheUint14 compressed = new CompressedFheUint14();
  execute(() -> fhe_uint14_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint14_add(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 add(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_overflowing_add(const struct FheUint14 *lhs,
  ///                                const struct FheUint14 *rhs,
  ///                                struct FheUint14 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint14, CompressedFheUint14> addWithOverflow(FheUint14 other) {
    FheUint14 result = new FheUint14();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint14_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint14_scalar_add(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 addScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_add_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void addAssign(FheUint14 other) {
    execute(() -> fhe_uint14_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_add_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void addScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_sub(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 subtract(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_overflowing_sub(const struct FheUint14 *lhs,
  ///                                const struct FheUint14 *rhs,
  ///                                struct FheUint14 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint14, CompressedFheUint14> subtractWithOverflow(FheUint14 other) {
    FheUint14 result = new FheUint14();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint14_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint14_scalar_sub(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 subtractScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_sub_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint14 other) {
    execute(() -> fhe_uint14_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_sub_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_mul(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 multiply(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_overflowing_mul(const struct FheUint14 *lhs,
  ///                                const struct FheUint14 *rhs,
  ///                                struct FheUint14 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint14, CompressedFheUint14> multiplyWithOverflow(FheUint14 other) {
    FheUint14 result = new FheUint14();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint14_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint14_scalar_mul(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 multiplyScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_mul_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint14 other) {
    execute(() -> fhe_uint14_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_mul_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_div(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 divide(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_div(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 divideScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_div_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint14 other) {
    execute(() -> fhe_uint14_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_div_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_rem(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 remainder(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_rem(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 remainderScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_rem_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint14 other) {
    execute(() -> fhe_uint14_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_rem_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_div_rem(const struct FheUint14 *lhs,
  ///                        const struct FheUint14 *rhs,
  ///                        struct FheUint14 **q_result,
  ///                        struct FheUint14 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheUint14, CompressedFheUint14> divideWithRemainder(FheUint14 other) {
    FheUint14 divider = new FheUint14();
    FheUint14 remainder = new FheUint14();
    execute(() -> fhe_uint14_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint14_scalar_div_rem(const struct FheUint14 *lhs,
  ///                               uint16_t rhs,
  ///                               struct FheUint14 **q_result,
  ///                               struct FheUint14 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheUint14, CompressedFheUint14> divideWithRemainderScalar(Short other) {
    FheUint14 divider = new FheUint14();
    FheUint14 remainder = new FheUint14();
    execute(() -> fhe_uint14_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint14_neg(const struct FheUint14 *input, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 negate() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint14_ilog2(const struct FheUint14 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint14 ilog2() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint14_checked_ilog2(const struct FheUint14 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Short, FheUint14, CompressedFheUint14> ilog2WithCheck() {
    FheUint14 result = new FheUint14();
    FheBool check = new FheBool();
    execute(() -> fhe_uint14_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint14_lt(const struct FheUint14 *lhs,
  ///                   const struct FheUint14 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_lt(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_le(const struct FheUint14 *lhs,
  ///                   const struct FheUint14 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_le(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_gt(const struct FheUint14 *lhs,
  ///                   const struct FheUint14 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_gt(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_ge(const struct FheUint14 *lhs,
  ///                   const struct FheUint14 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint14 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_ge(const struct FheUint14 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint14_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_min(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 min(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_min(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 minScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_max(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 max(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_max(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 maxScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_shl(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 shiftLeft(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_shl(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 shiftLeftScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_shl_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint14 other) {
    execute(() -> fhe_uint14_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_shl_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_shr(const struct FheUint14 *lhs,
  ///                    const struct FheUint14 *rhs,
  ///                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 shiftRight(FheUint14 other){
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_shr(const struct FheUint14 *lhs, uint16_t rhs, struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 shiftRightScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_shr_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint14 other) {
    execute(() -> fhe_uint14_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_shr_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_rotate_left(const struct FheUint14 *lhs,
  ///                            const struct FheUint14 *rhs,
  ///                            struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 rotateLeft(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_rotate_left(const struct FheUint14 *lhs,
  ///                                   uint16_t rhs,
  ///                                   struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 rotateLeftScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_rotate_left_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint14 other) {
    execute(() -> fhe_uint14_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_rotate_left_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint14_rotate_right(const struct FheUint14 *lhs,
  ///                             const struct FheUint14 *rhs,
  ///                             struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 rotateRight(FheUint14 other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_scalar_rotate_right(const struct FheUint14 *lhs,
  ///                                    uint16_t rhs,
  ///                                    struct FheUint14 **result);
  ///```
  @Override
  public FheUint14 rotateRightScalar(Short other) {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint14_rotate_right_assign(struct FheUint14 *lhs, const struct FheUint14 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint14 other) {
    execute(() -> fhe_uint14_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint14_scalar_rotate_right_assign(struct FheUint14 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Short other) {
    execute(() -> fhe_uint14_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint14_leading_ones(const struct FheUint14 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint14 leadingOnes() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint14_leading_zeros(const struct FheUint14 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint14 leadingZeros() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint14_trailing_ones(const struct FheUint14 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint14 trailingOnes() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint14_trailing_zeros(const struct FheUint14 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint14 trailingZeros() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_uint14(struct FheUint14 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheUint14 random(long seedLow, long seedHigh) {
    FheUint14 result = new FheUint14();
    execute(() -> generate_oblivious_pseudo_random_fhe_uint14(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_uint14(struct FheUint14 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheUint14 random(long seedLow, long seedHigh, long bitsCount) {
    FheUint14 result = new FheUint14();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint14(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int10(const struct FheUint14 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint14_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int1024(const struct FheUint14 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint14_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int12(const struct FheUint14 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint14_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int128(const struct FheUint14 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint14_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int14(const struct FheUint14 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint14_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int16(const struct FheUint14 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint14_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int160(const struct FheUint14 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint14_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int2(const struct FheUint14 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint14_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int2048(const struct FheUint14 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint14_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int256(const struct FheUint14 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint14_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int32(const struct FheUint14 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint14_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int4(const struct FheUint14 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint14_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int512(const struct FheUint14 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint14_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int6(const struct FheUint14 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint14_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int64(const struct FheUint14 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint14_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_int8(const struct FheUint14 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint14_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint10(const struct FheUint14 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint14_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint1024(const struct FheUint14 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint14_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint12(const struct FheUint14 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint14_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint128(const struct FheUint14 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint14_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint14(const struct FheUint14 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint14_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint16(const struct FheUint14 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint14_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint160(const struct FheUint14 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint14_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint2(const struct FheUint14 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint14_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint2048(const struct FheUint14 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint14_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint256(const struct FheUint14 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint14_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint32(const struct FheUint14 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint14_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint4(const struct FheUint14 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint14_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint512(const struct FheUint14 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint14_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint14_cast_into_fhe_uint6(const struct FheUint14 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint14_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint14_cast_into_fhe_uint64(const struct FheUint14 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint14_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint14_cast_into_fhe_uint8(const struct FheUint14 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint14_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
