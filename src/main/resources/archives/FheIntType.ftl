<#-- @ftlvariable name="fheType" type="io.github.rdlopes.tfhe.utils.FheType.FheIntType" -->
package ${fheType.packageName()};

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.types.FheBool;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import java.lang.foreign.MemorySegment;
<#if fheType.needsUtilityClass()>
import java.math.BigInteger;
</#if>

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ${fheType.name()} extends AddressLayoutPointer implements

Cloneable {

  protected ${fheType.name()}() {
    super(${fheType.name()}. class,TfheWrapper::${fheType.nativeName()}_destroy);
  }

  public static ${fheType.name()}
  encryptWithClientKey(${fheType.dataClass().getSimpleName()} clearValue, ClientKey clientKey) {
  ${fheType.name()} fhe = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("try_encrypt_with_client_key")}(${fheType.getDataValue("clearValue")}, clientKey.getValue(), fhe.getAddress()))
    ;
    return fhe;
  }

  public static ${fheType.name()}
  encryptWithPublicKey(${fheType.dataClass().getSimpleName()} clearValue, PublicKey publicKey) {
  ${fheType.name()} fhe = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("try_encrypt_with_public_key")}(${fheType.getDataValue("clearValue")}, publicKey.getValue(), fhe.getAddress()))
    ;
    return fhe;
  }

  public static ${fheType.name()} encryptTrivial(${fheType.dataClass().getSimpleName()} clearValue) {
  ${fheType.name()} fhe = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("try_encrypt_trivial")}(${fheType.getDataValue("clearValue")}, fhe.getAddress()))
    ;
    return fhe;
  }

  public static ${fheType.name()} deserialize( byte[] buffer, ServerKey serverKey){
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
  ${fheType.name()} fhe = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("safe_deserialize_conformant")}(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), fhe.getAddress()))
    ;
    return fhe;
  }

  public byte[] serialize () {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> ${fheType.method("safe_serialize")}(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE))
    ;
    return dynamicBuffer.toByteArray();
  }

  public ${fheType.dataClass().getSimpleName()}
  decryptWithClientKey(ClientKey clientKey) {
    MemorySegment clearValue = ARENA.allocate(${fheType.layout()});
    executeWithErrorHandling(() -> ${fheType.method("decrypt")}(getValue(), clientKey.getValue(), clearValue));
    return clearValue.get(${fheType.layout()}, 0);
  }

  public ${fheType.dataClass().getSimpleName()} decryptTrivial() {
    MemorySegment clearValue = ARENA.allocate(${fheType.layout()});
    executeWithErrorHandling(() -> ${fheType.method("try_decrypt_trivial")}(getValue(), clearValue));
    return clearValue.get(${fheType.layout()}, 0);
  }

  public ${fheType.name()} and(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void andAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitand_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} or(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void orAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitor_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} xor(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void xorAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_bitxor_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} scalarAnd(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitand(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarAndAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitand_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarOr(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitor(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarOrAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitor_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarXor(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitxor(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarXorAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_bitxor_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public FheBool eq (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_eq(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool ne (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_ne(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool scalarEq (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_eq(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public FheBool scalarNe (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_ne(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public FheBool ge (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_ge(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool gt (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_gt(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool le (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_le(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool lt (${fheType.name()} other) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_lt(getValue(), other.getValue(), result.getAddress()));
  return result;
  }

  public FheBool scalarGe (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_ge(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public FheBool scalarGt (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_gt(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public FheBool scalarLe (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_le(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public FheBool scalarLt (${fheType.dataClass().getSimpleName()} scalar) {
    FheBool result = new FheBool();
  executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_lt(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
  return result;
  }

  public ${fheType.name()} add(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_add(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void addAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_add_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} sub(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_sub(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void subAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_sub_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} mul(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_mul(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void mulAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_mul_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} scalarAdd(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_add(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarAddAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_add_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarSub(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_sub(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarSubAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_sub_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarMul(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_mul(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarMulAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_mul_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} div(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_div(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void divAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_div_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} rem(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_rem(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void remAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_rem_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} scalarDiv(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_div(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarDivAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_div_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarRem(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_rem(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarRemAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_rem_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} shl(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_shl(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void shlAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_shl_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} shr(${fheType.name()} other) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_shr(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void shrAssign (${fheType.name()} other) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_shr_assign(getValue(), other.getValue()));
  }

  public ${fheType.name()} scalarShl(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_shl(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarShlAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_shl_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public ${fheType.name()} scalarShr(${fheType.dataClass().getSimpleName()} scalar) {
  ${fheType.name()} result = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_shr(getValue(), ${fheType.getDataValue("scalar")}, result.getAddress()));
    return result;
  }

  public void scalarShrAssign (${fheType.dataClass().getSimpleName()} scalar) {
    executeWithErrorHandling(() -> ${fheType.nativeName()}_scalar_shr_assign(getValue(), ${fheType.getDataValue("scalar")}));
  }

  public Compressed${fheType.name()} compress () {
    Compressed${fheType.name()} compressed = new Compressed${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ${fheType.name()} clone() {
  ${fheType.name()} cloned = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.nativeName()}_clone(getValue(), cloned.getAddress()));
    return cloned;
  }


}
