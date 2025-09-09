<#import "snippets.ftl" as s>

<#macro setAsKey>
  public void setAsKey() {
    logger.trace("setAsKey");
    execute(() -> set_server_key(getValue()));
  }
</#macro>

<#macro createFromClientKey className nativeFunction>
  public static ${className} createFromClientKey(ClientKey clientKey) {
    logger.trace("createFromClientKey - clientKey: {}", clientKey);
    ${className} key = new ${className}();
    execute(() -> ${nativeFunction}(clientKey.getValue(), key.getAddress()));
    return key;
  }
</#macro>

<#macro createFromClientKeyTest className>
  @Test
  void createsFromClientKey() {
    logger.trace("createsFromClientKey");

    ${className} key = ${className}.createFromClientKey(keySet.clientKey());

    assertThat(key).isNotNull();
    assertThat(key.getAddress()).isNotNull();
  }
</#macro>
