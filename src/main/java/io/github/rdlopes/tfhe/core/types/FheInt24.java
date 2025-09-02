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

public class FheInt24 extends AddressLayoutPointer implements Cloneable {

  protected FheInt24() {
    super(FheInt24.class, TfheWrapper::fhe_int24_destroy);
  }

  public static FheInt24
  encryptWithClientKey(int clearValue, ClientKey clientKey) {
    FheInt24 fhe = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_try_encrypt_with_client_key_i32(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt24
  encryptWithPublicKey(int clearValue, PublicKey publicKey) {
    FheInt24 fhe = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_try_encrypt_with_public_key_i32(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt24 encryptTrivial(int clearValue) {
    FheInt24 fhe = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_try_encrypt_trivial_i32(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt24 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt24 fhe = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public int
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    executeWithErrorHandling(() -> fhe_int24_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_INT, 0);
  }

  public Integer decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    int result = fhe_int24_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_INT, 0) : null;
  }

  public FheInt24 and(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt24 or(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt24 xor(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt24 scalarAnd(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt24 scalarOr(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt24 scalarXor(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt24 compress() {
    CompressedFheInt24 compressed = new CompressedFheInt24();
    executeWithErrorHandling(() -> fhe_int24_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt24 clone() {
    FheInt24 cloned = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt24 add(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_add_assign(getValue(), other.getValue()));
  }

  public FheInt24 sub(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_sub_assign(getValue(), other.getValue()));
  }

  public FheInt24 mul(FheInt24 other) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt24 other) {
    executeWithErrorHandling(() -> fhe_int24_mul_assign(getValue(), other.getValue()));
  }

  public FheInt24 scalarAdd(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_add_assign(getValue(), scalar));
  }

  public FheInt24 scalarSub(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt24 scalarMul(int scalar) {
    FheInt24 result = new FheInt24();
    executeWithErrorHandling(() -> fhe_int24_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_int24_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int24_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
