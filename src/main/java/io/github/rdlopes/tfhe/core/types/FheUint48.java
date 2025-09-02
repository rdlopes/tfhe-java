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

public class FheUint48 extends AddressLayoutPointer implements Cloneable {

  protected FheUint48() {
    super(FheUint48.class, TfheWrapper::fhe_uint48_destroy);
  }

  public static FheUint48
  encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheUint48 fhe = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint48
  encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheUint48 fhe = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_try_encrypt_with_public_key_u64(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint48 encryptTrivial(long clearValue) {
    FheUint48 fhe = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_try_encrypt_trivial_u64(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint48 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint48 fhe = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_uint48_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_uint48_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }

  public FheUint48 and(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint48 or(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint48 xor(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint48 scalarAnd(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint48 scalarOr(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint48 scalarXor(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint48 compress() {
    CompressedFheUint48 compressed = new CompressedFheUint48();
    executeWithErrorHandling(() -> fhe_uint48_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint48 clone() {
    FheUint48 cloned = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint48 add(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_add_assign(getValue(), other.getValue()));
  }

  public FheUint48 sub(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_sub_assign(getValue(), other.getValue()));
  }

  public FheUint48 mul(FheUint48 other) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint48 other) {
    executeWithErrorHandling(() -> fhe_uint48_mul_assign(getValue(), other.getValue()));
  }

  public FheUint48 scalarAdd(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_add_assign(getValue(), scalar));
  }

  public FheUint48 scalarSub(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint48 scalarMul(long scalar) {
    FheUint48 result = new FheUint48();
    executeWithErrorHandling(() -> fhe_uint48_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_uint48_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint48 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint48_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
