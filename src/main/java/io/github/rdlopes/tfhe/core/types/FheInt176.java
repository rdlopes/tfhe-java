package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt176 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt176() {
    super(FheInt176.class, TfheWrapper::fhe_int176_destroy);
  }

  public static FheInt176
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt176 fhe = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt176
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt176 fhe = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt176 encryptTrivial(I256 clearValue) {
    FheInt176 fhe = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt176 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt176 fhe = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int176_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int176_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int176_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt176 and(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt176 or(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt176 xor(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt176 scalarAnd(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt176 scalarOr(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt176 scalarXor(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt176 compress() {
    CompressedFheInt176 compressed = new CompressedFheInt176();
    executeWithErrorHandling(() -> fhe_int176_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt176 clone() {
    FheInt176 cloned = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt176 add(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_add_assign(getValue(), other.getValue()));
  }

  public FheInt176 sub(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_sub_assign(getValue(), other.getValue()));
  }

  public FheInt176 mul(FheInt176 other) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt176 other) {
    executeWithErrorHandling(() -> fhe_int176_mul_assign(getValue(), other.getValue()));
  }

  public FheInt176 scalarAdd(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt176 scalarSub(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt176 scalarMul(I256 scalar) {
    FheInt176 result = new FheInt176();
    executeWithErrorHandling(() -> fhe_int176_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int176_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int176_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
