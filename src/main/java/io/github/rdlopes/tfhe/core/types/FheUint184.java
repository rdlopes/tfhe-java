package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint184 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint184() {
    super(FheUint184.class, TfheWrapper::fhe_uint184_destroy);
  }

  public static FheUint184
  encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint184 fhe = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint184
  encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint184 fhe = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint184 encryptTrivial(U256 clearValue) {
    FheUint184 fhe = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_try_encrypt_trivial_u256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint184 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint184 fhe = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U256
  decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint184_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint184_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint184 and(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint184 or(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint184 xor(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint184 scalarAnd(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint184 scalarOr(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint184 scalarXor(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint184 compress() {
    CompressedFheUint184 compressed = new CompressedFheUint184();
    executeWithErrorHandling(() -> fhe_uint184_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint184 clone() {
    FheUint184 cloned = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint184 add(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_add_assign(getValue(), other.getValue()));
  }

  public FheUint184 sub(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_sub_assign(getValue(), other.getValue()));
  }

  public FheUint184 mul(FheUint184 other) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint184 other) {
    executeWithErrorHandling(() -> fhe_uint184_mul_assign(getValue(), other.getValue()));
  }

  public FheUint184 scalarAdd(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint184 scalarSub(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint184 scalarMul(U256 scalar) {
    FheUint184 result = new FheUint184();
    executeWithErrorHandling(() -> fhe_uint184_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint184_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint184 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint184_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
