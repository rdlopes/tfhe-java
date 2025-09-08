<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#macro javadoc nativeSymbol>
  <#assign definition = (symbolsIndex.definitions()[nativeSymbol])!>
  <#if definition?? || definition?trim != "">
  /**
   * {@snippet lang = "c":
    <#list definition?split("\n") as line>
   * ${line?replace("/", "&#47;")?replace("*", "&#42;")}
    </#list>
   *}
   */
  </#if>
</#macro>
