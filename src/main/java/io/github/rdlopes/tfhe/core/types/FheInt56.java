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

public class FheInt56 extends AddressLayoutPointer implements Cloneable {

  protected FheInt56() {
    super(FheInt56.class, TfheWrapper::fhe_int56_destroy);
  }

  public static FheInt56 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    FheInt56 fheint56 = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), fheint56.getAddress()));
    return fheint56;
  }

  public static FheInt56 encryptWithPublicKey(long clearValue, PublicKey publicKey) {
    FheInt56 fheint56 = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_try_encrypt_with_public_key_i64(clearValue, publicKey.getValue(), fheint56.getAddress()));
    return fheint56;
  }

  public static FheInt56 encryptTrivial(long clearValue) {
    FheInt56 fheint56 = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_try_encrypt_trivial_i64(clearValue, fheint56.getAddress()));
    return fheint56;
  }

  public static FheInt56 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt56 fheint56 = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint56.getAddress()));
    return fheint56;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public long decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    executeWithErrorHandling(() -> fhe_int56_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_LONG, 0);
  }

  public Long decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_LONG);
    int result = fhe_int56_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_LONG, 0) : null;
  }


  public FheInt56 add(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_add_assign(getValue(), other.getValue()));
  }

  public FheInt56 sub(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_sub_assign(getValue(), other.getValue()));
  }

  public FheInt56 mul(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_mul_assign(getValue(), other.getValue()));
  }


  public FheInt56 and(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt56 or(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt56 xor(FheInt56 other) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt56 other) {
    executeWithErrorHandling(() -> fhe_int56_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt56 scalarAdd(long scalar) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int56_scalar_add_assign(getValue(), scalar));
  }

  public FheInt56 scalarSub(long scalar) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int56_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt56 scalarMul(long scalar) {
    FheInt56 result = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(long scalar) {
    executeWithErrorHandling(() -> fhe_int56_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt56 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(long scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int56_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt56 compress() {
    CompressedFheInt56 compressed = new CompressedFheInt56();
    executeWithErrorHandling(() -> fhe_int56_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt56 clone() {
    FheInt56 fheint56 = new FheInt56();
    executeWithErrorHandling(() -> fhe_int56_clone(getValue(), fheint56.getAddress()));
    return fheint56;
  }
}
