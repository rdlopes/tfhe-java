package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint256 extends AddressLayoutPointer implements Cloneable {

  protected FheUint256() {
    super(FheUint256.class, TfheWrapper::fhe_uint256_destroy);
  }

  public static FheUint256
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint256 fhe = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint256
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint256 fhe = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint256 encryptTrivial(U256 clearValue) {
    FheUint256 fhe = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint256 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint256 fhe = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint256_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint256_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint256_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint256 and(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint256 or(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint256 xor(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint256 scalarAnd(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint256 scalarOr(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint256 scalarXor(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint256 compress() {
    CompressedFheUint256 compressed = new CompressedFheUint256();
    executeWithErrorHandling(() -> fhe_uint256_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint256 clone() {
    FheUint256 cloned = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint256 add(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_add_assign(getValue(), other.getValue()));
  }

  public FheUint256 sub(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_sub_assign(getValue(), other.getValue()));
  }

  public FheUint256 mul(FheUint256 other) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint256 other) {
    executeWithErrorHandling(() -> fhe_uint256_mul_assign(getValue(), other.getValue()));
  }

  public FheUint256 scalarAdd(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint256 scalarSub(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint256 scalarMul(U256 scalar) {
    FheUint256 result = new FheUint256();
    executeWithErrorHandling(() -> fhe_uint256_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint256_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint256 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint256_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
