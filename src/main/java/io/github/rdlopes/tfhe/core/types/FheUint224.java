package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint224 extends AddressLayoutPointer implements Cloneable {

  protected FheUint224() {
    super(FheUint224.class, TfheWrapper::fhe_uint224_destroy);
  }

  public static FheUint224
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint224 fhe = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint224
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint224 fhe = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint224 encryptTrivial(U256 clearValue) {
    FheUint224 fhe = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint224 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint224 fhe = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint224_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint224_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint224_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint224 and(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint224 or(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint224 xor(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint224 scalarAnd(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint224 scalarOr(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint224 scalarXor(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint224 compress() {
    CompressedFheUint224 compressed = new CompressedFheUint224();
    executeWithErrorHandling(() -> fhe_uint224_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint224 clone() {
    FheUint224 cloned = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint224 add(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_add_assign(getValue(), other.getValue()));
  }

  public FheUint224 sub(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_sub_assign(getValue(), other.getValue()));
  }

  public FheUint224 mul(FheUint224 other) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint224 other) {
    executeWithErrorHandling(() -> fhe_uint224_mul_assign(getValue(), other.getValue()));
  }

  public FheUint224 scalarAdd(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint224 scalarSub(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint224 scalarMul(U256 scalar) {
    FheUint224 result = new FheUint224();
    executeWithErrorHandling(() -> fhe_uint224_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint224_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint224_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
