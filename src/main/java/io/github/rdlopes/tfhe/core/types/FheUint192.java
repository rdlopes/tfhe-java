package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint192 extends AddressLayoutPointer implements Cloneable {

  protected FheUint192() {
    super(FheUint192.class, TfheWrapper::fhe_uint192_destroy);
  }

  public static FheUint192 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fheuint192.getAddress()));
    return fheuint192;
  }

  public static FheUint192 encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fheuint192.getAddress()));
    return fheuint192;
  }

  public static FheUint192 encryptTrivial(U256 clearValue) {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_try_encrypt_trivial_u256(clearValue.getAddress(), fheuint192.getAddress()));
    return fheuint192;
  }

  public static FheUint192 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint192.getAddress()));
    return fheuint192;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U256 decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint192_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint192_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint192 add(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_add_assign(getValue(), other.getValue()));
  }

  public FheUint192 sub(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_sub_assign(getValue(), other.getValue()));
  }

  public FheUint192 mul(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_mul_assign(getValue(), other.getValue()));
  }


  public FheUint192 and(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint192 or(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint192 xor(FheUint192 other) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint192 other) {
    executeWithErrorHandling(() -> fhe_uint192_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint192 scalarAdd(U256 scalar) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint192_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint192 scalarSub(U256 scalar) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint192_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint192 scalarMul(U256 scalar) {
    FheUint192 result = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint192_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint192 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint192_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint192 compress() {
    CompressedFheUint192 compressed = new CompressedFheUint192();
    executeWithErrorHandling(() -> fhe_uint192_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint192 clone() {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> fhe_uint192_clone(getValue(), fheuint192.getAddress()));
    return fheuint192;
  }
}
