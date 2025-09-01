package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt136 extends AddressLayoutPointer implements Cloneable {

  protected FheInt136() {
    super(FheInt136.class, TfheWrapper::fhe_int136_destroy);
  }

  public static FheInt136 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint136.getAddress()));
    return fheint136;
  }

  public static FheInt136 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint136.getAddress()));
    return fheint136;
  }

  public static FheInt136 encryptTrivial(I256 clearValue) {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_try_encrypt_trivial_i256(clearValue.getAddress(), fheint136.getAddress()));
    return fheint136;
  }

  public static FheInt136 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint136.getAddress()));
    return fheint136;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int136_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int136_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt136 add(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_add_assign(getValue(), other.getValue()));
  }

  public FheInt136 sub(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_sub_assign(getValue(), other.getValue()));
  }

  public FheInt136 mul(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_mul_assign(getValue(), other.getValue()));
  }


  public FheInt136 and(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt136 or(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt136 xor(FheInt136 other) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt136 other) {
    executeWithErrorHandling(() -> fhe_int136_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt136 scalarAdd(I256 scalar) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int136_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt136 scalarSub(I256 scalar) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int136_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt136 scalarMul(I256 scalar) {
    FheInt136 result = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int136_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt136 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int136_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt136 compress() {
    CompressedFheInt136 compressed = new CompressedFheInt136();
    executeWithErrorHandling(() -> fhe_int136_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt136 clone() {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> fhe_int136_clone(getValue(), fheint136.getAddress()));
    return fheint136;
  }
}
