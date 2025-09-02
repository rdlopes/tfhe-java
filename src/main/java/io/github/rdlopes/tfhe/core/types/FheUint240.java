package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint240 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint240() {
    super(FheUint240.class, TfheWrapper::fhe_uint240_destroy);
  }

  public static FheUint240
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint240 fhe = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint240
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint240 fhe = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint240 encryptTrivial(U256 clearValue) {
    FheUint240 fhe = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint240 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint240 fhe = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint240_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint240_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint240 and(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint240 or(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint240 xor(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint240 scalarAnd(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint240 scalarOr(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint240 scalarXor(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint240 compress() {
    CompressedFheUint240 compressed = new CompressedFheUint240();
    executeWithErrorHandling(() -> fhe_uint240_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint240 clone() {
    FheUint240 cloned = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint240 add(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_add_assign(getValue(), other.getValue()));
  }

  public FheUint240 sub(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_sub_assign(getValue(), other.getValue()));
  }

  public FheUint240 mul(FheUint240 other) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint240 other) {
    executeWithErrorHandling(() -> fhe_uint240_mul_assign(getValue(), other.getValue()));
  }

  public FheUint240 scalarAdd(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint240 scalarSub(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint240 scalarMul(U256 scalar) {
    FheUint240 result = new FheUint240();
    executeWithErrorHandling(() -> fhe_uint240_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint240_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint240_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
