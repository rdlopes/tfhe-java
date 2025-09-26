package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheBoolean;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeAndReturn;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheBool extends NativePointer implements FheBoolean<FheBool, CompressedFheBool> {
  private static final Logger logger = LoggerFactory.getLogger(FheBool.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int fhe_bool_destroy(struct FheBool *ptr);
  ///```
  FheBool() {
    logger.trace("init");
    super(TfheHeader::fhe_bool_destroy);
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
  /// int fhe_bool_safe_serialize(const struct FheBool *sself,
  ///                             struct DynamicBuffer *result,
  ///                             uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  /// int fhe_bool_bitand(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool bitAnd(FheBool other){
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_bool_scalar_bitand(const struct FheBool *lhs, bool rhs, struct FheBool **result);
  ///```
  @Override
  public FheBool bitAndScalar(Boolean other){
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_bool_bitand_assign(struct FheBool *lhs, const struct FheBool *rhs);
  ///```
  @Override
  public void bitAndAssign(FheBool other){
    execute(() -> fhe_bool_bitand_assign(getValue(), other.getValue()));

  }

  /// ```c
  /// int fhe_bool_scalar_bitand_assign(struct FheBool *lhs, bool rhs);
  ///```
  @Override
  public void bitAndScalarAssign(Boolean other) {
    execute(() -> fhe_bool_scalar_bitand_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_bool_bitor(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
///```
@Override
public FheBool bitOr(FheBool other){
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_scalar_bitor(const struct FheBool *lhs, bool rhs, struct FheBool **result);
  ///```
  @Override
public FheBool bitOrScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_bitor(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_bool_bitor_assign(struct FheBool *lhs, const struct FheBool *rhs);
///```
@Override
public void bitOrAssign(FheBool other){
    execute(() -> fhe_bool_bitor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_bool_scalar_bitor_assign(struct FheBool *lhs, bool rhs);
  ///```
  @Override
  public void bitOrScalarAssign(Boolean other) {
    execute(() -> fhe_bool_scalar_bitor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_bool_bitxor(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
///```
@Override
public FheBool bitXor(FheBool other){
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitxor(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_scalar_bitxor(const struct FheBool *lhs, bool rhs, struct FheBool **result);
///```
@Override
public FheBool bitXorScalar(Boolean other) {
  FheBool result = new FheBool();
  execute(() -> fhe_bool_scalar_bitxor(getValue(), other, result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_bitxor_assign(struct FheBool *lhs, const struct FheBool *rhs);
///```
@Override
public void bitXorAssign(FheBool other){
    execute(() -> fhe_bool_bitxor_assign(getValue(), other.getValue()));

}

  /// ```c
  /// int fhe_bool_scalar_bitxor_assign(struct FheBool *lhs, bool rhs);
  ///```
  @Override
  public void bitXorScalarAssign(Boolean other) {
    execute(() -> fhe_bool_scalar_bitxor_assign(getValue(), other));

  }

  /// ```c
  /// int fhe_bool_not(const struct FheBool *input, struct FheBool **result);
///```
  @Override
  public FheBool bitNot() {
    FheBool result = new FheBool();
    execute(() -> fhe_bool_not(getValue(), result.getAddress()));
    return result;

  }

  @SuppressWarnings("unused")
  public static FheBool ifThenElse(FheBool condition, FheBool thenValue, FheBool elseValue) {
    throw new NotImplementedException("Unavailable for FheBool");
  }

  /// ```c
  /// int fhe_bool_eq(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
///```
@Override
public FheBool equalTo(FheBool other) {
  FheBool result = new FheBool();
  execute(() -> fhe_bool_eq(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_scalar_eq(const struct FheBool *lhs, bool rhs, struct FheBool **result);
///```
@Override
public FheBool equalToScalar(Boolean other) {
  FheBool result = new FheBool();
      execute(() -> fhe_bool_scalar_eq(getValue(), other, result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_ne(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
///```
  @Override
  public FheBool notEqualTo(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> fhe_bool_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_bool_scalar_ne(const struct FheBool *lhs, bool rhs, struct FheBool **result);
///```
  @Override
  public FheBool notEqualToScalar(Boolean other) {
    FheBool result = new FheBool();
      execute(() -> fhe_bool_scalar_ne(getValue(), other, result.getAddress()));
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
  /// int fhe_bool_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                          uint64_t serialized_size_limit,
  ///                                          const struct ServerKey *server_key,
///                                          struct FheBool **result);
///```
public static FheBool deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    FheBool deserialized = new FheBool();
    execute(() -> fhe_bool_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
  return deserialized;

}

  /// ```c
  /// int fhe_bool_try_encrypt_with_client_key_bool(bool value,
  ///                                               const struct ClientKey *client_key,
  ///                                               struct FheBool **result);
  ///```
  public static FheBool encrypt(Boolean clearValue, ClientKey clientKey) {
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int fhe_bool_try_encrypt_with_public_key_bool(bool value,
  ///                                               const struct PublicKey *public_key,
  ///                                               struct FheBool **result);
  ///```
  public static FheBool encrypt(Boolean clearValue, PublicKey publicKey) {
    FheBool encrypted = new FheBool();
      execute(() -> fhe_bool_try_encrypt_with_public_key_bool(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;

}

  /// ```c
  /// int fhe_bool_try_encrypt_trivial_bool(bool value, struct FheBool **result);
  ///```
  public static FheBool encrypt(Boolean clearValue){
    FheBool encrypted = new FheBool();
      execute(() -> fhe_bool_try_encrypt_trivial_bool(clearValue, encrypted.getAddress()));
    return encrypted;

}
/// ```c
/// int fhe_bool_clone(const struct FheBool *sself, struct FheBool **result);
///```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public FheBool clone(){
    FheBool cloned = new FheBool();
    execute(() -> fhe_bool_clone(getValue(), cloned.getAddress()));
    return cloned;

}

  /// ```c
  /// int fhe_bool_compress(const struct FheBool *sself, struct CompressedFheBool **result);
  ///```
  @Override
  public CompressedFheBool compress(){
    CompressedFheBool compressed = new CompressedFheBool();
    execute(() -> fhe_bool_compress(getValue(), compressed.getAddress()));
    return compressed;

  }

  /// ```c
  /// int fhe_bool_decrypt(const struct FheBool *encrypted_value,
  ///                      const struct ClientKey *client_key,
  ///                      bool *result);
  ///```
  @Override
public Boolean decrypt(ClientKey clientKey) {
    return executeAndReturn(Boolean.class, address -> fhe_bool_decrypt(getValue(), clientKey.getValue(), address));

}
  


  /// ```c
  /// int fhe_bool_cast_into_fhe_int10(const struct FheBool *sself, struct FheInt10 **result);
  ///```
public FheInt10 castIntoFheInt10() {
  FheInt10 result = new FheInt10();
  execute(() -> fhe_bool_cast_into_fhe_int10(getValue(), result.getAddress()));
  return result;
}

  /// ```c
  /// int fhe_bool_cast_into_fhe_int1024(const struct FheBool *sself, struct FheInt1024 **result);
  ///```
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_bool_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int104(const struct FheBool *sself, struct FheInt104 **result);
  ///```
  public FheInt104 castIntoFheInt104() {
    FheInt104 result = new FheInt104();
    execute(() -> fhe_bool_cast_into_fhe_int104(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int112(const struct FheBool *sself, struct FheInt112 **result);
  ///```
  public FheInt112 castIntoFheInt112() {
    FheInt112 result = new FheInt112();
    execute(() -> fhe_bool_cast_into_fhe_int112(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int12(const struct FheBool *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_bool_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int120(const struct FheBool *sself, struct FheInt120 **result);
  ///```
  public FheInt120 castIntoFheInt120() {
    FheInt120 result = new FheInt120();
    execute(() -> fhe_bool_cast_into_fhe_int120(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int128(const struct FheBool *sself, struct FheInt128 **result);
  ///```
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_bool_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int136(const struct FheBool *sself, struct FheInt136 **result);
  ///```
  public FheInt136 castIntoFheInt136() {
    FheInt136 result = new FheInt136();
    execute(() -> fhe_bool_cast_into_fhe_int136(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int14(const struct FheBool *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_bool_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int144(const struct FheBool *sself, struct FheInt144 **result);
  ///```
  public FheInt144 castIntoFheInt144() {
    FheInt144 result = new FheInt144();
    execute(() -> fhe_bool_cast_into_fhe_int144(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int152(const struct FheBool *sself, struct FheInt152 **result);
  ///```
  public FheInt152 castIntoFheInt152() {
    FheInt152 result = new FheInt152();
    execute(() -> fhe_bool_cast_into_fhe_int152(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int16(const struct FheBool *sself, struct FheInt16 **result);
  ///```
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_bool_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int160(const struct FheBool *sself, struct FheInt160 **result);
  ///```
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_bool_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int168(const struct FheBool *sself, struct FheInt168 **result);
  ///```
  public FheInt168 castIntoFheInt168() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_bool_cast_into_fhe_int168(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int176(const struct FheBool *sself, struct FheInt176 **result);
  ///```
  public FheInt176 castIntoFheInt176() {
    FheInt176 result = new FheInt176();
    execute(() -> fhe_bool_cast_into_fhe_int176(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int184(const struct FheBool *sself, struct FheInt184 **result);
  ///```
  public FheInt184 castIntoFheInt184() {
    FheInt184 result = new FheInt184();
    execute(() -> fhe_bool_cast_into_fhe_int184(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int192(const struct FheBool *sself, struct FheInt192 **result);
  ///```
  public FheInt192 castIntoFheInt192() {
    FheInt192 result = new FheInt192();
    execute(() -> fhe_bool_cast_into_fhe_int192(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int2(const struct FheBool *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_bool_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int200(const struct FheBool *sself, struct FheInt200 **result);
  ///```
  public FheInt200 castIntoFheInt200() {
    FheInt200 result = new FheInt200();
    execute(() -> fhe_bool_cast_into_fhe_int200(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int2048(const struct FheBool *sself, struct FheInt2048 **result);
  ///```
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_bool_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int208(const struct FheBool *sself, struct FheInt208 **result);
  ///```
  public FheInt208 castIntoFheInt208() {
    FheInt208 result = new FheInt208();
    execute(() -> fhe_bool_cast_into_fhe_int208(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int216(const struct FheBool *sself, struct FheInt216 **result);
  ///```
  public FheInt216 castIntoFheInt216() {
    FheInt216 result = new FheInt216();
    execute(() -> fhe_bool_cast_into_fhe_int216(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int224(const struct FheBool *sself, struct FheInt224 **result);
  ///```
  public FheInt224 castIntoFheInt224() {
    FheInt224 result = new FheInt224();
    execute(() -> fhe_bool_cast_into_fhe_int224(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int232(const struct FheBool *sself, struct FheInt232 **result);
  ///```
  public FheInt232 castIntoFheInt232() {
    FheInt232 result = new FheInt232();
    execute(() -> fhe_bool_cast_into_fhe_int232(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int24(const struct FheBool *sself, struct FheInt24 **result);
  ///```
  public FheInt24 castIntoFheInt24() {
    FheInt24 result = new FheInt24();
    execute(() -> fhe_bool_cast_into_fhe_int24(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int240(const struct FheBool *sself, struct FheInt240 **result);
  ///```
  public FheInt240 castIntoFheInt240() {
    FheInt240 result = new FheInt240();
    execute(() -> fhe_bool_cast_into_fhe_int240(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int248(const struct FheBool *sself, struct FheInt248 **result);
  ///```
  public FheInt248 castIntoFheInt248() {
    FheInt248 result = new FheInt248();
    execute(() -> fhe_bool_cast_into_fhe_int248(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int256(const struct FheBool *sself, struct FheInt256 **result);
  ///```
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_bool_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int32(const struct FheBool *sself, struct FheInt32 **result);
  ///```
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_bool_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int4(const struct FheBool *sself, struct FheInt4 **result);
  ///```
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_bool_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int40(const struct FheBool *sself, struct FheInt40 **result);
  ///```
  public FheInt40 castIntoFheInt40() {
    FheInt40 result = new FheInt40();
    execute(() -> fhe_bool_cast_into_fhe_int40(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int48(const struct FheBool *sself, struct FheInt48 **result);
  ///```
  public FheInt48 castIntoFheInt48() {
    FheInt48 result = new FheInt48();
    execute(() -> fhe_bool_cast_into_fhe_int48(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int512(const struct FheBool *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_bool_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int56(const struct FheBool *sself, struct FheInt56 **result);
  ///```
  public FheInt56 castIntoFheInt56() {
    FheInt56 result = new FheInt56();
    execute(() -> fhe_bool_cast_into_fhe_int56(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int6(const struct FheBool *sself, struct FheInt6 **result);
  ///```
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_bool_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int64(const struct FheBool *sself, struct FheInt64 **result);
  ///```
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_bool_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int72(const struct FheBool *sself, struct FheInt72 **result);
  ///```
  public FheInt72 castIntoFheInt72() {
    FheInt72 result = new FheInt72();
    execute(() -> fhe_bool_cast_into_fhe_int72(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int8(const struct FheBool *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_bool_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int80(const struct FheBool *sself, struct FheInt80 **result);
  ///```
  public FheInt80 castIntoFheInt80() {
    FheInt80 result = new FheInt80();
    execute(() -> fhe_bool_cast_into_fhe_int80(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int88(const struct FheBool *sself, struct FheInt88 **result);
  ///```
  public FheInt88 castIntoFheInt88() {
    FheInt88 result = new FheInt88();
    execute(() -> fhe_bool_cast_into_fhe_int88(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_int96(const struct FheBool *sself, struct FheInt96 **result);
  ///```
  public FheInt96 castIntoFheInt96() {
    FheInt96 result = new FheInt96();
    execute(() -> fhe_bool_cast_into_fhe_int96(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint10(const struct FheBool *sself, struct FheUint10 **result);
  ///```
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_bool_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint1024(const struct FheBool *sself, struct FheUint1024 **result);
  ///```
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_bool_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint104(const struct FheBool *sself, struct FheUint104 **result);
  ///```
  public FheUint104 castIntoFheUint104() {
    FheUint104 result = new FheUint104();
    execute(() -> fhe_bool_cast_into_fhe_uint104(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint112(const struct FheBool *sself, struct FheUint112 **result);
  ///```
  public FheUint112 castIntoFheUint112() {
    FheUint112 result = new FheUint112();
    execute(() -> fhe_bool_cast_into_fhe_uint112(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint12(const struct FheBool *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_bool_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint120(const struct FheBool *sself, struct FheUint120 **result);
  ///```
  public FheUint120 castIntoFheUint120() {
    FheUint120 result = new FheUint120();
    execute(() -> fhe_bool_cast_into_fhe_uint120(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint128(const struct FheBool *sself, struct FheUint128 **result);
  ///```
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_bool_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint136(const struct FheBool *sself, struct FheUint136 **result);
  ///```
  public FheUint136 castIntoFheUint136() {
    FheUint136 result = new FheUint136();
    execute(() -> fhe_bool_cast_into_fhe_uint136(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint14(const struct FheBool *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_bool_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint144(const struct FheBool *sself, struct FheUint144 **result);
  ///```
  public FheUint144 castIntoFheUint144() {
    FheUint144 result = new FheUint144();
    execute(() -> fhe_bool_cast_into_fhe_uint144(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint152(const struct FheBool *sself, struct FheUint152 **result);
  ///```
  public FheUint152 castIntoFheUint152() {
    FheUint152 result = new FheUint152();
    execute(() -> fhe_bool_cast_into_fhe_uint152(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint16(const struct FheBool *sself, struct FheUint16 **result);
  ///```
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_bool_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint160(const struct FheBool *sself, struct FheUint160 **result);
  ///```
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_bool_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint168(const struct FheBool *sself, struct FheUint168 **result);
  ///```
  public FheUint168 castIntoFheUint168() {
    FheUint168 result = new FheUint168();
    execute(() -> fhe_bool_cast_into_fhe_uint168(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint176(const struct FheBool *sself, struct FheUint176 **result);
  ///```
  public FheUint176 castIntoFheUint176() {
    FheUint176 result = new FheUint176();
    execute(() -> fhe_bool_cast_into_fhe_uint176(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint184(const struct FheBool *sself, struct FheUint184 **result);
  ///```
  public FheUint184 castIntoFheUint184() {
    FheUint184 result = new FheUint184();
    execute(() -> fhe_bool_cast_into_fhe_uint184(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint192(const struct FheBool *sself, struct FheUint192 **result);
  ///```
  public FheUint192 castIntoFheUint192() {
    FheUint192 result = new FheUint192();
    execute(() -> fhe_bool_cast_into_fhe_uint192(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint2(const struct FheBool *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_bool_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint200(const struct FheBool *sself, struct FheUint200 **result);
  ///```
  public FheUint200 castIntoFheUint200() {
    FheUint200 result = new FheUint200();
    execute(() -> fhe_bool_cast_into_fhe_uint200(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint2048(const struct FheBool *sself, struct FheUint2048 **result);
  ///```
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_bool_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint208(const struct FheBool *sself, struct FheUint208 **result);
  ///```
  public FheUint208 castIntoFheUint208() {
    FheUint208 result = new FheUint208();
    execute(() -> fhe_bool_cast_into_fhe_uint208(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint216(const struct FheBool *sself, struct FheUint216 **result);
  ///```
  public FheUint216 castIntoFheUint216() {
    FheUint216 result = new FheUint216();
    execute(() -> fhe_bool_cast_into_fhe_uint216(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint224(const struct FheBool *sself, struct FheUint224 **result);
  ///```
  public FheUint224 castIntoFheUint224() {
    FheUint224 result = new FheUint224();
    execute(() -> fhe_bool_cast_into_fhe_uint224(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint232(const struct FheBool *sself, struct FheUint232 **result);
  ///```
  public FheUint232 castIntoFheUint232() {
    FheUint232 result = new FheUint232();
    execute(() -> fhe_bool_cast_into_fhe_uint232(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint24(const struct FheBool *sself, struct FheUint24 **result);
  ///```
  public FheUint24 castIntoFheUint24() {
    FheUint24 result = new FheUint24();
    execute(() -> fhe_bool_cast_into_fhe_uint24(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint240(const struct FheBool *sself, struct FheUint240 **result);
  ///```
  public FheUint240 castIntoFheUint240() {
    FheUint240 result = new FheUint240();
    execute(() -> fhe_bool_cast_into_fhe_uint240(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint248(const struct FheBool *sself, struct FheUint248 **result);
  ///```
  public FheUint248 castIntoFheUint248() {
    FheUint248 result = new FheUint248();
    execute(() -> fhe_bool_cast_into_fhe_uint248(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint256(const struct FheBool *sself, struct FheUint256 **result);
  ///```
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_bool_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint32(const struct FheBool *sself, struct FheUint32 **result);
  ///```
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_bool_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint4(const struct FheBool *sself, struct FheUint4 **result);
  ///```
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_bool_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint40(const struct FheBool *sself, struct FheUint40 **result);
  ///```
  public FheUint40 castIntoFheUint40() {
    FheUint40 result = new FheUint40();
    execute(() -> fhe_bool_cast_into_fhe_uint40(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint48(const struct FheBool *sself, struct FheUint48 **result);
  ///```
  public FheUint48 castIntoFheUint48() {
    FheUint48 result = new FheUint48();
    execute(() -> fhe_bool_cast_into_fhe_uint48(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint512(const struct FheBool *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_bool_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint56(const struct FheBool *sself, struct FheUint56 **result);
  ///```
  public FheUint56 castIntoFheUint56() {
    FheUint56 result = new FheUint56();
    execute(() -> fhe_bool_cast_into_fhe_uint56(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint6(const struct FheBool *sself, struct FheUint6 **result);
  ///```
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
    execute(() -> fhe_bool_cast_into_fhe_uint6(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint64(const struct FheBool *sself, struct FheUint64 **result);
  ///```
  public FheUint64 castIntoFheUint64() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_bool_cast_into_fhe_uint64(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint72(const struct FheBool *sself, struct FheUint72 **result);
  ///```
  public FheUint72 castIntoFheUint72() {
    FheUint72 result = new FheUint72();
    execute(() -> fhe_bool_cast_into_fhe_uint72(getValue(), result.getAddress()));
    return result;
  }

  /// ```c
  /// int fhe_bool_cast_into_fhe_uint8(const struct FheBool *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_bool_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_bool_cast_into_fhe_uint80(const struct FheBool *sself, struct FheUint80 **result);
///```
public FheUint80 castIntoFheUint80() {
  FheUint80 result = new FheUint80();
  execute(() -> fhe_bool_cast_into_fhe_uint80(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_bool_cast_into_fhe_uint88(const struct FheBool *sself, struct FheUint88 **result);
///```
public FheUint88 castIntoFheUint88() {
  FheUint88 result = new FheUint88();
  execute(() -> fhe_bool_cast_into_fhe_uint88(getValue(), result.getAddress()));
  return result;
}

/// ```c
/// int fhe_bool_cast_into_fhe_uint96(const struct FheBool *sself, struct FheUint96 **result);
///```
public FheUint96 castIntoFheUint96() {
  FheUint96 result = new FheUint96();
  execute(() -> fhe_bool_cast_into_fhe_uint96(getValue(), result.getAddress()));
  return result;
}


  // @formatter:off
}
// @formatter:on
