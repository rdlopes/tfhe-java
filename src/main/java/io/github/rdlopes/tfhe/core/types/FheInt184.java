package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt184 extends AddressLayoutPointer implements Cloneable {

  protected FheInt184() {
    super(FheInt184.class, TfheWrapper::fhe_int184_destroy);
  }

  public static FheInt184
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt184 fhe = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt184
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt184 fhe = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt184 encryptTrivial(I256 clearValue) {
    FheInt184 fhe = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt184 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt184 fhe = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int184_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int184_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt184 and(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt184 or(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt184 xor(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt184 scalarAnd(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt184 scalarOr(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt184 scalarXor(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt184 compress() {
    CompressedFheInt184 compressed = new CompressedFheInt184();
    executeWithErrorHandling(() -> fhe_int184_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt184 clone() {
    FheInt184 cloned = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt184 add(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_add_assign(getValue(), other.getValue()));
  }

  public FheInt184 sub(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_sub_assign(getValue(), other.getValue()));
  }

  public FheInt184 mul(FheInt184 other) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt184 other) {
    executeWithErrorHandling(() -> fhe_int184_mul_assign(getValue(), other.getValue()));
  }

  public FheInt184 scalarAdd(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt184 scalarSub(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt184 scalarMul(I256 scalar) {
    FheInt184 result = new FheInt184();
    executeWithErrorHandling(() -> fhe_int184_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int184_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int184_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
