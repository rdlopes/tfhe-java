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

public class FheInt2 extends AddressLayoutPointer implements Cloneable {

  protected FheInt2() {
    super(FheInt2.class, TfheWrapper::fhe_int2_destroy);
  }

  public static FheInt2 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheInt2 fheint2 = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), fheint2.getAddress()));
    return fheint2;
  }

  public static FheInt2 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheInt2 fheint2 = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_try_encrypt_with_public_key_i8(clearValue, publicKey.getValue(), fheint2.getAddress()));
    return fheint2;
  }

  public static FheInt2 encryptTrivial(byte clearValue) {
    FheInt2 fheint2 = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_try_encrypt_trivial_i8(clearValue, fheint2.getAddress()));
    return fheint2;
  }

  public static FheInt2 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt2 fheint2 = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint2.getAddress()));
    return fheint2;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int2_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_int2_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_int2_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheInt2 add(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_add_assign(getValue(), other.getValue()));
  }

  public FheInt2 sub(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_sub_assign(getValue(), other.getValue()));
  }

  public FheInt2 mul(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_mul_assign(getValue(), other.getValue()));
  }


  public FheInt2 and(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt2 or(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt2 xor(FheInt2 other) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt2 other) {
    executeWithErrorHandling(() -> fhe_int2_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt2 scalarAdd(byte scalar) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int2_scalar_add_assign(getValue(), scalar));
  }

  public FheInt2 scalarSub(byte scalar) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int2_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt2 scalarMul(byte scalar) {
    FheInt2 result = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int2_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt2 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int2_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt2 compress() {
    CompressedFheInt2 compressed = new CompressedFheInt2();
    executeWithErrorHandling(() -> fhe_int2_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt2 clone() {
    FheInt2 fheint2 = new FheInt2();
    executeWithErrorHandling(() -> fhe_int2_clone(getValue(), fheint2.getAddress()));
    return fheint2;
  }
}
