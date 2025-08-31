package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;


public abstract class GroupLayoutPointer extends MemoryLayoutPointer<GroupLayout> {
  public GroupLayoutPointer(Class<?> clazz,
                            MemorySegment address,
                            GroupLayout layout,
                            Function<MemorySegment, Integer> destroyer) {
    super(clazz, address, layout, destroyer);
  }

  public GroupLayoutPointer(Class<?> clazz, GroupLayout layout, Function<MemorySegment, Integer> destroyer) {
    this(clazz, ARENA.allocate(layout), layout, destroyer);
  }

  public GroupLayoutPointer(MemorySegment address, GroupLayout layout) {
    this(null, address, layout, null);
  }

  public GroupLayoutPointer(GroupLayout layout) {
    this(ARENA.allocate(layout), layout);
  }
}
