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
public class FheUint32 extends NativePointer implements FheUnsignedInteger<Integer, FheUint32, CompressedFheUint32> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint32.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint32_destroy(struct FheUint32 *ptr);
  ///```
  FheUint32() {
    logger.trace("init");
    super(TfheHeader::fhe_uint32_destroy);
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
  /// int fhe_uint32_safe_serialize(const struct FheUint32 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint32_bitand(const struct FheUint32 *lhs,
  ///                       const struct FheUint32 *rhs,
  ///                       struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 bitAnd(FheUint32 other){
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_bitand(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 bitAndScalar(Integer other){
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_bitand_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void bitAndAssign(FheUint32 other){
    execute(() -> fhe_uint32_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_bitand_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_bitor(const struct FheUint32 *lhs,
  ///                      const struct FheUint32 *rhs,
  ///                      struct FheUint32 **result);
///```
@Override
public FheUint32 bitOr(FheUint32 other){
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint32_scalar_bitor(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 bitOrScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_bitor_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
///```
@Override
public void bitOrAssign(FheUint32 other){
    execute(() -> fhe_uint32_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint32_scalar_bitor_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_bitxor(const struct FheUint32 *lhs,
  ///                       const struct FheUint32 *rhs,
///                       struct FheUint32 **result);
///```
@Override
public FheUint32 bitXor(FheUint32 other){
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint32_scalar_bitxor(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 bitXorScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_bitxor_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint32 other) {
    execute(() -> fhe_uint32_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_bitxor_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_not(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 bitNot() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheUint32 *then_ct,
  ///                             const struct FheUint32 *else_ct,
  ///                             struct FheUint32 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint32 ifThenElse(FheBool condition, FheUint32 thenValue, FheUint32 elseValue) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_eq(const struct FheUint32 *lhs,
  ///                   const struct FheUint32 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_eq(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Integer other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint32_ne(const struct FheUint32 *lhs,
///                   const struct FheUint32 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_ne(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Integer other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_uint32_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                            uint64_t serialized_size_limit,
  ///                                            const struct ServerKey *server_key,
///                                            struct FheUint32 **result);
///```
public static FheUint32 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheUint32 deserialized = new FheUint32();
  execute(() -> fhe_uint32_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_uint32_try_encrypt_with_client_key_u32(uint32_t value,
  ///                                                const struct ClientKey *client_key,
///                                                struct FheUint32 **result);
  ///```
  public static FheUint32 encrypt(Integer clearValue, ClientKey clientKey){
    FheUint32 encrypted = new FheUint32();
    execute(() -> fhe_uint32_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

}
  
/// ```c
/// int fhe_uint32_try_encrypt_with_public_key_u32(uint32_t value,
///                                                const struct PublicKey *public_key,
///                                                struct FheUint32 **result);
///```
public static FheUint32 encrypt(Integer clearValue, PublicKey publicKey){
    FheUint32 encrypted = new FheUint32();
      execute(() -> fhe_uint32_try_encrypt_with_public_key_u32(clearValue, publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint32_try_encrypt_trivial_u32(uint32_t value, struct FheUint32 **result);
  ///```
  public static FheUint32 encrypt(Integer clearValue){
    FheUint32 encrypted = new FheUint32();
      execute(() -> fhe_uint32_try_encrypt_trivial_u32(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint32_clone(const struct FheUint32 *sself, struct FheUint32 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint32 clone() {
    FheUint32 cloned = new FheUint32();
    execute(() -> fhe_uint32_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint32_decrypt(const struct FheUint32 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        uint32_t *result);
  ///```
  @Override
  public Integer decrypt(ClientKey clientKey) {
    return executeAndReturn(Integer.class, address -> fhe_uint32_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_uint32_compress(const struct FheUint32 *sself, struct CompressedFheUint32 **result);
///```
@Override
public CompressedFheUint32 compress() {
  CompressedFheUint32 compressed = new CompressedFheUint32();
  execute(() -> fhe_uint32_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint32_add(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 add(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_overflowing_add(const struct FheUint32 *lhs,
  ///                                const struct FheUint32 *rhs,
  ///                                struct FheUint32 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Integer, FheUint32, CompressedFheUint32> addWithOverflow(FheUint32 other) {
    FheUint32 result = new FheUint32();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint32_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint32_scalar_add(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 addScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_add_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void addAssign(FheUint32 other) {
    execute(() -> fhe_uint32_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_add_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void addScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_sub(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 subtract(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_overflowing_sub(const struct FheUint32 *lhs,
  ///                                const struct FheUint32 *rhs,
  ///                                struct FheUint32 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Integer, FheUint32, CompressedFheUint32> subtractWithOverflow(FheUint32 other) {
    FheUint32 result = new FheUint32();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint32_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint32_scalar_sub(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 subtractScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_sub_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint32 other) {
    execute(() -> fhe_uint32_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_sub_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_mul(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 multiply(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_overflowing_mul(const struct FheUint32 *lhs,
  ///                                const struct FheUint32 *rhs,
  ///                                struct FheUint32 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Integer, FheUint32, CompressedFheUint32> multiplyWithOverflow(FheUint32 other) {
    FheUint32 result = new FheUint32();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint32_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint32_scalar_mul(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 multiplyScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_mul_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint32 other) {
    execute(() -> fhe_uint32_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_mul_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_div(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 divide(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_div(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 divideScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_div_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint32 other){
    execute(() -> fhe_uint32_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_div_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_rem(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 remainder(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_rem(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 remainderScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_rem_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint32 other) {
    execute(() -> fhe_uint32_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_rem_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_div_rem(const struct FheUint32 *lhs,
  ///                        const struct FheUint32 *rhs,
  ///                        struct FheUint32 **q_result,
  ///                        struct FheUint32 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Integer, FheUint32, CompressedFheUint32> divideWithRemainder(FheUint32 other) {
    FheUint32 divider = new FheUint32();
    FheUint32 remainder = new FheUint32();
    execute(() -> fhe_uint32_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint32_scalar_div_rem(const struct FheUint32 *lhs,
  ///                               uint32_t rhs,
  ///                               struct FheUint32 **q_result,
  ///                               struct FheUint32 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Integer, FheUint32, CompressedFheUint32> divideWithRemainderScalar(Integer other) {
    FheUint32 divider = new FheUint32();
    FheUint32 remainder = new FheUint32();
    execute(() -> fhe_uint32_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint32_neg(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 negate() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint32_ilog2(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 ilog2() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint32_checked_ilog2(const struct FheUint32 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Integer, FheUint32, CompressedFheUint32> ilog2WithCheck() {
    FheUint32 result = new FheUint32();
    FheBool check = new FheBool();
    execute(() -> fhe_uint32_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint32_lt(const struct FheUint32 *lhs,
  ///                   const struct FheUint32 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_lt(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Integer other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_le(const struct FheUint32 *lhs,
  ///                   const struct FheUint32 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_le(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Integer other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_gt(const struct FheUint32 *lhs,
  ///                   const struct FheUint32 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_gt(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Integer other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_ge(const struct FheUint32 *lhs,
  ///                   const struct FheUint32 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint32 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_ge(const struct FheUint32 *lhs, uint32_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Integer other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint32_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_min(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 min(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_min(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 minScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_max(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 max(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_max(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 maxScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_shl(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 shiftLeft(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_shl(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 shiftLeftScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_shl_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint32 other) {
    execute(() -> fhe_uint32_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_shl_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_shr(const struct FheUint32 *lhs,
  ///                    const struct FheUint32 *rhs,
  ///                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 shiftRight(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_shr(const struct FheUint32 *lhs, uint32_t rhs, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 shiftRightScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_shr_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint32 other) {
    execute(() -> fhe_uint32_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_shr_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_rotate_left(const struct FheUint32 *lhs,
  ///                            const struct FheUint32 *rhs,
  ///                            struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 rotateLeft(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_rotate_left(const struct FheUint32 *lhs,
  ///                                   uint32_t rhs,
  ///                                   struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 rotateLeftScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_rotate_left_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint32 other) {
    execute(() -> fhe_uint32_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_rotate_left_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint32_rotate_right(const struct FheUint32 *lhs,
  ///                             const struct FheUint32 *rhs,
  ///                             struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 rotateRight(FheUint32 other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_scalar_rotate_right(const struct FheUint32 *lhs,
  ///                                    uint32_t rhs,
  ///                                    struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 rotateRightScalar(Integer other) {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint32_rotate_right_assign(struct FheUint32 *lhs, const struct FheUint32 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint32 other) {
    execute(() -> fhe_uint32_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint32_scalar_rotate_right_assign(struct FheUint32 *lhs, uint32_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Integer other) {
    execute(() -> fhe_uint32_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint32_leading_ones(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 leadingOnes() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint32_leading_zeros(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 leadingZeros() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint32_trailing_ones(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 trailingOnes() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint32_trailing_zeros(const struct FheUint32 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint32 trailingZeros() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_uint32(struct FheUint32 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheUint32 random(long seedLow, long seedHigh) {
    FheUint32 result = new FheUint32();
    execute(() -> generate_oblivious_pseudo_random_fhe_uint32(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_uint32(struct FheUint32 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheUint32 random(long seedLow, long seedHigh, long bitsCount) {
    FheUint32 result = new FheUint32();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint32(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int10(const struct FheUint32 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint32_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int1024(const struct FheUint32 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint32_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int12(const struct FheUint32 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint32_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int128(const struct FheUint32 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint32_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int14(const struct FheUint32 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint32_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int16(const struct FheUint32 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint32_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int160(const struct FheUint32 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint32_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int2(const struct FheUint32 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint32_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int2048(const struct FheUint32 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint32_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int256(const struct FheUint32 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint32_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int32(const struct FheUint32 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint32_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int4(const struct FheUint32 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint32_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int512(const struct FheUint32 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint32_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int6(const struct FheUint32 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint32_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int64(const struct FheUint32 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint32_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_int8(const struct FheUint32 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint32_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint10(const struct FheUint32 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint32_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint1024(const struct FheUint32 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint32_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint12(const struct FheUint32 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint32_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint128(const struct FheUint32 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint32_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint14(const struct FheUint32 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint32_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint16(const struct FheUint32 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint32_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint160(const struct FheUint32 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint32_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint2(const struct FheUint32 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint32_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint2048(const struct FheUint32 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint32_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint256(const struct FheUint32 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint32_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint32(const struct FheUint32 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint32_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint4(const struct FheUint32 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint32_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint512(const struct FheUint32 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint32_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint32_cast_into_fhe_uint6(const struct FheUint32 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint32_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint32_cast_into_fhe_uint64(const struct FheUint32 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint32_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint32_cast_into_fhe_uint8(const struct FheUint32 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint32_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
