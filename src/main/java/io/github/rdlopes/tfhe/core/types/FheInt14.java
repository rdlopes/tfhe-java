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

public class FheInt14 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt14() {
    super(FheInt14.class, TfheWrapper::fhe_int14_destroy);
  }

  public static FheInt14
  encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheInt14 fhe = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt14
  encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheInt14 fhe = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_try_encrypt_with_public_key_i16(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt14 encryptTrivial(short clearValue) {
    FheInt14 fhe = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_try_encrypt_trivial_i16(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt14 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt14 fhe = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public short
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_int14_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_int14_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheInt14 and(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt14 or(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt14 xor(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt14 scalarAnd(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt14 scalarOr(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt14 scalarXor(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt14 compress() {
    CompressedFheInt14 compressed = new CompressedFheInt14();
    executeWithErrorHandling(() -> fhe_int14_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt14 clone() {
    FheInt14 cloned = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt14 add(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_add_assign(getValue(), other.getValue()));
  }

  public FheInt14 sub(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_sub_assign(getValue(), other.getValue()));
  }

  public FheInt14 mul(FheInt14 other) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt14 other) {
    executeWithErrorHandling(() -> fhe_int14_mul_assign(getValue(), other.getValue()));
  }

  public FheInt14 scalarAdd(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_add_assign(getValue(), scalar));
  }

  public FheInt14 scalarSub(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt14 scalarMul(short scalar) {
    FheInt14 result = new FheInt14();
    executeWithErrorHandling(() -> fhe_int14_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int14_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int14_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
