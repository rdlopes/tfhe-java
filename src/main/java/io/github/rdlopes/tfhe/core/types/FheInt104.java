package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt104 extends AddressLayoutPointer implements Cloneable {

  protected FheInt104() {
    super(FheInt104.class, TfheWrapper::fhe_int104_destroy);
  }

  public static FheInt104 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fheint104.getAddress()));
    return fheint104;
  }

  public static FheInt104 encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fheint104.getAddress()));
    return fheint104;
  }

  public static FheInt104 encryptTrivial(I128 clearValue) {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_try_encrypt_trivial_i128(clearValue.getAddress(), fheint104.getAddress()));
    return fheint104;
  }

  public static FheInt104 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint104.getAddress()));
    return fheint104;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I128 decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int104_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int104_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt104 add(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_add_assign(getValue(), other.getValue()));
  }

  public FheInt104 sub(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_sub_assign(getValue(), other.getValue()));
  }

  public FheInt104 mul(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_mul_assign(getValue(), other.getValue()));
  }


  public FheInt104 and(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt104 or(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt104 xor(FheInt104 other) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt104 other) {
    executeWithErrorHandling(() -> fhe_int104_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt104 scalarAdd(I128 scalar) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int104_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt104 scalarSub(I128 scalar) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int104_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt104 scalarMul(I128 scalar) {
    FheInt104 result = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int104_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt104 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int104_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt104 compress() {
    CompressedFheInt104 compressed = new CompressedFheInt104();
    executeWithErrorHandling(() -> fhe_int104_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt104 clone() {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> fhe_int104_clone(getValue(), fheint104.getAddress()));
    return fheint104;
  }
}
