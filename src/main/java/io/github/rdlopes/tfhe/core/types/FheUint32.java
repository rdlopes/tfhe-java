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

public class FheUint32 extends AddressLayoutPointer implements Cloneable {

  protected FheUint32() {
    super(FheUint32.class, TfheWrapper::fhe_uint32_destroy);
  }

  public static FheUint32 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), fheUint32.getAddress()));
    return fheUint32;
  }

  public static FheUint32 encryptWithPublicKey(int clearValue, PublicKey publicKey) {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_try_encrypt_with_public_key_u32(clearValue, publicKey.getValue(), fheUint32.getAddress()));
    return fheUint32;
  }

  public static FheUint32 encryptTrivial(int clearValue) {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_try_encrypt_trivial_u32(clearValue, fheUint32.getAddress()));
    return fheUint32;
  }

  public static FheUint32 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheUint32.getAddress()));
    return fheUint32;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public int decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    executeWithErrorHandling(() -> fhe_uint32_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_INT, 0);
  }

  public Integer decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    int result = fhe_uint32_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_INT, 0) : null;
  }

  public FheUint32 add(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_add_assign(getValue(), other.getValue()));
  }

  public FheUint32 sub(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_sub_assign(getValue(), other.getValue()));
  }

  public FheUint32 mul(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_mul_assign(getValue(), other.getValue()));
  }

  public FheUint32 and(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint32 or(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint32 xor(FheUint32 other) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint32 other) {
    executeWithErrorHandling(() -> fhe_uint32_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint32 scalarAdd(int scalar) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint32_scalar_add_assign(getValue(), scalar));
  }

  public FheUint32 scalarSub(int scalar) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint32_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint32 scalarMul(int scalar) {
    FheUint32 result = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint32_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ge(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint32 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint32_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint32 compress() {
    CompressedFheUint32 compressed = new CompressedFheUint32();
    executeWithErrorHandling(() -> fhe_uint32_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint32 clone() {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> fhe_uint32_clone(getValue(), fheUint32.getAddress()));
    return fheUint32;
  }
}
