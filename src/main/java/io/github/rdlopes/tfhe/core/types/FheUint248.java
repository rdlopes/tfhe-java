package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint248 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint248() {
    super(FheUint248.class, TfheWrapper::fhe_uint248_destroy);
  }

  public static FheUint248
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint248 fhe = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint248
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint248 fhe = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint248 encryptTrivial(U256 clearValue) {
    FheUint248 fhe = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint248 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint248 fhe = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint248_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint248_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint248_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint248 and(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint248 or(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint248 xor(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint248 scalarAnd(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint248 scalarOr(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint248 scalarXor(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint248 compress() {
    CompressedFheUint248 compressed = new CompressedFheUint248();
    executeWithErrorHandling(() -> fhe_uint248_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint248 clone() {
    FheUint248 cloned = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint248 add(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_add_assign(getValue(), other.getValue()));
  }

  public FheUint248 sub(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_sub_assign(getValue(), other.getValue()));
  }

  public FheUint248 mul(FheUint248 other) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint248 other) {
    executeWithErrorHandling(() -> fhe_uint248_mul_assign(getValue(), other.getValue()));
  }

  public FheUint248 scalarAdd(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint248 scalarSub(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint248 scalarMul(U256 scalar) {
    FheUint248 result = new FheUint248();
    executeWithErrorHandling(() -> fhe_uint248_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint248_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint248 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint248_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
