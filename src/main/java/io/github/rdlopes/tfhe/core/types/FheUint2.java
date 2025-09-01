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

public class FheUint2 extends AddressLayoutPointer implements Cloneable {

  protected FheUint2() {
    super(FheUint2.class, TfheWrapper::fhe_uint2_destroy);
  }

  public static FheUint2 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheUint2 fheuint2 = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), fheuint2.getAddress()));
    return fheuint2;
  }

  public static FheUint2 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheUint2 fheuint2 = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_try_encrypt_with_public_key_u8(clearValue, publicKey.getValue(), fheuint2.getAddress()));
    return fheuint2;
  }

  public static FheUint2 encryptTrivial(byte clearValue) {
    FheUint2 fheuint2 = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_try_encrypt_trivial_u8(clearValue, fheuint2.getAddress()));
    return fheuint2;
  }

  public static FheUint2 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint2 fheuint2 = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint2.getAddress()));
    return fheuint2;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint2_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_uint2_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_uint2_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheUint2 add(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_add_assign(getValue(), other.getValue()));
  }

  public FheUint2 sub(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_sub_assign(getValue(), other.getValue()));
  }

  public FheUint2 mul(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_mul_assign(getValue(), other.getValue()));
  }


  public FheUint2 and(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint2 or(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint2 xor(FheUint2 other) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint2 other) {
    executeWithErrorHandling(() -> fhe_uint2_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint2 scalarAdd(byte scalar) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint2_scalar_add_assign(getValue(), scalar));
  }

  public FheUint2 scalarSub(byte scalar) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint2_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint2 scalarMul(byte scalar) {
    FheUint2 result = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint2_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint2_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheUint2 compress() {
    CompressedFheUint2 compressed = new CompressedFheUint2();
    executeWithErrorHandling(() -> fhe_uint2_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint2 clone() {
    FheUint2 fheuint2 = new FheUint2();
    executeWithErrorHandling(() -> fhe_uint2_clone(getValue(), fheuint2.getAddress()));
    return fheuint2;
  }
}
