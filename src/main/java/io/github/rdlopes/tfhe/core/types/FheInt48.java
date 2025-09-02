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

public class FheInt48 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt48() {
    super(FheInt48.class, TfheWrapper::fhe_int48_destroy);
  }

  public static FheInt48
  encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheInt48 fhe = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt48
  encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheInt48 fhe = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_try_encrypt_with_public_key_i64(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt48 encryptTrivial(long clearValue) {
    FheInt48 fhe = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_try_encrypt_trivial_i64(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt48 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt48 fhe = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_int48_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_int48_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }

  public FheInt48 and(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt48 or(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt48 xor(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt48 scalarAnd(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt48 scalarOr(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt48 scalarXor(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt48 compress() {
    CompressedFheInt48 compressed = new CompressedFheInt48();
    executeWithErrorHandling(() -> fhe_int48_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt48 clone() {
    FheInt48 cloned = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt48 add(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_add_assign(getValue(), other.getValue()));
  }

  public FheInt48 sub(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_sub_assign(getValue(), other.getValue()));
  }

  public FheInt48 mul(FheInt48 other) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt48 other) {
    executeWithErrorHandling(() -> fhe_int48_mul_assign(getValue(), other.getValue()));
  }

  public FheInt48 scalarAdd(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_add_assign(getValue(), scalar));
  }

  public FheInt48 scalarSub(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt48 scalarMul(long scalar) {
    FheInt48 result = new FheInt48();
    executeWithErrorHandling(() -> fhe_int48_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int48_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int48_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
