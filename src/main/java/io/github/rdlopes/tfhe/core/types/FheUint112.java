package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint112 extends AddressLayoutPointer implements Cloneable {

  protected FheUint112() {
    super(FheUint112.class, TfheWrapper::fhe_uint112_destroy);
  }

  public static FheUint112
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint112 fhe = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint112
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint112 fhe = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint112 encryptTrivial(U128 clearValue) {
    FheUint112 fhe = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint112 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint112 fhe = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint112_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint112_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint112 and(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint112 or(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint112 xor(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint112 scalarAnd(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint112 scalarOr(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint112 scalarXor(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint112 compress() {
    CompressedFheUint112 compressed = new CompressedFheUint112();
    executeWithErrorHandling(() -> fhe_uint112_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint112 clone() {
    FheUint112 cloned = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint112 add(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_add_assign(getValue(), other.getValue()));
  }

  public FheUint112 sub(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_sub_assign(getValue(), other.getValue()));
  }

  public FheUint112 mul(FheUint112 other) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint112 other) {
    executeWithErrorHandling(() -> fhe_uint112_mul_assign(getValue(), other.getValue()));
  }

  public FheUint112 scalarAdd(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint112 scalarSub(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint112 scalarMul(U128 scalar) {
    FheUint112 result = new FheUint112();
    executeWithErrorHandling(() -> fhe_uint112_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint112_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint112 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint112_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
