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

public class FheUint16 extends AddressLayoutPointer implements Cloneable {

  public FheUint16() {
    super(FheUint16.class, TfheWrapper::fhe_uint16_destroy);
  }

  public static FheUint16 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), fheUint16.getAddress()));
    return fheUint16;
  }

  public static FheUint16 encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), fheUint16.getAddress()));
    return fheUint16;
  }

  public static FheUint16 encryptTrivial(short clearValue) {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_try_encrypt_trivial_u16(clearValue, fheUint16.getAddress()));
    return fheUint16;
  }

  public static FheUint16 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheUint16.getAddress()));
    return fheUint16;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer.view();
  }

  public short decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_uint16_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_uint16_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }

  public FheUint16 add(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_add_assign(getValue(), other.getValue()));
  }

  public FheUint16 sub(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_sub_assign(getValue(), other.getValue()));
  }

  public FheUint16 mul(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_mul_assign(getValue(), other.getValue()));
  }

  public FheUint16 and(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint16 or(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint16 xor(FheUint16 other) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint16 other) {
    executeWithErrorHandling(() -> fhe_uint16_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint16 scalarAdd(short scalar) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint16_scalar_add_assign(getValue(), scalar));
  }

  public FheUint16 scalarSub(short scalar) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint16_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint16 scalarMul(short scalar) {
    FheUint16 result = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint16_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint16 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint16_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint16 compress() {
    CompressedFheUint16 compressed = new CompressedFheUint16();
    executeWithErrorHandling(() -> fhe_uint16_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint16 clone() {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> fhe_uint16_clone(getValue(), fheUint16.getAddress()));
    return fheUint16;
  }
}
