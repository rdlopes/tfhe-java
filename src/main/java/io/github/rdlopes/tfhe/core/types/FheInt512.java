package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt512 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt512() {
    super(FheInt512.class, TfheWrapper::fhe_int512_destroy);
  }

  public static FheInt512
  encryptWithClientKey(I1024 clearValue, ClientKey clientKey) {
    FheInt512 fhe = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_try_encrypt_with_client_key_i512(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt512
  encryptWithPublicKey(I1024 clearValue, PublicKey publicKey) {
    FheInt512 fhe = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_try_encrypt_with_public_key_i512(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt512 encryptTrivial(I1024 clearValue) {
    FheInt512 fhe = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_try_encrypt_trivial_i512(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt512 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt512 fhe = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I1024
  decryptWithClientKey(ClientKey clientKey) {
    I1024 clearValue = new I1024();
    executeWithErrorHandling(() -> fhe_int512_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I1024 decryptTrivial() {
    I1024 clearValue = new I1024();
    executeWithErrorHandling(() -> fhe_int512_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt512 and(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt512 or(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt512 xor(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt512 scalarAnd(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt512 scalarOr(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt512 scalarXor(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt512 compress() {
    CompressedFheInt512 compressed = new CompressedFheInt512();
    executeWithErrorHandling(() -> fhe_int512_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt512 clone() {
    FheInt512 cloned = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt512 add(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_add_assign(getValue(), other.getValue()));
  }

  public FheInt512 sub(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_sub_assign(getValue(), other.getValue()));
  }

  public FheInt512 mul(FheInt512 other) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt512 other) {
    executeWithErrorHandling(() -> fhe_int512_mul_assign(getValue(), other.getValue()));
  }

  public FheInt512 scalarAdd(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt512 scalarSub(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt512 scalarMul(I1024 scalar) {
    FheInt512 result = new FheInt512();
    executeWithErrorHandling(() -> fhe_int512_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int512_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int512_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
