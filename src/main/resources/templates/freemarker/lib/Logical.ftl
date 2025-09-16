<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->

<#macro and className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} and(${className} other) {
    logger.trace("and - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro andAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void andAssign(${className} other) {
    logger.trace("andAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro or className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} or(${className} other) {
    logger.trace("or - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro orAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void orAssign(${className} other) {
    logger.trace("orAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro xor className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} xor(${className} other) {
    logger.trace("xor - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro xorAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void xorAssign(${className} other) {
    logger.trace("xorAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro not className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} not() {
    logger.trace("not");
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarAnd className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarAnd(boolean scalar) {
    logger.trace("scalarAnd - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarAndAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarAndAssign(boolean scalar) {
    logger.trace("scalarAndAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarOr className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarOr(boolean scalar) {
    logger.trace("scalarOr - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarOrAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarOrAssign(boolean scalar) {
    logger.trace("scalarOrAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarXor className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarXor(boolean scalar) {
    logger.trace("scalarXor - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarXorAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarXorAssign(boolean scalar) {
    logger.trace("scalarXorAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro testLogicalOperations className testValues dataClass>
  @Test
  void performsLogicalOperations() {
    logger.trace("performsLogicalOperations");

    ${dataClass.simpleName} first = ${testValues.first()};
    ${dataClass.simpleName} second = ${testValues.second()};
    ${className} a = ${className}.encryptWithClientKey(first, keySet.clientKey());
    ${className} b = ${className}.encryptWithClientKey(second, keySet.clientKey());

    ${className} andResult = a.and(b);
    assertThat(andResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first && second);

    ${className} orResult = a.or(b);
    assertThat(orResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first || second);

    ${className} xorResult = a.xor(b);
    assertThat(xorResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first ^ second);

    ${className} notResult = a.not();
    assertThat(notResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(!first);
  }
</#macro>

<#macro testScalarLogicalOperations className testValues dataClass>
  @Test
  void performsScalarLogicalOperations() {
    logger.trace("performsScalarLogicalOperations");

    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} a = ${className}.encryptWithClientKey(value, keySet.clientKey());
    ${dataClass.simpleName} scalar = ${testValues.second()};

    ${className} scalarAndResult = a.scalarAnd(scalar);
    assertThat(scalarAndResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value && scalar);

    ${className} scalarOrResult = a.scalarOr(scalar);
    assertThat(scalarOrResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value || scalar);

    ${className} scalarXorResult = a.scalarXor(scalar);
    assertThat(scalarXorResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value ^ scalar);
  }
</#macro>
