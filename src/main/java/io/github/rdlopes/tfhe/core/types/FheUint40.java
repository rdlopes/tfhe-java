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

public class FheUint40 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint40() {
    super(FheUint40.class, TfheWrapper::fhe_uint40_destroy);
  }

  public static FheUint40
  encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheUint40 fhe = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint40
  encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheUint40 fhe = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_try_encrypt_with_public_key_u64(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint40 encryptTrivial(long clearValue) {
    FheUint40 fhe = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_try_encrypt_trivial_u64(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint40 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint40 fhe = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_uint40_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_uint40_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }

  public FheUint40 and(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint40 or(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint40 xor(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint40 scalarAnd(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint40 scalarOr(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint40 scalarXor(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint40 compress() {
    CompressedFheUint40 compressed = new CompressedFheUint40();
    executeWithErrorHandling(() -> fhe_uint40_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint40 clone() {
    FheUint40 cloned = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint40 add(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_add_assign(getValue(), other.getValue()));
  }

  public FheUint40 sub(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_sub_assign(getValue(), other.getValue()));
  }

  public FheUint40 mul(FheUint40 other) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint40 other) {
    executeWithErrorHandling(() -> fhe_uint40_mul_assign(getValue(), other.getValue()));
  }

  public FheUint40 scalarAdd(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_add_assign(getValue(), scalar));
  }

  public FheUint40 scalarSub(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint40 scalarMul(long scalar) {
    FheUint40 result = new FheUint40();
    executeWithErrorHandling(() -> fhe_uint40_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint40_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint40_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
