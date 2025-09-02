package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint72 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint72() {
    super(FheUint72.class, TfheWrapper::fhe_uint72_destroy);
  }

  public static FheUint72
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint72 fhe = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint72
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint72 fhe = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint72 encryptTrivial(U128 clearValue) {
    FheUint72 fhe = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint72 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint72 fhe = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint72_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint72_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint72 and(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint72 or(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint72 xor(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint72 scalarAnd(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint72 scalarOr(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint72 scalarXor(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint72 compress() {
    CompressedFheUint72 compressed = new CompressedFheUint72();
    executeWithErrorHandling(() -> fhe_uint72_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint72 clone() {
    FheUint72 cloned = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint72 add(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_add_assign(getValue(), other.getValue()));
  }

  public FheUint72 sub(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_sub_assign(getValue(), other.getValue()));
  }

  public FheUint72 mul(FheUint72 other) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint72 other) {
    executeWithErrorHandling(() -> fhe_uint72_mul_assign(getValue(), other.getValue()));
  }

  public FheUint72 scalarAdd(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint72 scalarSub(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint72 scalarMul(U128 scalar) {
    FheUint72 result = new FheUint72();
    executeWithErrorHandling(() -> fhe_uint72_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint72_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint72_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
