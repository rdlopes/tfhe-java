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
<#import "lib/Encryptable.ftl" as encryptable>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Logical.ftl" as logical>
<#import "lib/Comparable.ftl" as comparable>
<#import "lib/Serializable.ftl" as serializable>
package ${testPackageName};

import ${sourcePackageName}.*;
<@s.testImports/>

<#-- @formatter:off -->
class ${testClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testClassName}.class);
<#-- @formatter:on -->
  <@s.testSetup/>

  <@encryptable.testEncryptDecryptWithClientKey className testValues dataClass/>
  <@encryptable.testEncryptDecryptWithPublicKey className testValues dataClass/>
  <@encryptable.testEncryptDecryptTrivial className testValues dataClass/>

  <@compressible.testCompressDecompress className compressedClassName testValues dataClass/>

  <@logical.testLogicalOperations className testValues dataClass/>
  <@logical.testScalarLogicalOperations className testValues dataClass/>

  <@comparable.testComparisonOperations className testValues dataClass/>
  <@comparable.testScalarComparisonOperations className testValues dataClass/>

  <@object.testClone className testValues dataClass/>

  <@serializable.testSerializeDeserializeSafely className testValues dataClass/>
  <@serializable.testSerializeDeserializeUnsafely className testValues dataClass/>

}
