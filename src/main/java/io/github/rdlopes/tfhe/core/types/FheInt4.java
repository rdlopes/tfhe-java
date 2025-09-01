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

public class FheInt4 extends AddressLayoutPointer implements Cloneable {

  protected FheInt4() {
    super(FheInt4.class, TfheWrapper::fhe_int4_destroy);
  }

  public static FheInt4 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    FheInt4 fheint4 = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), fheint4.getAddress()));
    return fheint4;
  }

  public static FheInt4 encryptWithPublicKey(byte clearValue, PublicKey publicKey) {
    FheInt4 fheint4 = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_try_encrypt_with_public_key_i8(clearValue, publicKey.getValue(), fheint4.getAddress()));
    return fheint4;
  }

  public static FheInt4 encryptTrivial(byte clearValue) {
    FheInt4 fheint4 = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_try_encrypt_trivial_i8(clearValue, fheint4.getAddress()));
    return fheint4;
  }

  public static FheInt4 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt4 fheint4 = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint4.getAddress()));
    return fheint4;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public byte decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    executeWithErrorHandling(() -> fhe_int4_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_CHAR, 0);
  }

  public Byte decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_CHAR);
    int result = fhe_int4_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_CHAR, 0) : null;
  }


  public FheInt4 add(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_add_assign(getValue(), other.getValue()));
  }

  public FheInt4 sub(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_sub_assign(getValue(), other.getValue()));
  }

  public FheInt4 mul(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_mul_assign(getValue(), other.getValue()));
  }


  public FheInt4 and(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt4 or(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt4 xor(FheInt4 other) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt4 other) {
    executeWithErrorHandling(() -> fhe_int4_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt4 scalarAdd(byte scalar) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int4_scalar_add_assign(getValue(), scalar));
  }

  public FheInt4 scalarSub(byte scalar) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int4_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt4 scalarMul(byte scalar) {
    FheInt4 result = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(byte scalar) {
    executeWithErrorHandling(() -> fhe_int4_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt4 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(byte scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int4_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt4 compress() {
    CompressedFheInt4 compressed = new CompressedFheInt4();
    executeWithErrorHandling(() -> fhe_int4_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt4 clone() {
    FheInt4 fheint4 = new FheInt4();
    executeWithErrorHandling(() -> fhe_int4_clone(getValue(), fheint4.getAddress()));
    return fheint4;
  }
}
