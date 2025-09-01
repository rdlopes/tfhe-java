package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint136 extends AddressLayoutPointer implements Cloneable {

  protected FheUint136() {
    super(FheUint136.class, TfheWrapper::fhe_uint136_destroy);
  }

  public static FheUint136 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fheuint136.getAddress()));
    return fheuint136;
  }

  public static FheUint136 encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fheuint136.getAddress()));
    return fheuint136;
  }

  public static FheUint136 encryptTrivial(U256 clearValue) {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_try_encrypt_trivial_u256(clearValue.getAddress(), fheuint136.getAddress()));
    return fheuint136;
  }

  public static FheUint136 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint136.getAddress()));
    return fheuint136;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U256 decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint136_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint136_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint136 add(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_add_assign(getValue(), other.getValue()));
  }

  public FheUint136 sub(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_sub_assign(getValue(), other.getValue()));
  }

  public FheUint136 mul(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_mul_assign(getValue(), other.getValue()));
  }


  public FheUint136 and(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint136 or(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint136 xor(FheUint136 other) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint136 other) {
    executeWithErrorHandling(() -> fhe_uint136_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint136 scalarAdd(U256 scalar) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint136_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint136 scalarSub(U256 scalar) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint136_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint136 scalarMul(U256 scalar) {
    FheUint136 result = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint136_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint136_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint136 compress() {
    CompressedFheUint136 compressed = new CompressedFheUint136();
    executeWithErrorHandling(() -> fhe_uint136_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint136 clone() {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> fhe_uint136_clone(getValue(), fheuint136.getAddress()));
    return fheuint136;
  }
}
