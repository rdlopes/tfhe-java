package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U512;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint512 extends NativePointer implements FheUnsignedInteger<U512, FheUint512, CompressedFheUint512> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint512.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint512_destroy(struct FheUint512 *ptr);
  ///```
  FheUint512() {
    logger.trace("init");
    super(TfheHeader::fhe_uint512_destroy);
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
  /// int fhe_uint512_safe_serialize(const struct FheUint512 *sself,
  ///                                struct DynamicBuffer *result,
  ///                                uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint512_bitand(const struct FheUint512 *lhs,
  ///                        const struct FheUint512 *rhs,
  ///                        struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 bitAnd(FheUint512 other){
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_bitand(const struct FheUint512 *lhs,
  ///                               struct U512 rhs,
  ///                               struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 bitAndScalar(U512 other){
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_bitand_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
///```
@Override
public void bitAndAssign(FheUint512 other){
    execute(() -> fhe_uint512_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint512_scalar_bitand_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void bitAndScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_bitor(const struct FheUint512 *lhs,
  ///                       const struct FheUint512 *rhs,
  ///                       struct FheUint512 **result);
///```
@Override
public FheUint512 bitOr(FheUint512 other){
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint512_scalar_bitor(const struct FheUint512 *lhs,
  ///                              struct U512 rhs,
  ///                              struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 bitOrScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_bitor_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
///```
@Override
public void bitOrAssign(FheUint512 other){
    execute(() -> fhe_uint512_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint512_scalar_bitor_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void bitOrScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_bitxor(const struct FheUint512 *lhs,
  ///                        const struct FheUint512 *rhs,
///                        struct FheUint512 **result);
///```
@Override
public FheUint512 bitXor(FheUint512 other){
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint512_scalar_bitxor(const struct FheUint512 *lhs,
  ///                               struct U512 rhs,
  ///                               struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 bitXorScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_bitxor_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint512 other) {
    execute(() -> fhe_uint512_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_bitxor_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void bitXorScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /// ```c
/// int fhe_uint512_not(const struct FheUint512 *input, struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 bitNot() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_if_then_else(const struct FheBool *condition_ct,
  ///                              const struct FheUint512 *then_ct,
  ///                              const struct FheUint512 *else_ct,
  ///                              struct FheUint512 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint512 ifThenElse(FheBool condition, FheUint512 thenValue, FheUint512 elseValue) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint512_eq(const struct FheUint512 *lhs,
///                    const struct FheUint512 *rhs,
///                    struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_eq(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(U512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

}
  
/// ```c
/// int fhe_uint512_ne(const struct FheUint512 *lhs,
///                    const struct FheUint512 *rhs,
///                    struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheUint512 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint512_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint512_scalar_ne(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool notEqualToScalar(U512 other){
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
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
  /// int fhe_uint512_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                             uint64_t serialized_size_limit,
  ///                                             const struct ServerKey *server_key,
///                                             struct FheUint512 **result);
///```
public static FheUint512 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheUint512 deserialized = new FheUint512();
  execute(() -> fhe_uint512_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}
/// ```c
/// int fhe_uint512_try_encrypt_with_client_key_u512(struct U512 value,
///                                                  const struct ClientKey *client_key,
///                                                  struct FheUint512 **result);
///```
public static FheUint512 encrypt(U512 clearValue, ClientKey clientKey) {
  FheUint512 encrypted = new FheUint512();
  execute(() -> fhe_uint512_try_encrypt_with_client_key_u512(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
  return encrypted;

}
  
/// ```c
/// int fhe_uint512_try_encrypt_with_public_key_u512(struct U512 value,
///                                                  const struct PublicKey *public_key,
///                                                  struct FheUint512 **result);
///```
public static FheUint512 encrypt(U512 clearValue, PublicKey publicKey){
    FheUint512 encrypted = new FheUint512();
      execute(() -> fhe_uint512_try_encrypt_with_public_key_u512(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint512_try_encrypt_trivial_u512(struct U512 value, struct FheUint512 **result);
  ///```
  public static FheUint512 encrypt(U512 clearValue){
    FheUint512 encrypted = new FheUint512();
      execute(() -> fhe_uint512_try_encrypt_trivial_u512(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint512_clone(const struct FheUint512 *sself, struct FheUint512 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint512 clone() {
    FheUint512 cloned = new FheUint512();
    execute(() -> fhe_uint512_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint512_decrypt(const struct FheUint512 *encrypted_value,
  ///                         const struct ClientKey *client_key,
  ///                         struct U512 *result);
  ///```
  @Override
  public U512 decrypt(ClientKey clientKey) {
    U512 decrypted = new U512();
    execute(() -> fhe_uint512_decrypt(getValue(), clientKey.getValue(), decrypted.getAddress()));
      return decrypted;

}
  

/// ```c
/// int fhe_uint512_compress(const struct FheUint512 *sself, struct CompressedFheUint512 **result);
///```
@Override
public CompressedFheUint512 compress() {
  CompressedFheUint512 compressed = new CompressedFheUint512();
  execute(() -> fhe_uint512_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint512_add(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 add(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_overflowing_add(const struct FheUint512 *lhs,
  ///                                 const struct FheUint512 *rhs,
  ///                                 struct FheUint512 **out_result,
  ///                                 struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U512, FheUint512, CompressedFheUint512> addWithOverflow(FheUint512 other) {
    FheUint512 result = new FheUint512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint512_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint512_scalar_add(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 addScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_add_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void addAssign(FheUint512 other) {
    execute(() -> fhe_uint512_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_add_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void addScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_add_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_sub(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 subtract(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_overflowing_sub(const struct FheUint512 *lhs,
  ///                                 const struct FheUint512 *rhs,
  ///                                 struct FheUint512 **out_result,
  ///                                 struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U512, FheUint512, CompressedFheUint512> subtractWithOverflow(FheUint512 other) {
    FheUint512 result = new FheUint512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint512_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint512_scalar_sub(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 subtractScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_sub_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint512 other) {
    execute(() -> fhe_uint512_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_sub_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void subtractScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_mul(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 multiply(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_overflowing_mul(const struct FheUint512 *lhs,
  ///                                 const struct FheUint512 *rhs,
  ///                                 struct FheUint512 **out_result,
  ///                                 struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U512, FheUint512, CompressedFheUint512> multiplyWithOverflow(FheUint512 other) {
    FheUint512 result = new FheUint512();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint512_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint512_scalar_mul(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 multiplyScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_mul_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint512 other) {
    execute(() -> fhe_uint512_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_mul_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void multiplyScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_div(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 divide(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_div(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 divideScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_div_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint512 other) {
    execute(() -> fhe_uint512_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_div_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void divideScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_div_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_rem(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 remainder(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_rem(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 remainderScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_rem_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint512 other) {
    execute(() -> fhe_uint512_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_rem_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void remainderScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_div_rem(const struct FheUint512 *lhs,
  ///                         const struct FheUint512 *rhs,
  ///                         struct FheUint512 **q_result,
  ///                         struct FheUint512 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U512, FheUint512, CompressedFheUint512> divideWithRemainder(FheUint512 other) {
    FheUint512 divider = new FheUint512();
    FheUint512 remainder = new FheUint512();
    execute(() -> fhe_uint512_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint512_scalar_div_rem(const struct FheUint512 *lhs,
  ///                                struct U512 rhs,
  ///                                struct FheUint512 **q_result,
  ///                                struct FheUint512 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U512, FheUint512, CompressedFheUint512> divideWithRemainderScalar(U512 other) {
    FheUint512 divider = new FheUint512();
    FheUint512 remainder = new FheUint512();
    execute(() -> fhe_uint512_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint512_neg(const struct FheUint512 *input, struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 negate() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint512_ilog2(const struct FheUint512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint512 ilog2() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint512_checked_ilog2(const struct FheUint512 *input,
  ///                               struct FheUint32 **result_1,
  ///                               struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<U512, FheUint512, CompressedFheUint512> ilog2WithCheck() {
    FheUint512 result = new FheUint512();
    FheBool check = new FheBool();
    execute(() -> fhe_uint512_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint512_lt(const struct FheUint512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_lt(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(U512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_le(const struct FheUint512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_le(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(U512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_gt(const struct FheUint512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_gt(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(U512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_ge(const struct FheUint512 *lhs,
  ///                    const struct FheUint512 *rhs,
  ///                    struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_ge(const struct FheUint512 *lhs, struct U512 rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(U512 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint512_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_min(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 min(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_min(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 minScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_max(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 max(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_max(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 maxScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_shl(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 shiftLeft(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_shl(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 shiftLeftScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_shl_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint512 other) {
    execute(() -> fhe_uint512_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_shl_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_shr(const struct FheUint512 *lhs,
  ///                     const struct FheUint512 *rhs,
  ///                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 shiftRight(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_shr(const struct FheUint512 *lhs,
  ///                            struct U512 rhs,
  ///                            struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 shiftRightScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_shr_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint512 other) {
    execute(() -> fhe_uint512_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_shr_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_rotate_left(const struct FheUint512 *lhs,
  ///                             const struct FheUint512 *rhs,
  ///                             struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 rotateLeft(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_rotate_left(const struct FheUint512 *lhs,
  ///                                    struct U512 rhs,
  ///                                    struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 rotateLeftScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_rotate_left_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint512 other) {
    execute(() -> fhe_uint512_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_rotate_left_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint512_rotate_right(const struct FheUint512 *lhs,
  ///                              const struct FheUint512 *rhs,
  ///                              struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 rotateRight(FheUint512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_scalar_rotate_right(const struct FheUint512 *lhs,
  ///                                     struct U512 rhs,
  ///                                     struct FheUint512 **result);
  ///```
  @Override
  public FheUint512 rotateRightScalar(U512 other) {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint512_rotate_right_assign(struct FheUint512 *lhs, const struct FheUint512 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint512 other) {
    execute(() -> fhe_uint512_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint512_scalar_rotate_right_assign(struct FheUint512 *lhs, struct U512 rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(U512 other) {
    execute(() -> fhe_uint512_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint512_leading_ones(const struct FheUint512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint512 leadingOnes() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint512_leading_zeros(const struct FheUint512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint512 leadingZeros() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint512_trailing_ones(const struct FheUint512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint512 trailingOnes() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint512_trailing_zeros(const struct FheUint512 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint512 trailingZeros() {
    FheUint512 result = new FheUint512();
      execute(() -> fhe_uint512_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int generate_oblivious_pseudo_random_fhe_uint512(struct FheUint512 **out_result,
  ///                                                  uint64_t seed_low_bytes,
  ///                                                  uint64_t seed_high_bytes);
  ///```
  @Override
  public FheUint512 random(long seedLow, long seedHigh) {
    FheUint512 result = new FheUint512();
    execute(() -> generate_oblivious_pseudo_random_fhe_uint512(result.getAddress(), seedLow, seedHigh));
    return result;

  }

  /// ```c
/// int generate_oblivious_pseudo_random_bounded_fhe_uint512(struct FheUint512 **out_result,
  ///                                                          uint64_t seed_low_bytes,
  ///                                                          uint64_t seed_high_bytes,
  ///                                                          uint64_t random_bits_count);
  ///```
  @Override
  public FheUint512 random(long seedLow, long seedHigh, long bitsCount) {
    FheUint512 result = new FheUint512();
    execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint512(result.getAddress(), seedLow, seedHigh, bitsCount));
    return result;

  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int10(const struct FheUint512 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint512_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int1024(const struct FheUint512 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint512_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int12(const struct FheUint512 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint512_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int128(const struct FheUint512 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint512_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int14(const struct FheUint512 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint512_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int16(const struct FheUint512 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint512_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int160(const struct FheUint512 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint512_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int2(const struct FheUint512 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint512_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int2048(const struct FheUint512 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint512_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int256(const struct FheUint512 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint512_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int32(const struct FheUint512 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint512_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int4(const struct FheUint512 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint512_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int512(const struct FheUint512 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint512_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int6(const struct FheUint512 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint512_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int64(const struct FheUint512 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint512_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_int8(const struct FheUint512 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint512_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint10(const struct FheUint512 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint512_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint1024(const struct FheUint512 *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint512_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint12(const struct FheUint512 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint512_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint128(const struct FheUint512 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint512_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint14(const struct FheUint512 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint512_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint16(const struct FheUint512 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint512_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint160(const struct FheUint512 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint512_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint2(const struct FheUint512 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint512_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint2048(const struct FheUint512 *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint512_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint256(const struct FheUint512 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint512_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint32(const struct FheUint512 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint512_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint4(const struct FheUint512 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint512_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint512(const struct FheUint512 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint512_cast_into_fhe_uint6(const struct FheUint512 *sself, struct FheUint6 **result);
///```
public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint512_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint512_cast_into_fhe_uint64(const struct FheUint512 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint512_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint512_cast_into_fhe_uint8(const struct FheUint512 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint512_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
