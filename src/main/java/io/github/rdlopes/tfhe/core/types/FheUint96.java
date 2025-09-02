package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint96 extends AddressLayoutPointer implements Cloneable {

  protected FheUint96() {
    super(FheUint96.class, TfheWrapper::fhe_uint96_destroy);
  }

  public static FheUint96
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint96 fhe = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint96
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint96 fhe = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint96 encryptTrivial(U128 clearValue) {
    FheUint96 fhe = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint96 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint96 fhe = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint96_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint96_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint96 and(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint96 or(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint96 xor(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint96 scalarAnd(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint96 scalarOr(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint96 scalarXor(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint96 compress() {
    CompressedFheUint96 compressed = new CompressedFheUint96();
    executeWithErrorHandling(() -> fhe_uint96_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint96 clone() {
    FheUint96 cloned = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint96 add(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_add_assign(getValue(), other.getValue()));
  }

  public FheUint96 sub(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_sub_assign(getValue(), other.getValue()));
  }

  public FheUint96 mul(FheUint96 other) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint96 other) {
    executeWithErrorHandling(() -> fhe_uint96_mul_assign(getValue(), other.getValue()));
  }

  public FheUint96 scalarAdd(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint96 scalarSub(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint96 scalarMul(U128 scalar) {
    FheUint96 result = new FheUint96();
    executeWithErrorHandling(() -> fhe_uint96_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint96_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint96_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
