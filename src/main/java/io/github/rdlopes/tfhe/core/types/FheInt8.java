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

public class FheInt8 extends AddressLayoutPointer implements Cloneable {

  protected FheInt8() {
    super(FheInt8.class, TfheWrapper::fhe_int8_destroy);
  }

  public static FheInt8 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), fheint8.getAddress()));
    return fheint8;
  }

  public static FheInt8 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_try_encrypt_with_public_key_i8(clearValue, publicKey.getValue(), fheint8.getAddress()));
    return fheint8;
  }

  public static FheInt8 encryptTrivial(byte clearValue) {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_try_encrypt_trivial_i8(clearValue, fheint8.getAddress()));
    return fheint8;
  }

  public static FheInt8 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint8.getAddress()));
    return fheint8;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_int8_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_int8_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheInt8 add(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_add_assign(getValue(), other.getValue()));
  }

  public FheInt8 sub(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_sub_assign(getValue(), other.getValue()));
  }

  public FheInt8 mul(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_mul_assign(getValue(), other.getValue()));
  }


  public FheInt8 and(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt8 or(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt8 xor(FheInt8 other) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt8 other) {
    executeWithErrorHandling(() -> fhe_int8_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt8 scalarAdd(byte scalar) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int8_scalar_add_assign(getValue(), scalar));
  }

  public FheInt8 scalarSub(byte scalar) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int8_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt8 scalarMul(byte scalar) {
    FheInt8 result = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int8_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt8 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int8_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt8 compress() {
    CompressedFheInt8 compressed = new CompressedFheInt8();
    executeWithErrorHandling(() -> fhe_int8_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt8 clone() {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> fhe_int8_clone(getValue(), fheint8.getAddress()));
    return fheint8;
  }
}
