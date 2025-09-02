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

public class FheInt10 extends AddressLayoutPointer implements Cloneable {

  protected FheInt10() {
    super(FheInt10.class, TfheWrapper::fhe_int10_destroy);
  }

  public static FheInt10
  encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheInt10 fhe = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt10
  encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheInt10 fhe = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_try_encrypt_with_public_key_i16(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheInt10 encryptTrivial(short clearValue) {
    FheInt10 fhe = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_try_encrypt_trivial_i16(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheInt10 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt10 fhe = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public short
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_int10_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_int10_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheInt10 and(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt10 or(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt10 xor(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_bitxor_assign(getValue(), other.getValue()));
  }

  public FheInt10 scalarAnd(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_bitand_assign(getValue(), scalar));
  }

  public FheInt10 scalarOr(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_bitor_assign(getValue(), scalar));
  }

  public FheInt10 scalarXor(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheInt10 compress() {
    CompressedFheInt10 compressed = new CompressedFheInt10();
    executeWithErrorHandling(() -> fhe_int10_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt10 clone() {
    FheInt10 cloned = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheInt10 add(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_add_assign(getValue(), other.getValue()));
  }

  public FheInt10 sub(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_sub_assign(getValue(), other.getValue()));
  }

  public FheInt10 mul(FheInt10 other) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt10 other) {
    executeWithErrorHandling(() -> fhe_int10_mul_assign(getValue(), other.getValue()));
  }

  public FheInt10 scalarAdd(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_add_assign(getValue(), scalar));
  }

  public FheInt10 scalarSub(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt10 scalarMul(short scalar) {
    FheInt10 result = new FheInt10();
    executeWithErrorHandling(() -> fhe_int10_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int10_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt10 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int10_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
