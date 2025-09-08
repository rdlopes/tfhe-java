<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
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
    ${className} original = ${className}.encryptWithClientKey(${testValues.first()}, clientKey);
    ${className} cloned = original.clone();
  <#if isCompressed>
    ${targetClassName} clonedDecompressed = cloned.decompress();
    ${dataClass.simpleName} clonedDecrypted = clonedDecompressed.decryptWithClientKey(clientKey);
  <#else>
    ${dataClass.simpleName} clonedDecrypted = cloned.decryptWithClientKey(clientKey);
  </#if>

    assertThat(clonedDecrypted).isEqualTo(${testValues.first()});
  }
</#macro>
