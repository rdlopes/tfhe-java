<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Serializable.ftl" as ser>

package ${testPackageName};
<@s.testImports/>

<#-- @formatter:off -->
class ${testClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testClassName}.class);
<#-- @formatter:on -->
  <@s.testSetup/>

  <@ser.serializeDeserializeTest className "server_key"/>

}
