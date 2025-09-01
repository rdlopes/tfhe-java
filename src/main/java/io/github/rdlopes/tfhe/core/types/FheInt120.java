package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt120 extends AddressLayoutPointer implements Cloneable {

  protected FheInt120() {
    super(FheInt120.class, TfheWrapper::fhe_int120_destroy);
  }

  public static FheInt120 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fheint120.getAddress()));
    return fheint120;
  }

  public static FheInt120 encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fheint120.getAddress()));
    return fheint120;
  }

  public static FheInt120 encryptTrivial(I128 clearValue) {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_try_encrypt_trivial_i128(clearValue.getAddress(), fheint120.getAddress()));
    return fheint120;
  }

  public static FheInt120 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint120.getAddress()));
    return fheint120;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int120_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I128 decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int120_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int120_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt120 add(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_add_assign(getValue(), other.getValue()));
  }

  public FheInt120 sub(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_sub_assign(getValue(), other.getValue()));
  }

  public FheInt120 mul(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_mul_assign(getValue(), other.getValue()));
  }


  public FheInt120 and(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt120 or(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt120 xor(FheInt120 other) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt120 other) {
    executeWithErrorHandling(() -> fhe_int120_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt120 scalarAdd(I128 scalar) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int120_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt120 scalarSub(I128 scalar) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int120_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt120 scalarMul(I128 scalar) {
    FheInt120 result = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int120_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt120 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int120_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt120 compress() {
    CompressedFheInt120 compressed = new CompressedFheInt120();
    executeWithErrorHandling(() -> fhe_int120_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt120 clone() {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> fhe_int120_clone(getValue(), fheint120.getAddress()));
    return fheint120;
  }
}
