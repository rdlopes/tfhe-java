package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint144 extends AddressLayoutPointer implements Cloneable {

  protected FheUint144() {
    super(FheUint144.class, TfheWrapper::fhe_uint144_destroy);
  }

  public static FheUint144
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint144 fhe = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint144
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint144 fhe = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint144 encryptTrivial(U256 clearValue) {
    FheUint144 fhe = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint144 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint144 fhe = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint144_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint144_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint144_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint144 and(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint144 or(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint144 xor(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint144 scalarAnd(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint144 scalarOr(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint144 scalarXor(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint144 compress() {
    CompressedFheUint144 compressed = new CompressedFheUint144();
    executeWithErrorHandling(() -> fhe_uint144_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint144 clone() {
    FheUint144 cloned = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint144 add(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_add_assign(getValue(), other.getValue()));
  }

  public FheUint144 sub(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_sub_assign(getValue(), other.getValue()));
  }

  public FheUint144 mul(FheUint144 other) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint144 other) {
    executeWithErrorHandling(() -> fhe_uint144_mul_assign(getValue(), other.getValue()));
  }

  public FheUint144 scalarAdd(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint144 scalarSub(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint144 scalarMul(U256 scalar) {
    FheUint144 result = new FheUint144();
    executeWithErrorHandling(() -> fhe_uint144_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint144_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint144_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
