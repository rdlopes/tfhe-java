package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt96 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt96() {
    super(FheInt96.class, TfheWrapper::fhe_int96_destroy);
  }

  public static FheInt96
  encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt96 fhe = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt96
  encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt96 fhe = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt96 encryptTrivial(I128 clearValue) {
    FheInt96 fhe = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_try_encrypt_trivial_i128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt96 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt96 fhe = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I128
  decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int96_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int96_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt96 and(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt96 or(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt96 xor(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt96 scalarAnd(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt96 scalarOr(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt96 scalarXor(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt96 compress() {
    CompressedFheInt96 compressed = new CompressedFheInt96();
    executeWithErrorHandling(() -> fhe_int96_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt96 clone() {
    FheInt96 cloned = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt96 add(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_add_assign(getValue(), other.getValue()));
  }

  public FheInt96 sub(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_sub_assign(getValue(), other.getValue()));
  }

  public FheInt96 mul(FheInt96 other) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt96 other) {
    executeWithErrorHandling(() -> fhe_int96_mul_assign(getValue(), other.getValue()));
  }

  public FheInt96 scalarAdd(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt96 scalarSub(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt96 scalarMul(I128 scalar) {
    FheInt96 result = new FheInt96();
    executeWithErrorHandling(() -> fhe_int96_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int96_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt96 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int96_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
