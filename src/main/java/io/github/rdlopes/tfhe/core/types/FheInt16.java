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

public class FheInt16 extends AddressLayoutPointer implements

  Cloneable {

  protected FheInt16() {
    super(FheInt16.class, TfheWrapper::fhe_int16_destroy);
  }

  public static FheInt16
  encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheInt16 fhe = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt16
  encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheInt16 fhe = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_try_encrypt_with_public_key_i16(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt16 encryptTrivial(short clearValue) {
    FheInt16 fhe = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_try_encrypt_trivial_i16(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt16 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt16 fhe = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public short
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_int16_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_int16_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheInt16 and(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt16 or(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt16 xor(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt16 scalarAnd(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt16 scalarOr(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt16 scalarXor(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt16 compress() {
    CompressedFheInt16 compressed = new CompressedFheInt16();
    executeWithErrorHandling(() -> fhe_int16_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt16 clone() {
    FheInt16 cloned = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt16 add(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_add_assign(getValue(), other.getValue()));
  }

  public FheInt16 sub(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_sub_assign(getValue(), other.getValue()));
  }

  public FheInt16 mul(FheInt16 other) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt16 other) {
    executeWithErrorHandling(() -> fhe_int16_mul_assign(getValue(), other.getValue()));
  }

  public FheInt16 scalarAdd(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_add_assign(getValue(), scalar));
  }

  public FheInt16 scalarSub(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt16 scalarMul(short scalar) {
    FheInt16 result = new FheInt16();
    executeWithErrorHandling(() -> fhe_int16_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int16_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int16_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
