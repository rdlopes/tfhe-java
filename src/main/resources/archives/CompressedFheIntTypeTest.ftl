<#-- @ftlvariable name="fheType" type="io.github.rdlopes.tfhe.utils.FheType.CompressedFheIntType" -->
<#-- @ftlvariable name="allFheTypes" type="java.util.Collection<io.github.rdlopes.tfhe.utils.FheType>" -->
package ${fheType.packageName()?replace("core.types", "test.core.types")};

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.types.${fheType.name()};
<#-- Find corresponding uncompressed type -->
<#assign uncompressedTypeName = fheType.name()?replace("Compressed", "")>
<#list allFheTypes as targetType>
  <#if targetType.exists() && targetType.name() == uncompressedTypeName>
import io.github.rdlopes.tfhe.core.types.${uncompressedTypeName};
    <#break>
  </#if>
</#list>
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
<#attempt>
  <#assign bitSize = fheType.bitSize()>
  <#if (bitSize >= 128)>
import org.junit.jupiter.api.Tag;
  </#if>
  <#recover>
</#attempt>

import static org.assertj.core.api.Assertions.assertThat;

<#attempt>
  <#assign bitSize = fheType.bitSize()>
  <#if (bitSize >= 128)>
@Tag("intensive")
  </#if>
  <#recover>
</#attempt>
class ${fheType.name()}Test {
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
  }

  @Test
  void encryptsSerializesAndDeserializes() {
  ${fheType.name()} compressed = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    byte[] buffer = compressed.serialize();
  ${fheType.name()} deserialized = ${fheType.name()}.deserialize(buffer, serverKey);
  ${uncompressedTypeName} decompressed = deserialized.decompress();
  ${fheType.dataClass().getSimpleName()} decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void decompressesRoundTrip() {
  ${uncompressedTypeName} original = ${uncompressedTypeName}.
    encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} compressed = original.compress();
  ${uncompressedTypeName} decompressed = compressed.decompress();
  ${fheType.dataClass().getSimpleName()} decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void clonesSuccessfully() {
  ${fheType.name()} original = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} cloned = original.clone();
  ${uncompressedTypeName} a = original.decompress();
  ${uncompressedTypeName} b = cloned.decompress();
  ${fheType.dataClass().getSimpleName()} da = a.decryptWithClientKey(clientKey);
  ${fheType.dataClass().getSimpleName()} db = b.decryptWithClientKey(clientKey);
    assertThat(da).isEqualTo(db);
  }
}
