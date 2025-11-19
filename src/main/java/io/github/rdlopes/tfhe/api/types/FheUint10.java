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
public class FheUint10 extends NativePointer implements FheUnsignedInteger<Short, FheUint10, CompressedFheUint10> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint10.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint10_destroy(struct FheUint10 *ptr);
  ///```
  FheUint10() {
    logger.trace("init");
    super(TfheHeader::fhe_uint10_destroy);
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
  /// int fhe_uint10_safe_serialize(const struct FheUint10 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint10_bitand(const struct FheUint10 *lhs,
  ///                       const struct FheUint10 *rhs,
  ///                       struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 bitAnd(FheUint10 other){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_bitand(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 bitAndScalar(Short other){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_bitand_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void bitAndAssign(FheUint10 other){
    execute(() -> fhe_uint10_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_bitand_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_bitor(const struct FheUint10 *lhs,
  ///                      const struct FheUint10 *rhs,
  ///                      struct FheUint10 **result);
///```
@Override
public FheUint10 bitOr(FheUint10 other){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint10_scalar_bitor(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 bitOrScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_bitor_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
///```
@Override
public void bitOrAssign(FheUint10 other){
    execute(() -> fhe_uint10_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint10_scalar_bitor_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_bitxor(const struct FheUint10 *lhs,
  ///                       const struct FheUint10 *rhs,
///                       struct FheUint10 **result);
///```
@Override
public FheUint10 bitXor(FheUint10 other){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint10_scalar_bitxor(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 bitXorScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_bitxor_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint10 other) {
    execute(() -> fhe_uint10_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_bitxor_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_not(const struct FheUint10 *input, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 bitNot() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheUint10 *then_ct,
  ///                             const struct FheUint10 *else_ct,
  ///                             struct FheUint10 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint10 ifThenElse(FheBool condition, FheUint10 thenValue, FheUint10 elseValue) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_eq(const struct FheUint10 *lhs,
  ///                   const struct FheUint10 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_eq(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint10_ne(const struct FheUint10 *lhs,
///                   const struct FheUint10 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_ne(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Short other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_uint10_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                            uint64_t serialized_size_limit,
  ///                                            const struct ServerKey *server_key,
///                                            struct FheUint10 **result);
///```
public static FheUint10 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheUint10 deserialized = new FheUint10();
  execute(() -> fhe_uint10_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_uint10_try_encrypt_with_client_key_u16(uint16_t value,
  ///                                                const struct ClientKey *client_key,
///                                                struct FheUint10 **result);
  ///```
  public static FheUint10 encrypt(Short clearValue, ClientKey clientKey){
    FheUint10 encrypted = new FheUint10();
    execute(() -> fhe_uint10_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

}
  
/// ```c
/// int fhe_uint10_try_encrypt_with_public_key_u16(uint16_t value,
///                                                const struct PublicKey *public_key,
///                                                struct FheUint10 **result);
///```
public static FheUint10 encrypt(Short clearValue, PublicKey publicKey){
    FheUint10 encrypted = new FheUint10();
      execute(() -> fhe_uint10_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint10_try_encrypt_trivial_u16(uint16_t value, struct FheUint10 **result);
  ///```
  public static FheUint10 encrypt(Short clearValue){
    FheUint10 encrypted = new FheUint10();
      execute(() -> fhe_uint10_try_encrypt_trivial_u16(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint10_clone(const struct FheUint10 *sself, struct FheUint10 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint10 clone() {
    FheUint10 cloned = new FheUint10();
    execute(() -> fhe_uint10_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint10_decrypt(const struct FheUint10 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        uint16_t *result);
  ///```
  @Override
  public Short decrypt(ClientKey clientKey) {
    return executeAndReturn(Short.class, address -> fhe_uint10_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_uint10_compress(const struct FheUint10 *sself, struct CompressedFheUint10 **result);
///```
@Override
public CompressedFheUint10 compress() {
  CompressedFheUint10 compressed = new CompressedFheUint10();
  execute(() -> fhe_uint10_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint10_add(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 add(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_overflowing_add(const struct FheUint10 *lhs,
  ///                                const struct FheUint10 *rhs,
  ///                                struct FheUint10 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint10, CompressedFheUint10> addWithOverflow(FheUint10 other) {
    FheUint10 result = new FheUint10();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint10_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint10_scalar_add(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 addScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_add_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void addAssign(FheUint10 other) {
    execute(() -> fhe_uint10_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_add_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void addScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_sub(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 subtract(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_overflowing_sub(const struct FheUint10 *lhs,
  ///                                const struct FheUint10 *rhs,
  ///                                struct FheUint10 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint10, CompressedFheUint10> subtractWithOverflow(FheUint10 other) {
    FheUint10 result = new FheUint10();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint10_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint10_scalar_sub(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 subtractScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_sub_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint10 other) {
    execute(() -> fhe_uint10_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_sub_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_mul(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 multiply(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_overflowing_mul(const struct FheUint10 *lhs,
  ///                                const struct FheUint10 *rhs,
  ///                                struct FheUint10 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Short, FheUint10, CompressedFheUint10> multiplyWithOverflow(FheUint10 other) {
    FheUint10 result = new FheUint10();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint10_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint10_scalar_mul(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 multiplyScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_mul_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint10 other) {
    execute(() -> fhe_uint10_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_mul_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_div(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 divide(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_div(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 divideScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_div_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint10 other) {
    execute(() -> fhe_uint10_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_div_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_rem(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 remainder(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_rem(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 remainderScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_rem_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint10 other) {
    execute(() -> fhe_uint10_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_rem_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_div_rem(const struct FheUint10 *lhs,
  ///                        const struct FheUint10 *rhs,
  ///                        struct FheUint10 **q_result,
  ///                        struct FheUint10 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheUint10, CompressedFheUint10> divideWithRemainder(FheUint10 other) {
    FheUint10 divider = new FheUint10();
    FheUint10 remainder = new FheUint10();
    execute(() -> fhe_uint10_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint10_scalar_div_rem(const struct FheUint10 *lhs,
  ///                               uint16_t rhs,
  ///                               struct FheUint10 **q_result,
  ///                               struct FheUint10 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Short, FheUint10, CompressedFheUint10> divideWithRemainderScalar(Short other) {
    FheUint10 divider = new FheUint10();
    FheUint10 remainder = new FheUint10();
    execute(() -> fhe_uint10_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint10_neg(const struct FheUint10 *input, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 negate() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint10_ilog2(const struct FheUint10 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint10 ilog2() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint10_checked_ilog2(const struct FheUint10 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Short, FheUint10, CompressedFheUint10> ilog2WithCheck() {
    FheUint10 result = new FheUint10();
    FheBool check = new FheBool();
    execute(() -> fhe_uint10_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint10_lt(const struct FheUint10 *lhs,
  ///                   const struct FheUint10 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_lt(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_le(const struct FheUint10 *lhs,
  ///                   const struct FheUint10 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_le(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_gt(const struct FheUint10 *lhs,
  ///                   const struct FheUint10 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_gt(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_ge(const struct FheUint10 *lhs,
  ///                   const struct FheUint10 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint10 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_ge(const struct FheUint10 *lhs, uint16_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Short other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint10_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_min(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 min(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_min(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 minScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_max(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 max(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_max(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 maxScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_shl(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 shiftLeft(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_shl(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 shiftLeftScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_shl_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint10 other) {
    execute(() -> fhe_uint10_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_shl_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_shr(const struct FheUint10 *lhs,
  ///                    const struct FheUint10 *rhs,
  ///                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 shiftRight(FheUint10 other){
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_shr(const struct FheUint10 *lhs, uint16_t rhs, struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 shiftRightScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_shr_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint10 other) {
    execute(() -> fhe_uint10_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_shr_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_rotate_left(const struct FheUint10 *lhs,
  ///                            const struct FheUint10 *rhs,
  ///                            struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 rotateLeft(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_rotate_left(const struct FheUint10 *lhs,
  ///                                   uint16_t rhs,
  ///                                   struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 rotateLeftScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_rotate_left_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint10 other) {
    execute(() -> fhe_uint10_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_rotate_left_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint10_rotate_right(const struct FheUint10 *lhs,
  ///                             const struct FheUint10 *rhs,
  ///                             struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 rotateRight(FheUint10 other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_scalar_rotate_right(const struct FheUint10 *lhs,
  ///                                    uint16_t rhs,
  ///                                    struct FheUint10 **result);
  ///```
  @Override
  public FheUint10 rotateRightScalar(Short other) {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint10_rotate_right_assign(struct FheUint10 *lhs, const struct FheUint10 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint10 other) {
    execute(() -> fhe_uint10_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint10_scalar_rotate_right_assign(struct FheUint10 *lhs, uint16_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Short other) {
    execute(() -> fhe_uint10_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint10_leading_ones(const struct FheUint10 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint10 leadingOnes() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint10_leading_zeros(const struct FheUint10 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint10 leadingZeros() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint10_trailing_ones(const struct FheUint10 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint10 trailingOnes() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint10_trailing_zeros(const struct FheUint10 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint10 trailingZeros() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_uint10(struct FheUint10 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheUint10 random(long seedLow, long seedHigh) {
    FheUint10 result = new FheUint10();
    execute(() -> generate_oblivious_pseudo_random_fhe_uint10(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_uint10(struct FheUint10 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheUint10 random(long seedLow, long seedHigh, long bitsCount) {
    FheUint10 result = new FheUint10();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint10(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int10(const struct FheUint10 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint10_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int1024(const struct FheUint10 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint10_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int12(const struct FheUint10 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint10_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int128(const struct FheUint10 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint10_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int14(const struct FheUint10 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint10_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int16(const struct FheUint10 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint10_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int160(const struct FheUint10 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint10_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int2(const struct FheUint10 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint10_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int2048(const struct FheUint10 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint10_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int256(const struct FheUint10 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint10_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int32(const struct FheUint10 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint10_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int4(const struct FheUint10 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint10_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int512(const struct FheUint10 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint10_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int6(const struct FheUint10 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint10_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int64(const struct FheUint10 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint10_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_int8(const struct FheUint10 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint10_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint10(const struct FheUint10 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint10_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint1024(const struct FheUint10 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint10_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint12(const struct FheUint10 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint10_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint128(const struct FheUint10 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint10_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint14(const struct FheUint10 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint10_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint16(const struct FheUint10 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint10_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint160(const struct FheUint10 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint10_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint2(const struct FheUint10 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint10_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint2048(const struct FheUint10 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint10_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint256(const struct FheUint10 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint10_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint32(const struct FheUint10 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint10_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint4(const struct FheUint10 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint10_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint512(const struct FheUint10 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint10_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint10_cast_into_fhe_uint6(const struct FheUint10 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint10_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint10_cast_into_fhe_uint64(const struct FheUint10 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint10_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint10_cast_into_fhe_uint8(const struct FheUint10 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint10_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
