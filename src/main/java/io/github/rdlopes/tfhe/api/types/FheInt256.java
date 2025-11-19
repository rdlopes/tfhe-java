package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I256;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt256 extends NativePointer implements FheInteger<I256, FheInt256, CompressedFheInt256> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt256.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_int256_destroy(struct FheInt256 *ptr);
  ///```
  FheInt256() {
    logger.trace("init");
    super(TfheHeader::fhe_int256_destroy);
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
  /// int fhe_int256_safe_serialize(const struct FheInt256 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int256_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_int256_bitand(const struct FheInt256 *lhs,
  ///                       const struct FheInt256 *rhs,
  ///                       struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 bitAnd(FheInt256 other){
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_bitand(const struct FheInt256 *lhs,
  ///                              struct I256 rhs,
  ///                              struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 bitAndScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_bitand_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
///```
@Override
public void bitAndAssign(FheInt256 other){
    execute(() -> fhe_int256_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int256_scalar_bitand_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void bitAndScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_bitor(const struct FheInt256 *lhs,
  ///                      const struct FheInt256 *rhs,
  ///                      struct FheInt256 **result);
///```
@Override
public FheInt256 bitOr(FheInt256 other){
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int256_scalar_bitor(const struct FheInt256 *lhs,
  ///                             struct I256 rhs,
  ///                             struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 bitOrScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_bitor_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
///```
@Override
public void bitOrAssign(FheInt256 other){
    execute(() -> fhe_int256_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int256_scalar_bitor_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void bitOrScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_bitxor(const struct FheInt256 *lhs,
  ///                       const struct FheInt256 *rhs,
///                       struct FheInt256 **result);
///```
@Override
public FheInt256 bitXor(FheInt256 other){
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int256_scalar_bitxor(const struct FheInt256 *lhs,
  ///                              struct I256 rhs,
  ///                              struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 bitXorScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_bitxor_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheInt256 other) {
    execute(() -> fhe_int256_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_bitxor_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void bitXorScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /// ```c
/// int fhe_int256_not(const struct FheInt256 *input, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 bitNot() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheInt256 *then_ct,
  ///                             const struct FheInt256 *else_ct,
  ///                             struct FheInt256 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheInt256 ifThenElse(FheBool condition, FheInt256 thenValue, FheInt256 elseValue) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_int256_eq(const struct FheInt256 *lhs,
///                   const struct FheInt256 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheInt256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_eq(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

}
  
/// ```c
/// int fhe_int256_ne(const struct FheInt256 *lhs,
///                   const struct FheInt256 *rhs,
///                   struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheInt256 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_int256_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int256_scalar_ne(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(I256 other){
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
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
  /// int fhe_int256_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
///                                            uint64_t serialized_size_limit,
///                                            const struct ServerKey *server_key,
///                                            struct FheInt256 **result);
///```
public static FheInt256 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheInt256 deserialized = new FheInt256();
  execute(() -> fhe_int256_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}
/// ```c
/// int fhe_int256_try_encrypt_with_client_key_i256(struct I256 value,
///                                                 const struct ClientKey *client_key,
///                                                 struct FheInt256 **result);
///```
public static FheInt256 encrypt(I256 clearValue, ClientKey clientKey){
    FheInt256 encrypted = new FheInt256();
  execute(() -> fhe_int256_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
  return encrypted;

}
  
/// ```c
/// int fhe_int256_try_encrypt_with_public_key_i256(struct I256 value,
///                                                 const struct PublicKey *public_key,
///                                                 struct FheInt256 **result);
///```
public static FheInt256 encrypt(I256 clearValue, PublicKey publicKey){
    FheInt256 encrypted = new FheInt256();
      execute(() -> fhe_int256_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_int256_try_encrypt_trivial_i256(struct I256 value, struct FheInt256 **result);
  ///```
  public static FheInt256 encrypt(I256 clearValue){
    FheInt256 encrypted = new FheInt256();
      execute(() -> fhe_int256_try_encrypt_trivial_i256(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int256_clone(const struct FheInt256 *sself, struct FheInt256 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt256 clone() {
    FheInt256 cloned = new FheInt256();
    execute(() -> fhe_int256_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_int256_decrypt(const struct FheInt256 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        struct I256 *result);
  ///```
  @Override
  public I256 decrypt(ClientKey clientKey) {
    I256 decrypted = new I256();
    execute(() -> fhe_int256_decrypt(getValue(), clientKey.getValue(), decrypted.getAddress()));
      return decrypted;

}
  

/// ```c
/// int fhe_int256_compress(const struct FheInt256 *sself, struct CompressedFheInt256 **result);
///```
@Override
public CompressedFheInt256 compress() {
  CompressedFheInt256 compressed = new CompressedFheInt256();
  execute(() -> fhe_int256_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_int256_add(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 add(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_overflowing_add(const struct FheInt256 *lhs,
  ///                                const struct FheInt256 *rhs,
  ///                                struct FheInt256 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I256, FheInt256, CompressedFheInt256> addWithOverflow(FheInt256 other) {
    FheInt256 result = new FheInt256();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int256_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int256_scalar_add(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 addScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_add_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void addAssign(FheInt256 other) {
    execute(() -> fhe_int256_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_add_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void addScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_add_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_sub(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 subtract(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_overflowing_sub(const struct FheInt256 *lhs,
  ///                                const struct FheInt256 *rhs,
  ///                                struct FheInt256 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I256, FheInt256, CompressedFheInt256> subtractWithOverflow(FheInt256 other){
    FheInt256 result = new FheInt256();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int256_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int256_scalar_sub(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 subtractScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_sub_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void subtractAssign(FheInt256 other) {
    execute(() -> fhe_int256_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_sub_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void subtractScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_mul(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 multiply(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_overflowing_mul(const struct FheInt256 *lhs,
  ///                                const struct FheInt256 *rhs,
  ///                                struct FheInt256 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I256, FheInt256, CompressedFheInt256> multiplyWithOverflow(FheInt256 other) {
    FheInt256 result = new FheInt256();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int256_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int256_scalar_mul(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 multiplyScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_mul_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheInt256 other) {
    execute(() -> fhe_int256_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_mul_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void multiplyScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_div(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 divide(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_div(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 divideScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_div_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void divideAssign(FheInt256 other) {
    execute(() -> fhe_int256_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_div_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void divideScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_div_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_rem(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 remainder(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_rem(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 remainderScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_rem_assign(struct FheInt256 *lhs, const struct FheInt256 *rhs);
  ///```
  @Override
  public void remainderAssign(FheInt256 other) {
    execute(() -> fhe_int256_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_rem_assign(struct FheInt256 *lhs, struct I256 rhs);
  ///```
  @Override
  public void remainderScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_div_rem(const struct FheInt256 *lhs,
  ///                        const struct FheInt256 *rhs,
  ///                        struct FheInt256 **q_result,
  ///                        struct FheInt256 **r_result);
  ///```
  @Override
  public DividerAndRemainder<I256, FheInt256, CompressedFheInt256> divideWithRemainder(FheInt256 other) {
    FheInt256 divider = new FheInt256();
    FheInt256 remainder = new FheInt256();
    execute(() -> fhe_int256_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int256_scalar_div_rem(const struct FheInt256 *lhs,
  ///                               struct I256 rhs,
  ///                               struct FheInt256 **q_result,
  ///                               struct FheInt256 **r_result);
  ///```
  @Override
  public DividerAndRemainder<I256, FheInt256, CompressedFheInt256> divideWithRemainderScalar(I256 other) {
    FheInt256 divider = new FheInt256();
    FheInt256 remainder = new FheInt256();
    execute(() -> fhe_int256_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int256_neg(const struct FheInt256 *input, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 negate() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_int256_ilog2(const struct FheInt256 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt256 ilog2() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_int256_checked_ilog2(const struct FheInt256 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<I256, FheInt256, CompressedFheInt256> ilog2WithCheck() {
    FheInt256 result = new FheInt256();
    FheBool check = new FheBool();
    execute(() -> fhe_int256_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_int256_lt(const struct FheInt256 *lhs,
  ///                   const struct FheInt256 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheInt256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_lt(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_le(const struct FheInt256 *lhs,
  ///                   const struct FheInt256 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheInt256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_le(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_gt(const struct FheInt256 *lhs,
  ///                   const struct FheInt256 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheInt256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_gt(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_ge(const struct FheInt256 *lhs,
  ///                   const struct FheInt256 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheInt256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_ge(const struct FheInt256 *lhs, struct I256 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int256_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_min(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 min(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_min(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 minScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_max(const struct FheInt256 *lhs,
  ///                    const struct FheInt256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 max(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_max(const struct FheInt256 *lhs, struct I256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 maxScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_shl(const struct FheInt256 *lhs,
  ///                    const struct FheUint256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 shiftLeft(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_shl(const struct FheInt256 *lhs, struct U256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 shiftLeftScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_shl_assign(struct FheInt256 *lhs, const struct FheUint256 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheInt256 other) {
    execute(() -> fhe_int256_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_shl_assign(struct FheInt256 *lhs, struct U256 rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_shr(const struct FheInt256 *lhs,
  ///                    const struct FheUint256 *rhs,
  ///                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 shiftRight(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_shr(const struct FheInt256 *lhs, struct U256 rhs, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 shiftRightScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_shr_assign(struct FheInt256 *lhs, const struct FheUint256 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheInt256 other) {
    execute(() -> fhe_int256_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_shr_assign(struct FheInt256 *lhs, struct U256 rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_rotate_left(const struct FheInt256 *lhs,
  ///                            const struct FheUint256 *rhs,
  ///                            struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 rotateLeft(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_rotate_left(const struct FheInt256 *lhs,
  ///                                   struct U256 rhs,
  ///                                   struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 rotateLeftScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_rotate_left_assign(struct FheInt256 *lhs, const struct FheUint256 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheInt256 other) {
    execute(() -> fhe_int256_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_rotate_left_assign(struct FheInt256 *lhs, struct U256 rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int256_rotate_right(const struct FheInt256 *lhs,
  ///                             const struct FheUint256 *rhs,
  ///                             struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 rotateRight(FheInt256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_scalar_rotate_right(const struct FheInt256 *lhs,
  ///                                    struct U256 rhs,
  ///                                    struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 rotateRightScalar(I256 other) {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_rotate_right_assign(struct FheInt256 *lhs, const struct FheUint256 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheInt256 other) {
    execute(() -> fhe_int256_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int256_scalar_rotate_right_assign(struct FheInt256 *lhs, struct U256 rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(I256 other) {
    execute(() -> fhe_int256_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_int256_leading_ones(const struct FheInt256 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt256 leadingOnes() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_int256_leading_zeros(const struct FheInt256 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt256 leadingZeros() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_int256_trailing_ones(const struct FheInt256 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt256 trailingOnes() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_int256_trailing_zeros(const struct FheInt256 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt256 trailingZeros() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_int256(struct FheInt256 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheInt256 random(long seedLow, long seedHigh) {
    FheInt256 result = new FheInt256();
    execute(() -> generate_oblivious_pseudo_random_fhe_int256(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_int256(struct FheInt256 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheInt256 random(long seedLow, long seedHigh, long bitsCount) {
    FheInt256 result = new FheInt256();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_int256(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the absolute value.
  ///  *
  ///  * (if x < 0 { -x } else { x })
  ///  */
  /// int fhe_int256_abs(const struct FheInt256 *input, struct FheInt256 **result);
  ///```
  @Override
  public FheInt256 abs() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_abs(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int10(const struct FheInt256 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int256_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int1024(const struct FheInt256 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int256_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int12(const struct FheInt256 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int256_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int128(const struct FheInt256 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int256_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int14(const struct FheInt256 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int256_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int16(const struct FheInt256 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int256_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int160(const struct FheInt256 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int256_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int2(const struct FheInt256 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int256_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int2048(const struct FheInt256 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int256_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int256(const struct FheInt256 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int32(const struct FheInt256 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int256_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int4(const struct FheInt256 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int256_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int512(const struct FheInt256 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int256_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int6(const struct FheInt256 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int256_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int64(const struct FheInt256 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int256_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_int8(const struct FheInt256 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int256_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint10(const struct FheInt256 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int256_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint1024(const struct FheInt256 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int256_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint12(const struct FheInt256 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int256_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint128(const struct FheInt256 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_int256_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint14(const struct FheInt256 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int256_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint16(const struct FheInt256 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int256_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint160(const struct FheInt256 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int256_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint2(const struct FheInt256 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int256_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint2048(const struct FheInt256 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int256_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint256(const struct FheInt256 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int256_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint32(const struct FheInt256 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int256_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint4(const struct FheInt256 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int256_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint512(const struct FheInt256 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int256_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int256_cast_into_fhe_uint6(const struct FheInt256 *sself, struct FheUint6 **result);
///```
public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_int256_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int256_cast_into_fhe_uint64(const struct FheInt256 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_int256_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int256_cast_into_fhe_uint8(const struct FheInt256 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_int256_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
