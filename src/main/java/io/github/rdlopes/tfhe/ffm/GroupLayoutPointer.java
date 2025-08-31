package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;


public abstract class GroupLayoutPointer extends MemoryLayoutPointer<GroupLayout> {
  protected GroupLayoutPointer(Class<?> clazz,
                            MemorySegment address,
                            GroupLayout layout,
                            Function<MemorySegment, Integer> destroyer) {
    super(clazz, address, layout, destroyer);
  }

  protected GroupLayoutPointer(MemorySegment address, GroupLayout layout) {
    this(null, address, layout, null);
  }
}
