package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I512;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt512 extends NativePointer implements FheInteger<I512, FheInt512, CompressedFheInt512> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt512.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_int512_destroy(struct FheInt512 *ptr);
  ///```
  FheInt512() {
    logger.trace("init");
    super(TfheHeader::fhe_int512_destroy);
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
  /// int fhe_int512_safe_serialize(const struct FheInt512 *sself,
  ///                               struct DynamicBuffer *result,
  ///                               uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_int512_bitand(const struct FheInt512 *lhs,
  ///                       const struct FheInt512 *rhs,
  ///                       struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 bitAnd(FheInt512 other){
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_bitand(const struct FheInt512 *lhs,
  ///                              struct I512 rhs,
  ///                              struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 bitAndScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_bitand_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
///```
@Override
public void bitAndAssign(FheInt512 other){
    execute(() -> fhe_int512_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int512_scalar_bitand_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void bitAndScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_bitor(const struct FheInt512 *lhs,
  ///                      const struct FheInt512 *rhs,
  ///                      struct FheInt512 **result);
///```
@Override
public FheInt512 bitOr(FheInt512 other){
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int512_scalar_bitor(const struct FheInt512 *lhs,
  ///                             struct I512 rhs,
  ///                             struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 bitOrScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_bitor_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
///```
@Override
public void bitOrAssign(FheInt512 other){
    execute(() -> fhe_int512_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_int512_scalar_bitor_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void bitOrScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_bitxor(const struct FheInt512 *lhs,
  ///                       const struct FheInt512 *rhs,
///                       struct FheInt512 **result);
///```
@Override
public FheInt512 bitXor(FheInt512 other){
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int512_scalar_bitxor(const struct FheInt512 *lhs,
  ///                              struct I512 rhs,
  ///                              struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 bitXorScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_bitxor_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheInt512 other) {
    execute(() -> fhe_int512_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_bitxor_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void bitXorScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /// ```c
/// int fhe_int512_not(const struct FheInt512 *input, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 bitNot() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_if_then_else(const struct FheBool *condition_ct,
  ///                             const struct FheInt512 *then_ct,
  ///                             const struct FheInt512 *else_ct,
  ///                             struct FheInt512 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheInt512 ifThenElse(FheBool condition, FheInt512 thenValue, FheInt512 elseValue) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_int512_eq(const struct FheInt512 *lhs,
///                   const struct FheInt512 *rhs,
///                   struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheInt512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_eq(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(I512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

}
  
/// ```c
/// int fhe_int512_ne(const struct FheInt512 *lhs,
///                   const struct FheInt512 *rhs,
///                   struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheInt512 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_int512_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_int512_scalar_ne(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(I512 other){
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
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
  /// int fhe_int512_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
///                                            uint64_t serialized_size_limit,
///                                            const struct ServerKey *server_key,
///                                            struct FheInt512 **result);
///```
public static FheInt512 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheInt512 deserialized = new FheInt512();
  execute(() -> fhe_int512_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}
/// ```c
/// int fhe_int512_try_encrypt_with_client_key_i512(struct I512 value,
///                                                 const struct ClientKey *client_key,
///                                                 struct FheInt512 **result);
///```
public static FheInt512 encrypt(I512 clearValue, ClientKey clientKey){
    FheInt512 encrypted = new FheInt512();
  execute(() -> fhe_int512_try_encrypt_with_client_key_i512(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
  return encrypted;

}
  
/// ```c
/// int fhe_int512_try_encrypt_with_public_key_i512(struct I512 value,
///                                                 const struct PublicKey *public_key,
///                                                 struct FheInt512 **result);
///```
public static FheInt512 encrypt(I512 clearValue, PublicKey publicKey){
    FheInt512 encrypted = new FheInt512();
      execute(() -> fhe_int512_try_encrypt_with_public_key_i512(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_int512_try_encrypt_trivial_i512(struct I512 value, struct FheInt512 **result);
  ///```
  public static FheInt512 encrypt(I512 clearValue){
    FheInt512 encrypted = new FheInt512();
      execute(() -> fhe_int512_try_encrypt_trivial_i512(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_int512_clone(const struct FheInt512 *sself, struct FheInt512 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt512 clone() {
    FheInt512 cloned = new FheInt512();
    execute(() -> fhe_int512_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_int512_decrypt(const struct FheInt512 *encrypted_value,
  ///                        const struct ClientKey *client_key,
  ///                        struct I512 *result);
  ///```
  @Override
  public I512 decrypt(ClientKey clientKey) {
    I512 decrypted = new I512();
    execute(() -> fhe_int512_decrypt(getValue(), clientKey.getValue(), decrypted.getAddress()));
      return decrypted;

}
  

/// ```c
/// int fhe_int512_compress(const struct FheInt512 *sself, struct CompressedFheInt512 **result);
///```
@Override
public CompressedFheInt512 compress() {
  CompressedFheInt512 compressed = new CompressedFheInt512();
  execute(() -> fhe_int512_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_int512_add(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 add(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_overflowing_add(const struct FheInt512 *lhs,
  ///                                const struct FheInt512 *rhs,
  ///                                struct FheInt512 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I512, FheInt512, CompressedFheInt512> addWithOverflow(FheInt512 other) {
    FheInt512 result = new FheInt512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int512_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int512_scalar_add(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 addScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_add_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void addAssign(FheInt512 other) {
    execute(() -> fhe_int512_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_add_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void addScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_add_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_sub(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 subtract(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_overflowing_sub(const struct FheInt512 *lhs,
  ///                                const struct FheInt512 *rhs,
  ///                                struct FheInt512 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I512, FheInt512, CompressedFheInt512> subtractWithOverflow(FheInt512 other){
    FheInt512 result = new FheInt512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int512_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int512_scalar_sub(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 subtractScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_sub_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void subtractAssign(FheInt512 other) {
    execute(() -> fhe_int512_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_sub_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void subtractScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_mul(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 multiply(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_overflowing_mul(const struct FheInt512 *lhs,
  ///                                const struct FheInt512 *rhs,
  ///                                struct FheInt512 **out_result,
  ///                                struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<I512, FheInt512, CompressedFheInt512> multiplyWithOverflow(FheInt512 other) {
    FheInt512 result = new FheInt512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int512_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_int512_scalar_mul(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 multiplyScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_mul_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheInt512 other) {
    execute(() -> fhe_int512_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_mul_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void multiplyScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_div(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 divide(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_div(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 divideScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_div_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void divideAssign(FheInt512 other) {
    execute(() -> fhe_int512_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_div_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void divideScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_div_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_rem(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 remainder(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_rem(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 remainderScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_rem_assign(struct FheInt512 *lhs, const struct FheInt512 *rhs);
  ///```
  @Override
  public void remainderAssign(FheInt512 other) {
    execute(() -> fhe_int512_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_rem_assign(struct FheInt512 *lhs, struct I512 rhs);
  ///```
  @Override
  public void remainderScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_div_rem(const struct FheInt512 *lhs,
  ///                        const struct FheInt512 *rhs,
  ///                        struct FheInt512 **q_result,
  ///                        struct FheInt512 **r_result);
  ///```
  @Override
  public DividerAndRemainder<I512, FheInt512, CompressedFheInt512> divideWithRemainder(FheInt512 other) {
    FheInt512 divider = new FheInt512();
    FheInt512 remainder = new FheInt512();
    execute(() -> fhe_int512_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int512_scalar_div_rem(const struct FheInt512 *lhs,
  ///                               struct I512 rhs,
  ///                               struct FheInt512 **q_result,
  ///                               struct FheInt512 **r_result);
  ///```
  @Override
  public DividerAndRemainder<I512, FheInt512, CompressedFheInt512> divideWithRemainderScalar(I512 other) {
    FheInt512 divider = new FheInt512();
    FheInt512 remainder = new FheInt512();
    execute(() -> fhe_int512_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_int512_neg(const struct FheInt512 *input, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 negate() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_int512_ilog2(const struct FheInt512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt512 ilog2() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_int512_checked_ilog2(const struct FheInt512 *input,
  ///                              struct FheUint32 **result_1,
  ///                              struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<I512, FheInt512, CompressedFheInt512> ilog2WithCheck() {
    FheInt512 result = new FheInt512();
    FheBool check = new FheBool();
    execute(() -> fhe_int512_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_int512_lt(const struct FheInt512 *lhs,
  ///                   const struct FheInt512 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheInt512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_lt(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(I512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_le(const struct FheInt512 *lhs,
  ///                   const struct FheInt512 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheInt512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_le(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(I512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_gt(const struct FheInt512 *lhs,
  ///                   const struct FheInt512 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheInt512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_gt(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(I512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_ge(const struct FheInt512 *lhs,
  ///                   const struct FheInt512 *rhs,
  ///                   struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheInt512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_ge(const struct FheInt512 *lhs, struct I512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(I512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int512_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_min(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 min(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_min(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 minScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_max(const struct FheInt512 *lhs,
  ///                    const struct FheInt512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 max(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_max(const struct FheInt512 *lhs, struct I512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 maxScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_shl(const struct FheInt512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 shiftLeft(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_shl(const struct FheInt512 *lhs, struct U512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 shiftLeftScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_shl_assign(struct FheInt512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheInt512 other) {
    execute(() -> fhe_int512_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_shl_assign(struct FheInt512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_shr(const struct FheInt512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 shiftRight(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_shr(const struct FheInt512 *lhs, struct U512 rhs, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 shiftRightScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_shr_assign(struct FheInt512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheInt512 other) {
    execute(() -> fhe_int512_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_shr_assign(struct FheInt512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_rotate_left(const struct FheInt512 *lhs,
  ///                            const struct FheUint512 *rhs,
  ///                            struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 rotateLeft(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_rotate_left(const struct FheInt512 *lhs,
  ///                                   struct U512 rhs,
  ///                                   struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 rotateLeftScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_rotate_left_assign(struct FheInt512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheInt512 other) {
    execute(() -> fhe_int512_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_rotate_left_assign(struct FheInt512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_int512_rotate_right(const struct FheInt512 *lhs,
  ///                             const struct FheUint512 *rhs,
  ///                             struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 rotateRight(FheInt512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_scalar_rotate_right(const struct FheInt512 *lhs,
  ///                                    struct U512 rhs,
  ///                                    struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 rotateRightScalar(I512 other) {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_rotate_right_assign(struct FheInt512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheInt512 other) {
    execute(() -> fhe_int512_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_int512_scalar_rotate_right_assign(struct FheInt512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(I512 other) {
    execute(() -> fhe_int512_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_int512_leading_ones(const struct FheInt512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt512 leadingOnes() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_int512_leading_zeros(const struct FheInt512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt512 leadingZeros() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_int512_trailing_ones(const struct FheInt512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt512 trailingOnes() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_int512_trailing_zeros(const struct FheInt512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheInt512 trailingZeros() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_int512(struct FheInt512 **out_result,
  ///                                                 uint64_t seed_low_bytes,
  ///                                                 uint64_t seed_high_bytes);
  ///```
  @Override
  public FheInt512 random(long seedLow, long seedHigh) {
    FheInt512 result = new FheInt512();
    execute(() -> generate_oblivious_pseudo_random_fhe_int512(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_bounded_fhe_int512(struct FheInt512 **out_result,
  ///                                                         uint64_t seed_low_bytes,
  ///                                                         uint64_t seed_high_bytes,
  ///                                                         uint64_t random_bits_count);
  ///```
  @Override
  public FheInt512 random(long seedLow, long seedHigh, long bitsCount) {
    FheInt512 result = new FheInt512();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_int512(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the absolute value.
  ///  *
  ///  * (if x < 0 { -x } else { x })
  ///  */
  /// int fhe_int512_abs(const struct FheInt512 *input, struct FheInt512 **result);
  ///```
  @Override
  public FheInt512 abs() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_abs(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int10(const struct FheInt512 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int512_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int1024(const struct FheInt512 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int512_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int12(const struct FheInt512 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int512_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int128(const struct FheInt512 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int512_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int14(const struct FheInt512 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int512_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int16(const struct FheInt512 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int512_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int160(const struct FheInt512 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int512_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int2(const struct FheInt512 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int512_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int2048(const struct FheInt512 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int512_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int256(const struct FheInt512 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int512_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int32(const struct FheInt512 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int512_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int4(const struct FheInt512 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int512_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int512(const struct FheInt512 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int6(const struct FheInt512 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int512_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int64(const struct FheInt512 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int512_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_int8(const struct FheInt512 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int512_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint10(const struct FheInt512 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int512_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint1024(const struct FheInt512 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int512_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint12(const struct FheInt512 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int512_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint128(const struct FheInt512 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_int512_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint14(const struct FheInt512 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int512_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint16(const struct FheInt512 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int512_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint160(const struct FheInt512 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int512_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint2(const struct FheInt512 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int512_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint2048(const struct FheInt512 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int512_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint256(const struct FheInt512 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int512_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint32(const struct FheInt512 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int512_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint4(const struct FheInt512 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int512_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint512(const struct FheInt512 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int512_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_int512_cast_into_fhe_uint6(const struct FheInt512 *sself, struct FheUint6 **result);
///```
public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_int512_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int512_cast_into_fhe_uint64(const struct FheInt512 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_int512_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_int512_cast_into_fhe_uint8(const struct FheInt512 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_int512_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
