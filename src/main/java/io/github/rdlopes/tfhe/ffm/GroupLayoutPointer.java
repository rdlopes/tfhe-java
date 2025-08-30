package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;


public class GroupLayoutPointer extends MemoryLayoutPointer<GroupLayout> {
  public GroupLayoutPointer(MemorySegment address, GroupLayout layout, Consumer<MemorySegment> cleaner) {
    super(address, layout, cleaner);
  }

  public GroupLayoutPointer(MemorySegment address, GroupLayout layout) {
    this(address, layout, null);
  }

  public GroupLayoutPointer(GroupLayout layout, Consumer<MemorySegment> cleaner) {
    this(ARENA.allocate(layout), layout, cleaner);
  }

  public GroupLayoutPointer(GroupLayout layout) {
    this(ARENA.allocate(layout), layout);
  }
}
