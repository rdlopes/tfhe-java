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

<#macro clone className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ${className} clone() {
    logger.trace("clone");
    ${className} clone = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), clone.getAddress()));
    return clone;
  }
</#macro>

<#macro testClone className testValues dataClass isCompressed=false targetClassName="">
  @Test
  void clonesSuccessfully() {
    logger.trace("clonesSuccessfully");

    ${className} original = ${className}.encryptWithClientKey(${testValues.first()}, keySet.clientKey());
    ${className} cloned = original.clone();
  <#if isCompressed>
    ${targetClassName} clonedDecompressed = cloned.decompress();
    ${dataClass.simpleName} clonedDecrypted = clonedDecompressed.decryptWithClientKey(keySet.clientKey());
  <#else>
    ${dataClass.simpleName} clonedDecrypted = cloned.decryptWithClientKey(keySet.clientKey());
  </#if>

    assertThat(clonedDecrypted).isEqualTo(${testValues.first()});
  }
</#macro>
