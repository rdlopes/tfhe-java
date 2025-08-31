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

public class FheUint8 extends AddressLayoutPointer implements Cloneable {

  public FheUint8() {
    super(FheUint8.class, TfheWrapper::fhe_uint8_destroy);
  }

  public static FheUint8 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), fheUint8.getAddress()));
    return fheUint8;
  }

  public static FheUint8 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_try_encrypt_with_public_key_u8(clearValue, publicKey.getValue(), fheUint8.getAddress()));
    return fheUint8;
  }

  public static FheUint8 encryptTrivial(byte clearValue) {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_try_encrypt_trivial_u8(clearValue, fheUint8.getAddress()));
    return fheUint8;
  }

  public static FheUint8 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheUint8.getAddress()));
    return fheUint8;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_uint8_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_uint8_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }

  public FheUint8 add(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_add_assign(getValue(), other.getValue()));
  }

  public FheUint8 sub(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_sub_assign(getValue(), other.getValue()));
  }

  public FheUint8 mul(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_mul_assign(getValue(), other.getValue()));
  }

  public FheUint8 and(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint8 or(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint8 xor(FheUint8 other) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint8 other) {
    executeWithErrorHandling(() -> fhe_uint8_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint8 scalarAdd(byte scalar) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint8_scalar_add_assign(getValue(), scalar));
  }

  public FheUint8 scalarSub(byte scalar) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint8_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint8 scalarMul(byte scalar) {
    FheUint8 result = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint8_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint8_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint8 compress() {
    CompressedFheUint8 compressed = new CompressedFheUint8();
    executeWithErrorHandling(() -> fhe_uint8_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint8 clone() {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> fhe_uint8_clone(getValue(), fheUint8.getAddress()));
    return fheUint8;
  }
}
