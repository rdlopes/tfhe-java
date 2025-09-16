<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->

<#macro add className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} add(${className} other) {
    logger.trace("add - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro addAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void addAssign(${className} other) {
    logger.trace("addAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro sub className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} sub(${className} other) {
    logger.trace("sub - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro subAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void subAssign(${className} other) {
    logger.trace("subAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro mul className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} mul(${className} other) {
    logger.trace("mul - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro mulAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void mulAssign(${className} other) {
    logger.trace("mulAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro div className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} div(${className} other) {
    logger.trace("div - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro divAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void divAssign(${className} other) {
    logger.trace("divAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro rem className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} rem(${className} other) {
    logger.trace("rem - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro remAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void remAssign(${className} other) {
    logger.trace("remAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro shl className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} shl(${className} other) {
    logger.trace("shl - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro shlAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void shlAssign(${className} other) {
    logger.trace("shlAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro shr className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} shr(${className} other) {
    logger.trace("shr - other: {}", other);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
</#macro>

<#macro shrAssign className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public void shrAssign(${className} other) {
    logger.trace("shrAssign - other: {}", other);
    execute(() -> ${nativeMethod}(getValue(), other.getValue()));
  }
</#macro>

<#macro scalarAdd className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarAdd(${dataClass} scalar) {
    logger.trace("scalarAdd - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarAddAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarAddAssign(${dataClass} scalar) {
    logger.trace("scalarAddAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarSub className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarSub(${dataClass} scalar) {
    logger.trace("scalarSub - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarSubAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarSubAssign(${dataClass} scalar) {
    logger.trace("scalarSubAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarMul className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarMul(${dataClass} scalar) {
    logger.trace("scalarMul - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarMulAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarMulAssign(${dataClass} scalar) {
    logger.trace("scalarMulAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarDiv className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarDiv(${dataClass} scalar) {
    logger.trace("scalarDiv - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarDivAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarDivAssign(${dataClass} scalar) {
    logger.trace("scalarDivAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarRem className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarRem(${dataClass} scalar) {
    logger.trace("scalarRem - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarRemAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarRemAssign(${dataClass} scalar) {
    logger.trace("scalarRemAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarShl className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarShl(${dataClass} scalar) {
    logger.trace("scalarShl - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarShlAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarShlAssign(${dataClass} scalar) {
    logger.trace("scalarShlAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro scalarShr className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public ${className} scalarShr(${dataClass} scalar) {
    logger.trace("scalarShr - scalar: {}", scalar);
    ${className} result = new ${className}();
    execute(() -> ${nativeMethod}(getValue(), scalar, result.getAddress()));
    return result;
  }
</#macro>

<#macro scalarShrAssign className nativeMethod dataClass>
  <@s.javadoc "${nativeMethod}"/>
  public void scalarShrAssign(${dataClass} scalar) {
    logger.trace("scalarShrAssign - scalar: {}", scalar);
    execute(() -> ${nativeMethod}(getValue(), scalar));
  }
</#macro>

<#macro testArithmeticOperations className testValues dataClass>
  @Test
  void performsArithmeticOperations() {
    logger.trace("performsArithmeticOperations");

    ${dataClass.simpleName} first = ${testValues.first()};
    ${dataClass.simpleName} second = ${testValues.second()};
    ${className} a = ${className}.encryptWithClientKey(first, keySet.clientKey());
    ${className} b = ${className}.encryptWithClientKey(second, keySet.clientKey());

    ${className} addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first + second);

    ${className} subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first - second);

    ${className} mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(first * second);
  }
</#macro>

<#macro testScalarArithmeticOperations className testValues dataClass>
  @Test
  void performsScalarArithmeticOperations() {
    logger.trace("performsScalarArithmeticOperations");

    ${dataClass.simpleName} value = ${testValues.first()};
    ${className} a = ${className}.encryptWithClientKey(value, keySet.clientKey());
    ${dataClass.simpleName} scalar = ${testValues.second()};

    ${className} scalarAddResult = a.scalarAdd(scalar);
    assertThat(scalarAddResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value + scalar);

    ${className} scalarSubResult = a.scalarSub(scalar);
    assertThat(scalarSubResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value - scalar);

    ${className} scalarMulResult = a.scalarMul(scalar);
    assertThat(scalarMulResult.decryptWithClientKey(keySet.clientKey())).isEqualTo(value * scalar);
  }
</#macro>
