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
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Serializable.ftl" as serializable>
package ${testPackageName};

import ${sourcePackageName}.*;
import io.github.rdlopes.tfhe.api.config.*;
import io.github.rdlopes.tfhe.api.keys.*;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.data.Offset.offset;
import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.*;

class ${testCompressedClassName} {
private static final Logger logger = LoggerFactory.getLogger(${testCompressedClassName}.
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

  <@compressible.testRoundTrip className compressedClassName testValues dataClass/>

  <@cloneable.testClone compressedClassName testValues dataClass true className/>

  <@serializable.testSerializeDeserializeSafely compressedClassName testValues dataClass true className/>

  <@serializable.testSerializeDeserializeUnsafely compressedClassName testValues dataClass true className/>

}
