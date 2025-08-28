package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;


public class GroupLayoutPointer extends MemoryLayoutPointer<GroupLayout> {
  public GroupLayoutPointer(MemorySegment address, GroupLayout layout) {
    super(address, layout);
  }

  public GroupLayoutPointer(GroupLayout layout) {
    this(ARENA.allocate(layout), layout);
  }
}
