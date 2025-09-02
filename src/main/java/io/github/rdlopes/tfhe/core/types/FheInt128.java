package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt128 extends AddressLayoutPointer implements Cloneable {

  protected FheInt128() {
    super(FheInt128.class, TfheWrapper::fhe_int128_destroy);
  }

  public static FheInt128
  encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt128 fhe = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt128
  encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt128 fhe = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt128 encryptTrivial(I128 clearValue) {
    FheInt128 fhe = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_try_encrypt_trivial_i128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt128 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt128 fhe = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I128
  decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int128_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int128_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt128 and(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt128 or(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt128 xor(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt128 scalarAnd(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt128 scalarOr(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt128 scalarXor(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt128 compress() {
    CompressedFheInt128 compressed = new CompressedFheInt128();
    executeWithErrorHandling(() -> fhe_int128_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt128 clone() {
    FheInt128 cloned = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt128 add(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_add_assign(getValue(), other.getValue()));
  }

  public FheInt128 sub(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_sub_assign(getValue(), other.getValue()));
  }

  public FheInt128 mul(FheInt128 other) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt128 other) {
    executeWithErrorHandling(() -> fhe_int128_mul_assign(getValue(), other.getValue()));
  }

  public FheInt128 scalarAdd(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt128 scalarSub(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt128 scalarMul(I128 scalar) {
    FheInt128 result = new FheInt128();
    executeWithErrorHandling(() -> fhe_int128_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int128_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt128 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int128_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
