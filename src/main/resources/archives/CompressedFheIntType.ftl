<#-- @ftlvariable name="fheType" type="io.github.rdlopes.tfhe.utils.FheType.CompressedFheIntType" -->

package ${fheType.packageName()};

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;
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
  ${fheType.name()} compressed = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("try_encrypt_with_client_key")}(${fheType.getDataValue("clearValue")}, clientKey.getValue(), compressed.getAddress()))
    ;
    return compressed;
  }

  public static ${fheType.name()} deserialize( byte[] buffer, ServerKey serverKey){
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
  ${fheType.name()} compressed = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("safe_deserialize_conformant")}(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()))
    ;
    return compressed;
  }

  public byte[] serialize () {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> ${fheType.method("safe_serialize")}(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE))
    ;
    return dynamicBuffer.toByteArray();
  }

<#assign decompressedTypeName = fheType.name()?replace("Compressed", "")>
  public ${decompressedTypeName} decompress() {
  ${decompressedTypeName} fhe = new ${decompressedTypeName}();
    executeWithErrorHandling(() -> ${fheType.method("decompress")}(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ${fheType.name()} clone() {
  ${fheType.name()} cloned = new ${fheType.name()}();
    executeWithErrorHandling(() -> ${fheType.method("clone")}(getValue(), cloned.getAddress()));
    return cloned;
  }
}
