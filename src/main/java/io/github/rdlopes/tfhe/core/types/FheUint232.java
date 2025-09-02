package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint232 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint232() {
    super(FheUint232.class, TfheWrapper::fhe_uint232_destroy);
  }

  public static FheUint232
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint232 fhe = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint232
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint232 fhe = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint232 encryptTrivial(U256 clearValue) {
    FheUint232 fhe = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint232 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint232 fhe = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint232_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint232_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint232 and(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint232 or(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint232 xor(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint232 scalarAnd(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint232 scalarOr(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint232 scalarXor(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint232 compress() {
    CompressedFheUint232 compressed = new CompressedFheUint232();
    executeWithErrorHandling(() -> fhe_uint232_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint232 clone() {
    FheUint232 cloned = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint232 add(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_add_assign(getValue(), other.getValue()));
  }

  public FheUint232 sub(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_sub_assign(getValue(), other.getValue()));
  }

  public FheUint232 mul(FheUint232 other) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint232 other) {
    executeWithErrorHandling(() -> fhe_uint232_mul_assign(getValue(), other.getValue()));
  }

  public FheUint232 scalarAdd(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint232 scalarSub(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint232 scalarMul(U256 scalar) {
    FheUint232 result = new FheUint232();
    executeWithErrorHandling(() -> fhe_uint232_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint232_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint232_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
