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

public class FheUint4 extends AddressLayoutPointer implements Cloneable {

  protected FheUint4() {
    super(FheUint4.class, TfheWrapper::fhe_uint4_destroy);
  }

  public static FheUint4
  encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheUint4 fhe = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint4
  encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheUint4 fhe = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_try_encrypt_with_public_key_u8(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint4 encryptTrivial(byte clearValue) {
    FheUint4 fhe = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_try_encrypt_trivial_u8(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint4 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint4 fhe = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public byte
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_uint4_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_uint4_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }

  public FheUint4 and(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint4 or(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint4 xor(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint4 scalarAnd(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint4 scalarOr(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint4 scalarXor(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint4 compress() {
    CompressedFheUint4 compressed = new CompressedFheUint4();
    executeWithErrorHandling(() -> fhe_uint4_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint4 clone() {
    FheUint4 cloned = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint4 add(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_add_assign(getValue(), other.getValue()));
  }

  public FheUint4 sub(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_sub_assign(getValue(), other.getValue()));
  }

  public FheUint4 mul(FheUint4 other) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint4 other) {
    executeWithErrorHandling(() -> fhe_uint4_mul_assign(getValue(), other.getValue()));
  }

  public FheUint4 scalarAdd(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_add_assign(getValue(), scalar));
  }

  public FheUint4 scalarSub(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint4 scalarMul(byte scalar) {
    FheUint4 result = new FheUint4();
    executeWithErrorHandling(() -> fhe_uint4_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint4_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint4_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
