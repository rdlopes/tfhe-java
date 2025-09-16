<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->

<#macro compress compressedClassName nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${compressedClassName} compress() {
    ${compressedClassName} compressed = new ${compressedClassName}();
    logger.trace("compress");
    execute(() -> ${nativeMethod}(getValue(), compressed.getAddress()));
    return compressed;
  }
</#macro>

<#macro decompress className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} decompress() {
    logger.trace("decompress");
    ${className} decompressed = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), decompressed.getAddress()));
    return decompressed;
  }
</#macro>

<#macro testCompressDecompress className compressedClassName testValues dataClass>
  @Test
  void compressesAndDecompresses() {
    logger.trace("compressesAndDecompresses");

    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} original = ${className}.encryptWithClientKey(value, keySet.clientKey());
    ${compressedClassName} compressed = original.compress();
    ${className} decompressed = compressed.decompress();
    ${dataClass.simpleName} decrypted = decompressed.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(${testValues.first()});
  }
</#macro>

<#macro testRoundTrip className compressedClassName testValues dataClass>
  @Test
  void roundTrip() {
    logger.trace("roundTrip");

    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} original = ${className}.encryptWithClientKey(value, clientKey);
    ${compressedClassName} compressed = original.compress();
    byte[] buffer = compressed.unsafeSerialize();
    ${compressedClassName} deserialized = ${compressedClassName}.unsafeDeserialize(buffer);
    ${className} decompressed = deserialized.decompress();
    ${dataClass.simpleName} decrypted = decompressed.decryptWithClientKey(clientKey);

    assertThat(decrypted).isEqualTo(${testValues.first()});
  }
</#macro>
