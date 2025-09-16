<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="keyType" type="String" -->
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

  <@object.constructor className "${keyType}_destroy"/>

<#if keyType == "client_key">
  <@ser.keyDeserialize className "client_key_safe_deserialize"/>
  <@ser.serialize className "client_key_safe_serialize"/>
  <@ser.unsafeSerialize "client_key_serialize"/>

<#elseif keyType == "server_key">
  <@ser.keyDeserialize className "server_key_safe_deserialize" false/>
  <@ser.serialize className "server_key_safe_serialize"/>
  <@ser.unsafeSerialize "server_key_serialize"/>
  <@key.setAsKey/>

<#elseif keyType == "public_key">
  <@key.createFromClientKey className "public_key_new"/>
  <@ser.keyDeserialize className "public_key_safe_deserialize"/>
  <@ser.serialize className "public_key_safe_serialize" false/>
  <@ser.unsafeSerialize "public_key_serialize"/>

<#elseif keyType == "compact_public_key">
  <@key.createFromClientKey className "compact_public_key_new"/>
  <@ser.keyDeserialize className "compact_public_key_safe_deserialize"/>
  <@ser.serialize className "compact_public_key_safe_serialize"/>
  <@ser.unsafeSerialize "compact_public_key_serialize"/>

<#elseif keyType == "compressed_server_key">
  <@key.createFromClientKey className "compressed_server_key_new"/>
  <@ser.keyDeserialize className "compressed_server_key_safe_deserialize"/>
  <@ser.serialize className "compressed_server_key_safe_serialize"/>
  <@ser.unsafeSerialize "compressed_server_key_serialize"/>
  <@comp.decompress "ServerKey" "compressed_server_key_decompress"/>

<#elseif keyType == "compressed_compact_public_key">
  <@key.createFromClientKey className "compressed_compact_public_key_new"/>
  <@ser.keyDeserialize className "compressed_compact_public_key_safe_deserialize"/>
  <@ser.serialize className "compressed_compact_public_key_safe_serialize"/>
  <@ser.unsafeSerialize "compressed_compact_public_key_serialize"/>
  <@comp.decompress "CompactPublicKey" "compressed_compact_public_key_decompress"/>
</#if>

}
