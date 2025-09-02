package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint200 extends AddressLayoutPointer implements Cloneable {

  protected FheUint200() {
    super(FheUint200.class, TfheWrapper::fhe_uint200_destroy);
  }

  public static FheUint200
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint200 fhe = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint200
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint200 fhe = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint200 encryptTrivial(U256 clearValue) {
    FheUint200 fhe = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint200 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint200 fhe = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint200_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint200_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint200_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint200 and(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint200 or(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint200 xor(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint200 scalarAnd(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint200 scalarOr(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint200 scalarXor(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint200 compress() {
    CompressedFheUint200 compressed = new CompressedFheUint200();
    executeWithErrorHandling(() -> fhe_uint200_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint200 clone() {
    FheUint200 cloned = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint200 add(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_add_assign(getValue(), other.getValue()));
  }

  public FheUint200 sub(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_sub_assign(getValue(), other.getValue()));
  }

  public FheUint200 mul(FheUint200 other) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint200 other) {
    executeWithErrorHandling(() -> fhe_uint200_mul_assign(getValue(), other.getValue()));
  }

  public FheUint200 scalarAdd(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint200 scalarSub(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint200 scalarMul(U256 scalar) {
    FheUint200 result = new FheUint200();
    executeWithErrorHandling(() -> fhe_uint200_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint200_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint200_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
