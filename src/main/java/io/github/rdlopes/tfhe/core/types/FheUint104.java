package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint104 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint104() {
    super(FheUint104.class, TfheWrapper::fhe_uint104_destroy);
  }

  public static FheUint104
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint104 fhe = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint104
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint104 fhe = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint104 encryptTrivial(U128 clearValue) {
    FheUint104 fhe = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint104 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint104 fhe = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint104_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint104_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint104 and(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint104 or(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint104 xor(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint104 scalarAnd(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint104 scalarOr(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint104 scalarXor(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint104 compress() {
    CompressedFheUint104 compressed = new CompressedFheUint104();
    executeWithErrorHandling(() -> fhe_uint104_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint104 clone() {
    FheUint104 cloned = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint104 add(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_add_assign(getValue(), other.getValue()));
  }

  public FheUint104 sub(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_sub_assign(getValue(), other.getValue()));
  }

  public FheUint104 mul(FheUint104 other) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint104 other) {
    executeWithErrorHandling(() -> fhe_uint104_mul_assign(getValue(), other.getValue()));
  }

  public FheUint104 scalarAdd(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint104 scalarSub(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint104 scalarMul(U128 scalar) {
    FheUint104 result = new FheUint104();
    executeWithErrorHandling(() -> fhe_uint104_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint104_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint104_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
