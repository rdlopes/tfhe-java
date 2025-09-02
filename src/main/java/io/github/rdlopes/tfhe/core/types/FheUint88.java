package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint88 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint88() {
    super(FheUint88.class, TfheWrapper::fhe_uint88_destroy);
  }

  public static FheUint88
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint88 fhe = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint88
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint88 fhe = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint88 encryptTrivial(U128 clearValue) {
    FheUint88 fhe = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint88 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint88 fhe = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint88_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint88_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint88 and(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint88 or(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint88 xor(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint88 scalarAnd(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint88 scalarOr(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint88 scalarXor(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint88 compress() {
    CompressedFheUint88 compressed = new CompressedFheUint88();
    executeWithErrorHandling(() -> fhe_uint88_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint88 clone() {
    FheUint88 cloned = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint88 add(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_add_assign(getValue(), other.getValue()));
  }

  public FheUint88 sub(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_sub_assign(getValue(), other.getValue()));
  }

  public FheUint88 mul(FheUint88 other) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint88 other) {
    executeWithErrorHandling(() -> fhe_uint88_mul_assign(getValue(), other.getValue()));
  }

  public FheUint88 scalarAdd(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint88 scalarSub(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint88 scalarMul(U128 scalar) {
    FheUint88 result = new FheUint88();
    executeWithErrorHandling(() -> fhe_uint88_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint88_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint88_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
