package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint152 extends AddressLayoutPointer implements Cloneable {

  protected FheUint152() {
    super(FheUint152.class, TfheWrapper::fhe_uint152_destroy);
  }

  public static FheUint152
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint152 fhe = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint152
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint152 fhe = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint152 encryptTrivial(U256 clearValue) {
    FheUint152 fhe = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint152 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint152 fhe = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint152_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint152_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint152 and(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint152 or(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint152 xor(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint152 scalarAnd(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint152 scalarOr(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint152 scalarXor(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint152 compress() {
    CompressedFheUint152 compressed = new CompressedFheUint152();
    executeWithErrorHandling(() -> fhe_uint152_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint152 clone() {
    FheUint152 cloned = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint152 add(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_add_assign(getValue(), other.getValue()));
  }

  public FheUint152 sub(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_sub_assign(getValue(), other.getValue()));
  }

  public FheUint152 mul(FheUint152 other) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint152 other) {
    executeWithErrorHandling(() -> fhe_uint152_mul_assign(getValue(), other.getValue()));
  }

  public FheUint152 scalarAdd(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint152 scalarSub(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint152 scalarMul(U256 scalar) {
    FheUint152 result = new FheUint152();
    executeWithErrorHandling(() -> fhe_uint152_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint152_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint152_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
