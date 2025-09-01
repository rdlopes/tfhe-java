package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt2048 extends AddressLayoutPointer implements Cloneable {

  protected FheInt2048() {
    super(FheInt2048.class, TfheWrapper::fhe_int2048_destroy);
  }

  public static FheInt2048 encryptWithClientKey(I2048 clearValue, ClientKey clientKey) {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_try_encrypt_with_client_key_i2048(clearValue.getAddress(), clientKey.getValue(), fheInt2048.getAddress()));
    return fheInt2048;
  }

  public static FheInt2048 encryptWithPublicKey(I2048 clearValue, PublicKey publicKey) {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_try_encrypt_with_public_key_i2048(clearValue.getAddress(), publicKey.getValue(), fheInt2048.getAddress()));
    return fheInt2048;
  }

  public static FheInt2048 encryptTrivial(I2048 clearValue) {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_try_encrypt_trivial_i2048(clearValue.getAddress(), fheInt2048.getAddress()));
    return fheInt2048;
  }

  public static FheInt2048 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheInt2048.getAddress()));
    return fheInt2048;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public I2048 decryptWithClientKey(ClientKey clientKey) {
    I2048 i2048 = new I2048();
    executeWithErrorHandling(() -> fhe_int2048_decrypt(getValue(), clientKey.getValue(), i2048.getAddress()));
    return i2048;
  }

  public I2048 decryptTrivial() {
    I2048 i2048 = new I2048();
    executeWithErrorHandling(() -> fhe_int2048_try_decrypt_trivial(getValue(), i2048.getAddress()));
    return i2048;
  }

  public FheInt2048 add(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_add_assign(getValue(), other.getValue()));
  }

  public FheInt2048 sub(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_sub_assign(getValue(), other.getValue()));
  }

  public FheInt2048 mul(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_mul_assign(getValue(), other.getValue()));
  }

  public FheInt2048 and(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt2048 or(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt2048 xor(FheInt2048 other) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt2048 other) {
    executeWithErrorHandling(() -> fhe_int2048_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt2048 scalarAdd(I2048 scalar) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I2048 scalar) {
    executeWithErrorHandling(() -> fhe_int2048_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt2048 scalarSub(I2048 scalar) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I2048 scalar) {
    executeWithErrorHandling(() -> fhe_int2048_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt2048 scalarMul(I2048 scalar) {
    FheInt2048 result = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I2048 scalar) {
    executeWithErrorHandling(() -> fhe_int2048_scalar_mul_assign(getValue(), scalar.getAddress()));
  }

  public FheBool eq(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2048_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public CompressedFheInt2048 compress() {
    CompressedFheInt2048 compressed = new CompressedFheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt2048 clone() {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> fhe_int2048_clone(getValue(), fheInt2048.getAddress()));
    return fheInt2048;
  }

}
