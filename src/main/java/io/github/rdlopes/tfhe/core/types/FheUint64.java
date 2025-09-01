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

public class FheUint64 extends AddressLayoutPointer implements Cloneable {

  protected FheUint64() {
    super(FheUint64.class, TfheWrapper::fhe_uint64_destroy);
  }

  public static FheUint64 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), fheUint64.getAddress()));
    return fheUint64;
  }

  public static FheUint64 encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_try_encrypt_with_public_key_u64(clearValue, publicKey.getValue(), fheUint64.getAddress()));
    return fheUint64;
  }

  public static FheUint64 encryptTrivial(long clearValue) {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_try_encrypt_trivial_u64(clearValue, fheUint64.getAddress()));
    return fheUint64;
  }

  public static FheUint64 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheUint64.getAddress()));
    return fheUint64;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG_LONG);
    executeWithErrorHandling(() -> fhe_uint64_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG_LONG);
    int result = fhe_uint64_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG_LONG, 0) : null;
  }

  public FheUint64 add(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_add_assign(getValue(), other.getValue()));
  }

  public FheUint64 sub(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_sub_assign(getValue(), other.getValue()));
  }

  public FheUint64 mul(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_mul_assign(getValue(), other.getValue()));
  }

  public FheUint64 and(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint64 or(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint64 xor(FheUint64 other) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint64 other) {
    executeWithErrorHandling(() -> fhe_uint64_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint64 scalarAdd(long scalar) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint64_scalar_add_assign(getValue(), scalar));
  }

  public FheUint64 scalarSub(long scalar) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint64_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint64 scalarMul(long scalar) {
    FheUint64 result = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint64_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint64 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint64_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint64 compress() {
    CompressedFheUint64 compressed = new CompressedFheUint64();
    executeWithErrorHandling(() -> fhe_uint64_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint64 clone() {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> fhe_uint64_clone(getValue(), fheUint64.getAddress()));
    return fheUint64;
  }
}
