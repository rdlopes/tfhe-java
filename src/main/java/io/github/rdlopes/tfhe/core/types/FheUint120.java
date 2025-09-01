package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint120 extends AddressLayoutPointer implements Cloneable {

  protected FheUint120() {
    super(FheUint120.class, TfheWrapper::fhe_uint120_destroy);
  }

  public static FheUint120 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), fheuint120.getAddress()));
    return fheuint120;
  }

  public static FheUint120 encryptWithPublicKey(U128 clearValue, PublicKey publicKey) {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_try_encrypt_with_public_key_u128(clearValue.getAddress(), publicKey.getValue(), fheuint120.getAddress()));
    return fheuint120;
  }

  public static FheUint120 encryptTrivial(U128 clearValue) {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_try_encrypt_trivial_u128(clearValue.getAddress(), fheuint120.getAddress()));
    return fheuint120;
  }

  public static FheUint120 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint120.getAddress()));
    return fheuint120;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint120_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U128 decryptWithClientKey(ClientKey clientKey) {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint120_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U128 decryptTrivial() {
    U128 clearValue = new U128();
    executeWithErrorHandling(() -> fhe_uint120_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint120 add(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_add_assign(getValue(), other.getValue()));
  }

  public FheUint120 sub(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_sub_assign(getValue(), other.getValue()));
  }

  public FheUint120 mul(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_mul_assign(getValue(), other.getValue()));
  }


  public FheUint120 and(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint120 or(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint120 xor(FheUint120 other) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint120 other) {
    executeWithErrorHandling(() -> fhe_uint120_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint120 scalarAdd(U128 scalar) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint120_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint120 scalarSub(U128 scalar) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint120_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint120 scalarMul(U128 scalar) {
    FheUint120 result = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U128 scalar) {
    executeWithErrorHandling(() -> fhe_uint120_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint120_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint120 compress() {
    CompressedFheUint120 compressed = new CompressedFheUint120();
    executeWithErrorHandling(() -> fhe_uint120_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint120 clone() {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> fhe_uint120_clone(getValue(), fheuint120.getAddress()));
    return fheuint120;
  }
}
