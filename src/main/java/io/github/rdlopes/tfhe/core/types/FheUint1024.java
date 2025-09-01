package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint1024 extends AddressLayoutPointer implements Cloneable {

  protected FheUint1024() {
    super(FheUint1024.class, TfheWrapper::fhe_uint1024_destroy);
  }

  public static FheUint1024 encryptWithClientKey(U1024 clearValue, ClientKey clientKey) {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_try_encrypt_with_client_key_u1024(clearValue.getAddress(), clientKey.getValue(), fheuint1024.getAddress()));
    return fheuint1024;
  }

  public static FheUint1024 encryptWithPublicKey(U1024 clearValue, PublicKey publicKey) {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_try_encrypt_with_public_key_u1024(clearValue.getAddress(), publicKey.getValue(), fheuint1024.getAddress()));
    return fheuint1024;
  }

  public static FheUint1024 encryptTrivial(U1024 clearValue) {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_try_encrypt_trivial_u1024(clearValue.getAddress(), fheuint1024.getAddress()));
    return fheuint1024;
  }

  public static FheUint1024 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint1024.getAddress()));
    return fheuint1024;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U1024 decryptWithClientKey(ClientKey clientKey) {
    U1024 clearValue = new U1024();
    executeWithErrorHandling(() -> fhe_uint1024_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U1024 decryptTrivial() {
    U1024 clearValue = new U1024();
    executeWithErrorHandling(() -> fhe_uint1024_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint1024 add(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_add_assign(getValue(), other.getValue()));
  }

  public FheUint1024 sub(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_sub_assign(getValue(), other.getValue()));
  }

  public FheUint1024 mul(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_mul_assign(getValue(), other.getValue()));
  }


  public FheUint1024 and(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint1024 or(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint1024 xor(FheUint1024 other) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint1024 other) {
    executeWithErrorHandling(() -> fhe_uint1024_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint1024 scalarAdd(U1024 scalar) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U1024 scalar) {
    executeWithErrorHandling(() -> fhe_uint1024_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint1024 scalarSub(U1024 scalar) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U1024 scalar) {
    executeWithErrorHandling(() -> fhe_uint1024_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint1024 scalarMul(U1024 scalar) {
    FheUint1024 result = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U1024 scalar) {
    executeWithErrorHandling(() -> fhe_uint1024_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint1024 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U1024 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint1024_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint1024 compress() {
    CompressedFheUint1024 compressed = new CompressedFheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint1024 clone() {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> fhe_uint1024_clone(getValue(), fheuint1024.getAddress()));
    return fheuint1024;
  }
}
