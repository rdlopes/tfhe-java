package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint176 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint176() {
    super(FheUint176.class, TfheWrapper::fhe_uint176_destroy);
  }

  public static FheUint176
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint176 fhe = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint176
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint176 fhe = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint176 encryptTrivial(U256 clearValue) {
    FheUint176 fhe = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint176 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint176 fhe = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint176_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint176_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint176_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint176 and(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint176 or(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint176 xor(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint176 scalarAnd(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint176 scalarOr(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint176 scalarXor(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint176 compress() {
    CompressedFheUint176 compressed = new CompressedFheUint176();
    executeWithErrorHandling(() -> fhe_uint176_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint176 clone() {
    FheUint176 cloned = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint176 add(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_add_assign(getValue(), other.getValue()));
  }

  public FheUint176 sub(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_sub_assign(getValue(), other.getValue()));
  }

  public FheUint176 mul(FheUint176 other) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint176 other) {
    executeWithErrorHandling(() -> fhe_uint176_mul_assign(getValue(), other.getValue()));
  }

  public FheUint176 scalarAdd(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint176 scalarSub(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint176 scalarMul(U256 scalar) {
    FheUint176 result = new FheUint176();
    executeWithErrorHandling(() -> fhe_uint176_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint176_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint176 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint176_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
