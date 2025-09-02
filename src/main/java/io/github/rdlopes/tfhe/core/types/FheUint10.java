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

public class FheUint10 extends AddressLayoutPointer implements Cloneable {

  protected FheUint10() {
    super(FheUint10.class, TfheWrapper::fhe_uint10_destroy);
  }

  public static FheUint10
  encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheUint10 fhe = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint10
  encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheUint10 fhe = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint10 encryptTrivial(short clearValue) {
    FheUint10 fhe = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_try_encrypt_trivial_u16(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint10 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint10 fhe = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public short
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_uint10_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_uint10_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheUint10 and(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint10 or(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint10 xor(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint10 scalarAnd(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint10 scalarOr(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint10 scalarXor(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint10 compress() {
    CompressedFheUint10 compressed = new CompressedFheUint10();
    executeWithErrorHandling(() -> fhe_uint10_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint10 clone() {
    FheUint10 cloned = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint10 add(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_add_assign(getValue(), other.getValue()));
  }

  public FheUint10 sub(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_sub_assign(getValue(), other.getValue()));
  }

  public FheUint10 mul(FheUint10 other) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint10 other) {
    executeWithErrorHandling(() -> fhe_uint10_mul_assign(getValue(), other.getValue()));
  }

  public FheUint10 scalarAdd(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_add_assign(getValue(), scalar));
  }

  public FheUint10 scalarSub(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint10 scalarMul(short scalar) {
    FheUint10 result = new FheUint10();
    executeWithErrorHandling(() -> fhe_uint10_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint10_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint10_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
