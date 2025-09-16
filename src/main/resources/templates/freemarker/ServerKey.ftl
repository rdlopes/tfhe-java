<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Serializable.ftl" as ser>
<#import "lib/Object.ftl" as object>
<#import "lib/Key.ftl" as key>

package ${sourcePackageName};

<@s.sourceImports/>

<#-- @formatter:off -->
public class ${className} extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(${className}. class);
<#-- @formatter:on -->

  <@object.constructor className "server_key_destroy"/>

  <@ser.keyDeserialize className "server_key_safe_deserialize" false/>
  <@ser.serialize className "server_key_safe_serialize"/>
  <@ser.unsafeSerialize "server_key_serialize"/>
  <@key.setAsKey/>

}
