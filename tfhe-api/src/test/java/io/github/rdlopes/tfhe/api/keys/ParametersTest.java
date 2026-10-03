package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ParametersTest {

  @Test
  void compactPublicKeyEncryptionParametersAreValid() {
    for (CompactPublicKeyEncryptionParameters param : CompactPublicKeyEncryptionParameters.values()) {
      assertThat(param.address()).isNotNull();
      assertThat(param.address().address()).isNotZero();
    }
  }

  @Test
  void compressionParametersAreValid() {
    for (CompressionParameters param : CompressionParameters.values()) {
      assertThat(param.address()).isNotNull();
      assertThat(param.address().address()).isNotZero();
    }
  }

  @Test
  void customParametersAreValid() {
    for (CustomParameters param : CustomParameters.values()) {
      assertThat(param.address()).isNotNull();
      assertThat(param.address().address()).isNotZero();
    }
  }

  @Test
  void allHeaderShortintParametersAreCoveredInEnums() {
    Set<String> enumNames = Stream.of(
            Arrays.stream(CustomParameters.values()).map(Enum::name),
            Arrays.stream(CompactPublicKeyEncryptionParameters.values()).map(Enum::name),
            Arrays.stream(CompressionParameters.values()).map(Enum::name)
        )
        .flatMap(s -> s)
        .collect(Collectors.toSet());

    Set<String> headerShortintMethods = Arrays.stream(TfheHeader.class.getMethods())
        .filter(m -> Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()))
        .filter(m -> m.getName().startsWith("SHORTINT_") && m.getParameterCount() == 0 && m.getReturnType().equals(MemorySegment.class))
        .map(Method::getName)
        .collect(Collectors.toSet());

    assertThat(headerShortintMethods).isNotEmpty();
    assertThat(headerShortintMethods).allSatisfy(methodName ->
        assertThat(enumNames).as("Header parameter %s should be present in enums", methodName).contains(methodName)
    );
  }
}
