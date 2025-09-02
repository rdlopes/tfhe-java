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

public class FheUint24 extends AddressLayoutPointer implements

  Cloneable {

  protected FheUint24() {
    super(FheUint24.class, TfheWrapper::fhe_uint24_destroy);
  }

  public static FheUint24
  encryptWithClientKey(int clearValue, ClientKey clientKey) {
    FheUint24 fhe = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint24
  encryptWithPublicKey(int clearValue, PublicKey publicKey) {
    FheUint24 fhe = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_try_encrypt_with_public_key_u32(clearValue, publicKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public static FheUint24 encryptTrivial(int clearValue) {
    FheUint24 fhe = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_try_encrypt_trivial_u32(clearValue, fhe.getAddress()));
    return fhe;
  }

  public static FheUint24 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint24 fhe = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()));
    return fhe;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public int
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    executeWithErrorHandling(() -> fhe_uint24_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_INT, 0);
  }

  public Integer decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_INT);
    int result = fhe_uint24_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_INT, 0) : null;
  }

  public FheUint24 and(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint24 or(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint24 xor(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_bitxor_assign(getValue(), other.getValue()));
  }

  public FheUint24 scalarAnd(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitand_assign(getValue(), scalar));
  }

  public FheUint24 scalarOr(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitor_assign(getValue(), scalar));
  }

  public FheUint24 scalarXor(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheUint24 compress() {
    CompressedFheUint24 compressed = new CompressedFheUint24();
    executeWithErrorHandling(() -> fhe_uint24_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint24 clone() {
    FheUint24 cloned = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_clone(getValue(), cloned.getAddress()));
    return cloned;
  }

  public FheUint24 add(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_add_assign(getValue(), other.getValue()));
  }

  public FheUint24 sub(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_sub_assign(getValue(), other.getValue()));
  }

  public FheUint24 mul(FheUint24 other) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint24 other) {
    executeWithErrorHandling(() -> fhe_uint24_mul_assign(getValue(), other.getValue()));
  }

  public FheUint24 scalarAdd(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_add_assign(getValue(), scalar));
  }

  public FheUint24 scalarSub(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint24 scalarMul(int scalar) {
    FheUint24 result = new FheUint24();
    executeWithErrorHandling(() -> fhe_uint24_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(int scalar) {
    executeWithErrorHandling(() -> fhe_uint24_scalar_mul_assign(getValue(), scalar));
  }

  public FheBool ge(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint24 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarGe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(int scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint24_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


}
