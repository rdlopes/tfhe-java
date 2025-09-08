<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- Default variables -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#-- Template variables -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Object.ftl" as object>
<#import "lib/Cloneable.ftl" as cloneable>
<#import "lib/Serializable.ftl" as serializable>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Encryptable.ftl" as encryptable>
package ${sourcePackageName};

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.*;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ${compressedClassName} extends NativeValue implements

Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(${compressedClassName}. class);

  <@object.constructor compressedClassName "compressed_fhe_bool_destroy"/>

  <@encryptable.encryptWithClientKey compressedClassName "compressed_fhe_bool_try_encrypt_with_client_key_bool" dataClass.simpleName/>

  <@serializable.deserialize compressedClassName "compressed_fhe_bool_safe_deserialize_conformant"/>

  <@serializable.serialize "compressed_fhe_bool_safe_serialize"/>

  <@serializable.unsafeSerialize "compressed_fhe_bool_serialize"/>

  <@serializable.unsafeDeserialize compressedClassName "compressed_fhe_bool_deserialize"/>

  <@compressible.decompress className "compressed_fhe_bool_decompress"/>

  <@cloneable.clone compressedClassName "compressed_fhe_bool_clone"/>

}
