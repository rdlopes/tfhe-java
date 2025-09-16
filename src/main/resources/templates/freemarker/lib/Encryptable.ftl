<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#macro encryptWithClientKey className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} encryptWithClientKey(${dataClass} clearValue, ClientKey clientKey) {
    logger.trace("encryptWithClientKey - clearValue: {}, clientKey: {}", clearValue, clientKey);
    ${className} encrypted = new ${className}();
    execute(() -> ${nativeMethod}(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }
</#macro>

<#macro encryptWithPublicKey className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} encryptWithPublicKey(${dataClass} clearValue, PublicKey publicKey) {
    logger.trace("encryptWithPublicKey - clearValue: {}, publicKey: {}", clearValue, publicKey);
    ${className} encrypted = new ${className}();
    execute(() -> ${nativeMethod}(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }
</#macro>

<#macro decryptWithClientKey className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${dataClass} decryptWithClientKey(ClientKey clientKey) {
    logger.trace("decryptWithClientKey - clientKey: {}", clientKey);
    return executeWith${dataClass}(address -> ${nativeMethod}(getValue(), clientKey.getValue(), address));
  }
</#macro>

<#macro encryptTrivial className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} encryptTrivial(${dataClass} clearValue) {
    logger.trace("encryptTrivial - clearValue: {}", clearValue);
    ${className} encrypted = new ${className}();
    execute(() -> ${nativeMethod}(clearValue, encrypted.getAddress()));
    return encrypted;
  }
</#macro>

<#macro decryptTrivial nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${dataClass} decryptTrivial() {
    logger.trace("decryptTrivial");
    return executeWith${dataClass}(address -> ${nativeMethod}(getValue(), address));
  }
</#macro>

<#macro testEncryptDecryptWithClientKey className testValues dataClass>
  @Test
  void encryptsAndDecryptsWithClientKey() {
    logger.trace("encryptsAndDecryptsWithClientKey");

    ${dataClass.simpleName} originalValue = ${testValues.first()};
    ${className} encrypted = ${className}.encryptWithClientKey(originalValue, keySet.clientKey());
    ${dataClass.simpleName} decrypted = encrypted.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(${testValues.first()});
  }
</#macro>

<#macro testEncryptDecryptWithPublicKey className testValues dataClass>
  @Test
  void encryptsAndDecryptsWithPublicKey() {
    logger.trace("encryptsAndDecryptsWithPublicKey");

    PublicKey publicKey = PublicKey.createFromClientKey(keySet.clientKey());
    ${dataClass.simpleName} originalValue = ${testValues.first()};
    ${className} encrypted = ${className}.encryptWithPublicKey(originalValue, publicKey);
    ${dataClass.simpleName} decrypted = encrypted.decryptWithClientKey(keySet.clientKey());

    assertThat(decrypted).isEqualTo(${testValues.first()});
  }
</#macro>

<#macro testEncryptDecryptTrivial className testValues dataClass>
  @Test
  void encryptsAndDecryptsTrivial() {
    logger.trace("encryptsAndDecryptsTrivial");

    ${dataClass.simpleName} originalValue = ${testValues.first()};
    ${className} encrypted = ${className}.encryptTrivial(originalValue);
    ${dataClass.simpleName} decrypted = encrypted.decryptTrivial();

    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(${testValues.first()});
  }
</#macro>
