package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt88 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt88() {
    super(FheInt88.class, TfheWrapper::fhe_int88_destroy);
  }

  public static FheInt88
  encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt88 fhe = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt88
  encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt88 fhe = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt88 encryptTrivial(I128 clearValue) {
    FheInt88 fhe = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_try_encrypt_trivial_i128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt88 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt88 fhe = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I128
  decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int88_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int88_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt88 and(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt88 or(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt88 xor(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt88 scalarAnd(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt88 scalarOr(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt88 scalarXor(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt88 compress() {
    CompressedFheInt88 compressed = new CompressedFheInt88();
    executeWithErrorHandling(() -> fhe_int88_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt88 clone() {
    FheInt88 cloned = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt88 add(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_add_assign(getValue(), other.getValue()));
  }

  public FheInt88 sub(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_sub_assign(getValue(), other.getValue()));
  }

  public FheInt88 mul(FheInt88 other) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt88 other) {
    executeWithErrorHandling(() -> fhe_int88_mul_assign(getValue(), other.getValue()));
  }

  public FheInt88 scalarAdd(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt88 scalarSub(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt88 scalarMul(I128 scalar) {
    FheInt88 result = new FheInt88();
    executeWithErrorHandling(() -> fhe_int88_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int88_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt88 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int88_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
