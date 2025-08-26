package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferTest {

  @Test
  void createDynamicBuffer() {
    DynamicBuffer buffer = new DynamicBuffer();
    assertThat(buffer).isNotNull();
    assertThat(buffer.address()).isNotNull();
  }

  @Test
  void testLength() {
    DynamicBuffer buffer = new DynamicBuffer();

    // Test setting and getting length
    buffer.length(100L);
    assertThat(buffer.length()).isEqualTo(100L);

    buffer.length(0L);
    assertThat(buffer.length()).isEqualTo(0L);

    buffer.length(Long.MAX_VALUE);
    assertThat(buffer.length()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void testPointer() {
    DynamicBuffer buffer = new DynamicBuffer();

    // Initially, dataPointer might be null or a default value
    MemorySegment initialPointer = buffer.pointer();
    assertThat(initialPointer).isNotNull();

    // Test setting dataPointer to null (which should be allowed)
    buffer.pointer(MemorySegment.NULL);
    assertThat(buffer.pointer()).isEqualTo(MemorySegment.NULL);
  }

  @Test
  void testDestructor() {
    DynamicBuffer buffer = new DynamicBuffer();

    // Test that destructor field can be accessed
    MemorySegment destructor = buffer.destructor();
    assertThat(destructor).isNotNull();

    // Test setting destructor to null
    buffer.destructor(MemorySegment.NULL);
    assertThat(buffer.destructor()).isEqualTo(MemorySegment.NULL);
  }
}
