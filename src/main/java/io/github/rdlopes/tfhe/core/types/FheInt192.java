package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt192 extends AddressLayoutPointer implements Cloneable {

  protected FheInt192() {
    super(FheInt192.class, TfheWrapper::fhe_int192_destroy);
  }

  public static FheInt192
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt192 fhe = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt192
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt192 fhe = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt192 encryptTrivial(I256 clearValue) {
    FheInt192 fhe = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt192 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt192 fhe = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int192_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int192_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt192 and(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt192 or(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt192 xor(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt192 scalarAnd(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt192 scalarOr(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt192 scalarXor(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt192 compress() {
    CompressedFheInt192 compressed = new CompressedFheInt192();
    executeWithErrorHandling(() -> fhe_int192_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt192 clone() {
    FheInt192 cloned = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt192 add(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_add_assign(getValue(), other.getValue()));
  }

  public FheInt192 sub(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_sub_assign(getValue(), other.getValue()));
  }

  public FheInt192 mul(FheInt192 other) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt192 other) {
    executeWithErrorHandling(() -> fhe_int192_mul_assign(getValue(), other.getValue()));
  }

  public FheInt192 scalarAdd(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt192 scalarSub(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt192 scalarMul(I256 scalar) {
    FheInt192 result = new FheInt192();
    executeWithErrorHandling(() -> fhe_int192_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int192_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int192_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
