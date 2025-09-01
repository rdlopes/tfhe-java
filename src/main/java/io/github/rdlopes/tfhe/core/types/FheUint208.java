package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint208 extends AddressLayoutPointer implements Cloneable {

  protected FheUint208() {
    super(FheUint208.class, TfheWrapper::fhe_uint208_destroy);
  }

  public static FheUint208 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint208 fheuint208 = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fheuint208.getAddress()));
    return fheuint208;
  }

  public static FheUint208 encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint208 fheuint208 = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fheuint208.getAddress()));
    return fheuint208;
  }

  public static FheUint208 encryptTrivial(U256 clearValue) {
    FheUint208 fheuint208 = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_try_encrypt_trivial_u256(clearValue.getAddress(), fheuint208.getAddress()));
    return fheuint208;
  }

  public static FheUint208 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint208 fheuint208 = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint208.getAddress()));
    return fheuint208;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U256 decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint208_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint208_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint208 add(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_add_assign(getValue(), other.getValue()));
  }

  public FheUint208 sub(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_sub_assign(getValue(), other.getValue()));
  }

  public FheUint208 mul(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_mul_assign(getValue(), other.getValue()));
  }


  public FheUint208 and(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint208 or(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint208 xor(FheUint208 other) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint208 other) {
    executeWithErrorHandling(() -> fhe_uint208_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint208 scalarAdd(U256 scalar) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint208_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint208 scalarSub(U256 scalar) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint208_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint208 scalarMul(U256 scalar) {
    FheUint208 result = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint208_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint208 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint208_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint208 compress() {
    CompressedFheUint208 compressed = new CompressedFheUint208();
    executeWithErrorHandling(() -> fhe_uint208_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint208 clone() {
    FheUint208 fheuint208 = new FheUint208();
    executeWithErrorHandling(() -> fhe_uint208_clone(getValue(), fheuint208.getAddress()));
    return fheuint208;
  }
}
