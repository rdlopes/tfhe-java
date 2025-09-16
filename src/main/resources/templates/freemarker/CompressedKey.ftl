<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="keyType" type="String" -->
<#-- @ftlvariable name="decompressedKeyType" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Serializable.ftl" as ser>
<#import "lib/Compressible.ftl" as comp>
<#import "lib/Object.ftl" as object>
<#import "lib/Key.ftl" as key>

package ${sourcePackageName};

<@s.sourceImports/>

<#-- @formatter:off -->
public class ${className} extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(${className}. class);
<#-- @formatter:on -->

<#if keyType == "compressed_server_key">
  <@object.constructor className "compressed_server_key_destroy"/>

  <@key.createFromClientKey className "compressed_server_key_new"/>
  <@ser.keyDeserialize className "compressed_server_key_safe_deserialize"/>
  <@ser.serialize className "compressed_server_key_safe_serialize"/>
  <@ser.unsafeSerialize "compressed_server_key_serialize"/>
  <@comp.decompress decompressedKeyType "compressed_server_key_decompress"/>

<#elseif keyType == "compressed_compact_public_key">
  <@object.constructor className "compressed_compact_public_key_destroy"/>

  <@key.createFromClientKey className "compressed_compact_public_key_new"/>
  <@ser.keyDeserialize className "compressed_compact_public_key_safe_deserialize"/>
  <@ser.serialize className "compressed_compact_public_key_safe_serialize"/>
  <@ser.unsafeSerialize "compressed_compact_public_key_serialize"/>
  <@comp.decompress decompressedKeyType "compressed_compact_public_key_decompress"/>
</#if>

}
