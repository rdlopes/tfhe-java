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

public class FheInt40 extends AddressLayoutPointer implements Cloneable {

  protected FheInt40() {
    super(FheInt40.class, TfheWrapper::fhe_int40_destroy);
  }

  public static FheInt40
  encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheInt40 fhe = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt40
  encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheInt40 fhe = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_try_encrypt_with_public_key_i64(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt40 encryptTrivial(long clearValue) {
    FheInt40 fhe = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_try_encrypt_trivial_i64(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt40 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt40 fhe = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public long
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_int40_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_int40_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }

  public FheInt40 and(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt40 or(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt40 xor(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt40 scalarAnd(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt40 scalarOr(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt40 scalarXor(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt40 compress() {
    CompressedFheInt40 compressed = new CompressedFheInt40();
    executeWithErrorHandling(() -> fhe_int40_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt40 clone() {
    FheInt40 cloned = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt40 add(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_add_assign(getValue(), other.getValue()));
  }

  public FheInt40 sub(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_sub_assign(getValue(), other.getValue()));
  }

  public FheInt40 mul(FheInt40 other) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt40 other) {
    executeWithErrorHandling(() -> fhe_int40_mul_assign(getValue(), other.getValue()));
  }

  public FheInt40 scalarAdd(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_add_assign(getValue(), scalar));
  }

  public FheInt40 scalarSub(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt40 scalarMul(long scalar) {
    FheInt40 result = new FheInt40();
    executeWithErrorHandling(() -> fhe_int40_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int40_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt40 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int40_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
