<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#macro constructor className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  ${className}(){
  logger.trace("init");
    super(TfheHeader::${nativeMethod});
  }
</#macro>
