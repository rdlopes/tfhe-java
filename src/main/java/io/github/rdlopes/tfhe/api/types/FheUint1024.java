package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U1024;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint1024 extends NativePointer implements FheUnsignedInteger<U1024, FheUint1024, CompressedFheUint1024> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint1024.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_uint1024_destroy(struct FheUint1024 *ptr);
  ///```
  FheUint1024() {
    logger.trace("init");
    super(TfheHeader::fhe_uint1024_destroy);
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
  /// int fhe_uint1024_safe_serialize(const struct FheUint1024 *sself,
  ///                                 struct DynamicBuffer *result,
  ///                                 uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_uint1024_bitand(const struct FheUint1024 *lhs,
  ///                         const struct FheUint1024 *rhs,
  ///                         struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 bitAnd(FheUint1024 other){
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_bitand(const struct FheUint1024 *lhs,
  ///                                struct U1024 rhs,
  ///                                struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 bitAndScalar(U1024 other){
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_bitand_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
///```
@Override
public void bitAndAssign(FheUint1024 other){
    execute(() -> fhe_uint1024_bitand_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint1024_scalar_bitand_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void bitAndScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_bitor(const struct FheUint1024 *lhs,
  ///                        const struct FheUint1024 *rhs,
  ///                        struct FheUint1024 **result);
///```
@Override
public FheUint1024 bitOr(FheUint1024 other){
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint1024_scalar_bitor(const struct FheUint1024 *lhs,
  ///                               struct U1024 rhs,
  ///                               struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 bitOrScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_bitor_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
///```
@Override
public void bitOrAssign(FheUint1024 other){
    execute(() -> fhe_uint1024_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_uint1024_scalar_bitor_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void bitOrScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_bitxor(const struct FheUint1024 *lhs,
  ///                         const struct FheUint1024 *rhs,
///                         struct FheUint1024 **result);
///```
@Override
public FheUint1024 bitXor(FheUint1024 other){
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_uint1024_scalar_bitxor(const struct FheUint1024 *lhs,
  ///                                struct U1024 rhs,
  ///                                struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 bitXorScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_bitxor_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void bitXorAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_bitxor_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_bitxor_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void bitXorScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /// ```c
/// int fhe_uint1024_not(const struct FheUint1024 *input, struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 bitNot() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_not(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_if_then_else(const struct FheBool *condition_ct,
  ///                               const struct FheUint1024 *then_ct,
  ///                               const struct FheUint1024 *else_ct,
  ///                               struct FheUint1024 **result);
  ///```
  @SuppressWarnings("unused")
  public static FheUint1024 ifThenElse(FheBool condition, FheUint1024 thenValue, FheUint1024 elseValue) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint1024_eq(const struct FheUint1024 *lhs,
///                     const struct FheUint1024 *rhs,
///                     struct FheBool **result);
  ///```
  @Override
  public FheBool equalTo(FheUint1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
/// int fhe_uint1024_scalar_eq(const struct FheUint1024 *lhs,
  ///                            struct U1024 rhs,
///                            struct FheBool **result);
  ///```
  @Override
  public FheBool equalToScalar(U1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

}
  
/// ```c
/// int fhe_uint1024_ne(const struct FheUint1024 *lhs,
///                     const struct FheUint1024 *rhs,
///                     struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheUint1024 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint1024_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}
  
/// ```c
/// int fhe_uint1024_scalar_ne(const struct FheUint1024 *lhs,
///                            struct U1024 rhs,
///                            struct FheBool **result);
///```
@Override
public FheBool notEqualToScalar(U1024 other){
    FheBool result = new FheBool();
  execute(() -> fhe_uint1024_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
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
  /// int fhe_uint1024_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
///                                              uint64_t serialized_size_limit,
///                                              const struct ServerKey *server_key,
///                                              struct FheUint1024 **result);
  ///```
  public static FheUint1024 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    FheUint1024 deserialized = new FheUint1024();
    execute(() -> fhe_uint1024_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

}
/// ```c
/// int fhe_uint1024_try_encrypt_with_client_key_u1024(struct U1024 value,
///                                                    const struct ClientKey *client_key,
///                                                    struct FheUint1024 **result);
///```
public static FheUint1024 encrypt(U1024 clearValue, ClientKey clientKey){
    FheUint1024 encrypted = new FheUint1024();
  execute(() -> fhe_uint1024_try_encrypt_with_client_key_u1024(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
  return encrypted;

}
  
/// ```c
/// int fhe_uint1024_try_encrypt_with_public_key_u1024(struct U1024 value,
///                                                    const struct PublicKey *public_key,
///                                                    struct FheUint1024 **result);
///```
public static FheUint1024 encrypt(U1024 clearValue, PublicKey publicKey){
    FheUint1024 encrypted = new FheUint1024();
      execute(() -> fhe_uint1024_try_encrypt_with_public_key_u1024(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}

  /// ```c
  /// int fhe_uint1024_try_encrypt_trivial_u1024(struct U1024 value, struct FheUint1024 **result);
  ///```
  public static FheUint1024 encrypt(U1024 clearValue){
    FheUint1024 encrypted = new FheUint1024();
      execute(() -> fhe_uint1024_try_encrypt_trivial_u1024(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_uint1024_clone(const struct FheUint1024 *sself, struct FheUint1024 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint1024 clone() {
    FheUint1024 cloned = new FheUint1024();
    execute(() -> fhe_uint1024_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /// ```c
  /// int fhe_uint1024_decrypt(const struct FheUint1024 *encrypted_value,
  ///                          const struct ClientKey *client_key,
  ///                          struct U1024 *result);
  ///```
  @Override
  public U1024 decrypt(ClientKey clientKey) {
    U1024 decrypted = new U1024();
    execute(() -> fhe_uint1024_decrypt(getValue(), clientKey.getValue(), decrypted.getAddress()));
      return decrypted;

}
  

/// ```c
/// int fhe_uint1024_compress(const struct FheUint1024 *sself, struct CompressedFheUint1024 **result);
///```
@Override
public CompressedFheUint1024 compress() {
  CompressedFheUint1024 compressed = new CompressedFheUint1024();
  execute(() -> fhe_uint1024_compress(getValue(), compressed.getAddress()));
  return compressed;

}

  /// ```c
  /// int fhe_uint1024_add(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 add(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_overflowing_add(const struct FheUint1024 *lhs,
  ///                                  const struct FheUint1024 *rhs,
  ///                                  struct FheUint1024 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U1024, FheUint1024, CompressedFheUint1024> addWithOverflow(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint1024_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint1024_scalar_add(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 addScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_add_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void addAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_add_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_add_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void addScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_add_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_sub(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 subtract(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_overflowing_sub(const struct FheUint1024 *lhs,
  ///                                  const struct FheUint1024 *rhs,
  ///                                  struct FheUint1024 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U1024, FheUint1024, CompressedFheUint1024> subtractWithOverflow(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint1024_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint1024_scalar_sub(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 subtractScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_sub_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void subtractAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_sub_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_sub_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void subtractScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_mul(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 multiply(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_overflowing_mul(const struct FheUint1024 *lhs,
  ///                                  const struct FheUint1024 *rhs,
  ///                                  struct FheUint1024 **out_result,
  ///                                  struct FheBool **out_overflowed);
  ///```
  @Override
  public CheckedResult<U1024, FheUint1024, CompressedFheUint1024> multiplyWithOverflow(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint1024_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return new CheckedResult<>(result, overflow);

  }

  /// ```c
  /// int fhe_uint1024_scalar_mul(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 multiplyScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_mul_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void multiplyAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_mul_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_mul_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void multiplyScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_div(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 divide(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_div(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 divideScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_div_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void divideAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_div_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_div_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void divideScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_div_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_rem(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 remainder(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_rem(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 remainderScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_rem_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void remainderAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_rem_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_rem_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void remainderScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_div_rem(const struct FheUint1024 *lhs,
  ///                          const struct FheUint1024 *rhs,
  ///                          struct FheUint1024 **q_result,
  ///                          struct FheUint1024 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U1024, FheUint1024, CompressedFheUint1024> divideWithRemainder(FheUint1024 other) {
    FheUint1024 divider = new FheUint1024();
    FheUint1024 remainder = new FheUint1024();
    execute(() -> fhe_uint1024_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint1024_scalar_div_rem(const struct FheUint1024 *lhs,
  ///                                 struct U1024 rhs,
  ///                                 struct FheUint1024 **q_result,
  ///                                 struct FheUint1024 **r_result);
  ///```
  @Override
  public DividerAndRemainder<U1024, FheUint1024, CompressedFheUint1024> divideWithRemainderScalar(U1024 other) {
    FheUint1024 divider = new FheUint1024();
    FheUint1024 remainder = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return new DividerAndRemainder<>(divider, remainder);

  }

  /// ```c
  /// int fhe_uint1024_neg(const struct FheUint1024 *input, struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 negate() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_neg(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Result has no meaning if self encrypts a value that is <= 0.
  ///  * See `checked_ilog2`
  ///  */
  /// int fhe_uint1024_ilog2(const struct FheUint1024 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint1024 ilog2() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the base 2 logarithm of the number, rounded down.
  ///  *
  ///  * Also returns a boolean flag that is true if the result is valid (i.e input was > 0)
  ///  */
  /// int fhe_uint1024_checked_ilog2(const struct FheUint1024 *input,
  ///                                struct FheUint32 **result_1,
  ///                                struct FheBool **result_2);
  ///```
  @Override
  public CheckedResult<U1024, FheUint1024, CompressedFheUint1024> ilog2WithCheck() {
    FheUint1024 result = new FheUint1024();
    FheBool check = new FheBool();
    execute(() -> fhe_uint1024_checked_ilog2(getValue(), result.getAddress(), check.getAddress()));
    return new CheckedResult<>(result, check);

  }

  /// ```c
  /// int fhe_uint1024_lt(const struct FheUint1024 *lhs,
  ///                     const struct FheUint1024 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool lessThan(FheUint1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_lt(const struct FheUint1024 *lhs,
  ///                            struct U1024 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanScalar(U1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_le(const struct FheUint1024 *lhs,
  ///                     const struct FheUint1024 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualTo(FheUint1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_le(const struct FheUint1024 *lhs,
  ///                            struct U1024 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool lessThanOrEqualToScalar(U1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_gt(const struct FheUint1024 *lhs,
  ///                     const struct FheUint1024 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThan(FheUint1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_gt(const struct FheUint1024 *lhs,
  ///                            struct U1024 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanScalar(U1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_ge(const struct FheUint1024 *lhs,
  ///                     const struct FheUint1024 *rhs,
  ///                     struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualTo(FheUint1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_ge(const struct FheUint1024 *lhs,
  ///                            struct U1024 rhs,
  ///                            struct FheBool **result);
  ///```
  @Override
  public FheBool greaterThanOrEqualToScalar(U1024 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint1024_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_min(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 min(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_min(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 minScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_max(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 max(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_max(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 maxScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_shl(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 shiftLeft(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_shl(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 shiftLeftScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_shl_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void shiftLeftAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_shl_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_shl_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void shiftLeftScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_shr(const struct FheUint1024 *lhs,
  ///                      const struct FheUint1024 *rhs,
  ///                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 shiftRight(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_shr(const struct FheUint1024 *lhs,
  ///                             struct U1024 rhs,
  ///                             struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 shiftRightScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_shr_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void shiftRightAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_shr_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_shr_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void shiftRightScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_rotate_left(const struct FheUint1024 *lhs,
  ///                              const struct FheUint1024 *rhs,
  ///                              struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 rotateLeft(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_rotate_left(const struct FheUint1024 *lhs,
  ///                                     struct U1024 rhs,
  ///                                     struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 rotateLeftScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_rotate_left_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void rotateLeftAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_rotate_left_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_rotate_left_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void rotateLeftScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /// ```c
  /// int fhe_uint1024_rotate_right(const struct FheUint1024 *lhs,
  ///                               const struct FheUint1024 *rhs,
  ///                               struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 rotateRight(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_scalar_rotate_right(const struct FheUint1024 *lhs,
  ///                                      struct U1024 rhs,
  ///                                      struct FheUint1024 **result);
  ///```
  @Override
  public FheUint1024 rotateRightScalar(U1024 other) {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_uint1024_rotate_right_assign(struct FheUint1024 *lhs, const struct FheUint1024 *rhs);
  ///```
  @Override
  public void rotateRightAssign(FheUint1024 other) {
    execute(() -> fhe_uint1024_rotate_right_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_uint1024_scalar_rotate_right_assign(struct FheUint1024 *lhs, struct U1024 rhs);
  ///```
  @Override
  public void rotateRightScalarAssign(U1024 other) {
    execute(() -> fhe_uint1024_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /// ```c
  ////**
  ///  * Returns the number of leading ones in the binary representation of input.
  ///  */
  /// int fhe_uint1024_leading_ones(const struct FheUint1024 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint1024 leadingOnes() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of leading zeros in the binary representation of input.
  ///  */
  /// int fhe_uint1024_leading_zeros(const struct FheUint1024 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint1024 leadingZeros() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing ones in the binary representation of input.
  ///  */
  /// int fhe_uint1024_trailing_ones(const struct FheUint1024 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint1024 trailingOnes() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  ////**
  ///  * Returns the number of trailing zeros in the binary representation of input.
  ///  */
  /// int fhe_uint1024_trailing_zeros(const struct FheUint1024 *input, struct FheUint32 **result);
  ///```
  @Override
  public FheUint1024 trailingZeros() {
    FheUint1024 result = new FheUint1024();
      execute(() -> fhe_uint1024_trailing_zeros(getValue(), result.getAddress()));
      return result;

}
/// ```c
/// int generate_oblivious_pseudo_random_fhe_uint1024(struct FheUint1024 **out_result,
///                                                   uint64_t seed_low_bytes,
///                                                   uint64_t seed_high_bytes);
///```
@Override
public FheUint1024 random(long seedLow, long seedHigh) {
  FheUint1024 result = new FheUint1024();
  execute(() -> generate_oblivious_pseudo_random_fhe_uint1024(result.getAddress(), seedLow, seedHigh));
  return result;

}
    
/// ```c
/// int generate_oblivious_pseudo_random_bounded_fhe_uint1024(struct FheUint1024 **out_result,
///                                                           uint64_t seed_low_bytes,
///                                                           uint64_t seed_high_bytes,
///                                                           uint64_t random_bits_count);
///```
@Override
public FheUint1024 random(long seedLow, long seedHigh, long bitsCount) {
  FheUint1024 result = new FheUint1024();
  execute(() -> generate_oblivious_pseudo_random_bounded_fhe_uint1024(result.getAddress(), seedLow, seedHigh, bitsCount));
  return result;

}

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int10(const struct FheUint1024 *sself, struct FheInt10 **result);
  ///```
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint1024_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int1024(const struct FheUint1024 *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_uint1024_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int12(const struct FheUint1024 *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint1024_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int128(const struct FheUint1024 *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint1024_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int14(const struct FheUint1024 *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint1024_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int16(const struct FheUint1024 *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint1024_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int160(const struct FheUint1024 *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint1024_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int2(const struct FheUint1024 *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint1024_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int2048(const struct FheUint1024 *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint1024_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int256(const struct FheUint1024 *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint1024_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int32(const struct FheUint1024 *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint1024_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int4(const struct FheUint1024 *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint1024_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int512(const struct FheUint1024 *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint1024_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int6(const struct FheUint1024 *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint1024_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int64(const struct FheUint1024 *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint1024_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_int8(const struct FheUint1024 *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint1024_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint10(const struct FheUint1024 *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint1024_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint1024(const struct FheUint1024 *sself,
  ///                                         struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint12(const struct FheUint1024 *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint1024_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint128(const struct FheUint1024 *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint1024_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint14(const struct FheUint1024 *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint1024_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint16(const struct FheUint1024 *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint1024_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint160(const struct FheUint1024 *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint1024_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint2(const struct FheUint1024 *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint1024_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint2048(const struct FheUint1024 *sself,
  ///                                         struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint1024_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint256(const struct FheUint1024 *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint1024_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint32(const struct FheUint1024 *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint1024_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint4(const struct FheUint1024 *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint1024_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint512(const struct FheUint1024 *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint1024_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_uint1024_cast_into_fhe_uint6(const struct FheUint1024 *sself, struct FheUint6 **result);
///```
public FheUint6 castIntoFheUint6() {
  FheUint6 result = new FheUint6();
  execute(() -> fhe_uint1024_cast_into_fhe_uint6(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint1024_cast_into_fhe_uint64(const struct FheUint1024 *sself, struct FheUint64 **result);
///```
public FheUint64 castIntoFheUint64() {
  FheUint64 result = new FheUint64();
  execute(() -> fhe_uint1024_cast_into_fhe_uint64(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_uint1024_cast_into_fhe_uint8(const struct FheUint1024 *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_uint1024_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
