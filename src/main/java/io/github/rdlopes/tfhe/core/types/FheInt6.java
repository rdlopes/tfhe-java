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

public class FheInt6 extends AddressLayoutPointer implements Cloneable {

  protected FheInt6() {
    super(FheInt6.class, TfheWrapper::fhe_int6_destroy);
  }

  public static FheInt6 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), fheint6.getAddress()));
    return fheint6;
  }

  public static FheInt6 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_try_encrypt_with_public_key_i8(clearValue, publicKey.getValue(), fheint6.getAddress()));
    return fheint6;
  }

  public static FheInt6 encryptTrivial(byte clearValue) {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_try_encrypt_trivial_i8(clearValue, fheint6.getAddress()));
    return fheint6;
  }

  public static FheInt6 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint6.getAddress()));
    return fheint6;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_int6_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_int6_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheInt6 add(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_add_assign(getValue(), other.getValue()));
  }

  public FheInt6 sub(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_sub_assign(getValue(), other.getValue()));
  }

  public FheInt6 mul(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_mul_assign(getValue(), other.getValue()));
  }


  public FheInt6 and(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt6 or(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt6 xor(FheInt6 other) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt6 other) {
    executeWithErrorHandling(() -> fhe_int6_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt6 scalarAdd(byte scalar) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int6_scalar_add_assign(getValue(), scalar));
  }

  public FheInt6 scalarSub(byte scalar) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int6_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt6 scalarMul(byte scalar) {
    FheInt6 result = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int6_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt6 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int6_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt6 compress() {
    CompressedFheInt6 compressed = new CompressedFheInt6();
    executeWithErrorHandling(() -> fhe_int6_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt6 clone() {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> fhe_int6_clone(getValue(), fheint6.getAddress()));
    return fheint6;
  }
}
