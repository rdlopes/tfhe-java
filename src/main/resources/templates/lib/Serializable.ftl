<#import "snippets.ftl" as s>
<#-- Shared variables -->
<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#macro serialize nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> ${nativeMethod}(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }
</#macro>

<#macro deserialize className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} deserialize(byte[] buffer, ServerKey serverKey) {
    logger.trace("deserialize - buffer: {}, serverKey: {}", buffer, serverKey);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    ${className} deserialized = new ${className}();
    execute(() -> ${nativeMethod}(dynamicBufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()))
    ;
    return deserialized;
  }
</#macro>

<#macro unsafeSerialize nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public byte[] unsafeSerialize() {
    logger.trace("unsafeSerialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> ${nativeMethod}(getValue(), dynamicBuffer.getAddress()));
      return dynamicBuffer.toByteArray();
    }
  }
</#macro>

<#macro unsafeDeserialize className nativeMethod>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} unsafeDeserialize(byte[] buffer) {
    logger.trace("unsafeDeserialize - buffer: {}", buffer);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    ${className} deserialized = new ${className}();
    execute(() -> ${nativeMethod}(dynamicBufferView.getAddress(), deserialized.getAddress()));
    return deserialized;
  }
</#macro>

<#macro testSerializeDeserializeSafely className testValues dataClass isCompressed=false targetClassName="">
  @Test
  void serializesAndDeserializesSafely() {
    ${dataClass.simpleName} originalValue = ${testValues.first()};
    ${className} original = ${className}.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.serialize();
    ${className} deserialized = ${className}.deserialize(serialized, serverKey);
  <#if isCompressed>
    ${targetClassName} deserializedDecompressed = deserialized.decompress();
    ${dataClass.simpleName} decrypted = deserializedDecompressed.decryptWithClientKey(clientKey);
  <#else>
    ${dataClass.simpleName} decrypted = deserialized.decryptWithClientKey(clientKey);
  </#if>

    assertThat(decrypted).isEqualTo(originalValue);
  }
</#macro>

<#macro testSerializeDeserializeUnsafely className testValues dataClass isCompressed=false targetClassName="">
  @Test
  void serializesAndDeserializesUnsafely() {
    ${dataClass.simpleName} originalValue = ${testValues.second()};
    ${className} original = ${className}.encryptWithClientKey(originalValue, clientKey);

    byte[] serialized = original.unsafeSerialize();
    ${className} deserialized = ${className}.unsafeDeserialize(serialized);
  <#if isCompressed>
    ${targetClassName} deserializedDecompressed = deserialized.decompress();
    ${dataClass.simpleName} decrypted = deserializedDecompressed.decryptWithClientKey(clientKey);
  <#else>
    ${dataClass.simpleName} decrypted = deserialized.decryptWithClientKey(clientKey);
  </#if>

    assertThat(decrypted).isEqualTo(originalValue);
  }
</#macro>
