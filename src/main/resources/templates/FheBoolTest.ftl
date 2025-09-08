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
<#import "lib/Cloneable.ftl" as cloneable>
<#import "lib/Encryptable.ftl" as encryptable>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Logical.ftl" as logical>
<#import "lib/Comparable.ftl" as comparable>
<#import "lib/Serializable.ftl" as serializable>
package ${testPackageName};

import ${sourcePackageName}.*;
import io.github.rdlopes.tfhe.api.config.*;
import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.api.types.booleans.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class ${testClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testClassName}.
class);

private ClientKey clientKey;
private ServerKey serverKey;

@BeforeEach
void setUp() {
  ConfigBuilder configBuilder = new ConfigBuilder();
  Config config = configBuilder.build();
  KeySet keySet = config.generateKeys();
  clientKey = keySet.clientKey();
  serverKey = keySet.serverKey();
  serverKey.setAsKey();
}

  <@encryptable.testEncryptDecryptWithClientKey className testValues dataClass/>
  <@encryptable.testEncryptDecryptWithPublicKey className testValues dataClass/>
  <@encryptable.testEncryptDecryptTrivial className testValues dataClass/>

  <@compressible.testCompressDecompress className compressedClassName testValues dataClass/>

  <@logical.testLogicalOperations className testValues dataClass/>
  <@logical.testScalarLogicalOperations className testValues dataClass/>

  <@comparable.testComparisonOperations className testValues dataClass/>
  <@comparable.testScalarComparisonOperations className testValues dataClass/>

  <@cloneable.testClone className testValues dataClass/>

  <@serializable.testSerializeDeserializeSafely className testValues dataClass/>
  <@serializable.testSerializeDeserializeUnsafely className testValues dataClass/>

}
