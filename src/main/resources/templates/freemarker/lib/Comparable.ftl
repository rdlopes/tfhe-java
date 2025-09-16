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

<#macro ge className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} ge(${className} other) {
    logger.trace("ge - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro greaterThan className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} gt(${className} other) {
    logger.trace("gt - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro le className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} le(${className} other) {
    logger.trace("le - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro lessThan className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} lt(${className} other) {
    logger.trace("lt - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarGe className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarGe(${dataClass} scalar) {
    logger.trace("scalarGe - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarGreaterThan className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarGt(${dataClass} scalar) {
    logger.trace("scalarGt - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarLe className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarLe(${dataClass} scalar) {
    logger.trace("scalarLe - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarLessThan className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarLt(${dataClass} scalar) {
    logger.trace("scalarLt - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro testComparisonOperations className testValues dataClass>
  @Test
  void performsComparisonOperations() {
    logger.trace("performsComparisonOperations");

    ${dataClass.simpleName} first = ${testValues.first()};
    ${dataClass.simpleName} second = ${testValues.second()};
    ${className} a = ${className}.encryptWithClientKey(first, keySet.clientKey());
    ${className} b = ${className}.encryptWithClientKey(second, keySet.clientKey());

    ${className} eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(keySet.clientKey())).isEqualTo(first == second);

    ${className} ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(keySet.clientKey())).isEqualTo(first != second);
  }
</#macro>

<#macro testScalarComparisonOperations className testValues dataClass>
  @Test
  void performsScalarComparisonOperations() {
    logger.trace("performsScalarComparisonOperations");

    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} a = ${className}.encryptWithClientKey(value, keySet.clientKey());
    ${dataClass.simpleName} scalar = ${testValues.second()};

    ${className} scalarEq = a.scalarEq(scalar);
    assertThat(scalarEq.decryptWithClientKey(keySet.clientKey())).isEqualTo(value == scalar);

    ${className} scalarNe = a.scalarNe(scalar);
    assertThat(scalarNe.decryptWithClientKey(keySet.clientKey())).isEqualTo(value != scalar);
  }
</#macro>
