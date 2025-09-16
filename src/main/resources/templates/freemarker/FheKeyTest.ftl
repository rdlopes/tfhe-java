<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="keyType" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Serializable.ftl" as ser>
<#import "lib/Key.ftl" as key>
package ${testPackageName};
<@s.testImports/>

<#-- @formatter:off -->
class ${testClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testClassName}.class);
<#-- @formatter:on -->
  <@s.testSetup/>

  <@ser.serializeDeserializeTest className keyType/>

  <#if keyType == "public_key" || keyType == "compact_public_key" || keyType == "compressed_server_key" || keyType == "compressed_compact_public_key">
    <@key.createFromClientKeyTest className/>
  </#if>
}
