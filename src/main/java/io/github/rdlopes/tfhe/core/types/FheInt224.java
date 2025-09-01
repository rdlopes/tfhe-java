package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt224 extends AddressLayoutPointer implements Cloneable {

  protected FheInt224() {
    super(FheInt224.class, TfheWrapper::fhe_int224_destroy);
  }

  public static FheInt224 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt224 fheint224 = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint224.getAddress()));
    return fheint224;
  }

  public static FheInt224 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt224 fheint224 = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint224.getAddress()));
    return fheint224;
  }

  public static FheInt224 encryptTrivial(I256 clearValue) {
    FheInt224 fheint224 = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_try_encrypt_trivial_i256(clearValue.getAddress(), fheint224.getAddress()));
    return fheint224;
  }

  public static FheInt224 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt224 fheint224 = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint224.getAddress()));
    return fheint224;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int224_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int224_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int224_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt224 add(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_add_assign(getValue(), other.getValue()));
  }

  public FheInt224 sub(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_sub_assign(getValue(), other.getValue()));
  }

  public FheInt224 mul(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_mul_assign(getValue(), other.getValue()));
  }


  public FheInt224 and(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt224 or(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt224 xor(FheInt224 other) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt224 other) {
    executeWithErrorHandling(() -> fhe_int224_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt224 scalarAdd(I256 scalar) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int224_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt224 scalarSub(I256 scalar) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int224_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt224 scalarMul(I256 scalar) {
    FheInt224 result = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int224_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt224 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int224_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt224 compress() {
    CompressedFheInt224 compressed = new CompressedFheInt224();
    executeWithErrorHandling(() -> fhe_int224_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt224 clone() {
    FheInt224 fheint224 = new FheInt224();
    executeWithErrorHandling(() -> fhe_int224_clone(getValue(), fheint224.getAddress()));
    return fheint224;
  }
}
