package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class FheInt32 extends AddressLayoutPointer implements Cloneable {

  protected FheInt32() {
    super(FheInt32.class, TfheWrapper::fhe_int32_destroy);
  }

  public static FheInt32 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    FheInt32 fheInt32 = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_try_encrypt_with_client_key_i32(clearValue, clientKey.getValue(), fheInt32.getAddress()));
    return fheInt32;
  }

  public static FheInt32 encryptWithPublicKey(int clearValue, PublicKey publicKey) {
    FheInt32 fheInt32 = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_try_encrypt_with_public_key_i32(clearValue, publicKey.getValue(), fheInt32.getAddress()));
    return fheInt32;
  }

  public static FheInt32 encryptTrivial(int clearValue) {
    FheInt32 fheInt32 = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_try_encrypt_trivial_i32(clearValue, fheInt32.getAddress()));
    return fheInt32;
  }

  public static FheInt32 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt32 fheInt32 = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheInt32.getAddress()));
    return fheInt32;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public int decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    executeWithErrorHandling(() -> fhe_int32_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_INT, 0);
  }

  public Integer decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    int result = fhe_int32_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_INT, 0) : null;
  }

  public FheInt32 add(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_add_assign(getValue(), other.getValue()));
  }

  public FheInt32 sub(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_sub_assign(getValue(), other.getValue()));
  }

  public FheInt32 mul(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_mul_assign(getValue(), other.getValue()));
  }

  public FheInt32 and(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt32 or(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt32 xor(FheInt32 other) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt32 other) {
    executeWithErrorHandling(() -> fhe_int32_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt32 scalarAdd(int scalar) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int32_scalar_add_assign(getValue(), scalar));
  }

  public FheInt32 scalarSub(int scalar) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int32_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt32 scalarMul(int scalar) {
    FheInt32 result = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int32_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int32_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt32 compress() {
    CompressedFheInt32 compressed = new CompressedFheInt32();
    executeWithErrorHandling(() -> fhe_int32_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt32 clone() {
    FheInt32 fheInt32 = new FheInt32();
    executeWithErrorHandling(() -> fhe_int32_clone(getValue(), fheInt32.getAddress()));
    return fheInt32;
  }
}
