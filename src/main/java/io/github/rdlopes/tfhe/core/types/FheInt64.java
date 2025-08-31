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

public class FheInt64 extends AddressLayoutPointer implements Cloneable {

  public FheInt64() {
    super(FheInt64.class, TfheWrapper::fhe_int64_destroy);
  }

  public static FheInt64 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), fheInt64.getAddress()));
    return fheInt64;
  }

  public static FheInt64 encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_try_encrypt_with_public_key_i64(clearValue, publicKey.getValue(), fheInt64.getAddress()));
    return fheInt64;
  }

  public static FheInt64 encryptTrivial(long clearValue) {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_try_encrypt_trivial_i64(clearValue, fheInt64.getAddress()));
    return fheInt64;
  }

  public static FheInt64 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheInt64.getAddress()));
    return fheInt64;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public long decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG_LONG);
    executeWithErrorHandling(() -> fhe_int64_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG_LONG);
    int result = fhe_int64_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG_LONG, 0) : null;
  }

  public FheInt64 add(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_add_assign(getValue(), other.getValue()));
  }

  public FheInt64 sub(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_sub_assign(getValue(), other.getValue()));
  }

  public FheInt64 mul(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_mul_assign(getValue(), other.getValue()));
  }

  public FheInt64 and(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt64 or(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt64 xor(FheInt64 other) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt64 other) {
    executeWithErrorHandling(() -> fhe_int64_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt64 scalarAdd(long scalar) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int64_scalar_add_assign(getValue(), scalar));
  }

  public FheInt64 scalarSub(long scalar) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int64_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt64 scalarMul(long scalar) {
    FheInt64 result = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int64_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int64_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt64 compress() {
    CompressedFheInt64 compressed = new CompressedFheInt64();
    executeWithErrorHandling(() -> fhe_int64_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt64 clone() {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> fhe_int64_clone(getValue(), fheInt64.getAddress()));
    return fheInt64;
  }
}
