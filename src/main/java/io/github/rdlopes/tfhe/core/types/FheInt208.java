package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt208 extends AddressLayoutPointer implements Cloneable {

  protected FheInt208() {
    super(FheInt208.class, TfheWrapper::fhe_int208_destroy);
  }

  public static FheInt208
  encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt208 fhe = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt208
  encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt208 fhe = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt208 encryptTrivial(I256 clearValue) {
    FheInt208 fhe = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_try_encrypt_trivial_i256(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt208 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt208 fhe = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I256
  decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int208_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int208_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt208 and(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt208 or(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt208 xor(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt208 scalarAnd(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt208 scalarOr(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt208 scalarXor(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt208 compress() {
    CompressedFheInt208 compressed = new CompressedFheInt208();
    executeWithErrorHandling(() -> fhe_int208_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt208 clone() {
    FheInt208 cloned = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt208 add(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_add_assign(getValue(), other.getValue()));
  }

  public FheInt208 sub(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_sub_assign(getValue(), other.getValue()));
  }

  public FheInt208 mul(FheInt208 other) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt208 other) {
    executeWithErrorHandling(() -> fhe_int208_mul_assign(getValue(), other.getValue()));
  }

  public FheInt208 scalarAdd(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt208 scalarSub(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt208 scalarMul(I256 scalar) {
    FheInt208 result = new FheInt208();
    executeWithErrorHandling(() -> fhe_int208_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int208_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int208_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
