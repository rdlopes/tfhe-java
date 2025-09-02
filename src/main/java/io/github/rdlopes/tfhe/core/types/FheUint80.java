package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint80 extends AddressLayoutPointer implements Cloneable {

  protected FheUint80() {
    super(FheUint80.class, TfheWrapper::fhe_uint80_destroy);
  }

  public static FheUint80
  encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint80 fhe = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint80
  encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint80 fhe = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint80 encryptTrivial(U128 clearValue) {
    FheUint80 fhe = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_try_encrypt_trivial_u128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint80 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint80 fhe = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public U128
  decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint80_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint80_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheUint80 and(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint80 or(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint80 xor(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint80 scalarAnd(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheUint80 scalarOr(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheUint80 scalarXor(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheUint80 compress() {
    CompressedFheUint80 compressed = new CompressedFheUint80();
    executeWithErrorHandling(() -> fhe_uint80_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint80 clone() {
    FheUint80 cloned = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint80 add(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_add_assign(getValue(), other.getValue()));
  }

  public FheUint80 sub(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_sub_assign(getValue(), other.getValue()));
  }

  public FheUint80 mul(FheUint80 other) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint80 other) {
    executeWithErrorHandling(() -> fhe_uint80_mul_assign(getValue(), other.getValue()));
  }

  public FheUint80 scalarAdd(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint80 scalarSub(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint80 scalarMul(U128 scalar) {
    FheUint80 result = new FheUint80();
    executeWithErrorHandling(() -> fhe_uint80_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint80_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint80_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
