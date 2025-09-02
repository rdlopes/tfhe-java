package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt168 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt168() {
    super(FheInt168.class, TfheWrapper::fhe_int168_destroy);
  }

  public static FheInt168
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt168 fhe = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt168
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt168 fhe = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt168 encryptTrivial(I256 clearValue) {
    FheInt168 fhe = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt168 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt168 fhe = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int168_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int168_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt168 and(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt168 or(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt168 xor(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt168 scalarAnd(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt168 scalarOr(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt168 scalarXor(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt168 compress() {
    CompressedFheInt168 compressed = new CompressedFheInt168();
    executeWithErrorHandling(() -> fhe_int168_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt168 clone() {
    FheInt168 cloned = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt168 add(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_add_assign(getValue(), other.getValue()));
  }

  public FheInt168 sub(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_sub_assign(getValue(), other.getValue()));
  }

  public FheInt168 mul(FheInt168 other) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt168 other) {
    executeWithErrorHandling(() -> fhe_int168_mul_assign(getValue(), other.getValue()));
  }

  public FheInt168 scalarAdd(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt168 scalarSub(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt168 scalarMul(I256 scalar) {
    FheInt168 result = new FheInt168();
    executeWithErrorHandling(() -> fhe_int168_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int168_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt168 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int168_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
