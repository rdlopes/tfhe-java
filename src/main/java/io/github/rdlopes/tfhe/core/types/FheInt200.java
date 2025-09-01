package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt200 extends AddressLayoutPointer implements Cloneable {

  protected FheInt200() {
    super(FheInt200.class, TfheWrapper::fhe_int200_destroy);
  }

  public static FheInt200 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint200.getAddress()));
    return fheint200;
  }

  public static FheInt200 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint200.getAddress()));
    return fheint200;
  }

  public static FheInt200 encryptTrivial(I256 clearValue) {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_try_encrypt_trivial_i256(clearValue.getAddress(), fheint200.getAddress()));
    return fheint200;
  }

  public static FheInt200 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint200.getAddress()));
    return fheint200;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int200_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int200_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int200_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt200 add(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_add_assign(getValue(), other.getValue()));
  }

  public FheInt200 sub(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_sub_assign(getValue(), other.getValue()));
  }

  public FheInt200 mul(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_mul_assign(getValue(), other.getValue()));
  }


  public FheInt200 and(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt200 or(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt200 xor(FheInt200 other) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt200 other) {
    executeWithErrorHandling(() -> fhe_int200_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt200 scalarAdd(I256 scalar) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int200_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt200 scalarSub(I256 scalar) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int200_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt200 scalarMul(I256 scalar) {
    FheInt200 result = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int200_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt200 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int200_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt200 compress() {
    CompressedFheInt200 compressed = new CompressedFheInt200();
    executeWithErrorHandling(() -> fhe_int200_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt200 clone() {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> fhe_int200_clone(getValue(), fheint200.getAddress()));
    return fheint200;
  }
}
