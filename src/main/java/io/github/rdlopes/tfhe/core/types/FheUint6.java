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

public class FheUint6 extends AddressLayoutPointer implements Cloneable {

  protected FheUint6() {
    super(FheUint6.class, TfheWrapper::fhe_uint6_destroy);
  }

  public static FheUint6 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), fheuint6.getAddress()));
    return fheuint6;
  }

  public static FheUint6 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_try_encrypt_with_public_key_u8(clearValue, publicKey.getValue(), fheuint6.getAddress()));
    return fheuint6;
  }

  public static FheUint6 encryptTrivial(byte clearValue) {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_try_encrypt_trivial_u8(clearValue, fheuint6.getAddress()));
    return fheuint6;
  }

  public static FheUint6 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint6.getAddress()));
    return fheuint6;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_uint6_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_uint6_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheUint6 add(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_add_assign(getValue(), other.getValue()));
  }

  public FheUint6 sub(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_sub_assign(getValue(), other.getValue()));
  }

  public FheUint6 mul(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_mul_assign(getValue(), other.getValue()));
  }


  public FheUint6 and(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint6 or(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint6 xor(FheUint6 other) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint6 other) {
    executeWithErrorHandling(() -> fhe_uint6_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint6 scalarAdd(byte scalar) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint6_scalar_add_assign(getValue(), scalar));
  }

  public FheUint6 scalarSub(byte scalar) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint6_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint6 scalarMul(byte scalar) {
    FheUint6 result = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_uint6_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint6_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheUint6 compress() {
    CompressedFheUint6 compressed = new CompressedFheUint6();
    executeWithErrorHandling(() -> fhe_uint6_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint6 clone() {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> fhe_uint6_clone(getValue(), fheuint6.getAddress()));
    return fheuint6;
  }
}
