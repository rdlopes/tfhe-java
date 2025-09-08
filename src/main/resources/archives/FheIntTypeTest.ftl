<#-- @ftlvariable name="fheType" type="io.github.rdlopes.tfhe.utils.FheType.FheIntType" -->
<#-- @ftlvariable name="allFheTypes" type="java.util.Collection<io.github.rdlopes.tfhe.utils.FheType>" -->
package ${fheType.packageName()?replace("core.types", "test.core.types")};

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.types.${fheType.name()};
<#-- Find corresponding compressed type if it exists -->
<#assign compressedTypeName = "Compressed" + fheType.name()>
<#list allFheTypes as targetType>
  <#if targetType.exists() && targetType.name() == compressedTypeName>
import io.github.rdlopes.tfhe.core.types.${compressedTypeName};
    <#break>
  </#if>
</#list>
import io.github.rdlopes.tfhe.core.types.FheBool;
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
  void encryptsAndDecryptsWithClientKey() {
  ${fheType.dataClass().getSimpleName()} originalValue = ${fheType.getPrimaryTestValue()};
  ${fheType.name()} encrypted = ${fheType.name()}.encryptWithClientKey(originalValue, clientKey);
  ${fheType.dataClass().getSimpleName()} decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void encryptsAndDecryptsWithPublicKey() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
  ${fheType.dataClass().getSimpleName()} originalValue = ${fheType.getPrimaryTestValue()};
  ${fheType.name()} encrypted = ${fheType.name()}.encryptWithPublicKey(originalValue, publicKey);
  ${fheType.dataClass().getSimpleName()} decrypted = encrypted.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void encryptsAndDecryptsTrivial() {
  ${fheType.dataClass().getSimpleName()} originalValue = ${fheType.getPrimaryTestValue()};
  ${fheType.name()} encrypted = ${fheType.name()}.encryptTrivial(originalValue);
  ${fheType.dataClass().getSimpleName()} decrypted = encrypted.decryptTrivial();
    assertThat(decrypted).isNotNull();
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void serializesAndDeserializes() {
  ${fheType.name()} original = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    byte[] buffer = original.serialize();
  ${fheType.name()} deserialized = ${fheType.name()}.deserialize(buffer, serverKey);
  ${fheType.dataClass().getSimpleName()} decrypted = deserialized.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

<#-- Generate compression test only if compressed type exists -->
<#list allFheTypes as targetType>
  <#if targetType.exists() && targetType.name() == compressedTypeName>
  @Test
  void compressesAndDecompresses() {
    ${fheType.name()} original = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    ${compressedTypeName} compressed = original.compress();
    ${fheType.name()} decompressed = compressed.decompress();
    ${fheType.dataClass().getSimpleName()} decrypted = decompressed.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }
    <#break>
  </#if>
</#list>

  @Test
  void clonesSuccessfully() {
  ${fheType.name()} original = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} cloned = original.clone();
    FheBool eq = cloned.eq(original);
    boolean decryptedEq = eq.decryptWithClientKey(clientKey);
    assertThat(decryptedEq).isTrue();
  ${fheType.dataClass().getSimpleName()} decrypted = cloned.decryptWithClientKey(clientKey);
    assertThat(decrypted).isEqualTo(${fheType.getPrimaryTestValue()});
  }

  @Test
  void performsArithmeticOperations() {
  ${fheType.name()} a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} b = ${fheType.name()}.encryptWithClientKey(${fheType.getSecondaryTestValue()}, clientKey);

  ${fheType.name()} addResult = a.add(b);
    assertThat(addResult.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedAddResult()});

  ${fheType.name()} subResult = a.sub(b);
    assertThat(subResult.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedSubResult()});

  ${fheType.name()} mulResult = a.mul(b);
    assertThat(mulResult.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedMulResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.addAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedAddResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.subAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedSubResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.mulAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedMulResult()});
  }

  @Test
  void performsBitwiseOperations() {
  ${fheType.name()} a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} b = ${fheType.name()}.encryptWithClientKey(${fheType.getSecondaryTestValue()}, clientKey);

  ${fheType.name()} rAnd = a.and(b);
    assertThat(rAnd.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedAndResult()});

  ${fheType.name()} rOr = a.or(b);
    assertThat(rOr.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedOrResult()});

  ${fheType.name()} rXor = a.xor(b);
    assertThat(rXor.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedXorResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.andAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedAndResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.orAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedOrResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.xorAssign(b);
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedXorResult()});
  }

  @Test
  void performsComparisonOperations() {
  ${fheType.name()} a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
  ${fheType.name()} b = ${fheType.name()}.encryptWithClientKey(${fheType.getSecondaryTestValue()}, clientKey);

    FheBool eq = a.eq(b);
    assertThat(eq.decryptWithClientKey(clientKey)).isEqualTo(false);

    FheBool ne = a.ne(b);
    assertThat(ne.decryptWithClientKey(clientKey)).isEqualTo(true);

    assertThat(a.ge(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.gt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(true);
    assertThat(a.le(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
    assertThat(a.lt(b)
                .decryptWithClientKey(clientKey)).isEqualTo(false);
  }

  @Test
  void performsScalarAddOperations() {
  ${fheType.name()} a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);

  ${fheType.name()} r = a.scalarAdd(${fheType.getTestScalarValue()});
    assertThat(r.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedScalarAddResult()});

    a = ${fheType.name()}.encryptWithClientKey(${fheType.getPrimaryTestValue()}, clientKey);
    a.scalarAddAssign(${fheType.getTestScalarValue()});
    assertThat(a.decryptWithClientKey(clientKey)).isEqualTo(${fheType.getExpectedScalarAddResult()});
  }
}
