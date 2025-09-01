package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt152 extends AddressLayoutPointer implements Cloneable {

  protected FheInt152() {
    super(FheInt152.class, TfheWrapper::fhe_int152_destroy);
  }

  public static FheInt152 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt152 fheint152 = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint152.getAddress()));
    return fheint152;
  }

  public static FheInt152 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt152 fheint152 = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint152.getAddress()));
    return fheint152;
  }

  public static FheInt152 encryptTrivial(I256 clearValue) {
    FheInt152 fheint152 = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_try_encrypt_trivial_i256(clearValue.getAddress(), fheint152.getAddress()));
    return fheint152;
  }

  public static FheInt152 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt152 fheint152 = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint152.getAddress()));
    return fheint152;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int152_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int152_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt152 add(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_add_assign(getValue(), other.getValue()));
  }

  public FheInt152 sub(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_sub_assign(getValue(), other.getValue()));
  }

  public FheInt152 mul(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_mul_assign(getValue(), other.getValue()));
  }


  public FheInt152 and(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt152 or(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt152 xor(FheInt152 other) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt152 other) {
    executeWithErrorHandling(() -> fhe_int152_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt152 scalarAdd(I256 scalar) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int152_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt152 scalarSub(I256 scalar) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int152_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt152 scalarMul(I256 scalar) {
    FheInt152 result = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int152_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt152 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int152_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt152 compress() {
    CompressedFheInt152 compressed = new CompressedFheInt152();
    executeWithErrorHandling(() -> fhe_int152_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt152 clone() {
    FheInt152 fheint152 = new FheInt152();
    executeWithErrorHandling(() -> fhe_int152_clone(getValue(), fheint152.getAddress()));
    return fheint152;
  }
}
