package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint128 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint128() {
    super(FheUint128.class, TfheWrapper::fhe_uint128_destroy);
  }

  public static FheUint128
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint128 fhe = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint128
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint128 fhe = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint128 encryptTrivial(U128 clearValue) {
    FheUint128 fhe = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint128 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint128 fhe = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint128_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint128_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint128 and(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint128 or(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint128 xor(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint128 scalarAnd(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint128 scalarOr(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint128 scalarXor(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint128 compress() {
    CompressedFheUint128 compressed = new CompressedFheUint128();
    executeWithErrorHandling(() -> fhe_uint128_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint128 clone() {
    FheUint128 cloned = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint128 add(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_add_assign(getValue(), other.getValue()));
  }

  public FheUint128 sub(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_sub_assign(getValue(), other.getValue()));
  }

  public FheUint128 mul(FheUint128 other) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint128 other) {
    executeWithErrorHandling(() -> fhe_uint128_mul_assign(getValue(), other.getValue()));
  }

  public FheUint128 scalarAdd(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint128 scalarSub(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint128 scalarMul(U128 scalar) {
    FheUint128 result = new FheUint128();
    executeWithErrorHandling(() -> fhe_uint128_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint128_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint128_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
