<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#-- @ftlvariable name="bitSize" type="String" -->
<#-- @ftlvariable name="bitLength" type="String" -->
<#-- @ftlvariable name="compressedNativePrefix" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Object.ftl" as object>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Encryptable.ftl" as encryptable>
<#import "lib/Serializable.ftl" as serializable>
package ${sourcePackageName};

<@s.sourceImports/>

<#-- @formatter:off -->
public class ${compressedClassName} extends NativeValue implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(${compressedClassName}.class);
<#-- @formatter:on -->

<@object.constructor compressedClassName "${compressedNativePrefix}${bitSize}_destroy"/>

<@encryptable.encryptWithClientKey compressedClassName "${compressedNativePrefix}${bitSize}_try_encrypt_with_client_key_i${bitLength}" dataClass.simpleName/>

<@compressible.decompress className?replace("Compressed", "") "${compressedNativePrefix}${bitSize}_decompress"/>

<@serializable.serialize compressedClassName "${compressedNativePrefix}${bitSize}_safe_serialize"/>
<@serializable.deserialize compressedClassName "${compressedNativePrefix}${bitSize}_safe_deserialize_conformant"/>

<@object.clone compressedClassName "${compressedNativePrefix}${bitSize}_clone"/>

}
