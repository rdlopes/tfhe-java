package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt160 extends AddressLayoutPointer implements Cloneable {

  protected FheInt160() {
    super(FheInt160.class, TfheWrapper::fhe_int160_destroy);
  }

  public static FheInt160 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    FheInt160 fheint160 = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), fheint160.getAddress()));
    return fheint160;
  }

  public static FheInt160 encryptWithPublicKey(I256 clearValue, PublicKey publicKey) {
    FheInt160 fheint160 = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), fheint160.getAddress()));
    return fheint160;
  }

  public static FheInt160 encryptTrivial(I256 clearValue) {
    FheInt160 fheint160 = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_try_encrypt_trivial_i256(clearValue.getAddress(), fheint160.getAddress()));
    return fheint160;
  }

  public static FheInt160 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt160 fheint160 = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint160.getAddress()));
    return fheint160;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int160_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public I256 decryptWithClientKey(ClientKey clientKey) {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int160_decrypt(getValue(), clientKey.getValue(), clearValue.getAddress()));
    return clearValue;
  }

  public I256 decryptTrivial() {
    I256 clearValue = new I256();
    executeWithErrorHandling(() -> fhe_int160_try_decrypt_trivial(getValue(), clearValue.getAddress()));
    return clearValue;
  }


  public FheInt160 add(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_add_assign(getValue(), other.getValue()));
  }

  public FheInt160 sub(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_sub_assign(getValue(), other.getValue()));
  }

  public FheInt160 mul(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_mul_assign(getValue(), other.getValue()));
  }


  public FheInt160 and(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt160 or(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt160 xor(FheInt160 other) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt160 other) {
    executeWithErrorHandling(() -> fhe_int160_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt160 scalarAdd(I256 scalar) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_scalar_add(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarAddAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int160_scalar_add_assign(getValue(), scalar.getAddress()));
  }

  public FheInt160 scalarSub(I256 scalar) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_scalar_sub(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarSubAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int160_scalar_sub_assign(getValue(), scalar.getAddress()));
  }

  public FheInt160 scalarMul(I256 scalar) {
    FheInt160 result = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_scalar_mul(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public void scalarMulAssign(I256 scalar) {
    executeWithErrorHandling(() -> fhe_int160_scalar_mul_assign(getValue(), scalar.getAddress()));
  }


  public FheBool eq(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt160 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_eq(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarNe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_ne(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_ge(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarGt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_gt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLe(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_le(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }

  public FheBool scalarLt(I256 scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int160_scalar_lt(getValue(), scalar.getAddress(), result.getAddress()));
    return result;
  }


  public CompressedFheInt160 compress() {
    CompressedFheInt160 compressed = new CompressedFheInt160();
    executeWithErrorHandling(() -> fhe_int160_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt160 clone() {
    FheInt160 fheint160 = new FheInt160();
    executeWithErrorHandling(() -> fhe_int160_clone(getValue(), fheint160.getAddress()));
    return fheint160;
  }
}
