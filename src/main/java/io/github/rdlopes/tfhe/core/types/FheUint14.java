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

public class FheUint14 extends AddressLayoutPointer implements Cloneable {

  protected FheUint14() {
    super(FheUint14.class, TfheWrapper::fhe_uint14_destroy);
  }

  public static FheUint14 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), fheuint14.getAddress()));
    return fheuint14;
  }

  public static FheUint14 encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), fheuint14.getAddress()));
    return fheuint14;
  }

  public static FheUint14 encryptTrivial(short clearValue) {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_try_encrypt_trivial_u16(clearValue, fheuint14.getAddress()));
    return fheuint14;
  }

  public static FheUint14 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheuint14.getAddress()));
    return fheuint14;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_uint14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public short decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_uint14_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_uint14_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }


  public FheUint14 add(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_add_assign(getValue(), other.getValue()));
  }

  public FheUint14 sub(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_sub_assign(getValue(), other.getValue()));
  }

  public FheUint14 mul(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_mul_assign(getValue(), other.getValue()));
  }


  public FheUint14 and(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_bitand_assign(getValue(), other.getValue()));
  }

  public FheUint14 or(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_bitor_assign(getValue(), other.getValue()));
  }

  public FheUint14 xor(FheUint14 other) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheUint14 other) {
    executeWithErrorHandling(() -> fhe_uint14_bitxor_assign(getValue(), other.getValue()));
  }


  public FheUint14 scalarAdd(short scalar) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint14_scalar_add_assign(getValue(), scalar));
  }

  public FheUint14 scalarSub(short scalar) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint14_scalar_sub_assign(getValue(), scalar));
  }

  public FheUint14 scalarMul(short scalar) {
    FheUint14 result = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_uint14_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheUint14 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_uint14_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheUint14 compress() {
    CompressedFheUint14 compressed = new CompressedFheUint14();
    executeWithErrorHandling(() -> fhe_uint14_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint14 clone() {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> fhe_uint14_clone(getValue(), fheuint14.getAddress()));
    return fheuint14;
  }
}
