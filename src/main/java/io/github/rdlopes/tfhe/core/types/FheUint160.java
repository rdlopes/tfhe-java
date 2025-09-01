package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheUint160 extends AddressLayoutPointer implements Cloneable {

  protected FheUint160() {
    super(FheUint160.class, TfheWrapper::fhe_uint160_destroy);
  }

  public static FheUint160 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), fheuint160.getAddress()));
    return fheuint160;
  }

  public static FheUint160 encryptWithPublicKey(U256 clearValue, PublicKey publicKey) {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_try_encrypt_with_public_key_u256(clearValue.getAddress(), publicKey.getValue(), fheuint160.getAddress()));
    return fheuint160;
  }

  public static FheUint160 encryptTrivial(U256 clearValue) {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_try_encrypt_trivial_u256(clearValue.getAddress(), fheuint160.getAddress()));
    return fheuint160;
  }

  public static FheUint160 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint160.getAddress()));
    return fheuint160;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint160_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public U256 decryptWithClientKey(ClientKey clientKey) {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint160_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public U256 decryptTrivial() {
    U256 clearValue = new U256();
    executeWithErrorHandling(() -> fhe_uint160_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheUint160 add(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_add_assign(getValue(), other.getValue()));
  }

  public FheUint160 sub(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_sub_assign(getValue(), other.getValue()));
  }

  public FheUint160 mul(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_mul_assign(getValue(), other.getValue()));
  }


  public FheUint160 and(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint160 or(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint160 xor(FheUint160 other) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint160 other) {
    executeWithErrorHandling(() -> fhe_uint160_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint160 scalarAdd(U256 scalar) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint160_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheUint160 scalarSub(U256 scalar) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint160_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheUint160 scalarMul(U256 scalar) {
    FheUint160 result = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(U256 scalar) {
    executeWithErrorHandling(() -> fhe_uint160_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(U256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint160_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheUint160 compress() {
    CompressedFheUint160 compressed = new CompressedFheUint160();
    executeWithErrorHandling(() -> fhe_uint160_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint160 clone() {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> fhe_uint160_clone(getValue(), fheuint160.getAddress()));
    return fheuint160;
  }
}
