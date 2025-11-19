package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U2048;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint2048 extends NativePointer implements FheUnsignedInteger<U2048, FheUint2048, CompressedFheUint2048> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint2048.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint2048_destroy(struct FheUint2048 *ptr);
  ///```
  FheUint2048() {
    logger.trace("init");
    super(TfheHeader::fhe_uint2048_destroy);
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
  /// int fhe_uint2048_safe_serialize(const struct FheUint2048 *sself,
  ///                                 struct DynamicBuffer *result,
  ///                                 uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint2048_bitand(const struct FheUint2048 *lhs,
  ///                         const struct FheUint2048 *rhs,
  ///                         struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 bitAnd(FheUint2048 other){
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_bitand(const struct FheUint2048 *lhs,
  ///                                struct U2048 rhs,
  ///                                struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 bitAndScalar(U2048 other){
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_bitand_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
///```
@Override
public void bitAndAssign(FheUint2048 other){
    execute(() -> fhe_uint2048_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint2048_scalar_bitand_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void bitAndScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_bitor(const struct FheUint2048 *lhs,
  ///                        const struct FheUint2048 *rhs,
  ///                        struct FheUint2048 **result);
///```
@Override
public FheUint2048 bitOr(FheUint2048 other){
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint2048_scalar_bitor(const struct FheUint2048 *lhs,
  ///                               struct U2048 rhs,
  ///                               struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 bitOrScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_bitor_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
///```
@Override
public void bitOrAssign(FheUint2048 other){
    execute(() -> fhe_uint2048_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint2048_scalar_bitor_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void bitOrScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_bitxor(const struct FheUint2048 *lhs,
  ///                         const struct FheUint2048 *rhs,
///                         struct FheUint2048 **result);
///```
@Override
public FheUint2048 bitXor(FheUint2048 other){
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint2048_scalar_bitxor(const struct FheUint2048 *lhs,
  ///                                struct U2048 rhs,
  ///                                struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 bitXorScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_bitxor_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_bitxor_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void bitXorScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /// ```c
/// int fhe_uint2048_not(const struct FheUint2048 *input, struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 bitNot() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_if_then_else(const struct FheBool *condition_ct,
  ///                               const struct FheUint2048 *then_ct,
  ///                               const struct FheUint2048 *else_ct,
  ///                               struct FheUint2048 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint2048 ifThenElse(FheBool condition, FheUint2048 thenValue, FheUint2048 elseValue) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint2048_eq(const struct FheUint2048 *lhs,
///                     const struct FheUint2048 *rhs,
///                     struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint2048_scalar_eq(const struct FheUint2048 *lhs,
  ///                            struct U2048 rhs,
///                            struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(U2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

}
  
/// ```c
/// int fhe_uint2048_ne(const struct FheUint2048 *lhs,
///                     const struct FheUint2048 *rhs,
///                     struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheUint2048 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint2048_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}
  
/// ```c
/// int fhe_uint2048_scalar_ne(const struct FheUint2048 *lhs,
///                            struct U2048 rhs,
///                            struct FheBool **result);
///```
@Override
public FheBool notEqualToScalar(U2048 other){
    FheBool result = new FheBool();
  execute(() -> fhe_uint2048_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
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
  /// int fhe_uint2048_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
///                                              uint64_t serialized_size_limit,
///                                              const struct ServerKey *server_key,
///                                              struct FheUint2048 **result);
  ///```
  public static FheUint2048 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    FheUint2048 deserialized = new FheUint2048();
    execute(() -> fhe_uint2048_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

}
/// ```c
/// int fhe_uint2048_try_encrypt_with_client_key_u2048(struct U2048 value,
///                                                    const struct ClientKey *client_key,
///                                                    struct FheUint2048 **result);
///```
public static FheUint2048 encrypt(U2048 clearValue, ClientKey clientKey){
    FheUint2048 encrypted = new FheUint2048();
  execute(() -> fhe_uint2048_try_encrypt_with_client_key_u2048(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
  return encrypted;

}
  
/// ```c
/// int fhe_uint2048_try_encrypt_with_public_key_u2048(struct U2048 value,
///                                                    const struct PublicKey *public_key,
///                                                    struct FheUint2048 **result);
///```
public static FheUint2048 encrypt(U2048 clearValue, PublicKey publicKey){
    FheUint2048 encrypted = new FheUint2048();
      execute(() -> fhe_uint2048_try_encrypt_with_public_key_u2048(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint2048_try_encrypt_trivial_u2048(struct U2048 value, struct FheUint2048 **result);
  ///```
  public static FheUint2048 encrypt(U2048 clearValue){
    FheUint2048 encrypted = new FheUint2048();
      execute(() -> fhe_uint2048_try_encrypt_trivial_u2048(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint2048_clone(const struct FheUint2048 *sself, struct FheUint2048 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint2048 clone() {
    FheUint2048 cloned = new FheUint2048();
    execute(() -> fhe_uint2048_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint2048_decrypt(const struct FheUint2048 *encrypted_value,
  ///                          const struct ClientKey *client_key,
  ///                          struct U2048 *result);
  ///```
  @Override
  public U2048 decrypt(ClientKey clientKey) {
    U2048 decrypted = new U2048();
    execute(() -> fhe_uint2048_decrypt(getValue(), clientKey.getValue(), decrypted.getAddress()));
      return decrypted;

}
  

/// ```c
/// int fhe_uint2048_compress(const struct FheUint2048 *sself, struct CompressedFheUint2048 **result);
///```
@Override
public CompressedFheUint2048 compress() {
  CompressedFheUint2048 compressed = new CompressedFheUint2048();
  execute(() -> fhe_uint2048_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint2048_add(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 add(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_overflowing_add(const struct FheUint2048 *lhs,
  ///                                  const struct FheUint2048 *rhs,
  ///                                  struct FheUint2048 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U2048, FheUint2048, CompressedFheUint2048> addWithOverflow(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint2048_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint2048_scalar_add(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 addScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_add_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void addAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_add_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void addScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_add_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_sub(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 subtract(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_overflowing_sub(const struct FheUint2048 *lhs,
  ///                                  const struct FheUint2048 *rhs,
  ///                                  struct FheUint2048 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U2048, FheUint2048, CompressedFheUint2048> subtractWithOverflow(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint2048_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint2048_scalar_sub(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 subtractScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_sub_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_sub_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void subtractScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_mul(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 multiply(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_overflowing_mul(const struct FheUint2048 *lhs,
  ///                                  const struct FheUint2048 *rhs,
  ///                                  struct FheUint2048 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U2048, FheUint2048, CompressedFheUint2048> multiplyWithOverflow(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint2048_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint2048_scalar_mul(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 multiplyScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_mul_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_mul_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void multiplyScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_div(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 divide(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_div(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 divideScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_div_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_div_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void divideScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_div_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_rem(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 remainder(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_rem(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 remainderScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_rem_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_rem_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void remainderScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_div_rem(const struct FheUint2048 *lhs,
  ///                          const struct FheUint2048 *rhs,
  ///                          struct FheUint2048 **q_result,
  ///                          struct FheUint2048 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U2048, FheUint2048, CompressedFheUint2048> divideWithRemainder(FheUint2048 other) {
    FheUint2048 divider = new FheUint2048();
    FheUint2048 remainder = new FheUint2048();
    execute(() -> fhe_uint2048_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint2048_scalar_div_rem(const struct FheUint2048 *lhs,
  ///                                 struct U2048 rhs,
  ///                                 struct FheUint2048 **q_result,
  ///                                 struct FheUint2048 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U2048, FheUint2048, CompressedFheUint2048> divideWithRemainderScalar(U2048 other) {
    FheUint2048 divider = new FheUint2048();
    FheUint2048 remainder = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint2048_neg(const struct FheUint2048 *input, struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 negate() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint2048_ilog2(const struct FheUint2048 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint2048 ilog2() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint2048_checked_ilog2(const struct FheUint2048 *input,
  ///                                struct FheUint32 **result_1,
  ///                                struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<U2048, FheUint2048, CompressedFheUint2048> ilog2WithCheck() {
    FheUint2048 result = new FheUint2048();
    FheBool check = new FheBool();
    execute(() -> fhe_uint2048_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint2048_lt(const struct FheUint2048 *lhs,
  ///                     const struct FheUint2048 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_lt(const struct FheUint2048 *lhs,
  ///                            struct U2048 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(U2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_le(const struct FheUint2048 *lhs,
  ///                     const struct FheUint2048 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_le(const struct FheUint2048 *lhs,
  ///                            struct U2048 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(U2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_gt(const struct FheUint2048 *lhs,
  ///                     const struct FheUint2048 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_gt(const struct FheUint2048 *lhs,
  ///                            struct U2048 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(U2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_ge(const struct FheUint2048 *lhs,
  ///                     const struct FheUint2048 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_ge(const struct FheUint2048 *lhs,
  ///                            struct U2048 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(U2048 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint2048_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_min(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 min(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_min(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 minScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_max(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 max(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_max(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 maxScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_shl(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 shiftLeft(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_shl(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 shiftLeftScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_shl_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_shl_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_shr(const struct FheUint2048 *lhs,
  ///                      const struct FheUint2048 *rhs,
  ///                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 shiftRight(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_shr(const struct FheUint2048 *lhs,
  ///                             struct U2048 rhs,
  ///                             struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 shiftRightScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_shr_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_shr_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_rotate_left(const struct FheUint2048 *lhs,
  ///                              const struct FheUint2048 *rhs,
  ///                              struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 rotateLeft(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_rotate_left(const struct FheUint2048 *lhs,
  ///                                     struct U2048 rhs,
  ///                                     struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 rotateLeftScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_rotate_left_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_rotate_left_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint2048_rotate_right(const struct FheUint2048 *lhs,
  ///                               const struct FheUint2048 *rhs,
  ///                               struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 rotateRight(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_scalar_rotate_right(const struct FheUint2048 *lhs,
  ///                                      struct U2048 rhs,
  ///                                      struct FheUint2048 **result);
  ///```
  @Override
  public FheUint2048 rotateRightScalar(U2048 other) {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint2048_rotate_right_assign(struct FheUint2048 *lhs, const struct FheUint2048 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint2048 other) {
    execute(() -> fhe_uint2048_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint2048_scalar_rotate_right_assign(struct FheUint2048 *lhs, struct U2048 rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(U2048 other) {
    execute(() -> fhe_uint2048_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint2048_leading_ones(const struct FheUint2048 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint2048 leadingOnes() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint2048_leading_zeros(const struct FheUint2048 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint2048 leadingZeros() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint2048_trailing_ones(const struct FheUint2048 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint2048 trailingOnes() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint2048_trailing_zeros(const struct FheUint2048 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint2048 trailingZeros() {
    FheUint2048 result = new FheUint2048();
      execute(() -> fhe_uint2048_trailing_zeros(getValue(), result.getAddress()));
      return result;

}
/// ```c
/// int generate_oblivious_pseudo_random_fhe_uint2048(struct FheUint2048 **out_result,
///                                                   uint64_t seed_low_bytes,
///                                                   uint64_t seed_high_bytes);
///```
@Override
public FheUint2048 random(long seedLow, long seedHigh) {
  FheUint2048 result = new FheUint2048();
  execute(() -> generate_oblivious_pseudo_random_fhe_uint2048(result.getAddress(), seedLow, seedHigh));
  return result;

}
    
/// ```c
/// int generate_oblivious_pseudo_random_bounded_fhe_uint2048(struct FheUint2048 **out_result,
///                                                           uint64_t seed_low_bytes,
///                                                           uint64_t seed_high_bytes,
///                                                           uint64_t random_bits_count);
///```
@Override
public FheUint2048 random(long seedLow, long seedHigh, long bitsCount) {
  FheUint2048 result = new FheUint2048();
  execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint2048(result.getAddress(), seedLow, seedHigh, bitsCount));
  return result;

}

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int10(const struct FheUint2048 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint2048_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int1024(const struct FheUint2048 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint2048_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int12(const struct FheUint2048 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint2048_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int128(const struct FheUint2048 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint2048_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int14(const struct FheUint2048 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint2048_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int16(const struct FheUint2048 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint2048_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int160(const struct FheUint2048 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint2048_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int2(const struct FheUint2048 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint2048_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int2048(const struct FheUint2048 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint2048_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int256(const struct FheUint2048 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint2048_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int32(const struct FheUint2048 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint2048_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int4(const struct FheUint2048 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint2048_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int512(const struct FheUint2048 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint2048_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int6(const struct FheUint2048 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint2048_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int64(const struct FheUint2048 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint2048_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_int8(const struct FheUint2048 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint2048_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint10(const struct FheUint2048 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint2048_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint1024(const struct FheUint2048 *sself,
  ///                                         struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint2048_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint12(const struct FheUint2048 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint2048_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint128(const struct FheUint2048 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint2048_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint14(const struct FheUint2048 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint2048_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint16(const struct FheUint2048 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint2048_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint160(const struct FheUint2048 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint2048_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint2(const struct FheUint2048 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint2048_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint2048(const struct FheUint2048 *sself,
  ///                                         struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint256(const struct FheUint2048 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint2048_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint32(const struct FheUint2048 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint2048_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint4(const struct FheUint2048 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint2048_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint512(const struct FheUint2048 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint2048_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint2048_cast_into_fhe_uint6(const struct FheUint2048 *sself, struct FheUint6 **result);
///```
public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint2048_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint2048_cast_into_fhe_uint64(const struct FheUint2048 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint2048_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint2048_cast_into_fhe_uint8(const struct FheUint2048 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint2048_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
