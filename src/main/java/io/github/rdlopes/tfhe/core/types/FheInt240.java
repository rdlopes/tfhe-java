package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt240 extends AddressLayoutPointer implements Cloneable {

  protected FheInt240() {
    super(FheInt240.class, TfheWrapper::fhe_int240_destroy);
  }

  public static FheInt240 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt240 fheint240 = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint240.getAddress()));
    return fheint240;
  }

  public static FheInt240 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt240 fheint240 = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint240.getAddress()));
    return fheint240;
  }

  public static FheInt240 encryptTrivial(I256 clearValue) {
    FheInt240 fheint240 = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_try_encrypt_trivial_i256(clearValue.getAddress(), fheint240.getAddress()));
    return fheint240;
  }

  public static FheInt240 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt240 fheint240 = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint240.getAddress()));
    return fheint240;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int240_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int240_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt240 add(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_add_assign(getValue(), other.getValue()));
  }

  public FheInt240 sub(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_sub_assign(getValue(), other.getValue()));
  }

  public FheInt240 mul(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_mul_assign(getValue(), other.getValue()));
  }


  public FheInt240 and(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt240 or(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt240 xor(FheInt240 other) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt240 other) {
    executeWithErrorHandling(() -> fhe_int240_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt240 scalarAdd(I256 scalar) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int240_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt240 scalarSub(I256 scalar) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int240_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt240 scalarMul(I256 scalar) {
    FheInt240 result = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int240_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt240 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int240_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt240 compress() {
    CompressedFheInt240 compressed = new CompressedFheInt240();
    executeWithErrorHandling(() -> fhe_int240_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt240 clone() {
    FheInt240 fheint240 = new FheInt240();
    executeWithErrorHandling(() -> fhe_int240_clone(getValue(), fheint240.getAddress()));
    return fheint240;
  }
}
