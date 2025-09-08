<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->

<#macro eq className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} eq(${className} other) {
    logger.trace("eq - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro ne className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} ne(${className} other) {
    logger.trace("ne - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarEq className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarEq(${dataClass} scalar) {
    logger.trace("scalarEq - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarNe className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarNe(${dataClass} scalar) {
    logger.trace("scalarNe - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro testComparisonOperations className testValues dataClass>
  @Test
  void performsComparisonOperations() {
    ${dataClass.simpleName} first = ${testValues.first()};
    ${dataClass.simpleName} second = ${testValues.second()};
    ${className} a = ${className}.encryptWithClientKey(first, clientKey);
    ${className} b = ${className}.encryptWithClientKey(second, clientKey);

    ${className} eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(clientKey)).isEqualTo(first == second);

    ${className} ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(clientKey)).isEqualTo(first != second);
  }
</#macro>

<#macro testScalarComparisonOperations className testValues dataClass>
  @Test
  void performsScalarComparisonOperations() {
    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} a = ${className}.encryptWithClientKey(value, clientKey);
    ${dataClass.simpleName} scalar = ${testValues.second()};

    ${className} scalarEq = a.scalarEq(scalar);
    assertThat(scalarEq.decryptWithClientKey(clientKey)).isEqualTo(value == scalar);

    ${className} scalarNe = a.scalarNe(scalar);
    assertThat(scalarNe.decryptWithClientKey(clientKey)).isEqualTo(value != scalar);
  }
</#macro>
