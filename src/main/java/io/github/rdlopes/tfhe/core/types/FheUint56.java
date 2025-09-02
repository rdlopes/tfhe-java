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

public class FheUint56 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint56() {
    super(FheUint56.class, TfheWrapper::fhe_uint56_destroy);
  }

  public static FheUint56
  encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheUint56 fhe = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint56
  encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheUint56 fhe = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_try_encrypt_with_public_key_u64(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint56 encryptTrivial(long clearValue) {
    FheUint56 fhe = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_try_encrypt_trivial_u64(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint56 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint56 fhe = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_uint56_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_uint56_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }

  public FheUint56 and(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint56 or(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint56 xor(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint56 scalarAnd(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint56 scalarOr(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint56 scalarXor(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint56 compress() {
    CompressedFheUint56 compressed = new CompressedFheUint56();
    executeWithErrorHandling(() -> fhe_uint56_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint56 clone() {
    FheUint56 cloned = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint56 add(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_add_assign(getValue(), other.getValue()));
  }

  public FheUint56 sub(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_sub_assign(getValue(), other.getValue()));
  }

  public FheUint56 mul(FheUint56 other) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint56 other) {
    executeWithErrorHandling(() -> fhe_uint56_mul_assign(getValue(), other.getValue()));
  }

  public FheUint56 scalarAdd(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_add_assign(getValue(), scalar));
  }

  public FheUint56 scalarSub(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint56 scalarMul(long scalar) {
    FheUint56 result = new FheUint56();
    executeWithErrorHandling(() -> fhe_uint56_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint56_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint56_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
