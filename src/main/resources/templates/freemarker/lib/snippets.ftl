<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#macro javadoc nativeSymbol>
  <#assign definition = (symbolsIndex.definitions()[nativeSymbol])!>
  <#if definition?? || definition?trim != "">
  /**
   * {@snippet lang = "c":
    <#list definition?split("\n") as line>
   * ${line?replace("/**", "&#47;&#42;&#42;")?replace("*/", "&#42;&#47;")}
    </#list>
   *}
   */
  </#if>
</#macro>

<#macro sourceImports>
import io.github.rdlopes.tfhe.api.keys.*;
</#macro>

<#macro testSetup>
<#-- @formatter:off -->
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    keySet = config.generateKeys();
    keySet.serverKey()
          .set();
  }

  @AfterEach
  void tearDown() {
    keySet.destroy();
  }
<#-- @formatter:on -->
</#macro>

<#macro testImports>
import org.junit.jupiter.api .*;
  import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.rdlopes.tfhe.api.keys .*;

  import static io.github.rdlopes.tfhe.assertions.TfheAssertions .*;
</#macro>

