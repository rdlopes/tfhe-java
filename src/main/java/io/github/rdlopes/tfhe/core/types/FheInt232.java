package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt232 extends AddressLayoutPointer implements Cloneable {

  protected FheInt232() {
    super(FheInt232.class, TfheWrapper::fhe_int232_destroy);
  }

  public static FheInt232
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt232 fhe = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt232
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt232 fhe = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt232 encryptTrivial(I256 clearValue) {
    FheInt232 fhe = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt232 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt232 fhe = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int232_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int232_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt232 and(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt232 or(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt232 xor(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt232 scalarAnd(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt232 scalarOr(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt232 scalarXor(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt232 compress() {
    CompressedFheInt232 compressed = new CompressedFheInt232();
    executeWithErrorHandling(() -> fhe_int232_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt232 clone() {
    FheInt232 cloned = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt232 add(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_add_assign(getValue(), other.getValue()));
  }

  public FheInt232 sub(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_sub_assign(getValue(), other.getValue()));
  }

  public FheInt232 mul(FheInt232 other) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt232 other) {
    executeWithErrorHandling(() -> fhe_int232_mul_assign(getValue(), other.getValue()));
  }

  public FheInt232 scalarAdd(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt232 scalarSub(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt232 scalarMul(I256 scalar) {
    FheInt232 result = new FheInt232();
    executeWithErrorHandling(() -> fhe_int232_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int232_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt232 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int232_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
