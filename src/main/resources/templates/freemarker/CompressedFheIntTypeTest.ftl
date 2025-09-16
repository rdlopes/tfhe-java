<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Object.ftl" as object>
<#import "lib/Serializable.ftl" as serializable>
<#import "lib/Compressible.ftl" as compressible>
package ${testPackageName};

import ${sourcePackageName}.*;
<@s.testImports/>

<#-- @formatter:off -->
class ${testClassName} {
  private static final Logger logger = LoggerFactory.getLogger(${testClassName}.class);
<#-- @formatter:on -->
  <@s.testSetup/>

  <@serializable.testSerializeDeserializeSafely className testValues dataClass/>

  <@compressible.testRoundTrip className?replace("Compressed", "") className testValues dataClass/>

  <@object.testClone className testValues dataClass true className?replace("Compressed", "")/>

  }
