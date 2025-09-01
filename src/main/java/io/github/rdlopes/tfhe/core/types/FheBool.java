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

public class FheBool extends AddressLayoutPointer implements Cloneable {

  protected FheBool() {
    super(FheBool.class, TfheWrapper::fhe_bool_destroy);
  }

  public static FheBool encryptWithClientKey(boolean clearValue, ClientKey clientKey) {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), fheBool.getAddress()));
    return fheBool;
  }

  public static FheBool encryptWithPublicKey(boolean clearValue, PublicKey publicKey) {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_try_encrypt_with_public_key_bool(clearValue, publicKey.getValue(), fheBool.getAddress()));
    return fheBool;
  }

  public static FheBool encryptTrivial(boolean clearValue) {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_try_encrypt_trivial_bool(clearValue, fheBool.getAddress()));
    return fheBool;
  }

  public static FheBool deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fheBool.getAddress()));
    return fheBool;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public boolean decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(C_BOOL);
    executeWithErrorHandling(() -> fhe_bool_decrypt(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(C_BOOL, 0);
  }

  public Boolean decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(C_BOOL);
    int result = fhe_bool_try_decrypt_trivial(getValue(), clearValue);
    return result == 0 ? clearValue.get(C_BOOL, 0) : null;
  }

  public FheBool and(FheBool other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign(FheBool other) {
    executeWithErrorHandling(() -> fhe_bool_bitand_assign(getValue(), other.getValue()));
  }

  public FheBool or(FheBool other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign(FheBool other) {
    executeWithErrorHandling(() -> fhe_bool_bitor_assign(getValue(), other.getValue()));
  }

  public FheBool xor(FheBool other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign(FheBool other) {
    executeWithErrorHandling(() -> fhe_bool_bitxor_assign(getValue(), other.getValue()));
  }

  public FheBool not() {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_not(getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarAnd(boolean scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarAndAssign(boolean scalar) {
    executeWithErrorHandling(() -> fhe_bool_scalar_bitand_assign(getValue(), scalar));
  }

  public FheBool scalarOr(boolean scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarOrAssign(boolean scalar) {
    executeWithErrorHandling(() -> fhe_bool_scalar_bitor_assign(getValue(), scalar));
  }

  public FheBool scalarXor(boolean scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }

  public void scalarXorAssign(boolean scalar) {
    executeWithErrorHandling(() -> fhe_bool_scalar_bitxor_assign(getValue(), scalar));
  }

  public FheBool eq(FheBool other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool ne(FheBool other) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public FheBool scalarEq(boolean scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }

  public FheBool scalarNe(boolean scalar) {
    FheBool result = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  public CompressedFheBool compress() {
    CompressedFheBool compressed = new CompressedFheBool();
    executeWithErrorHandling(() -> fhe_bool_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheBool clone() {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> fhe_bool_clone(getValue(), fheBool.getAddress()));
    return fheBool;
  }
}
