package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt1024 extends AddressLayoutPointer implements Cloneable {

  protected FheInt1024() {
    super(FheInt1024.class, TfheWrapper::fhe_int1024_destroy);
  }

  public static FheInt1024 encryptWithClientKey(I1024 clearValue, ClientKey clientKey) {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_try_encrypt_with_client_key_i1024(clearValue.getAddress(), clientKey.getValue(), fheint1024.getAddress()));
    return fheint1024;
  }

  public static FheInt1024 encryptWithPublicKey(I1024 clearValue, PublicKey publicKey) {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_try_encrypt_with_public_key_i1024(clearValue.getAddress(), publicKey.getValue(), fheint1024.getAddress()));
    return fheint1024;
  }

  public static FheInt1024 encryptTrivial(I1024 clearValue) {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_try_encrypt_trivial_i1024(clearValue.getAddress(), fheint1024.getAddress()));
    return fheint1024;
  }

  public static FheInt1024 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint1024.getAddress()));
    return fheint1024;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I1024 decryptWithClientKey(ClientKey clientKey) {
    I1024 clearValue = new I1024();
    executeWithErrorHandling(() -> fhe_int1024_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I1024 decryptTrivial() {
    I1024 clearValue = new I1024();
    executeWithErrorHandling(() -> fhe_int1024_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt1024 add(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_add_assign(getValue(), other.getValue()));
  }

  public FheInt1024 sub(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_sub_assign(getValue(), other.getValue()));
  }

  public FheInt1024 mul(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_mul_assign(getValue(), other.getValue()));
  }


  public FheInt1024 and(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt1024 or(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt1024 xor(FheInt1024 other) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt1024 other) {
    executeWithErrorHandling(() -> fhe_int1024_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt1024 scalarAdd(I1024 scalar) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int1024_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt1024 scalarSub(I1024 scalar) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int1024_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt1024 scalarMul(I1024 scalar) {
    FheInt1024 result = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I1024 scalar) {
    executeWithErrorHandling(() -> fhe_int1024_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int1024_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt1024 compress() {
    CompressedFheInt1024 compressed = new CompressedFheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt1024 clone() {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> fhe_int1024_clone(getValue(), fheint1024.getAddress()));
    return fheint1024;
  }
}
