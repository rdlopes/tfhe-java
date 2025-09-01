package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint2048 extends AddressLayoutPointer implements Cloneable {

  protected FheUint2048() {
    super(FheUint2048.class, TfheWrapper::fhe_uint2048_destroy);
  }

  public static FheUint2048 encryptWithClientKey(U2048 clearValue, ClientKey clientKey) {
    FheUint2048 fheuint2048 = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_try_encrypt_with_client_key_u2048(clearValue.getAddress(), clientKey.getValue(), fheuint2048.getAddress()));
    return fheuint2048;
  }

  public static FheUint2048 encryptWithPublicKey(U2048 clearValue, PublicKey publicKey) {
    FheUint2048 fheuint2048 = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_try_encrypt_with_public_key_u2048(clearValue.getAddress(), publicKey.getValue(), fheuint2048.getAddress()));
    return fheuint2048;
  }

  public static FheUint2048 encryptTrivial(U2048 clearValue) {
    FheUint2048 fheuint2048 = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_try_encrypt_trivial_u2048(clearValue.getAddress(), fheuint2048.getAddress()));
    return fheuint2048;
  }

  public static FheUint2048 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint2048 fheuint2048 = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint2048.getAddress()));
    return fheuint2048;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U2048 decryptWithClientKey(ClientKey clientKey) {
    U2048 clearValue = new U2048();
    executeWithErrorHandling(() -> fhe_uint2048_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U2048 decryptTrivial() {
    U2048 clearValue = new U2048();
    executeWithErrorHandling(() -> fhe_uint2048_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint2048 add(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_add_assign(getValue(), other.getValue()));
  }

  public FheUint2048 sub(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_sub_assign(getValue(), other.getValue()));
  }

  public FheUint2048 mul(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_mul_assign(getValue(), other.getValue()));
  }


  public FheUint2048 and(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint2048 or(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint2048 xor(FheUint2048 other) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint2048 other) {
    executeWithErrorHandling(() -> fhe_uint2048_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint2048 scalarAdd(U2048 scalar) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U2048 scalar) {
    executeWithErrorHandling(() -> fhe_uint2048_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint2048 scalarSub(U2048 scalar) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U2048 scalar) {
    executeWithErrorHandling(() -> fhe_uint2048_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint2048 scalarMul(U2048 scalar) {
    FheUint2048 result = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U2048 scalar) {
    executeWithErrorHandling(() -> fhe_uint2048_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint2048 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U2048 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2048_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint2048 compress() {
    CompressedFheUint2048 compressed = new CompressedFheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint2048 clone() {
    FheUint2048 fheuint2048 = new FheUint2048();
    executeWithErrorHandling(() -> fhe_uint2048_clone(getValue(), fheuint2048.getAddress()));
    return fheuint2048;
  }
}
