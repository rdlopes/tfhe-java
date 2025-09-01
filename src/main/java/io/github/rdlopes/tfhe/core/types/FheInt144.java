package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt144 extends AddressLayoutPointer implements Cloneable {

  protected FheInt144() {
    super(FheInt144.class, TfheWrapper::fhe_int144_destroy);
  }

  public static FheInt144 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint144.getAddress()));
    return fheint144;
  }

  public static FheInt144 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint144.getAddress()));
    return fheint144;
  }

  public static FheInt144 encryptTrivial(I256 clearValue) {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_try_encrypt_trivial_i256(clearValue.getAddress(), fheint144.getAddress()));
    return fheint144;
  }

  public static FheInt144 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint144.getAddress()));
    return fheint144;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int144_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int144_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int144_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt144 add(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_add_assign(getValue(), other.getValue()));
  }

  public FheInt144 sub(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_sub_assign(getValue(), other.getValue()));
  }

  public FheInt144 mul(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_mul_assign(getValue(), other.getValue()));
  }


  public FheInt144 and(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt144 or(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt144 xor(FheInt144 other) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt144 other) {
    executeWithErrorHandling(() -> fhe_int144_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt144 scalarAdd(I256 scalar) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int144_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt144 scalarSub(I256 scalar) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int144_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt144 scalarMul(I256 scalar) {
    FheInt144 result = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int144_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt144 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int144_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt144 compress() {
    CompressedFheInt144 compressed = new CompressedFheInt144();
    executeWithErrorHandling(() -> fhe_int144_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt144 clone() {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> fhe_int144_clone(getValue(), fheint144.getAddress()));
    return fheint144;
  }
}
