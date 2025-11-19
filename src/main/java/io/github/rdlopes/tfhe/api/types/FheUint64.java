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
public class FheUint64 extends NativePointer implements FheUnsignedInteger<Long, FheUint64, CompressedFheUint64> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint64.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint64_destroy(struct FheUint64 *ptr);
  ///```
  FheUint64() {
    logger.trace("init");
    super(TfheHeader::fhe_uint64_destroy);
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
  /// int fhe_uint64_safe_serialize(const struct FheUint64 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint64_bitand(const struct FheUint64 *lhs,
  ///                       const struct FheUint64 *rhs,
  ///                       struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 bitAnd(FheUint64 other){
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_bitand(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 bitAndScalar(Long other){
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_bitand_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void bitAndAssign(FheUint64 other){
    execute(() -> fhe_uint64_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_bitand_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_bitor(const struct FheUint64 *lhs,
  ///                      const struct FheUint64 *rhs,
  ///                      struct FheUint64 **result);
///```
@Override
public FheUint64 bitOr(FheUint64 other){
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint64_scalar_bitor(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 bitOrScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_bitor_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
///```
@Override
public void bitOrAssign(FheUint64 other){
    execute(() -> fhe_uint64_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint64_scalar_bitor_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_bitxor(const struct FheUint64 *lhs,
  ///                       const struct FheUint64 *rhs,
///                       struct FheUint64 **result);
///```
@Override
public FheUint64 bitXor(FheUint64 other){
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint64_scalar_bitxor(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 bitXorScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_bitxor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_bitxor_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint64 other) {
    execute(() -> fhe_uint64_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_bitxor_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_not(const struct FheUint64 *input, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 bitNot() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheUint64 *then_ct,
  ///                             const struct FheUint64 *else_ct,
  ///                             struct FheUint64 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint64 ifThenElse(FheBool condition, FheUint64 thenValue, FheUint64 elseValue) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_eq(const struct FheUint64 *lhs,
  ///                   const struct FheUint64 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_eq(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint64_ne(const struct FheUint64 *lhs,
///                   const struct FheUint64 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualTo(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_ne(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(Long other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_uint64_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                            uint64_t serialized_size_limit,
  ///                                            const struct ServerKey *server_key,
///                                            struct FheUint64 **result);
///```
public static FheUint64 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheUint64 deserialized = new FheUint64();
  execute(() -> fhe_uint64_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
/// int fhe_uint64_try_encrypt_with_client_key_u64(uint64_t value,
  ///                                                const struct ClientKey *client_key,
///                                                struct FheUint64 **result);
  ///```
  public static FheUint64 encrypt(Long clearValue, ClientKey clientKey){
    FheUint64 encrypted = new FheUint64();
    execute(() -> fhe_uint64_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

}
  
/// ```c
/// int fhe_uint64_try_encrypt_with_public_key_u64(uint64_t value,
///                                                const struct PublicKey *public_key,
///                                                struct FheUint64 **result);
///```
public static FheUint64 encrypt(Long clearValue, PublicKey publicKey){
    FheUint64 encrypted = new FheUint64();
      execute(() -> fhe_uint64_try_encrypt_with_public_key_u64(clearValue, publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint64_try_encrypt_trivial_u64(uint64_t value, struct FheUint64 **result);
  ///```
  public static FheUint64 encrypt(Long clearValue){
    FheUint64 encrypted = new FheUint64();
      execute(() -> fhe_uint64_try_encrypt_trivial_u64(clearValue, encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint64_clone(const struct FheUint64 *sself, struct FheUint64 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint64 clone() {
    FheUint64 cloned = new FheUint64();
    execute(() -> fhe_uint64_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint64_decrypt(const struct FheUint64 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        uint64_t *result);
  ///```
  @Override
  public Long decrypt(ClientKey clientKey) {
    return executeAndReturn(Long.class, address -> fhe_uint64_decrypt(getValue(), clientKey.getValue(), address));

}
  

/// ```c
/// int fhe_uint64_compress(const struct FheUint64 *sself, struct CompressedFheUint64 **result);
///```
@Override
public CompressedFheUint64 compress() {
  CompressedFheUint64 compressed = new CompressedFheUint64();
  execute(() -> fhe_uint64_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint64_add(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 add(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_overflowing_add(const struct FheUint64 *lhs,
  ///                                const struct FheUint64 *rhs,
  ///                                struct FheUint64 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheUint64, CompressedFheUint64> addWithOverflow(FheUint64 other) {
    FheUint64 result = new FheUint64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint64_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint64_scalar_add(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 addScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_add(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_add_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void addAssign(FheUint64 other) {
    execute(() -> fhe_uint64_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_add_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void addScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_add_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_sub(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 subtract(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_overflowing_sub(const struct FheUint64 *lhs,
  ///                                const struct FheUint64 *rhs,
  ///                                struct FheUint64 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheUint64, CompressedFheUint64> subtractWithOverflow(FheUint64 other) {
    FheUint64 result = new FheUint64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint64_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint64_scalar_sub(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 subtractScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_sub(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_sub_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint64 other) {
    execute(() -> fhe_uint64_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_sub_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void subtractScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_sub_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_mul(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 multiply(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_overflowing_mul(const struct FheUint64 *lhs,
  ///                                const struct FheUint64 *rhs,
  ///                                struct FheUint64 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<Long, FheUint64, CompressedFheUint64> multiplyWithOverflow(FheUint64 other) {
    FheUint64 result = new FheUint64();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint64_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint64_scalar_mul(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 multiplyScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_mul(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_mul_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint64 other) {
    execute(() -> fhe_uint64_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_mul_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void multiplyScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_mul_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_div(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 divide(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_div(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 divideScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_div(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_div_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint64 other) {
    execute(() -> fhe_uint64_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_div_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void divideScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_div_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_rem(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 remainder(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_rem(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 remainderScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_rem(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_rem_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint64 other) {
    execute(() -> fhe_uint64_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_rem_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void remainderScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_rem_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_div_rem(const struct FheUint64 *lhs,
  ///                        const struct FheUint64 *rhs,
  ///                        struct FheUint64 **q_result,
  ///                        struct FheUint64 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Long, FheUint64, CompressedFheUint64> divideWithRemainder(FheUint64 other) {
    FheUint64 divider = new FheUint64();
    FheUint64 remainder = new FheUint64();
    execute(() -> fhe_uint64_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint64_scalar_div_rem(const struct FheUint64 *lhs,
  ///                               uint64_t rhs,
  ///                               struct FheUint64 **q_result,
  ///                               struct FheUint64 **r_result);
  ///```
  @Override
  public DividerAndRemainder<Long, FheUint64, CompressedFheUint64> divideWithRemainderScalar(Long other) {
    FheUint64 divider = new FheUint64();
    FheUint64 remainder = new FheUint64();
    execute(() -> fhe_uint64_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint64_neg(const struct FheUint64 *input, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 negate() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint64_ilog2(const struct FheUint64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint64 ilog2() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint64_checked_ilog2(const struct FheUint64 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<Long, FheUint64, CompressedFheUint64> ilog2WithCheck() {
    FheUint64 result = new FheUint64();
    FheBool check = new FheBool();
    execute(() -> fhe_uint64_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint64_lt(const struct FheUint64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_lt(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_lt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_le(const struct FheUint64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_le(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_le(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_gt(const struct FheUint64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_gt(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_gt(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_ge(const struct FheUint64 *lhs,
  ///                   const struct FheUint64 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint64 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_ge(const struct FheUint64 *lhs, uint64_t rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(Long other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint64_scalar_ge(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_min(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 min(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_min(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 minScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_min(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_max(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 max(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_max(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 maxScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_max(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_shl(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 shiftLeft(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_shl(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 shiftLeftScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_shl(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_shl_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint64 other) {
    execute(() -> fhe_uint64_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_shl_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_shl_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_shr(const struct FheUint64 *lhs,
  ///                    const struct FheUint64 *rhs,
  ///                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 shiftRight(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_shr(const struct FheUint64 *lhs, uint64_t rhs, struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 shiftRightScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_shr(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_shr_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint64 other) {
    execute(() -> fhe_uint64_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_shr_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_shr_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_rotate_left(const struct FheUint64 *lhs,
  ///                            const struct FheUint64 *rhs,
  ///                            struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 rotateLeft(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_rotate_left(const struct FheUint64 *lhs,
  ///                                   uint64_t rhs,
  ///                                   struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 rotateLeftScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_rotate_left_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint64 other) {
    execute(() -> fhe_uint64_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_rotate_left_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_rotate_left_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_uint64_rotate_right(const struct FheUint64 *lhs,
  ///                             const struct FheUint64 *rhs,
  ///                             struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 rotateRight(FheUint64 other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_scalar_rotate_right(const struct FheUint64 *lhs,
  ///                                    uint64_t rhs,
  ///                                    struct FheUint64 **result);
  ///```
  @Override
  public FheUint64 rotateRightScalar(Long other) {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint64_rotate_right_assign(struct FheUint64 *lhs, const struct FheUint64 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint64 other) {
    execute(() -> fhe_uint64_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint64_scalar_rotate_right_assign(struct FheUint64 *lhs, uint64_t rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(Long other) {
    execute(() -> fhe_uint64_scalar_rotate_right_assign(getValue(), other));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint64_leading_ones(const struct FheUint64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint64 leadingOnes() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint64_leading_zeros(const struct FheUint64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint64 leadingZeros() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint64_trailing_ones(const struct FheUint64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint64 trailingOnes() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint64_trailing_zeros(const struct FheUint64 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint64 trailingZeros() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint64_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_uint64(struct FheUint64 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheUint64 random(long seedLow, long seedHigh) {
    FheUint64 result = new FheUint64();
    execute(() -> generate_oblivious_pseudo_random_fhe_uint64(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_uint64(struct FheUint64 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheUint64 random(long seedLow, long seedHigh, long bitsCount) {
    FheUint64 result = new FheUint64();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint64(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int10(const struct FheUint64 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint64_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int1024(const struct FheUint64 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint64_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int12(const struct FheUint64 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint64_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int128(const struct FheUint64 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint64_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int14(const struct FheUint64 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint64_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int16(const struct FheUint64 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint64_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int160(const struct FheUint64 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint64_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int2(const struct FheUint64 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint64_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int2048(const struct FheUint64 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint64_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int256(const struct FheUint64 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint64_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int32(const struct FheUint64 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint64_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int4(const struct FheUint64 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint64_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int512(const struct FheUint64 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint64_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int6(const struct FheUint64 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint64_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int64(const struct FheUint64 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint64_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_int8(const struct FheUint64 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint64_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint10(const struct FheUint64 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint64_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint1024(const struct FheUint64 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint64_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint12(const struct FheUint64 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint64_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint128(const struct FheUint64 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint64_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint14(const struct FheUint64 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint64_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint16(const struct FheUint64 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint64_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint160(const struct FheUint64 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint64_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint2(const struct FheUint64 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint64_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint2048(const struct FheUint64 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint64_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint256(const struct FheUint64 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint64_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint32(const struct FheUint64 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint64_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint4(const struct FheUint64 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint64_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint512(const struct FheUint64 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint64_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint64_cast_into_fhe_uint6(const struct FheUint64 *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint64_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint64_cast_into_fhe_uint64(const struct FheUint64 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint64_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint64_cast_into_fhe_uint8(const struct FheUint64 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint64_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
