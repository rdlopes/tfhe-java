package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt72 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt72() {
    super(FheInt72.class, TfheWrapper::fhe_int72_destroy);
  }

  public static FheInt72
  encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt72 fhe = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt72
  encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt72 fhe = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt72 encryptTrivial(I128 clearValue) {
    FheInt72 fhe = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_try_encrypt_trivial_i128(clearValue.getAddress(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt72 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt72 fhe = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I128
  decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int72_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int72_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public FheInt72 and(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt72 or(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt72 xor(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt72 scalarAnd(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_bitand(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAndAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_bitand_assign(getValue(), scalar.getAddress()));
  }

  public FheInt72 scalarOr(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_bitor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarOrAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_bitor_assign(getValue(), scalar.getAddress()));
  }

  public FheInt72 scalarXor(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_bitxor(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarXorAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_bitxor_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt72 compress() {
    CompressedFheInt72 compressed = new CompressedFheInt72();
    executeWithErrorHandling(() -> fhe_int72_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt72 clone() {
    FheInt72 cloned = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt72 add(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_add_assign(getValue(), other.getValue()));
  }

  public FheInt72 sub(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_sub_assign(getValue(), other.getValue()));
  }

  public FheInt72 mul(FheInt72 other) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt72 other) {
    executeWithErrorHandling(() -> fhe_int72_mul_assign(getValue(), other.getValue()));
  }

  public FheInt72 scalarAdd(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt72 scalarSub(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt72 scalarMul(I128 scalar) {
    FheInt72 result = new FheInt72();
    executeWithErrorHandling(() -> fhe_int72_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int72_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool ge(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt72 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int72_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


}
