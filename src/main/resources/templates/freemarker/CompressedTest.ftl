<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Serializable.ftl" as ser>
<#import "lib/Compressible.ftl" as comp>

package ${testPackageName};
<@s.testImports/>

<#-- @formatter:off -->
class ${testCompressedClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testCompressedClassName}.class);
<#-- @formatter:on -->
  <@s.testSetup/>

  <@ser.testSerializeDeserializeUnsafely compressedClassName testValues dataClass true className/>

  <@ser.testSerializeDeserializeSafely compressedClassName testValues dataClass true className/>

  <@comp.testCompressDecompress className compressedClassName testValues dataClass/>

}
