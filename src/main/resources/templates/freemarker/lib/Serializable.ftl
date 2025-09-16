<#import "snippets.ftl" as s>

<#macro serialize className nativeMethod supported=true>
  <@s.javadoc "${nativeMethod}"/>
  public byte[] serialize() {
    logger.trace("serialize");
  <#if supported>
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> ${nativeMethod}(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  <#else>
    throw new UnsupportedOperationException("${className} cannot be serialized");
  </#if>
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

<#macro deserialize className nativeMethod supported=true>
  <@s.javadoc "${nativeMethod}"/>
  public static ${className} deserialize(byte[] buffer, ServerKey serverKey) {
    logger.trace("deserialize - buffer: {}, serverKey: {}", buffer, serverKey);
  <#if supported>
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    ${className} deserialized = new ${className}();
    execute(() -> ${nativeMethod}(dynamicBufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()))
    ;
    return deserialized;
  <#else>
    throw new UnsupportedOperationException("${className} cannot be deserialized");
  </#if>
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

<#macro keyDeserialize className nativeFunction supported=true>
  <#if supported>
  public static ${className} deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    ${className} key = new ${className}();
    execute(() -> ${nativeFunction}(bufferView.getAddress(), BUFFER_MAX_SIZE, key.getAddress()));
    return key;
  }
  <#else>
  public static ${className} deserialize(byte[] ignoredBuffer) {
    logger.trace("deserialize - ignoredBuffer: {}", ignoredBuffer);
    throw new UnsupportedOperationException("${className} cannot be deserialized");
  }
  </#if>
</#macro>

<#macro testSerializeDeserializeSafely className testValues dataClass isCompressed=false targetClassName="">
  @Test
  void serializesAndDeserializesSafely() {
    logger.trace("serializesAndDeserializesSafely");

    ${dataClass.simpleName} originalValue = ${testValues.first()};
    ${className} original = ${className}.encryptWithClientKey(originalValue, keySet.clientKey());

    byte[] serialized = original.serialize();
    ${className} deserialized = ${className}.deserialize(serialized, keySet.serverKey());
  <#if isCompressed>
    ${targetClassName} deserializedDecompressed = deserialized.decompress();
    ${dataClass.simpleName} decrypted = deserializedDecompressed.decryptWithClientKey(keySet.clientKey());
  <#else>
    ${dataClass.simpleName} decrypted = deserialized.decryptWithClientKey(keySet.clientKey());
  </#if>

    assertThat(decrypted).isEqualTo(originalValue);
  }
</#macro>

<#macro testSerializeDeserializeUnsafely className testValues dataClass isCompressed=false targetClassName="">
  @Test
  void serializesAndDeserializesUnsafely() {
    logger.trace("serializesAndDeserializesUnsafely");

    ${dataClass.simpleName} originalValue = ${testValues.second()};
    ${className} original = ${className}.encryptWithClientKey(originalValue, keySet.clientKey());
    byte[] serialized = original.unsafeSerialize();
    ${className} deserialized = ${className}.unsafeDeserialize(serialized);
  <#if isCompressed>
    ${targetClassName} deserializedDecompressed = deserialized.decompress();
    ${dataClass.simpleName} decrypted = deserializedDecompressed.decryptWithClientKey(keySet.clientKey());
  <#else>
    ${dataClass.simpleName} decrypted = deserialized.decryptWithClientKey(keySet.clientKey());
  </#if>

    assertThat(decrypted).isEqualTo(originalValue);
  }
</#macro>

<#macro serializeDeserializeTest className keyType>
  <#if keyType == "client_key">
  @Test
  void serializesAndDeserializes() {
    logger.trace("serializesAndDeserializes");

    byte[] buffer = keySet.clientKey()
                          .serialize();
    ${className} deserialized = ${className}.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();
  }
  <#elseif keyType == "server_key">
  @Test
  void serializesButCannotDeserialize() {
    logger.trace("serializesButCannotDeserialize");

    byte[] buffer = keySet.serverKey()
                          .serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    assertThatThrownBy(() -> ${className}.deserialize(buffer))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("${className} cannot be deserialized");
  }
  <#elseif keyType == "public_key">
  @Test
  void cannotSerialize() {
    logger.trace("cannotSerialize");

    ${className} key = ${className}.createFromClientKey(keySet.clientKey());

    assertThatThrownBy(key::serialize)
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("${className} cannot be serialized");
  }
  <#else>
  @Test
  void serializesAndDeserializes() {
    logger.trace("serializesAndDeserializes");

    ${className} key = ${className}.createFromClientKey(keySet.clientKey());

    byte[] buffer = key.serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    ${className} deserialized = ${className}.deserialize(buffer);

    assertThat(deserialized).isNotNull();
    assertThat(deserialized.getAddress()).isNotNull();
  }
  </#if>
</#macro>
