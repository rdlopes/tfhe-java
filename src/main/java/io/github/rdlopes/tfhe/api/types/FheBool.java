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
  public void bitXorAssign(FheBool other) {
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
public FheBool bitNot(){
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
public FheBool equalTo(FheBool other){
    FheBool result = new FheBool();
  execute(() -> fhe_bool_eq(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_scalar_eq(const struct FheBool *lhs, bool rhs, struct FheBool **result);
  ///```
  @Override
public FheBool equalToScalar(Boolean other){
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_eq(getValue(), other, result.getAddress()));
    return result;

  }

  /// ```c
  /// int fhe_bool_ne(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
///```
@Override
public FheBool notEqualTo(FheBool other){
    FheBool result = new FheBool();
  execute(() -> fhe_bool_ne(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /// ```c
  /// int fhe_bool_scalar_ne(const struct FheBool *lhs, bool rhs, struct FheBool **result);
///```
@Override
public FheBool notEqualToScalar(Boolean other){
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
  public static FheBool encrypt(Boolean clearValue) {
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_trivial_bool(clearValue, encrypted.getAddress()));
    return encrypted;

}
/// ```c
/// int fhe_bool_clone(const struct FheBool *sself, struct FheBool **result);
///```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public FheBool clone() {
  FheBool cloned = new FheBool();
  execute(() -> fhe_bool_clone(getValue(), cloned.getAddress()));
  return cloned;

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
/// int fhe_bool_compress(const struct FheBool *sself, struct CompressedFheBool **result);
///```
@Override
public CompressedFheBool compress(){
    CompressedFheBool compressed = new CompressedFheBool();
  execute(() -> fhe_bool_compress(getValue(), compressed.getAddress()));
    return compressed;

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
  /// int fhe_bool_cast_into_fhe_int12(const struct FheBool *sself, struct FheInt12 **result);
  ///```
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_bool_cast_into_fhe_int12(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_int14(const struct FheBool *sself, struct FheInt14 **result);
  ///```
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_bool_cast_into_fhe_int14(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_int2(const struct FheBool *sself, struct FheInt2 **result);
  ///```
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_bool_cast_into_fhe_int2(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_int512(const struct FheBool *sself, struct FheInt512 **result);
  ///```
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_bool_cast_into_fhe_int512(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_int8(const struct FheBool *sself, struct FheInt8 **result);
  ///```
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_bool_cast_into_fhe_int8(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_uint12(const struct FheBool *sself, struct FheUint12 **result);
  ///```
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_bool_cast_into_fhe_uint12(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_uint14(const struct FheBool *sself, struct FheUint14 **result);
  ///```
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_bool_cast_into_fhe_uint14(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_uint2(const struct FheBool *sself, struct FheUint2 **result);
  ///```
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_bool_cast_into_fhe_uint2(getValue(), result.getAddress()));
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
  /// int fhe_bool_cast_into_fhe_uint512(const struct FheBool *sself, struct FheUint512 **result);
  ///```
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_bool_cast_into_fhe_uint512(getValue(), result.getAddress()));
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
/// int fhe_bool_cast_into_fhe_uint8(const struct FheBool *sself, struct FheUint8 **result);
///```
public FheUint8 castIntoFheUint8() {
  FheUint8 result = new FheUint8();
  execute(() -> fhe_bool_cast_into_fhe_uint8(getValue(), result.getAddress()));
  return result;
}

  // @formatter:off
}
// @formatter:on
