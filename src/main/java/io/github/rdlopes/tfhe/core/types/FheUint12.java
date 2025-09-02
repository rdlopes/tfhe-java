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

public class FheUint12 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint12() {
    super(FheUint12.class, TfheWrapper::fhe_uint12_destroy);
  }

  public static FheUint12
  encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheUint12 fhe = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint12
  encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheUint12 fhe = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint12 encryptTrivial(short clearValue) {
    FheUint12 fhe = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_try_encrypt_trivial_u16(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint12 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint12 fhe = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public short
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_uint12_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_uint12_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheUint12 and(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint12 or(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint12 xor(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint12 scalarAnd(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint12 scalarOr(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint12 scalarXor(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint12 compress() {
    CompressedFheUint12 compressed = new CompressedFheUint12();
    executeWithErrorHandling(() -> fhe_uint12_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint12 clone() {
    FheUint12 cloned = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint12 add(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_add_assign(getValue(), other.getValue()));
  }

  public FheUint12 sub(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_sub_assign(getValue(), other.getValue()));
  }

  public FheUint12 mul(FheUint12 other) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint12 other) {
    executeWithErrorHandling(() -> fhe_uint12_mul_assign(getValue(), other.getValue()));
  }

  public FheUint12 scalarAdd(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_add_assign(getValue(), scalar));
  }

  public FheUint12 scalarSub(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint12 scalarMul(short scalar) {
    FheUint12 result = new FheUint12();
    executeWithErrorHandling(() -> fhe_uint12_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint12_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint12_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
