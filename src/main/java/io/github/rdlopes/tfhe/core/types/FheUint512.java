package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint512 extends AddressLayoutPointer implements Cloneable {

  protected FheUint512() {
    super(FheUint512.class, TfheWrapper::fhe_uint512_destroy);
  }

  public static FheUint512 encryptWithClientKey(U512 clearValue, ClientKey clientKey) {
    FheUint512 fheuint512 = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_try_encrypt_with_client_key_u512(clearValue.getAddress(), clientKey.getValue(), fheuint512.getAddress()));
    return fheuint512;
  }

  public static FheUint512 encryptWithPublicKey(U512 clearValue, PublicKey publicKey) {
    FheUint512 fheuint512 = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_try_encrypt_with_public_key_u512(clearValue.getAddress(), publicKey.getValue(), fheuint512.getAddress()));
    return fheuint512;
  }

  public static FheUint512 encryptTrivial(U512 clearValue) {
    FheUint512 fheuint512 = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_try_encrypt_trivial_u512(clearValue.getAddress(), fheuint512.getAddress()));
    return fheuint512;
  }

  public static FheUint512 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint512 fheuint512 = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint512.getAddress()));
    return fheuint512;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U512 decryptWithClientKey(ClientKey clientKey) {
    U512 clearValue = new U512();
    executeWithErrorHandling(() -> fhe_uint512_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U512 decryptTrivial() {
    U512 clearValue = new U512();
    executeWithErrorHandling(() -> fhe_uint512_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint512 add(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_add_assign(getValue(), other.getValue()));
  }

  public FheUint512 sub(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_sub_assign(getValue(), other.getValue()));
  }

  public FheUint512 mul(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_mul_assign(getValue(), other.getValue()));
  }


  public FheUint512 and(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint512 or(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint512 xor(FheUint512 other) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint512 other) {
    executeWithErrorHandling(() -> fhe_uint512_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint512 scalarAdd(U512 scalar) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U512 scalar) {
    executeWithErrorHandling(() -> fhe_uint512_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint512 scalarSub(U512 scalar) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U512 scalar) {
    executeWithErrorHandling(() -> fhe_uint512_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint512 scalarMul(U512 scalar) {
    FheUint512 result = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U512 scalar) {
    executeWithErrorHandling(() -> fhe_uint512_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint512 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U512 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint512_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint512 compress() {
    CompressedFheUint512 compressed = new CompressedFheUint512();
    executeWithErrorHandling(() -> fhe_uint512_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint512 clone() {
    FheUint512 fheuint512 = new FheUint512();
    executeWithErrorHandling(() -> fhe_uint512_clone(getValue(), fheuint512.getAddress()));
    return fheuint512;
  }
}
