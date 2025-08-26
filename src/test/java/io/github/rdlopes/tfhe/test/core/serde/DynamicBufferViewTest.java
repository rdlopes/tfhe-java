package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferViewTest {

  @Test
  void createDynamicBufferView() {
    DynamicBufferView bufferView = new DynamicBufferView();
    assertThat(bufferView).isNotNull();
    assertThat(bufferView.address()).isNotNull();
  }

  @Test
  void testLength() {
    DynamicBufferView bufferView = new DynamicBufferView();

    // Test setting and getting length
    bufferView.length(100L);
    assertThat(bufferView.length()).isEqualTo(100L);

    bufferView.length(0L);
    assertThat(bufferView.length()).isEqualTo(0L);

    bufferView.length(Long.MAX_VALUE);
    assertThat(bufferView.length()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void testPointer() {
    DynamicBufferView bufferView = new DynamicBufferView();

    // Initially, dataPointer might be null or a default value
    MemorySegment initialPointer = bufferView.pointer();
    assertThat(initialPointer).isNotNull();

    // Test setting dataPointer to null (which should be allowed)
    bufferView.pointer(MemorySegment.NULL);
    assertThat(bufferView.pointer()).isEqualTo(MemorySegment.NULL);
  }
}
