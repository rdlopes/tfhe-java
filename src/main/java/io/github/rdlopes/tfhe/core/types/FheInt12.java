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

public class FheInt12 extends AddressLayoutPointer implements Cloneable {

  protected FheInt12() {
    super(FheInt12.class, TfheWrapper::fhe_int12_destroy);
  }

  public static FheInt12 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), fheint12.getAddress()));
    return fheint12;
  }

  public static FheInt12 encryptWithPublicKey(short clearValue, PublicKey publicKey) {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_try_encrypt_with_public_key_i16(clearValue, publicKey.getValue(), fheint12.getAddress()));
    return fheint12;
  }

  public static FheInt12 encryptTrivial(short clearValue) {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_try_encrypt_trivial_i16(clearValue, fheint12.getAddress()));
    return fheint12;
  }

  public static FheInt12 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheint12.getAddress()));
    return fheint12;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_int12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }


  public short decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    executeWithErrorHandling(() -> fhe_int12_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_SHORT, 0);
  }

  public Short decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_SHORT);
    int result = fhe_int12_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_SHORT, 0) : null;
  }


  public FheInt12 add(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_add_assign(getValue(), other.getValue()));
  }

  public FheInt12 sub(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_sub_assign(getValue(), other.getValue()));
  }

  public FheInt12 mul(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_mul_assign(getValue(), other.getValue()));
  }


  public FheInt12 and(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_bitand_assign(getValue(), other.getValue()));
  }

  public FheInt12 or(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_bitor_assign(getValue(), other.getValue()));
  }

  public FheInt12 xor(FheInt12 other) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheInt12 other) {
    executeWithErrorHandling(() -> fhe_int12_bitxor_assign(getValue(), other.getValue()));
  }


  public FheInt12 scalarAdd(short scalar) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_scalar_add(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAddAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int12_scalar_add_assign(getValue(), scalar));
  }

  public FheInt12 scalarSub(short scalar) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_scalar_sub(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarSubAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int12_scalar_sub_assign(getValue(), scalar));
  }

  public FheInt12 scalarMul(short scalar) {
    FheInt12 result = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_scalar_mul(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarMulAssign(short scalar) {
    executeWithErrorHandling(() -> fhe_int12_scalar_mul_assign(getValue(), scalar));
  }


  public FheBool eq(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }


  public FheBool ge(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_ge(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool gt(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_gt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool le(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_le(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool lt(FheInt12 other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_lt(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_ge(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarGt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_gt(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLe(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_le(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarLt(short scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_int12_scalar_lt(getValue(), scalar, result.getAddress()));
    return result;
  }


  public CompressedFheInt12 compress() {
    CompressedFheInt12 compressed = new CompressedFheInt12();
    executeWithErrorHandling(() -> fhe_int12_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt12 clone() {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> fhe_int12_clone(getValue(), fheint12.getAddress()));
    return fheint12;
  }
}
