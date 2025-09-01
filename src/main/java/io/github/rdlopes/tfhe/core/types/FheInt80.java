package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt80 extends AddressLayoutPointer implements Cloneable {

  protected FheInt80() {
    super(FheInt80.class, TfheWrapper::fhe_int80_destroy);
  }

  public static FheInt80 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), fheint80.getAddress()));
    return fheint80;
  }

  public static FheInt80 encryptWithPublicKey(I128 clearValue, PublicKey publicKey) {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_try_encrypt_with_public_key_i128(clearValue.getAddress(), publicKey.getValue(), fheint80.getAddress()));
    return fheint80;
  }

  public static FheInt80 encryptTrivial(I128 clearValue) {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_try_encrypt_trivial_i128(clearValue.getAddress(), fheint80.getAddress()));
    return fheint80;
  }

  public static FheInt80 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint80.getAddress()));
    return fheint80;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I128 decryptWithClientKey(ClientKey clientKey) {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int80_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I128 decryptTrivial() {
    I128 clearValue = new I128();
    executeWithErrorHandling(() -> fhe_int80_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt80 add(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_add_assign(getValue(), other.getValue()));
  }

  public FheInt80 sub(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_sub_assign(getValue(), other.getValue()));
  }

  public FheInt80 mul(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_mul_assign(getValue(), other.getValue()));
  }


  public FheInt80 and(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt80 or(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt80 xor(FheInt80 other) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt80 other) {
    executeWithErrorHandling(() -> fhe_int80_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt80 scalarAdd(I128 scalar) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int80_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt80 scalarSub(I128 scalar) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int80_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt80 scalarMul(I128 scalar) {
    FheInt80 result = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I128 scalar) {
    executeWithErrorHandling(() -> fhe_int80_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt80 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I128 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int80_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt80 compress() {
    CompressedFheInt80 compressed = new CompressedFheInt80();
    executeWithErrorHandling(() -> fhe_int80_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt80 clone() {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> fhe_int80_clone(getValue(), fheint80.getAddress()));
    return fheint80;
  }
}
